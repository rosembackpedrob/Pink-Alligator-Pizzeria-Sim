package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class MT_Supplier extends Thread{

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

    private Random random = new Random();
    private int quantity;
    private String ingredient;
    private boolean running = true;

    public MT_Supplier(String name, Float prodTime, Pantry pantry, int _quantity, String _ingredient) {
        this.name = name;
        this.prodTime = prodTime;
        currentProdTime = prodTime;
        this.pantry = pantry;

        producingNow = "nothing";

        supplierBatch = new SpriteBatch();
        texture = new Texture(texturePath);
        font = new BitmapFont();

        this.quantity = _quantity;
        this.ingredient = _ingredient;
    }
    @Override
    public void run() {
        //run thread in loop
        while (running) {
            try {
                //is not problematic to sleep for 16ms?
                Thread.sleep(16); // Approx 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            update();
        }
    }

    public void update() {
        produceIngredient(quantity, ingredient);
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

    public void stopRunning() {
        running = false;
    }

    public void produceIngredient (int quantity, String ingredient){

        if(currentProdTime > 0) {
            currentProdTime -= Gdx.graphics.getDeltaTime();
            if(currentProdTime <= prodTime/2) {
                producedFeedback = "";
            }
            return;
        }
        currentProdTime = prodTime;
        producedFeedback = "produced";

        boolean produceIngredients = pantry.produceIngredients(quantity, ingredient);
        if(produceIngredients){
            producingNow += quantity;
            producingNow = ingredient;
        } else {
            //couldn't produce MORE ingredients
            producingNow = "nothing";

        }

    }

}
