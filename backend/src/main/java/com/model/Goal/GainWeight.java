package com.model.Goal;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

import com.model.User.History;
import com.model.User.Information;
import com.model.User.Statistics;
import com.model.User.User;
import com.model.Workout.NegativeWorkout;
import com.model.Workout.Workout;

public class GainWeight extends GoalDecorator implements GoalSelector {
    private Statistics stats;
    private Information info;
    private User user;

    private Random rand = new Random();

    public GainWeight(User user, Statistics statistics, Information information, Goal baseGoal) {
        super(baseGoal);
        this.user = user;
        this.stats = statistics;
        this.info = information;
    }

    @Override
    // calcualte the BMI score and suggest a daily target calories to the user
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
        // if user's current weight is within the range of targetWeight down 2
        // if current weight = 110
        // target weight = 120
        // 110 < 118 false
        // 119 > 118 true
        if (currentWeight >= targetWeight - 2) {
            info.updateGoal(new MaintainWeight(user, stats, info, super.getBaseGoal()));
        }
    }

    @Override
    public void handleWeightCalc() {
        float weight = stats.getWeight() + rand.nextInt(3) + 1;
        Statistics mostRecentInfo = new Statistics(weight, stats.getTargetCalories());
        History newHist = new History(mostRecentInfo);
        user.addHistory(newHist);
    }

    // For gaining weight, if the current calories is mroe than the target, switch
    // to maintain.

    //
    // ouget the difference, then pick a random number between 1 & 3. for each
    // number representing an intensity. Divides differnec eby intensity calorie
    // rate
    // wich is our minutes, we return a new Workout Instance
    public Workout recommendExercise() {
        info.updateGoal(new MaintainWeight(user, stats, info, super.getBaseGoal()));
        int caloriesOver = (int) (stats.getCalories() - stats.getTargetCalories());

        return new NegativeWorkout(caloriesOver);
    }

    public static void main(String[] args) {
        LocalDate a = LocalDate.now();
        LocalDate b = LocalDate.of(2002, 8, 1);
        int age = Period.between(a, b).getYears();
        System.out.println(age);
    }
}
