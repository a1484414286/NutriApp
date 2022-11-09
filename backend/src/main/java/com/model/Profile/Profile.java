package com.model.Profile;

import java.util.HashMap;
import java.util.List;

import com.model.Food.Ingredient;
import com.model.Goal.Goal;
import com.model.Goal.GoalSelector;
import com.model.User.Inventory;
import com.model.User.Team;
import com.model.User.User;

public class Profile {
    private float dailyWeight;
    private GoalSelector curGoal;
    private Inventory ingredientInventory;
    private Team teamMembers;
    private String commitTitle;

    public Profile(float dailyWeight, GoalSelector curGoal, Inventory ingredientInventory, Team teamMembers, String commitTitle){
        this.dailyWeight = dailyWeight;
        this.curGoal = curGoal;
        this.ingredientInventory = ingredientInventory;
        this.teamMembers = teamMembers;
        this.commitTitle = commitTitle;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredientInventory.addIngredient(ingredient);
    }
    public void addIngredient(Ingredient ingredient, double amount) {
        ingredientInventory.addIngredient(ingredient);
    }

    public void addTeamMember(User user){
        teamMembers.addMembers(user);
    }

    public void removeTeamMember(User user) {
        teamMembers.removeMembers(user);
    }
    
    public void leaveTeam() {
        this.teamMembers = null;
    }

    public void joinTeam(Team theTeam) {
        this.teamMembers = theTeam;
    }

    public void setDailyWeight(float dailyWeight){
        this.dailyWeight = dailyWeight;
    }

    public void setCurGoal(GoalSelector goal){
        this.curGoal = goal;
    }

    public PreModifiedProfile saveProfile(){
        PreModifiedProfile newSave = new PreModifiedProfile(this.dailyWeight,this.curGoal,this.ingredientInventory,this.teamMembers,this.commitTitle);
        return newSave;
    }

    public void restoreProfile(PreModifiedProfile restoreProfile) {
        this.commitTitle = restoreProfile.getCommitTitle();
        this.curGoal = restoreProfile.getCurGoal();
        this.dailyWeight = restoreProfile.getDailyWeight();
        this.ingredientInventory = restoreProfile.getIngredients();
        this.teamMembers = restoreProfile.getTeamMembers();
    }
    
    public void setTitle(String titleString) {
        this.commitTitle = titleString;
    }
}
