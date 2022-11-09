package controller;

import com.model.User.History;
import com.model.User.Statistics;
import com.model.User.User;

public class UpdateCalories implements Command<Void> {
    private final float MULTIPLIER = -1;

    private User user;
    private float calories;

    public UpdateCalories(User user, float calories) {
        this.user = user;
        this.calories = calories;
    }

    private Statistics getStats() {
        History history = this.user.getHistory();
        return history.getStatistics();
    }

    @Override
    public Void execute() {
        Statistics stats = getStats();
        stats.updateCalories(calories);
        return null;
    }

    @Override
    public void undo() {
        Statistics stats = getStats();
        stats.updateCalories(calories * MULTIPLIER);
    }
}
