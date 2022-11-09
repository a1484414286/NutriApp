package com.model.Goal;

import com.model.Workout.Workout;

public interface GoalSelector {
    public void handleTransition();

    public void handleDailyTarget();

    public void handleWeightCalc();

    public Workout recommendExercise();
}
