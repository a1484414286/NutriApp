package com.model.ShoppingList;
import java.util.HashMap;
import java.util.List;

import com.model.Food.Ingredient;
import com.model.Food.Recipe;
import com.model.User.Inventory;
import com.model.User.User;

public class ShopListImpl implements ShoppingListMaker {

	/**
	 * @param recipe recipe wanted for the shopping list 
	 * @param user user with the specific inventory 
	 * If the amount of the ingredient needed 
	 */
	@Override
	public HashMap<String, Double> visit(Recipe recipe, User user) {
		List<Ingredient> ingredients = recipe.getComponents();
		HashMap<String, Double> shopList = new HashMap<String, Double>();

		for(Ingredient ingredient : ingredients){
			Inventory userInventory = user.getInventory(); 
			Ingredient ourIngredinet = userInventory.getIngredient(ingredient.getName()); 
			double ourAmount  = ourIngredinet.getAmount();
			// If the amount of the ingredient needed for the recipe is more than the 
			// current amount of that ingredient in the database, we add it to the 
			// Shopping list 
			double amountNeeded = ingredient.getAmount() - ourAmount;
			if(ingredient.getAmount() < ourAmount){
				shopList.put(ingredient.getName(), amountNeeded);
			}

			//Make the shopping list a hashmap with the string as the name and then key as the amount 
		}
		return shopList;
	}

	@Override
	public void visit(Ingredient ingredient) {
		// TODO Auto-generated method stub
		// Get out of stock ingredients and add those to 
		
	}

}
