package com.model.ShoppingList;
import java.util.HashMap;

import com.model.Food.Ingredient;
import com.model.Food.Recipe;
import com.model.User.User;


/**
 * Shopping list interface defining visit methods to create shopping list for a given 
 * type of visitor
 */
public interface ShoppingListMaker {

    /**
     * @param recipe to create shopping list for missing ingredients 
     */
    public HashMap<String, Double> visit(Recipe recipe, User user);

    /**
     * @param ingredient to create shopping list for
     */
    public void visit(Ingredient ingredient);
}
