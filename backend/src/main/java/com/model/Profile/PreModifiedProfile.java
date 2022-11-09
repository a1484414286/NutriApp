package com.model.Profile;

import java.util.HashMap;
import java.util.List;

import com.model.Goal.Goal;
import com.model.Goal.GoalSelector;
import com.model.User.Inventory;
import com.model.User.Team;
import com.model.User.User;


public class PreModifiedProfile extends Profile {
    private float dailyWeight;
    private GoalSelector curGoal;
    private Inventory ingredients;
    private Team teamMembers;
    private String commitTitle;

    public PreModifiedProfile(float dailyWeight, GoalSelector curGoal, Inventory ingredients, Team teamMembers, String commitTitle){
        super(dailyWeight, curGoal, ingredients, teamMembers, commitTitle);
    }

    public String getCommitTitle() {
        return commitTitle;
    }public GoalSelector getCurGoal() {
        return curGoal;
    }public float getDailyWeight() {
        return dailyWeight;
    }public Inventory getIngredients() {
        return ingredients;
    }public Team getTeamMembers() {
        return teamMembers;
    }
    
}
