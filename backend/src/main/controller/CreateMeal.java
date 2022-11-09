package controller;

import java.util.LinkedList;
import java.util.List;

import com.model.Food.Recipe;
import com.model.User.Inventory;
import com.model.User.User;

public class CreateMeal implements Command<Void> {
    private Inventory inventory;
    private String name;
    private List<Recipe> recipes;

    public CreateMeal(User user, String name) {
        this(user, name, new LinkedList<>());
    }

    public CreateMeal(User user, String name, List<Recipe> recipes) {
        this.inventory = user.getInventory();
        this.name = name;
        this.recipes = recipes;
    }

    @Override
    public Void execute() {
        inventory.createMeal(name, recipes);
        return null;
    }

    @Override
    public void undo() {
        inventory.removeMeal(this.name);
    }
}
