package com.model.Food;

import java.util.LinkedList;
import java.util.List;

public class Recipe implements Food {
    private String name;
    private List<Ingredient> ingredients;
    private List<String> instructions;
    private String unitType;

    public Recipe(String name) {
        this(name, new LinkedList<>());
    }

    public Recipe(String name, List<Ingredient> ingredients) {
        this(name, ingredients, new LinkedList<>());
    }

    public Recipe(String name, List<Ingredient> ingredients, List<String> instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe copy(){
        Recipe copy = new Recipe(this.name);

        for(Ingredient ingredient : this.ingredients){
            copy.addIngredient(ingredient.copy());
        }

        for(String instruction : this.instructions){
            copy.addInstruction(instruction);
        }

        return copy;
    }

    /**
     * @param content all the information surrounding the ingredient
     */
    public void addIngredient(Ingredient content) {

        this.ingredients.add(content);
    }

    public void removeIngredient(Ingredient content) {

        this.ingredients.remove(content);
    }

    /**
     * @param step The instructions for the recipe
     */
    public void addInstruction(String step) {
        this.instructions.add(step);
    }

    /**
     * Method returns the instructions on the recipe
     */
    public List<String> getinstructions() {
        return this.instructions;
    }

    /**
     * Returns the ingredients involved in the recipe
     */
    public List<Ingredient> getComponents() {

        return this.ingredients;
    }

    @Override
    public double getCalories() {

        double calories = 0;
        for (int i = 0; i < this.ingredients.size(); i++) {
            calories += this.ingredients.get(i).getCalories();
        }

        return calories;
    }

    @Override
    public String getUnit() {
        return this.unitType;
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

    @Override
    public String toString() {
        String result = String.format("Name: %s\nIngredients:\n", this.name);
        Ingredient ingredient;
        String instruction;
        double count = 0;

        for (int i = 0; i < this.ingredients.size(); i++) {
            ingredient = this.ingredients.get(i);
            count += ingredient.getAmount();
            result += String.format("   [%d] %s - %.2f\n", i, ingredient.getName(), ingredient.getAmount());
        }
        result += String.format("Total ingredient count: %.2f\nInstructions:\n", count);
        for (int i = 0; i < this.instructions.size(); i++) {
            instruction = this.instructions.get(i);
            result += String.format("   [%d] %s\n", i, instruction);
        }
        result += String.format("Total instructions count: %d", this.instructions.size());
        return result;
    }
}