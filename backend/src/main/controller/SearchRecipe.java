package controller;

import com.model.Food.Recipe;
import com.model.User.Inventory;
import com.model.User.User;

public class SearchRecipe implements Command<Recipe> {
    private Inventory inventory;
    private String name;

    public SearchRecipe(User user, String name) {
        this.inventory = user.getInventory();
        this.name = name;
    }

    @Override
    public Recipe execute() {
        return this.inventory.getRecipe(name);
    }
}
