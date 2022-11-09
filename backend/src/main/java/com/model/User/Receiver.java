package com.model.User;

public class Receiver implements Colleague{
    private User user;
    private Notifier notifier;
    private int teamNumber;
    private String name;
    
    public Receiver(User user, Notifier notifier) {
        this.notifier = notifier;
        this.user = user;
        this.teamNumber = user.getTeamNumber();
        this.name = user.getName();
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }
    public User getUser() {
        return user;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public String getName() {
        return name;
    }
    public boolean acceptInvite(int id){
        return notifier.respond(true,id);
        //send back the response to the mediator class
    }
    
    public boolean denyInvite(int id){
        return notifier.respond(false, id);
        //send back the response to the mediator class
    }

    @Override
    public String toString() {
        return getUser().getInformation().getName();
    }

	@Override
	public boolean leaveTeam() {
		return notifier.leaveTeam(this);
	}

	@Override
	public String getTotalRanking() {
		return notifier.getRanking(this);
	}

	@Override
	public String readAMessage(int i) {
		return notifier.readMessage(i,this);
	}

	@Override
	public String getWeeklyRanking(int i) {
		return notifier.getWeeklyRanking(i,this);
	}

	@Override
	public String readMostRecentMessage() {
		return notifier.getMostRecentMessage(this);
	}

	@Override
	public void viewTeam() {
        notifier.viewTeam(this);
	}
}
