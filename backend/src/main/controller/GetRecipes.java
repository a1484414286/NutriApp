package controller;

import java.util.ArrayList;
import java.util.Collection;

import com.model.Food.Recipe;
import com.model.User.Inventory;
import com.model.User.User;

public class GetRecipes implements Command<ArrayList<Recipe>> {

    private Inventory inventory;

    public GetRecipes(User user) {
        this.inventory = user.getInventory();
    }

    @Override
    public ArrayList<Recipe> execute() {
        return this.inventory.getRecipes();
    }
}
