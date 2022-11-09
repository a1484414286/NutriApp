package com.model.stream;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.model.Food.Ingredient;
import com.model.Food.Meal;
import com.model.Food.Recipe;
import com.model.User.History;
import com.model.User.Statistics;
import com.model.Workout.Workout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLStream implements StreamIO {

    private static final String DATA_KEY = "Data";
    private static final String INGREDIENT_KEY = "Ingredients";
    private static final String RECIPE_KEY = "Recipes";
    private static final String MEAL_KEY = "Meals";
    private static final String HISTORY_KEY = "Histories";

    private Node getData(String filename, String key) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filename));
            Element root = doc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(key))
                    return node;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private String getText(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private double getDouble(Element element, String tagName) {
        return Double.parseDouble(getText(element, tagName));
    }

    private float getFloat(Element element, String tagName) {
        return Float.parseFloat(getText(element, tagName));
    }

    private int getInt(Element element, String tagName) {
        return Integer.parseInt(getText(element, tagName));
    }

    private Ingredient createIngredients(Element element) {
        return new Ingredient(getText(element, "Name"), getDouble(element, "Amount"),
                getText(element, "Unit"), getDouble(element, "Fat"), getDouble(element, "Protein"),
                getDouble(element, "Fiber"), getDouble(element, "Carbohydrates"), getInt(element, "Calories"));
    }

    @Override
    public Collection<Ingredient> importIngredient(String filename) {
        Node data = getData(filename, INGREDIENT_KEY);
        if (data == null)
            return null;
        NodeList nodes = data.getChildNodes();
        LinkedList<Ingredient> ingredients = new LinkedList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element) node;
            ingredients.add(createIngredients(element));
        }
        return ingredients;
    }

    private Recipe createRecipe(Element element) {
        LinkedList<Ingredient> ingredients = new LinkedList<>();
        LinkedList<String> instructions = new LinkedList<>();
        NodeList nodes = element.getElementsByTagName(INGREDIENT_KEY);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element2 = (Element) node;
            NodeList nodes2 = element2.getChildNodes();
            for (int j = 0; j < nodes2.getLength(); j++) {
                Node node2 = nodes2.item(j);
                if (node2.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                Element element3 = (Element) node;
                ingredients.add(createIngredients(element3));
            }
        }
        nodes = element.getElementsByTagName("Instructions");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element2 = (Element) node;
            NodeList nodes2 = element2.getElementsByTagName("Step");
            for (int j = 0; j < nodes2.getLength(); j++) {
                instructions.add(nodes2.item(j).getTextContent());
            }
        }
        return new Recipe(getText(element, "Name"), ingredients, instructions);
    }

    @Override
    public Collection<Recipe> importRecipe(String filename) {
        Node data = getData(filename, RECIPE_KEY);
        if (data == null)
            return null;
        NodeList nodes = data.getChildNodes();
        LinkedList<Recipe> recipes = new LinkedList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element) node;
            recipes.add(createRecipe(element));
        }
        return recipes;
    }

    private Meal createMeal(Element element) {
        LinkedList<Recipe> recipes = new LinkedList<>();
        NodeList nodes = element.getElementsByTagName(RECIPE_KEY);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element2 = (Element) node;
            recipes.add(createRecipe(element2));
        }
        return new Meal(getText(element, "Name"), recipes);
    }

    @Override
    public Collection<Meal> importMeal(String filename) {
        Node data = getData(filename, MEAL_KEY);
        if (data == null)
            return null;
        NodeList nodes = data.getChildNodes();
        LinkedList<Meal> meals = new LinkedList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element) node;
            meals.add(createMeal(element));
        }
        return meals;
    }

    @Override
    public Collection<History> importHistory(String filename) {
        Node data = getData(filename, HISTORY_KEY);
        if (data == null)
            return null;
        NodeList nodes = data.getChildNodes();
        LinkedList<History> histories = new LinkedList<>();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element) node;
            NodeList nodes2 = element.getElementsByTagName(MEAL_KEY);
            Element element2 = (Element) nodes2.item(0);
            nodes2 = element2.getChildNodes();
            LinkedList<Meal> meals = new LinkedList<>();
            for (int j = 0; j < nodes2.getLength(); j++) {
                Node node2 = nodes2.item(j);
                if (node2.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                element2 = (Element) node2;
                meals.add(createMeal(element2));
            }
            nodes2 = element.getElementsByTagName("Workout");
            element2 = (Element) nodes2.item(0);
            Workout workout = new Workout(getInt(element2, "Minutes"), null, getDouble(element2, "Rate"));
            ArrayList<Workout> workouts = new ArrayList<>();
            workouts.add(workout);
            Statistics stats = new Statistics(getFloat(element, "Weight"), getFloat(element, "TargetWeight"),
                    getFloat(element, "TargetCalories"), getFloat(element, "Calories"), meals, workouts);
            histories.add(new History(stats));
        }
        return histories;
    }

    private Document getDoc() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            return doc;
        } catch (Exception e) {
            return null;
        }
    }

    private Element setup(Document doc) {
        Element root = doc.createElement(DATA_KEY);
        doc.appendChild(root);
        return root;
    }

    private Element createTextNode(Document doc, String tagName, String text) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(text));
        return element;
    }

    private Element createTextNode(Document doc, String tagName, double text) {
        return createTextNode(doc, tagName, Double.toString(text));
    }

    private void save(String filename, Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createIngredientsElement(Document doc, Element root, Collection<Ingredient> foods) {
        Element ingredients = doc.createElement(INGREDIENT_KEY);
        root.appendChild(ingredients);

        for (Ingredient ing : foods) {
            Element ingredient = doc.createElement("Ingredient");
            Element[] elements = { createTextNode(doc, "Name", ing.getName()),
                    createTextNode(doc, "Amount", ing.getAmount()), createTextNode(doc, "Unit", ing.getUnit()),
                    createTextNode(doc, "Fat", ing.getFatContent()),
                    createTextNode(doc, "Protein", ing.getProteinContent()),
                    createTextNode(doc, "Fiber", ing.getFiberContent()),
                    createTextNode(doc, "Carbohydrates", ing.getCarbContent()),
                    createTextNode(doc, "Caloies", ing.getCalories()), };
            for (Element e : elements)
                ingredient.appendChild(e);
            ingredients.appendChild(ingredient);
        }
    }

    @Override
    public void exportIngredient(String filename, Collection<Ingredient> foods) {
        Document doc = getDoc();
        if (doc == null)
            return;

        Element root = setup(doc);
        createIngredientsElement(doc, root, foods);
        save(filename, doc);
    }

    private void createRecipesElement(Document doc, Element root, Collection<Recipe> foods) {
        Element recipes = doc.createElement(RECIPE_KEY);
        root.appendChild(recipes);

        for (Recipe rcp : foods) {
            Element recipe = doc.createElement("Recipe");
            recipe.appendChild(createTextNode(doc, "Name", rcp.getName()));
            createIngredientsElement(doc, recipe, rcp.getComponents());
            Element instructions = doc.createElement("Instructions");
            recipe.appendChild(instructions);
            rcp.getinstructions().stream().forEach(
                    e -> instructions.appendChild(createTextNode(doc, "Step", e)));
            recipes.appendChild(recipe);
        }
    }

    @Override
    public void exportRecipe(String filename, Collection<Recipe> foods) {
        Document doc = getDoc();
        if (doc == null)
            return;

        Element root = setup(doc);
        createRecipesElement(doc, root, foods);
        save(filename, doc);
    }

    private void createMealsElement(Document doc, Element root, Collection<Meal> foods) {
        Element meals = doc.createElement(MEAL_KEY);
        root.appendChild(meals);

        for (Meal m : foods) {
            Element meal = doc.createElement("Meal");
            meal.appendChild(createTextNode(doc, "Name", m.getName()));
            createRecipesElement(doc, meal, m.getRecipes());
            meals.appendChild(meal);
        }
    }

    @Override
    public void exportMeal(String filename, Collection<Meal> foods) {
        Document doc = getDoc();
        if (doc == null)
            return;

        Element root = setup(doc);
        createMealsElement(doc, root, foods);
        save(filename, doc);

    }

    @Override
    public void exportHistory(String filename, Collection<History> histories) {
        Document doc = getDoc();
        if (doc == null)
            return;

        Element root = setup(doc);
        Element histories2 = doc.createElement(HISTORY_KEY);
        root.appendChild(histories2);

        for (History h : histories) {
            Statistics stats = h.getStatistics();
            Element history = doc.createElement("History");
            history.appendChild(createTextNode(doc, "Weight", stats.getWeight()));
            history.appendChild(createTextNode(doc, "TargetWeight", stats.getTargetWeight()));
            history.appendChild(createTextNode(doc, "TargetCalories", stats.getTargetCalories()));
            history.appendChild(createTextNode(doc, "Calories", stats.getCalories()));
            createMealsElement(doc, history, stats.gMeals());
            histories2.appendChild(history);
        }
        save(filename, doc);
    }
}
