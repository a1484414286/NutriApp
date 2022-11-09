package com.model.User;


public interface Colleague {

    User getUser();
    int getTeamNumber();
    String getName();
    boolean leaveTeam();
    String getTotalRanking();
    String readAMessage(int i);
    String getWeeklyRanking(int i);
    String readMostRecentMessage();
    void viewTeam();
}
