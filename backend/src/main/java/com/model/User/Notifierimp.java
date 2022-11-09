package com.model.User;
import java.util.HashMap;

import com.model.Workout.Workout;

public interface Notifierimp {
    boolean leaveTeam(Colleague o);
    boolean issueChallenge();
    void logWorkout(Workout workout);
    String getRanking(Colleague o);
    boolean sendInvite(Receiver receiver);
    boolean respond(boolean receiverRespond, int senderTeamID);
    void Notify(String msg);
    boolean createNewTeam(String name);
    History viewPersonalWorkout(Colleague receiver);
    void addMember(Receiver receiver);
    void assignTeamNumberReceiver(int i);
    void assignTeamNumberSender(int i);
    void viewTeam(Colleague o);
    String getMostRecentMessage(Colleague o);
    String readMessage(int i, Colleague o);
    String getWeeklyRanking(int i, Colleague o);
    HashMap<String, History> getTeamHistory();
    String getRecentWeeklyRanking();
}
