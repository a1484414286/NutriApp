package com.model.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.model.Workout.Workout;

public class Challenge{
    private LocalDate startDate;
    private LocalDate endDate;
    private HashMap<String, Integer> challengeWorkout;

    public Challenge(HashMap<String, Integer> challengeWorkout){
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusDays(7);
        this.challengeWorkout = challengeWorkout;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public HashMap<String, Integer> getWeeklyChallenge() {
        return challengeWorkout;
    }

    public void addUser(Sender sender){
        challengeWorkout.put(sender.getName(), 0);
    }

    //add their workout and minutes into the challengeWorkout hashmap
    //where only when weekly challenge starts, this would activate
    //when sender is not found in the hashset, it would catch a null pointer expection
    //and return its name.
    public void addPersonalWorkoutMinutes(Sender sender,Workout o){
        try {
            int prev_mins = challengeWorkout.get(sender.getName());
            int curr_mins = o.getMinutes();
            prev_mins += curr_mins;
            challengeWorkout.put(sender.getName(), prev_mins);
            setChallengeWorkout(challengeWorkout);
            
        } catch (NullPointerException e) {
            System.out.println(sender.getName() + ", currently is not in the team that issued a challenge");
        }
    }

    public void setChallengeWorkout(HashMap<String, Integer> challengeWorkout) {
        this.challengeWorkout = challengeWorkout;
    }

    //used lamba to sort hashmap based on the value(total time)
    public String getRanking() {
        String ans = "";
        List<Map.Entry<String, Integer>> list= new LinkedList<Map.Entry<String, Integer>>(challengeWorkout.entrySet());
        Collections.sort(list,(i1,i2) -> i2.getValue().compareTo(i1.getValue()));
        for (Map.Entry<String, Integer> aa : list) {
            ans += ("User : " + aa.getKey()+ ", Minutes : " + aa.getValue() + '\n');
        }
        return("Weekly ranking : \n"+ans);
    }
}

