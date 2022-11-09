package com.model.Goal;

public abstract class GoalDecorator implements Goal {
    private Goal goal;

    public GoalDecorator(Goal newGoal){
        this.goal = newGoal;
    }

    public boolean determineWorkout(float currentCal, float targetCal){
        return this.goal.determineWorkout(currentCal, targetCal);
    }

    public Goal getBaseGoal(){
        return this.goal.getBaseGoal();
    }
}
