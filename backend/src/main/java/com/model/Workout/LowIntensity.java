package com.model.Workout;

public class LowIntensity extends Workout {

    public LowIntensity(int minutes) {
        super(minutes);
        this.rate = 5.0;
    }

    @Override
    public String toString() {
        return "Low Intensity";
    }
}
