package com.model.User;

public class Message {
    private String message;
    private boolean read;

    public Message(String msg){
        this.message = msg;
        this.read = false;
    }

    public String getMessage() {
        return message;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean getRead(){
        return read;
    }

    @Override
    public String toString() {
        return message + ". Read Status = " + read;
    }


}
