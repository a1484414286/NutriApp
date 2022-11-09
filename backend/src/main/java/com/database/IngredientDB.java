package com.database;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.model.Food.Ingredient;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

@Component()
public class IngredientDB {
    private static HashMap<String, Ingredient> ingredients;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public IngredientDB() {

        try {

            File ingredientCSVFile = new File("src/main/java/com/database/ingredients.csv");
            FileReader fileReader = new FileReader(ingredientCSVFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            this.ingredients = new HashMap<>();

            // Format for CSV
            // Fat Content -

            String currentIngredient = bufferedReader.readLine();
            currentIngredient = bufferedReader.readLine();
            while (currentIngredient != null) {

                String[] curretnProperties = currentIngredient.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                String name = curretnProperties[1].replace("\"", "");
                double amount = 0.0;
                String unitType = curretnProperties[49];

                double fat = 0;
                for (int i = 44; i < 47; i++) {
                    if (!curretnProperties[i].isEmpty())
                        fat += Double.parseDouble(curretnProperties[i]);
                }
                double protein = 0;
                if (!curretnProperties[4].isEmpty())
                    protein += Double.parseDouble(curretnProperties[4]);

                double fiber = 0;
                if (!curretnProperties[8].isEmpty())
                    fiber += Double.parseDouble(curretnProperties[8]);

                double carb = 0;
                if (!curretnProperties[7].isEmpty())
                    carb += Double.parseDouble(curretnProperties[7]);

                Ingredient currentInputIngredient = new Ingredient(name, Double.parseDouble(df.format(amount)),
                        unitType, Double.parseDouble(df.format(fat)), Double.parseDouble(df.format(protein)),
                        Double.parseDouble(df.format(fiber)),
                        Double.parseDouble(df.format(carb)), Integer.parseInt(curretnProperties[3]));

                ingredients.put(name, currentInputIngredient);
                currentIngredient = bufferedReader.readLine();
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public HashMap<String, Ingredient> getDatabase() {
        return this.ingredients;
    }

    public HashMap<String, Ingredient> getIngredient(String name) {
        try {
            HashMap<String, Ingredient> result = new HashMap<>();

            for (Map.Entry dbElement : this.ingredients.entrySet()) {

                String dbName = (String) dbElement.getKey();

                if (dbName.contains(name.toUpperCase())) {
                    Ingredient currentIngredient = (Ingredient) dbElement.getValue();
                    result.put(dbName, currentIngredient.copy());
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
