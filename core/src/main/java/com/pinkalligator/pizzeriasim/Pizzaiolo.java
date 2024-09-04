package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Pizzaiolo {

    private String name;
    private String functionTag; //this determines WHICH type of pizza the pizzaiolo makes
    private Pantry pantry;
    private ArrayList<Pizza> pizzasStack; //where it's stores the pizza made by each pizzaiolo

    private boolean stopMaking;

    SpriteBatch pizzaioloBatch;

    private String texturePath = "bucket.png";
    private String tagTexturePath = "bucket.png"; //texture for the type of pizza this guy makes
    private Texture texture;
    private Texture tagTexture;

    private BitmapFont font;


    public Pizzaiolo (String name, String functionTag, Pantry pantry) {
        this.name = name;
        this.functionTag = functionTag;
        this.pantry = pantry;
        this.pizzasStack = new ArrayList<>(); // Initializes the ArrayList

        stopMaking = false;

        pizzaioloBatch = new SpriteBatch();

        texture = new Texture(texturePath);
        tagTexture = new Texture(tagTexturePath);
        font = new BitmapFont();
    }

    public void draw(Vector2 pizzaioloInitialPos, Vector2 tagInitialPos, Vector2 fontPos) {
        pizzaioloBatch.begin();

        pizzaioloBatch.draw(texture, pizzaioloInitialPos.x, pizzaioloInitialPos.y);
        pizzaioloBatch.draw(tagTexture, tagInitialPos.x, tagInitialPos.y);

        int stackQnt = pizzasStack.size();
        String text = "Qnt  " + stackQnt;
        font.draw(pizzaioloBatch, text, fontPos.x, fontPos.y);

        pizzaioloBatch.end();
    }

    public void makeMyTagOfPizza(){
        switch (functionTag) {
            case "TomatoPizza":
                makePizzaTomato();
                break;
            case "PepperoniPizza":
                makePizzaPepperoni();
                break;
        }
    }

    public void makePizzaTomato(){
        int quantityNeeded = 1; // consume 1 of each to make this pizza
        String[] ingredientsList = {"dough","tomato"};
        boolean hasConsumed = pantry.consumeIngredients(quantityNeeded, ingredientsList);
        if(hasConsumed && !stopMaking) {
            Pizza newPizza = new Pizza("Tomato_Pizza");;
            System.out.println("fiz pizza");
            pizzasStack.add(newPizza);
        } else {
            //couldn't make pizza
            this.stopMaking = true;
        }
    }

    public void makePizzaPepperoni(){
        int quantityNeeded = 1; // consume 1 of each to make this pizza
        String[] ingredientsList = {"dough","pepperoni"};
        boolean hasConsumed = pantry.consumeIngredients(quantityNeeded, ingredientsList);
        if(hasConsumed) {
            Pizza newPizza = new Pizza("Pepperoni_Pizza");;
            pizzasStack.add(newPizza);
        } else {
            //couldn't produce
        }
    }

}
