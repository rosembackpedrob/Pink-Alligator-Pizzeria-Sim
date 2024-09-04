package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Pantry {

    //Ingredients
    private int dough;
    private int tomato;
    private int pepperoni;

    SpriteBatch pantryBatch;
    String texturePath = "bucket.png";
    Texture texture;

    private int ingredientsLimit = 100;

    public Pantry (int dough, int tomato, int pepperoni, int ingredientsLimit) {
        this.dough = dough;
        this.tomato = tomato;
        this.pepperoni = tomato;

        pantryBatch = new SpriteBatch();
        texture = new Texture(texturePath);

        this.ingredientsLimit = ingredientsLimit;
    }

    public void draw(Vector2 initialPos) {
        pantryBatch.begin();

        pantryBatch.draw(texture, initialPos.x, initialPos.y);

        pantryBatch.end();
    }

    public boolean produceIngredients(int quantity, String... ingredientsNames) {
        boolean canProduce = false;

        for (String ingredient : ingredientsNames) {
            switch (ingredient) {
                case "dough":
                    canProduce = canProduce(this.dough, quantity);
                    this.dough += quantity;
                    break;
                case "tomato":
                    canProduce = canProduce(this.tomato, quantity);
                    this.tomato += quantity;
                    break;
                case "pepperoni":
                    canProduce = canProduce(this.pepperoni, quantity);
                    this.pepperoni += quantity;
                    break;
            }
        }
        return canProduce;
    }

    public boolean consumeIngredients(int quantity, String... ingredientsNames) {
        boolean canConsume = false;

        for (String ingredient : ingredientsNames) {
            switch(ingredient) {
                case "dough":
                    canConsume = canConsume(this.dough, quantity);
                    this.dough -= quantity;
                    break;
                case "tomato":
                    canConsume = canConsume(this.tomato, quantity);
                    this.tomato -= quantity;
                    break;
                case "pepperoni":
                    canConsume = canConsume(this.pepperoni, quantity);
                    this.pepperoni -= quantity;
                    break;
            }
        }
        return canConsume;
    }

    //these 2 methods below CHECKS if can = true AND actually consume or produce
    private boolean canProduce(int ingredientInPantry, int quantityToProduce) {
        boolean isPantryFull = ingredientInPantry + quantityToProduce >= this.ingredientsLimit ;

        if(!isPantryFull) {
            return true;
        } else {
            return false;
            //pantry is full
        }

    }

    private boolean canConsume(int ingredientInPantry, int quantityToConsume) {

        if(ingredientInPantry >= quantityToConsume) {
            return true;
        } else{
            return false;
            //there is none on the pantry
        }
    }

}
