package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Pantry {

    //Ingredients
    private int dough;
    private int tomato;
    private int pepperoni;

    SpriteBatch pantryBatch;
    String texturePath = "Objects/pantry.png";
    Texture texture;
    BitmapFont font;

    private int ingredientsLimit = 100;

    public Pantry (int dough, int tomato, int pepperoni, int ingredientsLimit) {
        this.dough = dough;
        this.tomato = tomato;
        this.pepperoni = pepperoni;

        pantryBatch = new SpriteBatch();
        texture = new Texture(texturePath);
        font = new BitmapFont();

        this.ingredientsLimit = ingredientsLimit;
    }

    public void draw(Vector2 initialPos, Vector2 fontPos) {
        pantryBatch.begin();

        pantryBatch.draw(texture, initialPos.x, initialPos.y);

        String text = "In pantry...\n" + "dough: " + this.dough + "  tomato: " + this.tomato + "  pepperoni: " + this.pepperoni;
        font.setColor(1f,1f,1f,1f);
        //font.getData().setScale(1.5f);
        font.draw(pantryBatch, text, fontPos.x, fontPos.y);

        pantryBatch.end();
    }

    public void dispose()
    {
        pantryBatch.dispose();
        texture.dispose();
        font.dispose();
    }

    public boolean produceIngredients(int quantity, String ingredientName) {
        boolean canProduce = false;
            //System.out.println(quantity + "  " + ingredientName);
            switch (ingredientName) {
                case "dough":
                    canProduce = canProduce(this.dough, quantity);
                    if(canProduce) {
                        this.dough += quantity;
                    }
                    break;
                case "tomato":
                    canProduce = canProduce(this.tomato, quantity);
                    if(canProduce) {
                        this.tomato += quantity;
                    }
                    break;
                case "pepperoni":
                    canProduce = canProduce(this.pepperoni, quantity);
                    if(canProduce) {
                        this.pepperoni += quantity;
                    }
                    break;
            }

        return canProduce;
    }

    public void consumeIngredients(int quantity, String ingredientName) {
            switch(ingredientName) {
                case "dough":
                    this.dough -= quantity;
                    break;
                case "tomato":
                    this.tomato -= quantity;
                    break;
                case "pepperoni":
                    this.pepperoni -= quantity;
                    break;
            }
    }

    //these 2 methods below CHECKS if can = true AND actually consume or produce
    private boolean canProduce(int ingredientInPantry, int quantityToProduce) {
        boolean isPantryFull = ingredientInPantry + quantityToProduce > this.ingredientsLimit ;

        if(!isPantryFull) {
            return true;
        } else {
            return false;
            //pantry is full
        }

    }

    public boolean canConsume(int quantityToConsume, String... ingredientsNames) {
        int canConsume = 0;

        for (String ingredient : ingredientsNames) {
            switch(ingredient) {
                case "dough":
                    if(this.dough >= quantityToConsume)
                    {
                        canConsume++;
                    }
                    break;
                case "tomato":
                    if(this.tomato >= quantityToConsume)
                    {
                        canConsume++;
                    }
                    break;
                case "pepperoni":
                    if(this.pepperoni >= quantityToConsume)
                    {
                        canConsume++;
                    }
                    break;
            }
        }
        return canConsume == ingredientsNames.length;
    }

}
