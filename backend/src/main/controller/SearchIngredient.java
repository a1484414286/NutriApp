package controller;

import com.model.Food.Ingredient;
import com.model.User.Inventory;
import com.model.User.User;

public class SearchIngredient implements Command<Ingredient> {
    private Inventory inventory;
    private String name;

    public SearchIngredient(User user, String name) {
        this.inventory = user.getInventory();
        this.name = name;
    }

    @Override
    public Ingredient execute() {
        return this.inventory.getIngredient(this.name);
    }
}
