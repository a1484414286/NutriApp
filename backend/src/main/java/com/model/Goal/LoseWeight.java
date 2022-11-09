package com.model.Goal;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import com.model.User.*;
import com.model.Workout.*;

public class LoseWeight extends GoalDecorator implements GoalSelector {
    private Statistics stats;
    private Information info;
    private User user;

    private Random rand = new Random();

    public LoseWeight(User user, Statistics statistics, Information information, Goal baseGoal) {
        super(baseGoal);
        this.user = user;
        this.stats = statistics;
        this.info = information;
    }

    @Override
    // calculates the daily target calories by getting current weight
    public void handleDailyTarget() {
        float weight = stats.getWeight();
        float height = info.getHeight();
        Period age = Period.between(info.getDob(), LocalDate.now());
        float targetCal = (float) (66 + (6.3 * weight) + (4.7 * height) - (6.8 * age.getYears()));
        stats.updateTargetCalories(targetCal);
    }

    @Override
    public void handleTransition() {
        float currentWeight = stats.getWeight();
        float targetWeight = stats.getTargetWeight();
        // if user's current weight is within the range of targetWeight up 2
        // if target weight is 120 and current weight is 130, then we find the range
        // within +2
        // 130 >= 122 false
        // 121 <= 122 true
        if (currentWeight <= targetWeight + 2) {
            info.updateGoal(new MaintainWeight(user, stats, info, super.getBaseGoal()));
        }
    }

    public void handleWeightCalc() {
        float weight = stats.getWeight() - rand.nextInt(3) + 1;
        Statistics mostRecentInfo = new Statistics(weight, stats.getTargetCalories());
        History newHist = new History(mostRecentInfo);
        // user.getHistory().add(newHist);
        user.addHistory(newHist);
    }

    public Workout recommendExercise() {
        if (stats.getCalories() > stats.getTargetCalories()) {
            info.updateGoal(new MaintainWeight(user, stats, info, super.getBaseGoal()));
            int caloriesOver = (int) (stats.getCalories() - stats.getTargetCalories());

            int randomNumber = rand.nextInt(3) + 1;
            Workout chosenWorkout = null;
            int intensityTime;
            if (randomNumber == 1) {
                intensityTime = caloriesOver / 5;
                chosenWorkout = new LowIntensity(intensityTime);
            } else if (randomNumber == 2) {
                intensityTime = (int) Math.floor(caloriesOver / 7.5);
                chosenWorkout = new MediumIntensity(intensityTime);
            } else if (randomNumber == 3) {
                intensityTime = caloriesOver / 10;
                chosenWorkout = new HighIntensity(intensityTime);
            }

            return chosenWorkout;
        }

        return null;
    }
}
