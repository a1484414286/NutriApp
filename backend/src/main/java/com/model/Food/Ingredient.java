package com.model.Food;

public class Ingredient implements Food {

    private String name;
    private double amount;
    private String unit;
    private double fat;
    private double protein;
    private double fiber;
    private double carbohydrates;
    private int calories;

    public Ingredient(String name, double amount, String unit, double fat,
            double protein, double fiber, double carbohydrates, int calories) {
        /**
         * Default Constuctor for Ingredient.
         */
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.fat = fat;
        this.protein = protein;
        this.fiber = fiber;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
    }

    public void incrementAmount(double amountChange) {
        /**
         *
         */
        this.amount += amountChange;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public double getAmount() {
        return this.amount;
    }

    public double getFatContent() {
        return fat;
    }

    public double getCarbContent() {
        return carbohydrates;
    }

    public double getFiberContent() {
        return fiber;
    }

    public double getProteinContent() {
        return protein;
    }

    public int getCaloriesPerUnit() {
        return calories;
    }

    public Ingredient copy() {
        return new Ingredient(this.name, this.amount, this.unit, this.fat, this.protein,
                this.fiber, this.carbohydrates, this.calories);
    }

    @Override
    public double getCalories() {
        return this.calories * this.amount;
    }

    @Override
    public String getUnit() {
        return this.unit;
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
        return String.format("Name: %s\nAmount: %.2f\nCalories per unit: %d\n" +
                "Grams of fat: %.2f\nProtein: %.2f\nFiber: %.2f\nCarbohydrates: %.2f\n", this.name, this.amount,
                this.calories, this.fat, this.protein, this.fiber, this.carbohydrates);
    }
}