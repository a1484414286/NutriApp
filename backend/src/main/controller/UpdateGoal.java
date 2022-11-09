package controller;

import com.model.Goal.GoalSelector;
import com.model.User.Information;
import com.model.User.User;

public class UpdateGoal implements Command<Void> {
    private Information information;
    private GoalSelector newGoal;
    private GoalSelector oldGoal;

    public UpdateGoal(User user, GoalSelector goal) {
        this.newGoal = goal;
        this.information = user.getInformation();
        this.oldGoal = this.information.getGoal();
    }

    @Override
    public Void execute() {
        this.information.updateGoal(this.newGoal);
        return null;
    }

    @Override
    public void undo() {
        this.information.updateGoal(this.oldGoal);
    }
}
