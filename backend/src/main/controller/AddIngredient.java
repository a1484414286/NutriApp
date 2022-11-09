package controller;

import com.model.Food.Ingredient;
import com.model.User.Inventory;
import com.model.User.User;

public class AddIngredient implements Command<Void> {

    private Inventory inventory;
    private Ingredient ingredient;

    public AddIngredient(User user, Ingredient ingredient) {
        this.inventory = user.getInventory();
        this.ingredient = ingredient;
    }

    @Override
    public Void execute() {
        this.inventory.addIngredient(ingredient);
        return null;
    }

}
