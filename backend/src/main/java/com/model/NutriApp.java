package com.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import com.database.IngredientDB;
import com.model.Food.Ingredient;
import com.model.Food.Meal;
import com.model.Food.Recipe;
import com.model.Goal.DefaultGoal;
import com.model.Goal.GainWeight;
import com.model.Goal.Goal;
import com.model.Goal.GoalSelector;
import com.model.Goal.LoseWeight;
import com.model.Goal.MaintainWeight;
import com.model.Goal.PhysicalFitnessGoal;
import com.model.Profile.PreModifiedProfile;
import com.model.Profile.Profile;
import com.model.Profile.ProfileHistory;
import com.model.User.History;
import com.model.User.Information;
import com.model.User.Notifier;
import com.model.User.Receiver;
import com.model.User.Sender;
import com.model.User.Statistics;
import com.model.User.User;
import com.model.Workout.HighIntensity;
import com.model.Workout.LowIntensity;
import com.model.Workout.MediumIntensity;
import com.model.stream.CSVStream;
import com.model.stream.JSONStream;
import com.model.stream.XMLStream;

import org.springframework.web.multipart.MultipartFile;

public class NutriApp {

    private static final String DEST = "./src/imports/";

    private HashMap<String, String> credentials;
    private HashMap<String, User> users;
    private IngredientDB db;
    private HashMap<String, Recipe> recipes;
    private HashMap<String, Meal> meals;
    private Notifier notifier;
    private Sender sender;
    private Receiver receiver;
    private HashMap<String, Profile> profiles;
    private HashMap<String, ProfileHistory> profileHistories;

    public NutriApp() {
        db = new IngredientDB();
        credentials = new HashMap<>();
        users = new HashMap<>();
        recipes = new HashMap<>();
        meals = new HashMap<>();
        notifier = new Notifier();
        profiles = new HashMap<>();
        profileHistories = new HashMap<>();
    }

    public static boolean validateFile(String filename) {
        String[] types = { "json", "csv", "xml" };
        String[] tokens = filename.split("\\.");
        for (String type : types) {
            if (type.equals(tokens[tokens.length - 1]))
                return true;
        }
        return false;
    }

