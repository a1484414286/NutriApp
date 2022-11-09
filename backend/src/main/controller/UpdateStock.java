package controller;

import com.model.Food.Ingredient;

public class UpdateStock implements Command<Void> {
    private final float MULTIPLIER = -1;

    private Ingredient ingredient;
    private double stock;

    public UpdateStock(Ingredient ingredient, double stock) {
        this.ingredient = ingredient;
        this.stock = stock;
    }

    @Override
    public Void execute() {
        this.ingredient.incrementAmount(stock);
        return null;
    }

    @Override
    public void undo() {
        this.ingredient.incrementAmount(stock * MULTIPLIER);
    }
}
