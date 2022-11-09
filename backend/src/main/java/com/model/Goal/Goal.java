package com.model.Goal;

public interface Goal {
    public boolean determineWorkout(float currentCal, float targetCal);
    public Goal getBaseGoal();
}