package controller;

import com.model.Food.Meal;
import com.model.User.Inventory;
import com.model.User.User;

public class SearchMeal implements Command<Meal> {
    private Inventory inventory;
    private String name;

    public SearchMeal(User user, String name) {
        this.inventory = user.getInventory();
        this.name = name;
    }

    @Override
    public Meal execute() {
        return this.inventory.getMeal(name);
    }
}
