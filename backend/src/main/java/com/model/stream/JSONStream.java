package com.model.stream;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

import com.model.Food.Food;
import com.model.Food.Ingredient;
import com.model.Food.Meal;
import com.model.Food.Recipe;
import com.model.User.History;
import com.model.User.Statistics;
import com.model.Workout.Workout;

import com.google.gson.*;
import java.lang.reflect.Type;

public class JSONStream implements StreamIO {

    private static final String INGREDIENT_KEY = "ingredients";
    private static final String RECIPE_KEY = "recipes";
    private static final String MEAL_KEY = "meals";
    private static final String HISTORY_KEY = "histories";

    /**
     * class retrieved from
     * https://stackoverflow.com/questions/39192945/serialize-java-8-localdate-as-yyyy-mm-dd-with-gson
     * required to properly parse a LocalDate object
     */
    private class GsonLocalDate implements JsonSerializer<LocalDate> {
        public JsonElement serialize(LocalDate date, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // "yyyy-mm-dd"
        }
    }

    private JsonArray getData(String filename, String key) {
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(new File(filename)));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return jsonObject.get(key).getAsJsonArray();

        } catch (Exception e) {
            return null;
        }
    }

    private Ingredient createIngredient(JsonObject obj) {
        return new Ingredient(obj.get("name").getAsString(), obj.get("amount").getAsDouble(),
                obj.get("unit").getAsString(),
                obj.get("fat").getAsDouble(),
                obj.get("protein").getAsDouble(), obj.get("fiber").getAsDouble(),
                obj.get("carbohydrates").getAsDouble(), obj.get("calories").getAsInt());
    }

    @Override
    public Collection<Ingredient> importIngredient(String filename) {
        JsonArray ingredientsData = getData(filename, INGREDIENT_KEY);
        if (ingredientsData == null)
            return null;
        LinkedList<Ingredient> ingredients = new LinkedList<>();
        for (JsonElement element : ingredientsData) {
            JsonObject obj = element.getAsJsonObject();
            ingredients.add(createIngredient(obj));
        }
        return ingredients;
    }

    private Recipe createRecipe(JsonObject obj) {
        LinkedList<Ingredient> ingredients = new LinkedList<>();
        JsonArray ingredientsData = obj.get(INGREDIENT_KEY).getAsJsonArray();
        for (JsonElement element2 : ingredientsData) {
            JsonObject obj2 = element2.getAsJsonObject();
            ingredients.add(createIngredient(obj2));
        }
        LinkedList<String> instructions = new LinkedList<>();
        JsonArray instructionsData = obj.get("instructions").getAsJsonArray();
        for (JsonElement element2 : instructionsData) {
            instructions.add(element2.getAsString());
        }
        return new Recipe(obj.get("name").getAsString(), ingredients, instructions);
    }

    @Override
    public Collection<Recipe> importRecipe(String filename) {
        JsonArray recipesData = getData(filename, RECIPE_KEY);
        if (recipesData == null)
            return null;
        LinkedList<Recipe> recipes = new LinkedList<>();
        for (JsonElement element : recipesData) {
            JsonObject obj = element.getAsJsonObject();
            recipes.add(createRecipe(obj));
        }
        return recipes;
    }

    private Meal createMeal(JsonObject obj) {
        LinkedList<Recipe> recipes = new LinkedList<>();
        JsonArray recipesData = obj.get(RECIPE_KEY).getAsJsonArray();
        for (JsonElement element2 : recipesData) {
            recipes.add(createRecipe(element2.getAsJsonObject()));
        }
        return new Meal(obj.get("name").getAsString(), recipes);
    }

    @Override
    public Collection<Meal> importMeal(String filename) {
        JsonArray mealsData = getData(filename, MEAL_KEY);
        if (mealsData == null)
            return null;
        LinkedList<Meal> meals = new LinkedList<>();
        for (JsonElement element : mealsData) {
            JsonObject obj = element.getAsJsonObject();
            meals.add(createMeal(obj));
        }
        return meals;
    }

    @Override
    public Collection<History> importHistory(String filename) {
        JsonArray historyData = getData(filename, HISTORY_KEY);
        if (historyData == null)
            return null;
        LinkedList<History> history = new LinkedList<>();
        for (JsonElement element : historyData) {
            JsonObject obj = element.getAsJsonObject();
            LinkedList<Meal> meals = new LinkedList<>();
            JsonArray mealsData = obj.get(MEAL_KEY).getAsJsonArray();
            for (JsonElement element2 : mealsData) {
                meals.add(createMeal(element2.getAsJsonObject()));
            }
            JsonObject workoutObj = obj.get("workout").getAsJsonObject();

            // handle intensity
            String dateString = workoutObj.get("date").getAsString();
            LocalDate date = LocalDate.parse(dateString);
            Workout workout = new Workout(workoutObj.get("minutes").getAsInt(), date,
                    workoutObj.get("rate").getAsDouble());
            ArrayList<Workout> workouts = new ArrayList<>();
            workouts.add(workout);
            Statistics stats = new Statistics(obj.get("weight").getAsFloat(), obj.get("targetWeight").getAsFloat(),
                    obj.get("targetCalories").getAsFloat(), obj.get("calories").getAsFloat(), meals, workouts);
            System.out.println(workout.getDate());
            history.add(new History(stats));
        }
        return history;
    }

    @Override
    public void exportIngredient(String filename, Collection<Ingredient> foods) {
        Gson gson = new Gson();
        try {
            FileWriter fileWriter = new FileWriter(filename);
            HashMap<String, Object> data = new HashMap<>();
            data.put(INGREDIENT_KEY, foods);
            gson.toJson(data, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportRecipe(String filename, Collection<Recipe> foods) {
        Gson gson = new Gson();
        try {
            FileWriter fileWriter = new FileWriter(filename);
            HashMap<String, Object> data = new HashMap<>();
            data.put(RECIPE_KEY, foods);
            gson.toJson(data, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportMeal(String filename, Collection<Meal> foods) {
        Gson gson = new Gson();
        try {
            FileWriter fileWriter = new FileWriter(filename);
            HashMap<String, Object> data = new HashMap<>();
            data.put(MEAL_KEY, foods);
            gson.toJson(data, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportHistory(String filename, Collection<History> histories) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new GsonLocalDate())
                .create();
        try {
            FileWriter fileWriter = new FileWriter(filename);
            HashMap<String, Object> data = new HashMap<>();
            data.put(HISTORY_KEY, histories.stream().map(e -> e.getStatistics()).collect(Collectors.toList()));
            gson.toJson(data, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
