package com.model.Goal;

public class PhysicalFitnessGoal implements Goal {

    @Override
    public boolean determineWorkout(float currentCal, float targetCal) {
        return true;
    }

    @Override
    public Goal getBaseGoal() {
        return new PhysicalFitnessGoal();
    }
    
}
