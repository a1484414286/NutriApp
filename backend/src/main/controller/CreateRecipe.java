package controller;

import java.util.LinkedList;
import java.util.List;

import com.model.Food.Ingredient;
import com.model.User.Inventory;
import com.model.User.User;

public class CreateRecipe implements Command<Void> {
    private Inventory inventory;
    private String name;
    private List<Ingredient> ingredients;
    private List<String> instructions;

    public CreateRecipe(User user, String name) {
        this(user, name, new LinkedList<>());

    }

    public CreateRecipe(User user, String name, List<Ingredient> ingredients) {
        this(user, name, ingredients, new LinkedList<>());

    }

    public CreateRecipe(User user, String name, List<Ingredient> ingredients, List<String> instructions) {
        this.inventory = user.getInventory();
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @Override
    public Void execute() {
        inventory.createRecipe(name, ingredients, instructions);
        return null;
    }

    @Override
    public void undo() {
        inventory.removeRecipe(name);
    }
}
