package com.model.Workout;

public class MediumIntensity extends Workout {

    public MediumIntensity(int minutes) {
        super(minutes);
        this.rate = 7.5;
    }

    @Override
    public String toString() {
        return "Medium workout";
    }

}
