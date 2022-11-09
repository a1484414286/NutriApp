package com.model.User;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Stack;

import com.model.Workout.Workout;

public class Notifier implements Notifierimp{
    private Sender sender;
    private Receiver receiver;
    public HashMap<Integer,Team> teams;


    public Notifier(Sender sender, Receiver receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.teams = new HashMap<>();
    }

    public Notifier() {
        this.sender = null;
        this.receiver = null;
        this.teams = new HashMap<>();
    }

    public Receiver getReceiver() {
        return receiver;
    }
    public Sender getSender() {
        return sender;
    }
    public HashMap<Integer, Team> getTeams() {
        return teams;
    }
    
    public void setTeams(HashMap<Integer, Team> teams) {
        this.teams = teams;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }


    //this method would check if sender and receiver's team number are the same,
    //if they are, then print out receiver's history
    @Override
    public History viewPersonalWorkout(Colleague colleague) {
        if(sender.getUser().getTeamNumber() == colleague.getUser().getTeamNumber()){
            return(colleague.getUser().getHistory());
        }
        else{
            System.out.println("you are not in his/her team");
            return new History(new Statistics(0, 0));
        }
    }

    //when the method is called, it would check if the hashmap has a team, if not it would create one
    //then check if previous weekly challenege is over, if not it will send a message, else it would add a new challenge
    @Override
    public boolean issueChallenge() {
        Team currentTeam = teams.get(sender.getTeamNumber());
        int teamSize = currentTeam.getChallenges().size();
        if(teamSize == 0){
            teams.get(sender.getTeamNumber()).addChallenge();
            return true;
        }
        else if(currentTeam.getChallenges().get(teamSize-1).getEndDate() != LocalDate.now() && teamSize > 0){
            System.out.println("Your previous weekly challenge has started, please wait until it ends");
            return false;
        }else{
            teams.get(sender.getTeamNumber()).addChallenge();
            return true;
        }
    }

    //this would check if sender is already on a team, if receiver is on a team, and if they're both on a same team
    @Override
    public boolean sendInvite(Receiver o) {
        setReceiver(o);
        if(o.getTeamNumber() == -1){
            System.out.println(" You are not in a team, try to create a team first");
            return false;
        }
        else if(o.getTeamNumber() != -1){
            System.out.println(receiver.getName() + " is already on a team");
            return false;
        }
        else if(sender.getTeamNumber() == o.getTeamNumber()){
            System.out.println(receiver.getName() + " is already on your team");
            return false;
        }
        else{
            Notify(o.toString() + " has send you an invite, accept or deny?");
            return true;
        }
    }

    //this would be used by the receiver, to respond to the invite that sender sent
    @Override
    public boolean respond(boolean receiverRespond, int id) {
        if(receiverRespond){
            System.out.println(receiver.getName() + " has accepted your offer");
            //this would update both user and sender's team number
            //now it's set that it can bascially accept any team's invitation
            assignTeamNumberReceiver(id);
            receiver.setTeamNumber(id);
            addMember(receiver);
            return true;
        }
        else{
            System.out.println(receiver.getName() + " has denied your offer");
            return false;
        }
    }

    //this would check if sender is in a team or not, then remove the object from the hashmap
    @Override
    public boolean leaveTeam(Colleague o) {
        if(o.getTeamNumber() != -1){
            Team aTeam = teams.get(o.getTeamNumber());
            Notify(o.getName() + " has left the team");
            aTeam.getMembers().remove(o.getUser());
            System.out.println(o.getName() + " left the team"); 
            return true;
        }
        else{
            System.out.println(" You're not on a team");
            return false;
        }
    }

