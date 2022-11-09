package com.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.model.Workout.Workout;

public class Team{
    private String name;
    private HashSet<User> members;
    private HashMap<String, Integer> totalWorkouts;
    private ArrayList<Challenge> challenges;
    private boolean weeklyChallenge;

    public Team(String name){
        this.name = name;
        this.members = new HashSet<>();
        this.totalWorkouts = new HashMap<>();
        this.challenges = new ArrayList<>();
    }

    public HashMap<String, History> getTeamHistory(){
        HashMap<String,History> ans = new HashMap<>();
        for(User i : members){
            ans.put(i.getName(), i.getHistory());
        }
        return ans;
    }

    public void setMembers(HashSet<User> members) {
        this.members = members;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Set<User> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    
    public String viewTeam() {
        String ans = "------- \n Current team : "+ getName()+"\n";
        int count = 0;
        for(User i : members){
            ans += String.format("[Member %d : " + i.getName() + "]\n", (count+=1));
        }
        return ans + "-------";
    }
    public HashMap<String, Integer> getWorkouts() {
        return totalWorkouts;
    }
    public void setWorkouts(HashMap<String, Integer> workouts) {
        this.totalWorkouts = workouts;
    }

    public ArrayList<Challenge> getChallenges() {
        return challenges;
    }
    
    public void setChallenge(ArrayList<Challenge> challenges) {
        this.challenges = challenges;
    }

    //this method would be called when sender is requesting a weekly challenge
    //and would also add a new challenge to the arraylist with current team members, also turn weeklychallenge boolean on
    public void addChallenge(){
        challenges.add(new Challenge(teamMembersImport()));
        setTrueFalse(true);
    }

    //this would check if most recent challenge has ended, if ended, then weeklychallenge boolean would be off
    public boolean checkIfChallengeEnded(){
        if(challenges.size() > 1){
            setTrueFalse(false);
            return true;
        }else{
            return false;
        }
    }

    public void setTotalWorkouts(HashMap<String, Integer> totalWorkouts) {
        this.totalWorkouts = totalWorkouts;
    }
    
    public void setTrueFalse(boolean o){
        this.weeklyChallenge = o;
    }
    
    //this method would import members into weeklychallenge workout
    public HashMap<String, Integer> teamMembersImport() {
        HashMap<String, Integer> weeklyChallenge = new HashMap<>();
        for(User i : members){
            weeklyChallenge.put(i.getName(), 0);
        }
        return weeklyChallenge;
    }

    //this method would import members into overall workout
    public void teamMembersImportTotal(){
        for(User i : members){
            if(!totalWorkouts.containsKey(i.getName())){
                totalWorkouts.put(i.getName(), 0);
            }
        }
        
    }

    //increments the minutes of workout that a user has performed
    public void addPersonalWorkoutMinutes(Colleague colleague,Workout o){
        teamMembersImportTotal();
        User user = colleague.getUser();
        int prev_mins = totalWorkouts.get(user.getName());
        int curr_mins = o.getMinutes();
        prev_mins += curr_mins;
        totalWorkouts.put(user.getName(), prev_mins);
        setWorkouts(totalWorkouts);
    }

    public boolean getWeeklyChallenge(){
        return weeklyChallenge;
    }

    //sorting based on value
    public String getRanking() {
        String ans = "";
        List<Map.Entry<String, Integer>> list= new LinkedList<Map.Entry<String, Integer>>(totalWorkouts.entrySet());
        Collections.sort(list,(i1,i2) -> i2.getValue().compareTo(i1.getValue()));
        for (Map.Entry<String, Integer> aa : list) {
            ans += ("User : " + aa.getKey()+ ", Minutes : " + aa.getValue() + '\n');
        }

        return ("Total ranking : \n"+ans);
    }

    //calls a method in the challenge class and get the index of that challenge, if it exist
    public String getWeeklyRanking(int i){
        return challenges.get(i).getRanking();
    }
    
    public void addMembers(User a) {
        this.members.add(a);
    }
    
    public void removeMembers(User a) {
        this.members.remove(a);
    }

    public String getRecentWeeklyRanking() {
        if(challenges.size() > 0){
            return challenges.get(challenges.size()-1).getRanking();
        }
        else{
            return "It does not exist";
        }
    }

}
