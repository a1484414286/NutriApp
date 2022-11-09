package com;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.model.Food.Ingredient;
import com.model.Food.Recipe;
import com.model.Goal.GoalSelector;
import com.model.User.History;
import com.model.User.User;

public interface NASubject {

    public User createUser(String username, String password, String name, float height, float weight, LocalDate dob); 
    
    public User logIn(String username, String password);

    public boolean chooseGoal(GoalSelector newGoal, String username);

    public boolean enterDailyWeight(String username, float newWeight);

    public Object getIngredients(String keyword);

    public boolean addIngredientStock(String username, String ingredientName, int amount);

    public boolean createRecipe(String username, String recipeName, List<String> ingredients, List<String> instructions);

    public boolean createMeal(String username, String mealName, List<Recipe> recipes);

    public boolean consumeMeal(String username, String mealName);

    public boolean performWorkout(String username, String workoutType, int intensity);

    public List<Ingredient> createStockShoppingList(String username);

    public List<Ingredient> createRecipeShoppingList(String username, String recipeName);

    public List<Ingredient> createMealShoppingList(String username, String mealName);

    public History browseHistory(String username);

    public void changePassword(String username, String oldPassowrd, String newPassowrd);

    public boolean createTeam(String username, String teamName);

    public boolean teamInvite(String username, String inviteUsername, String teamName);

    public HashMap<String, History> getTeamHistory(String username, String teamName);

    public void acceptInvite(String username, String teamName);

    public void issueChallenge(String username, String teamName);

    public boolean leaveTeam(String username);

    public boolean importData(String username, File importFile);

    public File exportCSV(String username);

    public File exportJSON(String username);

    public File exportXML(String username);

    public void undo(String username);

    public void logout(String username);
}
