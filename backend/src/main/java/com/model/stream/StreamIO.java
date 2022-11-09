package com.model.stream;

import java.util.Collection;

import com.model.Food.Ingredient;
import com.model.Food.Meal;
import com.model.Food.Recipe;
import com.model.User.History;

public interface StreamIO {

    public Collection<Ingredient> importIngredient(String filename);

    public Collection<Recipe> importRecipe(String filename);

    public Collection<Meal> importMeal(String filename);

    public Collection<History> importHistory(String filename);

    public void exportIngredient(String filename, Collection<Ingredient> foods);

    public void exportRecipe(String filename, Collection<Recipe> foods);

    public void exportMeal(String filename, Collection<Meal> foods);

    public void exportHistory(String filename, Collection<History> histories);
}
