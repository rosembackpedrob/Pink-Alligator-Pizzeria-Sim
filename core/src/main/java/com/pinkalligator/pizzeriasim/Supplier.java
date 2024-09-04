package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Supplier {

    private String name;
    private float prodTime = 1; // time to Produce in Seconds
    private float currentProdTime; //current prodution time
    private Pantry pantry;

    private String producingNow;
    private String producedFeedback = "";

    private SpriteBatch supplierBatch;
    private String texturePath = "Objects/supplier.png";
    private Texture texture;
    private BitmapFont font;

    public Supplier (String name, Float prodTime, Pantry pantry) {
        this.name = name;
        this.prodTime = prodTime;
        currentProdTime = prodTime;
        this.pantry = pantry;

        producingNow = "nothing";

        supplierBatch = new SpriteBatch();
        texture = new Texture(texturePath);
        font = new BitmapFont();
    }

    public void draw(Vector2 initialPos, Vector2 fontPos) {
        supplierBatch.begin();

        supplierBatch.draw(texture, initialPos.x, initialPos.y);

        String text = "Producing... " + producingNow + " " + producedFeedback;
        font.setColor(1f,1f,1f,1f);
        //font.getData().setScale(1.5f);
        font.draw(supplierBatch, text, fontPos.x, fontPos.y);

        supplierBatch.end();
    }

    public void dispose() {
        supplierBatch.dispose();
        texture.dispose();
        font.dispose();
    }

    public void produceIngredient(int quantity, String ingredient){

        if(currentProdTime > 0) {
            currentProdTime -= Gdx.graphics.getDeltaTime();
            if(currentProdTime <= prodTime/2) {
                producedFeedback = "";
            }
            return;
        }
        currentProdTime = prodTime;
        producedFeedback = "produced";

        boolean canProduce = pantry.produceIngredients(quantity, ingredient);
        if(canProduce){
            producingNow += quantity;
            producingNow = ingredient;
        } else {
            //couldn't produce MORE ingredients
            producingNow = "nothing";

        }

    }

}
