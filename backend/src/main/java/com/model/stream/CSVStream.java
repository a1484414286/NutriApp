package com.model.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.model.Food.Ingredient;
import com.model.Food.Meal;
import com.model.Food.Recipe;
import com.model.User.History;
import com.model.User.Statistics;

public class CSVStream implements StreamIO {

    private static final String COMMA_DELIMITER = ",";
    private static final String ARRAY_DELIMITER = "\\+";
    private static final String ELEMENT_DELIMITER = "\\|";
    private static final String DASH_DELIMITER = "\\-";
    private static final String COLON_DELIMITER = "\\:";

    private List<List<String>> getData(String filename) {
        List<List<String>> records = new LinkedList<>();
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
            bufferedReader.close();
            fileReader.close();
            return records;
        } catch (Exception e) {
            return null;
        }
    }

    private Ingredient createIngredient(List<String> list) {
        return new Ingredient(list.get(0), Double.parseDouble(list.get(1)),
                list.get(2), Double.parseDouble(list.get(3)), Double.parseDouble(list.get(4)),
                Double.parseDouble(list.get(5)), Double.parseDouble(list.get(6)), Integer.parseInt(list.get(7)));
    }

    @Override
    public Collection<Ingredient> importIngredient(String filename) {
        List<List<String>> data = getData(filename);
        if (data == null)
            return null;
        LinkedList<Ingredient> ingredients = new LinkedList<>();
        for (List<String> list : data) {
            ingredients.add(createIngredient(list));
        }
        return ingredients;
    }

    private Recipe createRecipe(List<String> list) {
        String name = list.get(0);
        String[] ingredientsS = list.get(1).split(ARRAY_DELIMITER);
        LinkedList<Ingredient> ingredients = new LinkedList<>();
        for (String string : ingredientsS) {
            ingredients.add(createIngredient(Arrays.asList(string.split(ELEMENT_DELIMITER))));
        }
        String[] instructionsS = list.get(2).split(ARRAY_DELIMITER);
        LinkedList<String> instructions = new LinkedList<>(Arrays.asList(instructionsS));
        return new Recipe(name, ingredients, instructions);
    }

    @Override
    public Collection<Recipe> importRecipe(String filename) {
        List<List<String>> data = getData(filename);
        if (data == null)
            return null;
        LinkedList<Recipe> recipes = new LinkedList<>();
        for (List<String> list : data) {
            recipes.add(createRecipe(list));
        }
        return recipes;
    }

    private Meal createMeal(List<String> list) {
        String name = list.get(0);
        LinkedList<Recipe> recipes = new LinkedList<>();
        for (int i = 1; i < list.size(); i++) {
            recipes.add(createRecipe(Arrays.asList(list.get(i).split(DASH_DELIMITER))));
        }
        return new Meal(name, recipes);
    }

    @Override
    public Collection<Meal> importMeal(String filename) {
        List<List<String>> data = getData(filename);
        if (data == null)
            return null;
        LinkedList<Meal> meals = new LinkedList<>();
        for (List<String> list : data) {
            meals.add(createMeal(list));
        }
        return meals;
    }

    @Override
    public Collection<History> importHistory(String filename) {
        List<List<String>> data = getData(filename);
        if (data == null)
            return null;
        LinkedList<History> histories = new LinkedList<>();
        for (List<String> list : data) {
            float weight = Float.parseFloat(list.get(0));
            float targetWeight = Float.parseFloat(list.get(1));
            float targetCalories = Float.parseFloat(list.get(2));
            float calories = Float.parseFloat(list.get(3));
            LinkedList<Meal> meals = new LinkedList<>();
            for (int i = 4; i < list.size(); i++) {
                String line = list.get(i);
                meals.add(createMeal(Arrays.asList(line.split(COLON_DELIMITER))));
            }
            histories.add(new History(new Statistics(weight, targetWeight, targetCalories, calories, meals, null)));
        }
        return histories;
    }

    @Override
    public void exportIngredient(String filename, Collection<Ingredient> foods) {

    }

    @Override
    public void exportRecipe(String filename, Collection<Recipe> foods) {

    }

    @Override
    public void exportMeal(String filename, Collection<Meal> foods) {

    }

    @Override
    public void exportHistory(String filename, Collection<History> histories) {
    }
}
