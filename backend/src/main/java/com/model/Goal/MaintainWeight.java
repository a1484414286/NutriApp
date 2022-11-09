package com.model.Goal;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import com.model.User.History;
import com.model.User.Information;
import com.model.User.Statistics;
import com.model.User.User;
import com.model.Workout.HighIntensity;
import com.model.Workout.LowIntensity;
import com.model.Workout.MediumIntensity;
import com.model.Workout.NegativeWorkout;
import com.model.Workout.Workout;

public class MaintainWeight extends GoalDecorator implements GoalSelector {
    private User user;
    private Statistics stats;
    private Information info;

    private Random rand = new Random();

    public MaintainWeight(User user, Statistics statistics, Information information, Goal baseGaol) {
        super(baseGaol);
        this.user = user;
        this.stats = statistics;
        this.info = information;
    }

    public MaintainWeight(Goal baseGoal) {
        super(baseGoal);
    }

    public boolean determineWorkout() {
        return super.determineWorkout(user.getHistory().getStatistics().getCalories(),
                user.getHistory().getStatistics().getTargetCalories());
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
        // if user's current weight is above five, transition to lose
        if (currentWeight >= targetWeight + 5) {
            user.getInformation().updateGoal(new LoseWeight(user, stats, info, super.getBaseGoal()));
        }
        // if user's current weight is below five, transition to gain
        else if (currentWeight <= targetWeight - 5) {
            user.getInformation().updateGoal(new GainWeight(user, stats, info, super.getBaseGoal()));
        }
    }

    @Override
    public void handleWeightCalc() {
        int a = rand.nextInt(3) + 1;
        if (a % 2 == 0) {
            float weight = stats.getWeight() - rand.nextInt(3) + 1;
            Statistics mostRecentInfo = new Statistics(weight, stats.getTargetCalories());
            History newHist = new History(mostRecentInfo);
            user.addHistory(newHist);
        } else {
            float weight = stats.getWeight() + rand.nextInt(3) + 1;
            Statistics mostRecentInfo = new Statistics(weight, stats.getTargetCalories());
            History newHist = new History(mostRecentInfo);
            user.addHistory(newHist);
        }
    }

    public Workout recommendExercise() {

        if (stats.getWeight() > (stats.getTargetWeight() + 2)) {
            info.updateGoal(new LoseWeight(user, stats, info, super.getBaseGoal()));
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
        } else if (stats.getWeight() < (stats.getTargetWeight() - 2)) {
            info.updateGoal(new GainWeight(user, stats, info, super.getBaseGoal()));
            int caloriesOver = (int) (stats.getCalories() - stats.getTargetCalories());
            return new NegativeWorkout(caloriesOver);
        }

        return null;

    }

}
