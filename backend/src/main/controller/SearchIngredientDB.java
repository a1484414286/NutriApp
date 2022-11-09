package controller;

import com.database.IngredientDB;
import com.model.Food.Ingredient;

public class SearchIngredientDB implements Command<Ingredient> {
    private IngredientDB db;
    private String name;

    public SearchIngredientDB(IngredientDB db, String name) {
        this.db = db;
        this.name = name;
    }

    @Override
    public Ingredient execute() {
        return this.db.getIngredient(name);
    }
}
