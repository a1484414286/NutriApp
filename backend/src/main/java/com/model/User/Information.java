package com.model.User;

import java.time.LocalDate;
import java.time.Period;

import com.model.Goal.GoalSelector;

public class Information {
    private String name;
    private LocalDate dob;
    private float height;
    private float weight;
    private GoalSelector goal;

    public Information(String name, float height, float weight, LocalDate dob) {
        this.name = name;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
    }

    public void updateWeight(float newWeight){
        this.weight = newWeight;
        this.goal.handleTransition();
    }

    public GoalSelector getGoal() {
        return goal;
    }

    public void updateGoal(GoalSelector goal) {
        this.goal = goal;
    }

    public String getName() {
        return this.name;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public LocalDate getDob() {
        return dob;
    }

    public int getAge() {
        LocalDate curr = LocalDate.now();
        return Period.between(dob, curr).getYears();
    }
}
