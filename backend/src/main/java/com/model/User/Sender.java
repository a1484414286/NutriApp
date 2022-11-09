package com.model.User;

import java.util.HashMap;
import java.util.Stack;

import com.model.Workout.Workout;

public class Sender implements Colleague{
    private User user;
    private int teamNumber;
    private Notifier notifier;
    private String name;

    public Sender(User user, Notifier notifier) {
        this.notifier = notifier;
        this.user = user;
        this.teamNumber = user.getTeamNumber();
        this.name = user.getName();
    }

    public String getName() {
        return name;
    }
    
    public int getTeamNumber() {
        return teamNumber;
    }

    public Stack<Message> getMessage(){
        return user.getMessage();
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public User getUser() {
        return user;
    }

    public boolean sendInvite(Receiver receiver){
        return notifier.sendInvite(receiver);
    }

    public void logWorkout(Workout workout){
        notifier.logWorkout(workout);
    }

    public boolean issueChallenge(){
        return notifier.issueChallenge();
    }

    public History viewPersonalWorkout(Colleague receiver){
        return notifier.viewPersonalWorkout(receiver);
    }

    public boolean leaveTeam(){
        return notifier.leaveTeam(this);
    }

    public String getTotalRanking(){
        return notifier.getRanking(this);
    }

    public String getWeeklyRanking(int i){
        return notifier.getWeeklyRanking(i,this);
    }

    public boolean createNewTeam(String name){
        return notifier.createNewTeam(name);
    }

    public HashMap<String,History> getTeamHistory(){
        return notifier.getTeamHistory();
    }
    public String readAMessage(int i){
        return notifier.readMessage(i,this);
    }

    public String readMostRecentMessage(){
        return notifier.getMostRecentMessage(this);
    }

    public void viewTeam(){
        notifier.viewTeam(this);
    }

    public String viewRecentWeeklyChallenge(){
        return notifier.getRecentWeeklyRanking();
    }
}