    private boolean saveFile(MultipartFile file, String filename) {
        try {
            File fileC = new File("./src/imports/" + filename);
            try (OutputStream os = new FileOutputStream(fileC)) {
                os.write(file.getBytes());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void importFile(String username, MultipartFile file) {
        try {
            String[] tokens = file.getOriginalFilename().split("\\.");
            String type = tokens[tokens.length - 1];

            switch (type) {
                case "json":
                    saveFile(file, "import.json");
                    importJSONData(username, "./src/imports/import.json");
                    break;
                case "csv":
                    saveFile(file, "import.csv");
                    importCSVData(username, "./src/imports/import.csv");
                    break;
                case "xml":
                    saveFile(file, "import.xml");
                    importXMLData(username, "./src/imports/import.xml");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public HashMap<String, Ingredient> getIngredients(String keyword) {
        return db.getIngredient(keyword);
    }

    public HashMap<String, Recipe> getRecipes(String keyword) {
        HashMap<String, Recipe> results = new HashMap<>();

        for (Map.Entry recipeElement : this.recipes.entrySet()) {
            String recipeName = (String) recipeElement.getKey();

            if (recipeName.equalsIgnoreCase(keyword)) {
                Recipe currentRecipe = (Recipe) recipeElement.getValue();
                results.put(recipeName, currentRecipe);
            }
        }

        return results;
    }

    public HashMap<String, Meal> getMeals(String keyword) {
        HashMap<String, Meal> results = new HashMap<>();

        for (Map.Entry mealElement : this.meals.entrySet()) {
            String mealName = (String) mealElement.getKey();

            if (mealName.equalsIgnoreCase(keyword)) {
                Meal currentMeal = (Meal) mealElement.getValue();
                results.put(mealName, currentMeal);
            }
        }

        return results;
    }

    public User register(String username, String password, String name, float height, float weight, LocalDate dob) {
        if (!this.credentials.keySet().contains(username)) {
            this.credentials.put(username, password);
            return createUser(username, name, height, weight, dob);
        }
        return null;
    }

    public User createUser(String username, String name, float height, float weight, LocalDate dob) {
        User user = new User(name, height, weight, dob);
        // initializing first history, the daily target calories doesn't get calculated
        // until the user choose a goal.
        user.addHistory(new History(new Statistics(weight, 0)));
        users.put(username, user);
        profiles.put(username, new Profile(weight, null, null, null, "User just created"));
        profileHistories.put(username, new ProfileHistory(new ArrayList<>()));
        return user;
    }

    public void createNewUserProfile(String username) {
        profileHistories.get(username).addProfile(profiles.get(username).saveProfile());
    }

    public User logIn(String username, String password) {
        if (credentials.containsKey(username) && credentials.get(username).equals(password)) {
            return users.get(username);
        }
        return null;
    }

    public User getProfile(String username) {
        if (credentials.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }

    public Profile getProfileProfile(String username) {
        if (profiles.containsKey(username)) {
            return profiles.get(username);
        }
        return null;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (credentials.containsKey(username) && credentials.get(username).equals(oldPassword)) {
            credentials.put(username, newPassword);
            return true;
        }
        return false;
    }

    public boolean chooseGoal(String username, String baseGoal, String decoratedGoal, float desiredWeight) {
        if (users.keySet().contains(username)) {
            GoalSelector newGoal;
            Goal newBaseGoal;

            User currentUser = users.get(username);
            Statistics currentStats = currentUser.getRecentHistory().getStatistics();
            Information currentInfo = currentUser.getInformation();

            switch (baseGoal) {
                case "Default":
                    newBaseGoal = new DefaultGoal();
                    break;
                case "Workout":
                    newBaseGoal = new PhysicalFitnessGoal();
                    break;
                default:
                    return false;
            }

            switch (decoratedGoal) {
                case "Lose":
                    newGoal = new LoseWeight(currentUser, currentStats, currentInfo, newBaseGoal);
                    break;
                case "Maintain":
                    newGoal = new MaintainWeight(currentUser, currentStats, currentInfo, newBaseGoal);
                    break;
                case "Gain":
                    newGoal = new GainWeight(currentUser, currentStats, currentInfo, newBaseGoal);
                    break;
                default:
                    return false;
            }
            users.get(username).getInformation().updateGoal(newGoal);
            profiles.get(username).setCurGoal(newGoal);
            return true;
        }
        return false;
    }

    public boolean enterDailyWeight(String username, float newWeight) {
        if (users.keySet().contains(username)) {
            users.get(username);
            profiles.get(username).setDailyWeight(newWeight);
            return true;
        }
        return false;
    }

    public boolean addIngredientStock(String username, String ingredientName, int amount) {
        if (users.keySet().contains(username)) {
            HashMap<String, Ingredient> fetch = db.getIngredient(ingredientName);
            if (fetch.size() == 1) {
                users.get(username).getInventory().addIngredient(fetch.get(ingredientName), amount);
                profiles.get(username).addIngredient(fetch.get(ingredientName), amount);
                return true;
            }
        }
        return false;
    }

    public boolean createRecipe(String username, String recipeName, String[] ingredients, String[] amount,
            String[] instructions) {

        if (ingredients.length != amount.length)
            return false;

        if (users.keySet().contains(username)) {
            if (!recipes.keySet().contains(recipeName)) {

                List<Ingredient> inputIngredients = new ArrayList<>();

                for (int i = 0; i < ingredients.length; i++) {
                    HashMap<String, Ingredient> currentFetch = this.db.getIngredient(ingredients[i]);

                    if (currentFetch.size() != 1) {
                        return false;
                    }

                    Ingredient newIngredient = currentFetch.get(ingredients[i]);

                    newIngredient.setAmount(Double.parseDouble(amount[i]));

                    inputIngredients.add(newIngredient.copy());
                }

                List<String> inputInstrustions = new ArrayList<>();

                for (int i = 0; i < amount.length; i++) {
                    inputInstrustions.add(instructions[i]);
                }

                Recipe newRecipe = new Recipe(recipeName, inputIngredients, inputInstrustions);

                this.recipes.put(recipeName, newRecipe);

                return true;
            }
        }
        return false;
    }

    public boolean createMeal(String username, String mealName, String[] recipes) {
        if (users.keySet().contains(username)) {
            if (!meals.keySet().contains(mealName)) {

                List<Recipe> inputRecipes = new ArrayList<>();

                for (int i = 0; i < recipes.length; i++) {

                    if (!this.recipes.keySet().contains(recipes[i]))
                        return false;

                    inputRecipes.add(this.recipes.get(recipes[i]).copy());
                }

                Meal newMeal = new Meal(mealName, inputRecipes);

                this.meals.put(mealName, newMeal);

                return true;
            }
        }
        return false;
    }

    public boolean consumeMeal(String username, String mealName) {
        // TODO Auto-generated method stub
        return false;
    }

    public List<Ingredient> createStockShoppingList(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Ingredient> createRecipeShoppingList(String username, String recipeName) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Ingredient> createMealShoppingList(String username, String mealName) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean performWorkout(String username, String intensity, String durration) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        int minutes = Integer.parseInt(durration);
        switch (intensity.toUpperCase()) {
            case "HIGH":
                sender.logWorkout(new HighIntensity(minutes));
                return true;
            case "MEDIUM":
                sender.logWorkout(new MediumIntensity(minutes));
                return true;
            case "LOW":
                sender.logWorkout(new LowIntensity(minutes));
                return true;
            default:
                return false;
        }
    }

    public History browseHistory(String username) {
        Sender sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.viewPersonalWorkout(sender);
    }

    public boolean createTeam(String username, String teamName) {
        Sender sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.createNewTeam(teamName);
    }

    public boolean teamInvite(String username, String inviteUsername) {
        sender = new Sender(users.get(username), notifier);
        receiver = new Receiver(users.get(inviteUsername), notifier);
        notifier.setSender(sender);
        notifier.setReceiver(receiver);
        return sender.sendInvite(receiver);
    }

    public HashMap<String, History> getTeamHistory(String username) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.getTeamHistory();
    }

    public boolean acceptInvite(String username, int senderTeamID) {
        receiver = new Receiver(users.get(username), notifier);
        notifier.setReceiver(receiver);
        return receiver.acceptInvite(senderTeamID);
    }

    public boolean denyInvite(String username, int senderTeamID) {
        receiver = new Receiver(users.get(username), notifier);
        notifier.setReceiver(receiver);
        return receiver.denyInvite(senderTeamID);
    }

    public boolean issueChallenge(String username) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.issueChallenge();
    }

    public boolean leaveTeam(String username) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);

        profiles.get(username).leaveTeam();

        return sender.leaveTeam();
    }

    public String viewTotalRanking(String username) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.getTotalRanking();
    }

    public String viewWeeklyRanking(String username, String date) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.getWeeklyRanking(Integer.parseInt(date));
    }

    public String viewRecentWeeklyRanking(String username) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.viewRecentWeeklyChallenge();
    }

