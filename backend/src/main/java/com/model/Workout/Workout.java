package com.model.Workout;

import java.time.LocalDate;

public class Workout {
    private int minutes;
    private LocalDate date;
    protected double rate;

    public Workout(int minutes) {
        this.minutes = minutes;
        this.date = LocalDate.now();
        this.rate = 0;
    }

    public Workout(int minutes, LocalDate date, double rate) {
        this.minutes = minutes;
        this.date = date;
        this.rate = rate;
    }

    public double getCalories() {
        return (rate * minutes);
    }


    public int getMinutes() {
        return minutes;
    }

    public LocalDate getDate() {

        return date;
    }

}
