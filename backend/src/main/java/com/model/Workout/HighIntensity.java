package com.model.Workout;

public class HighIntensity extends Workout {

    public HighIntensity(int minutes) {
        super(minutes);
        this.rate = 10;
    }

    @Override
    public String toString() {
        return "High Intensity";
    }
}