    public String readMostRecentMessage(String username) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.readMostRecentMessage();
    }

    public String readMessage(String username, String index) {
        sender = new Sender(users.get(username), notifier);
        notifier.setSender(sender);
        return sender.readAMessage(Integer.parseInt(index));
    }

    public boolean importCSVData(String username, String importFile) {
        if (users.keySet().contains(username)) {
            CSVStream newCSVStream = new CSVStream();

            Collection<Ingredient> importedIngredients = newCSVStream.importIngredient(importFile);
            Collection<Recipe> importedRecipes = newCSVStream.importRecipe(importFile);
            Collection<Meal> importedMeals = newCSVStream.importMeal(importFile);
            Collection<History> importedHistory = newCSVStream.importHistory(importFile);

            HashMap<String, Ingredient> newIngredients = new HashMap<>();

            for (Ingredient ingredient : importedIngredients) {
                newIngredients.put(ingredient.getName(), ingredient.copy());
            }

            HashMap<String, Recipe> newRecipes = new HashMap<>();

            for (Recipe recipe : importedRecipes) {
                newRecipes.put(recipe.getName(), recipe.copy());
            }

            HashMap<String, Meal> newMeals = new HashMap<>();

            for (Meal meal : importedMeals) {
                newMeals.put(meal.getName(), meal.copy());
            }

            Stack<History> newHistory = new Stack<>();

            for (History history : importedHistory) {
                newHistory.push(history);
            }

            User currentUser = this.users.get(username);

            currentUser.setHistory(newHistory);

            currentUser.getInventory().setIngredients(newIngredients);

            this.recipes = newRecipes;

            this.meals = newMeals;

            return true;
        }
        return false;
    }

    public boolean importJSONData(String username, String importFile) {
        if (users.keySet().contains(username)) {
            JSONStream newJSONStream = new JSONStream();

            Collection<Ingredient> importedIngredients = newJSONStream.importIngredient(importFile);
            Collection<Recipe> importedRecipes = newJSONStream.importRecipe(importFile);
            Collection<Meal> importedMeals = newJSONStream.importMeal(importFile);
            Collection<History> importedHistory = newJSONStream.importHistory(importFile);

            HashMap<String, Ingredient> newIngredients = new HashMap<>();

            for (Ingredient ingredient : importedIngredients) {
                newIngredients.put(ingredient.getName(), ingredient.copy());
            }

            HashMap<String, Recipe> newRecipes = new HashMap<>();

            for (Recipe recipe : importedRecipes) {
                newRecipes.put(recipe.getName(), recipe.copy());
            }

            HashMap<String, Meal> newMeals = new HashMap<>();

            for (Meal meal : importedMeals) {
                newMeals.put(meal.getName(), meal.copy());
            }

            Stack<History> newHistory = new Stack<>();

            for (History history : importedHistory) {
                newHistory.push(history);
            }

            User currentUser = this.users.get(username);

            currentUser.setHistory(newHistory);

            currentUser.getInventory().setIngredients(newIngredients);

            this.recipes = newRecipes;

            this.meals = newMeals;

            return true;
        }
        return false;
    }

    public boolean importXMLData(String username, String importFile) {
        if (users.keySet().contains(username)) {
            XMLStream newXMLStream = new XMLStream();

            Collection<Ingredient> importedIngredients = newXMLStream.importIngredient(importFile);
            Collection<Recipe> importedRecipes = newXMLStream.importRecipe(importFile);
            Collection<Meal> importedMeals = newXMLStream.importMeal(importFile);
            Collection<History> importedHistory = newXMLStream.importHistory(importFile);

            HashMap<String, Ingredient> newIngredients = new HashMap<>();

            for (Ingredient ingredient : importedIngredients) {
                newIngredients.put(ingredient.getName(), ingredient.copy());
            }

            HashMap<String, Recipe> newRecipes = new HashMap<>();

            for (Recipe recipe : importedRecipes) {
                newRecipes.put(recipe.getName(), recipe.copy());
            }

            HashMap<String, Meal> newMeals = new HashMap<>();

            for (Meal meal : importedMeals) {
                newMeals.put(meal.getName(), meal.copy());
            }

            Stack<History> newHistory = new Stack<>();

            for (History history : importedHistory) {
                newHistory.push(history);
            }

            User currentUser = this.users.get(username);

            currentUser.setHistory(newHistory);

            currentUser.getInventory().setIngredients(newIngredients);

            this.recipes = newRecipes;

            this.meals = newMeals;

            return true;
        }
        return false;
    }

    public boolean exportCSV(String username, String filename) {
        CSVStream newCSVexporter = new CSVStream();

        if (users.keySet().contains(username)) {
            newCSVexporter.exportHistory(filename, users.get(username).getAllHistory());

            newCSVexporter.exportIngredient(filename, users.get(username).getInventory().getIngredients().values());
        } else {
            newCSVexporter.exportIngredient(filename, this.db.getDatabase().values());
        }

        newCSVexporter.exportRecipe(filename, this.recipes.values());

        newCSVexporter.exportMeal(filename, this.meals.values());

        return true;
    }

    public boolean exportJSON(String username, String filename) {
        JSONStream newJSONexporter = new JSONStream();

        if (users.keySet().contains(username)) {
            newJSONexporter.exportHistory(filename, users.get(username).getAllHistory());

            newJSONexporter.exportIngredient(filename, users.get(username).getInventory().getIngredients().values());
        } else {
            newJSONexporter.exportIngredient(filename, this.db.getDatabase().values());
        }

        newJSONexporter.exportRecipe(filename, this.recipes.values());

        newJSONexporter.exportMeal(filename, this.meals.values());

        return true;
    }

    public boolean exportXML(String username, String filename) {
        XMLStream newXMLexporter = new XMLStream();

        if (users.keySet().contains(username)) {
            newXMLexporter.exportHistory(filename, users.get(username).getAllHistory());

            newXMLexporter.exportIngredient(filename, users.get(username).getInventory().getIngredients().values());
        } else {
            newXMLexporter.exportIngredient(filename, this.db.getDatabase().values());
        }

        newXMLexporter.exportRecipe(filename, this.recipes.values());

        newXMLexporter.exportMeal(filename, this.meals.values());

        return true;
    }

    public boolean undo(String username) {
        if (profiles.containsKey(username)) {
            PreModifiedProfile latest = (PreModifiedProfile) profileHistories.get(username).getLatestProfile();
            profiles.get(username).restoreProfile(latest);
            return true;
        }
        return false;
    }
}