    //there would be two different workout, a weekly challenge workout and a total workout ranking
    @Override
    public void logWorkout(Workout workout) {
        sender.getUser().getStatisticsByIndex(0).addWorkout(workout); 
        Team senderTeam = teams.get(sender.getTeamNumber());
        senderTeam.checkIfChallengeEnded();
        if(senderTeam.getWeeklyChallenge()){
            senderTeam.getChallenges().get(senderTeam.getChallenges().size()-1).addPersonalWorkoutMinutes(sender, workout);
            senderTeam.addPersonalWorkoutMinutes(sender, workout);
            Notify(sender.getName() + " has logged an workout");
        }
        else{
            senderTeam.addPersonalWorkoutMinutes(sender, workout);
            Notify(sender.getName() + " has logged an workout");
        }
    }

    @Override
    public void Notify(String msg) {
        Team i = teams.get(sender.getTeamNumber());
        for(User j : i.getMembers()){
            j.addMessage(new Message(msg));
        }
    }

    //just a method to see whos on the team(for testing purposes), has nothing to do with the implementation.
    @Override
    public void viewTeam(Colleague o){
        if(o.getTeamNumber() == -1){
            System.out.println("You do not have a team");
        }
        else{
            System.out.println(teams.get(o.getTeamNumber()).viewTeam());
        }
    }

    @Override
    public String getMostRecentMessage(Colleague o){
        int count = 0;
        Stack<Message> notification = o.getUser().getMessage();
        Message returnMessage = new Message("");
        for(Message msg : notification){
            if(!msg.getRead()){
                msg.setRead(true);
                returnMessage = msg;
                return "Notification : " + returnMessage;
            }
            else if(count == notification.size()-1){
                return ("All notifications has been read");
            }
            count ++;
        }
        return "Notification : "+returnMessage;
    }

    @Override
    public String readMessage(int i,Colleague o){
        if(o.getUser().getMessage().size() != 0){
            o.getUser().getMessage().get(i).setRead(true);
            return ("Notification : " + o.getUser().getMessage().get(i) + '\n');
        }
        else if(o.getUser().getMessage().size()==0){
            return ("Currently, there is no notification");
        }
        else{
            return ("The index you selected has no message."); 
        }
    }
    
    @Override
    public boolean createNewTeam(String name) {
        //when this gets called, it creates a new team, the sender will be the first member of the team, and also assign a team number for him
        if(sender.getTeamNumber() == -1){
            Team newTeam = new Team(name);
            int teamNum = teams.size()+1;
            newTeam.addMembers(sender.getUser());
            teams.put(teamNum, newTeam);
            assignTeamNumberSender(teamNum);
            sender.setTeamNumber(teamNum);
            return true;
        }
        else{
            System.out.println("------");
            System.out.println("You are already in a team, cannot create a new team");
            System.out.println("------");
            return false;
        }
    }
    
    @Override
    public void addMember(Receiver receiver) {
        //this would be adding a receiver to sender's team, if the receiver accepts the invite, then they will be add to the team based on sender's team
        Team a = teams.get(sender.getTeamNumber());
        a.addMembers(receiver.getUser());
        assignTeamNumberReceiver(sender.getTeamNumber());
        teams.put(sender.getTeamNumber(), a);
    }

    @Override
    public void assignTeamNumberReceiver(int i) {
        this.receiver.getUser().setTeamNumber(i);
    }

    @Override
    public void assignTeamNumberSender(int i) {
        this.sender.getUser().setTeamNumber(i);
    }
    
    @Override
    public HashMap<String, History> getTeamHistory(){
        if(sender.getTeamNumber() != -1){
            return teams.get(sender.getTeamNumber()).getTeamHistory();
        }else{
            return new HashMap<String,History>();
        }
    }

    @Override
    public String getRanking(Colleague o) {
        return teams.get(o.getTeamNumber()).getRanking(); 
    }
    
    @Override
    public String getWeeklyRanking(int i, Colleague o){
        return (teams.get(o.getTeamNumber()).getWeeklyRanking(i));
    }
    @Override
    public String getRecentWeeklyRanking(){
        return (teams.get(sender.getTeamNumber()).getRecentWeeklyRanking());
    }
}
