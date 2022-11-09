package com.app;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.app.utils.EntryForm;
import com.app.utils.Register;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.model.NutriApp;
import com.model.Food.Ingredient;
import com.model.Food.Meal;
import com.model.Food.Recipe;
import com.model.User.History;
import com.model.User.User;
import com.model.stream.StreamIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class APIController {
	private NutriApp realSubject;
	private ObjectMapper mapper;

	public APIController() {
		this.realSubject = new NutriApp();
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@RequestMapping(value = "/ingredients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getIngredients(@RequestParam("keywords") String[] keywords) throws JsonProcessingException {
		HashMap<String, Ingredient> ingredients = realSubject.getIngredients(keywords[0]);
		return new ObjectMapper().writeValueAsString(ingredients);
	}

	@RequestMapping(value = "/recipes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getRecipes(@RequestParam("keywords") String[] keywords) throws JsonProcessingException {
		HashMap<String, Recipe> ingredients = realSubject.getRecipes(keywords[0]);
		return new ObjectMapper().writeValueAsString(ingredients);
	}

	@RequestMapping(value = "/meals", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getMeals(@RequestParam("keywords") String[] keywords) throws JsonProcessingException {
		HashMap<String, Meal> ingredients = realSubject.getMeals(keywords[0]);
		return new ObjectMapper().writeValueAsString(ingredients);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String register(@RequestBody Register register) throws JsonProcessingException {
		User newUser = realSubject.register(register.getUsername(), register.getPassword(), register.getName(),
				Float.parseFloat(register.getHeight()),
				Float.parseFloat(register.getWeight()), LocalDate.parse(register.getDob()));
		if (newUser != null) {
			return mapper.writeValueAsString(newUser);
		}
		return "{}";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String logIn(@RequestBody EntryForm loginForm) throws JsonProcessingException {
		User loginUser = realSubject.logIn(loginForm.getUsername(), loginForm.getPassword());
		if (loginUser != null) {
			return mapper.writeValueAsString(loginUser);
		}
		return "{}";
	}

	@RequestMapping(value = "/profile/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getProfile(@PathVariable String username) throws JsonProcessingException {
		User profileInstance = realSubject.getProfile(username);
		return mapper.writeValueAsString(profileInstance);
	}

	@RequestMapping(value = "/change-password/{username}/{oldPassword}/{newPassword}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String changePassword(@PathVariable String username, @PathVariable String oldPassword,
			@PathVariable String newPassword) throws JsonProcessingException {
		Boolean result = realSubject.changePassword(username, oldPassword, newPassword);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/choose-goal/{username}/{baseGoal}/{decoratedGoal}/{desiredWeight}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String chooseGoal(@PathVariable String username, @PathVariable String baseGoal,
			@PathVariable String decoratedGoal, @PathVariable float desiredWeight) throws JsonProcessingException {
		Boolean result = realSubject.chooseGoal(username, baseGoal, decoratedGoal, desiredWeight);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/enter-daily-weight/{username}/{newWeight}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String enterDailyWeight(String username, float newWeight) throws JsonProcessingException {
		Boolean result = realSubject.enterDailyWeight(username, newWeight);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/add-ingredient-stock/{username}/{ingredientName}/{amount}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addIngredientStock(@PathVariable String username, @PathVariable String ingredientName,
			@PathVariable int amount) throws JsonProcessingException {
		Boolean result = realSubject.addIngredientStock(username, ingredientName, amount);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/create-recipe/{username}/{recipeName}/{ingredients}/{amount}/{instructions}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createRecipe(@PathVariable String username, @PathVariable String recipeName,
			@PathVariable String ingredients, @PathVariable String amount,
			@PathVariable String instructions) throws JsonProcessingException {
		Boolean result = realSubject.createRecipe(username, recipeName,
				ingredients.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1), amount.split(","), instructions.split(","));
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/create-meal/{username}/{mealName}/{recipes}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createMeal(@PathVariable String username, @PathVariable String mealName, @PathVariable String recipes)
			throws JsonProcessingException {
		Boolean result = realSubject.createMeal(username, mealName, recipes.split(","));
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/consume-meal/{username}/{mealName}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String consumeMeal(@PathVariable String username, @PathVariable String mealName)
			throws JsonProcessingException {
		Boolean result = realSubject.consumeMeal(username, mealName);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/perform-workout/{username}/{intensity}/{durration}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String performWorkout(@PathVariable String username, @PathVariable String intensity,
			@PathVariable String durration) throws JsonProcessingException {
		Boolean result = realSubject.performWorkout(username, intensity, durration);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/create-shop-list/stock/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createStockShoppingList(@PathVariable String username) throws JsonProcessingException {
		List<Ingredient> shopList = realSubject.createStockShoppingList(username);
		return new ObjectMapper().writeValueAsString(shopList);
	}

	@RequestMapping(value = "/create-shop-list/recipe/{username}/{recipeName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createRecipeShoppingList(@PathVariable String username, @PathVariable String recipeName)
			throws JsonProcessingException {
		List<Ingredient> shopList = realSubject.createRecipeShoppingList(username, recipeName);
		return new ObjectMapper().writeValueAsString(shopList);
	}

	@RequestMapping(value = "/create-shop-list/meal/{username}/{mealName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createMealShoppingList(String username, String mealName) throws JsonProcessingException {
		List<Ingredient> shopList = realSubject.createMealShoppingList(username, mealName);
		return new ObjectMapper().writeValueAsString(shopList);
	}

	@RequestMapping(value = "/history/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String browseHistory(@PathVariable String username) throws JsonProcessingException {
		History userHistory = realSubject.browseHistory(username);
		return new ObjectMapper().writeValueAsString(userHistory);
	}

	@RequestMapping(value = "/create-team/{username}/{teamName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String createTeam(@PathVariable String username, @PathVariable String teamName)
			throws JsonProcessingException {
		Boolean result = realSubject.createTeam(username, teamName);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/team-invite/{username}/{invitedUser}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String teamInvite(@PathVariable String username, @PathVariable String inviteUsername,
			@PathVariable String teamName) throws JsonProcessingException {
		Boolean result = realSubject.teamInvite(username, inviteUsername);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/get-team-history/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getTeamHistory(@PathVariable String username)
			throws JsonProcessingException {
		HashMap<String, History> teamHistory = realSubject.getTeamHistory(username);
		return new ObjectMapper().writeValueAsString(teamHistory);
	}

	@RequestMapping(value = "/accept-invite/{username}/{teamID}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String acceptInvite(@PathVariable String username, @PathVariable Integer teamID)
			throws JsonProcessingException {
		Boolean result = realSubject.acceptInvite(username, teamID);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/deny-invite/{username}/{teamID}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String denyInvite(@PathVariable String username, @PathVariable Integer teamID)
			throws JsonProcessingException {
		Boolean result = realSubject.denyInvite(username, teamID);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/issue-challenge/{username}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String issueChallenge(@PathVariable String username)
			throws JsonProcessingException {
		Boolean result = realSubject.issueChallenge(username);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/leave-team/{username}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public String leaveTeam(@PathVariable String username)
			throws JsonProcessingException {
		Boolean result = realSubject.leaveTeam(username);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/view-total-ranking/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String viewTotalRanking(@PathVariable String username)
			throws JsonProcessingException {
		String totalRanking = realSubject.viewTotalRanking(username);
		return new ObjectMapper().writeValueAsString(totalRanking);
	}

	@RequestMapping(value = "/view-recent-weekly-ranking/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String viewRecentWeeklyRanking(@PathVariable String username)
			throws JsonProcessingException {
		String recentWeeklyChallenge = realSubject.viewRecentWeeklyRanking(username);
		return new ObjectMapper().writeValueAsString(recentWeeklyChallenge);
	}

	// note that 0 will be this week and 1 will be last week.
	@RequestMapping(value = "/view-weekly-ranking/{username}/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String viewWeeklyRanking(@PathVariable String username, @PathVariable String date)
			throws JsonProcessingException {
		String recentWeeklyChallenge = realSubject.viewWeeklyRanking(username, date);
		return new ObjectMapper().writeValueAsString(recentWeeklyChallenge);
	}

	@RequestMapping(value = "/read-message/{username}/{index}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String readMessage(@PathVariable String username, @PathVariable String index)
			throws JsonProcessingException {
		String message = realSubject.readMessage(username, index);
		return new ObjectMapper().writeValueAsString(message);
	}

	@RequestMapping(value = "/read-recent-message/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String readMostRecentMessage(@PathVariable String username)
			throws JsonProcessingException {
		String recentMessage = realSubject.readMostRecentMessage(username);
		return new ObjectMapper().writeValueAsString(recentMessage);
	}

	@RequestMapping(value = "/import", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> importData(@RequestParam("username") String username,
			@RequestParam(value = "file", required = true) MultipartFile file) {
		String name = file.getOriginalFilename();
		if (!NutriApp.validateFile(name))
			return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
		realSubject.importFile(username, file);
		return new ResponseEntity<>("{}", HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/export/CSV/{username}/{filename}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String exportCSV(@PathVariable String username, @PathVariable String filename)
			throws JsonProcessingException {
		Boolean result = realSubject.exportCSV(username, filename);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/export/JSON/{username}/{filename}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String exportJSON(@PathVariable String username, @PathVariable String filename)
			throws JsonProcessingException {
		Boolean result = realSubject.exportJSON(username, filename);
		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/export/XML/{username}/{filename}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String exportXML(@PathVariable String username, @PathVariable String filename)
			throws JsonProcessingException {
		Boolean result = realSubject.exportXML(username, filename);

		return new ObjectMapper().writeValueAsString(result);
	}

	@RequestMapping(value = "/undo/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String undo(@PathVariable String username) throws JsonProcessingException {
		Boolean result = realSubject.undo(username);
		return new ObjectMapper().writeValueAsString(result);
	}

}