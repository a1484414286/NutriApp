package com.model.Goal;

public class DefaultGoal implements Goal {

    @Override
    public boolean determineWorkout(float currentCal, float targetCal) {
        return currentCal > targetCal;
    }

    @Override
    public Goal getBaseGoal() {
        return new DefaultGoal();
    }

}
