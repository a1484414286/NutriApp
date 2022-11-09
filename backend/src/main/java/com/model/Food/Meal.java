package com.model.Food;

import java.util.LinkedList;
import java.util.List;

public class Meal implements Food {
    private String name;
    private List<Recipe> recipes;

    public Meal(String name){
        this.name = name;
        this.recipes = new LinkedList<>();
    }

    public Meal(String name, Recipe recipe) {
        this.recipes = new LinkedList<>();
        this.recipes.add(recipe);
        this.name = name;
    }

    public Meal(String name, List<Recipe> recipes) {
        this.name = name;
        this.recipes = recipes;
    }

    public Meal copy(){
        Meal copy = new Meal(this.name);

        for(Recipe recipe : this.recipes){
            copy.addContent(recipe.copy());
        }

        return copy;
    }

    public void addContent(Recipe inputRecipe) {
        /**
         *
         */
        this.recipes.add(inputRecipe);
    }

    public void removeContent(Recipe targetRecipe) {
        /**
         *
         */
        this.recipes.remove(targetRecipe);
    }

    @Override
    public double getCalories() {
        double calories = 0;

        for (int i = 0; i < this.recipes.size(); i++) {
            calories += this.recipes.get(i).getCalories();
        }

        return calories;
    }

    @Override
    public String getUnit() {
        return null;
    }

    @Override
    public void consume() {

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getContent(String type) {
        return 0;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public String toString() {
        String result = String.format("Name: %s\nRecipes:\n", this.name);
        Recipe recipe;
        for (int i = 0; i < this.recipes.size(); i++) {
            recipe = this.recipes.get(i);
            result += String.format("   [%d] %s", i, recipe.getName());
        }
        return result;
    }
}