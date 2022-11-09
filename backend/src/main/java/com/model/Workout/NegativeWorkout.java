package com.model.Workout;

public class NegativeWorkout extends Workout {

    int calories;

    public NegativeWorkout(int calories) {
        super(0);
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "None";
    }

}
