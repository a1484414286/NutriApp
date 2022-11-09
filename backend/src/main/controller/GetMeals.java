package controller;

import java.util.ArrayList;

import com.model.Food.Meal;
import com.model.User.Inventory;
import com.model.User.User;

public class GetMeals implements Command<ArrayList<Meal>> {

    private Inventory inventory;

    public GetMeals(User user) {
        this.inventory = user.getInventory();
    }

    @Override
    public ArrayList<Meal> execute() {
        return this.inventory.getMeals();
    }
}
