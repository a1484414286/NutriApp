package com.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.model.Food.Meal;
import com.model.Workout.Workout;

public class Statistics {
    private float weight;
    private float targetWeight;
    private float targetCalories;
    private float calories = 0;

    private List<Meal> meals;
    private ArrayList<Workout> workout;


    public Statistics(float weight, float targetWeight, float targetCalories, float calories, List<Meal> meals,
            ArrayList<Workout> workout) {
        this.weight = weight;
        this.targetCalories = targetCalories;
        this.targetWeight = targetWeight;
        this.calories = calories;
        this.meals = meals;
        this.workout = workout;
    }

    public Statistics(float weight, float targetCalories) {
        this.weight = weight;
        this.targetCalories = targetCalories;
        this.meals = new ArrayList<>();
        this.workout = new ArrayList<>();
    }

    public void updateWeight(float Weight) {
        this.weight = Weight;
    }

    public void updateCalories(float calories) {
        this.calories += calories;
    }

    public void updateTargetCalories(float calories) {
        this.calories = calories;
    }


    public void updateWorkout(ArrayList<Workout> workout){
        this.workout = workout;
    }

    public void addAmeal(Meal meal){

        meals.add(meal);
    }

    public float getCalories() {
        return calories;
    }

    public float getTargetCalories() {
        return targetCalories;
    }

    public float getWeight() {
        return weight;
    }

    public float getTargetWeight() {
        return targetWeight;
    }

    public List<Meal> gMeals() {
        return this.meals;
    }

    public String getMeals() {
        String ans = "";
        int counter;

        for (Meal i : meals) {
            counter = 0;
            if (counter == meals.size() - 1) {
                ans = ans + i.getName();
            } else {
                ans = ans + i.getName() + " ,";
                counter++;
            }
        }
        return ans;
    }

    public ArrayList<Workout> getWorkout() {
        return workout;
    }
    
    public void addWorkout(Workout e){
        workout.add(e);
    }

    public HashMap<Workout, String> workoutWithIntensity(){
        HashMap<Workout, String> result = new HashMap<>();
        for(Workout o : workout){
            result.put(o, o.getMinutes() + " minutes");
        }
        return result;
    }
    
    @Override
    public String toString() {
        String result = (
            String.format(
            "Your current history: \n"
        +   "Weight            : %.2f\n"
        +   "Target Calories   : %.2f\n"
        +   "Consumed Calories : %.2f\n"
        +   "Meals             : %s \n"
        +   "Workouts          : %s\n"

        , getWeight(), getTargetCalories(), getCalories(), getMeals(), workoutWithIntensity()));
        return result;
    }
}