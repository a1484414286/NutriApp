package com.model.User;
import java.time.LocalDate;
import java.util.Stack;

public class User {
    private Information information;
    private Stack<History> history;
    private Inventory inventory;
    private Stack<Message> message;
    private int teamNumber;
    private boolean online;

    public User(String name, float height, float weight, LocalDate dob) {
        this.information = new Information(name, height, weight, dob);
        this.history = new Stack<History>();
        this.inventory = new Inventory();
        this.message = new Stack<>();
        this.online = true;
        this.teamNumber = -1;
    }
    
    public boolean getOnlineStatus(){
        return online;
    }

    public void setHistory(Stack<History> newHistory){
        this.history = newHistory;
    }

    public int getTeamNumber() {
        return teamNumber;
    }
    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }
    
    public Stack<Message> getMessage() {
        return message;
    }

    public void addMessage(Message msg){
        this.message.push(msg);
    }

    public Information getInformation() {
        return this.information;
    }

    public Stack<History> getAllHistory() {
        return history;
    }

    public Statistics getStatisticsByIndex(int i){
       return history.get(i).getStatistics();
    }

    public History getRecentHistory() {
        return history.peek();
    }

    public void addHistory(History his) {
        history.add(his);
    }

    public History getHistory() {
        if (this.history.empty())
            return null;
        return this.history.peek();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return this.information.getName();
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nAge: %s\nHeight: %s\nWeight: %s", information.getName(), information.getAge(),
                information.getHeight(), information.getWeight());
    }

}