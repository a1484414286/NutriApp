package com.model.Food;

public interface Food{

    public double getCalories();

    public String getUnit();

    public void consume();

    public String getName();

    public double getContent(String type);
}