package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Supplier {

    private String name;
    private float speed;
    private Pantry pantry;
    //private Random random;

    private int producingNow;

    private SpriteBatch supplierBatch;
    private String texturePath = "bucket.png";
    private Texture texture;
    private BitmapFont font;

    public Supplier (String name, Float speed, Pantry pantry) {
        this.name = name;
        this.speed = speed;
        this.pantry = pantry;

        producingNow = 0;

        supplierBatch = new SpriteBatch();
        texture = new Texture(texturePath);
        font = new BitmapFont();
    }

    public void draw(Vector2 initialPos, Vector2 fontPos) {
        supplierBatch.begin();

        supplierBatch.draw(texture, initialPos.x, initialPos.y);

        String text = "Producing... " + producingNow;
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

    public void produceIngredient(int quantity, String... ingredients){
        boolean canProduce = pantry.produceIngredients(quantity, ingredients);
        if(canProduce){
            producingNow += quantity;
        } else {
            //couldn't produce MORE ingredients
            producingNow = 0;
        }

    }

}
