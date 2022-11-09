package com.model.User;

import java.util.HashMap;
import java.util.List;

import com.model.Food.Ingredient;
import com.model.Food.Meal;
import com.model.Food.Recipe;

public class Inventory {
    private HashMap<String, Ingredient> ingredients = new HashMap<String, Ingredient>();
    private static HashMap<String, Recipe> recipes = new HashMap<String, Recipe>();
    private static HashMap<String, Meal> meals = new HashMap<String, Meal>();

    public Ingredient getIngredient(String ingredientName) {
        return ingredients.get(ingredientName);
    }

    public HashMap<String, Ingredient> getIngredients(){
        return this.ingredients;
    }

    public void setIngredients(HashMap<String, Ingredient> newIngredients){
        this.ingredients = newIngredients;
    }

    public void addIngredient(Ingredient ingredient) {
        this.addIngredient(ingredient, 1);
    }

    public void addIngredient(Ingredient ingredient, double amount) {
        Ingredient found = this.ingredients.get(ingredient.getName());
        if (found == null) {
            this.ingredients.put(ingredient.getName(), ingredient);
            found = ingredient;
        }
        found.incrementAmount(amount);
    }

    public Recipe getRecipe(String recipeName) {
        return recipes.get(recipeName);
    }

    public Meal getMeal(String mealName) {
        return meals.get(mealName);
    }

    public void createRecipe(String name, List<Ingredient> ingredients, List<String> instructions) {
        recipes.put(name, new Recipe(name, ingredients, instructions));
    }

    public void removeRecipe(String name) {
        recipes.remove(name);
    }

    public void createMeal(String name, List<Recipe> recipes) {
        meals.put(name, new Meal(name, recipes));
    }

    public void removeMeal(String name) {
        meals.remove(name);
    }

    public HashMap<String, Recipe> getRecipes() {
       return recipes;
    }

    public HashMap<String, Meal> getMeals() {
        return meals;
    }
}
