package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.Gdx;
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

    float makingTime = 0.5f;
    float currentMakeTime = 0f;
    private String doneFeedback = "";

    SpriteBatch pizzaioloBatch;

    private String texturePath = "Objects/pinkAlligatorChef.png";
    private String tagTexturePath = "Objects/"; //texture for the type of pizza this guy makes
    private Texture texture;
    private Texture tagTexture;

    private BitmapFont font;


    public Pizzaiolo (String name, String functionTag, Pantry pantry) {
        this.name = name;
        this.functionTag = functionTag;
        this.pantry = pantry;
        this.pizzasStack = new ArrayList<>(); // Initializes the ArrayList

        pizzaioloBatch = new SpriteBatch();

        texture = new Texture(texturePath);
        tagTexture = new Texture(tagTexturePath + functionTag + ".png");
        font = new BitmapFont();
    }

    public void draw(Vector2 pizzaioloInitialPos, Vector2 tagInitialPos, Vector2 fontPos) {
        pizzaioloBatch.begin();

        pizzaioloBatch.draw(texture, pizzaioloInitialPos.x, pizzaioloInitialPos.y);
        pizzaioloBatch.draw(tagTexture, tagInitialPos.x, tagInitialPos.y);

        int stackQnt = pizzasStack.size();
        String text = this.name + " -- PizzasStack:  " + stackQnt + "  " + doneFeedback;
        font.draw(pizzaioloBatch, text, fontPos.x, fontPos.y);

        pizzaioloBatch.end();
    }

    public void dispose()
    {
        pizzaioloBatch.dispose();

        texture.dispose();
        tagTexture.dispose();
        font.dispose();
    }

    public void makeMyTagOfPizza(float makingTime){
        this.makingTime = makingTime;
        if(currentMakeTime > 0) {
            currentMakeTime -= Gdx.graphics.getDeltaTime();
            if(currentMakeTime <= makingTime /2) {
                doneFeedback = "";
            }
            return;
        }
        currentMakeTime = makingTime;

        String[] ingredients = {};
        switch (functionTag) {
            case "TomatoPizza":
                ingredients = new String[]{"dough", "tomato"};
                makePizza("TomatoPizza", ingredients);
                break;
            case "PepperoniPizza":
                ingredients = new String[]{"dough", "pepperoni"};
                makePizza("PepperoniPizza", ingredients);
                break;
        }
    }

    public void makePizza(String pizzaName, String... ingredientsList){
        int quantityNeeded = 1; // consume 1 of each to make this pizza
        boolean canConsume = pantry.canConsume(quantityNeeded,ingredientsList);

        if(canConsume) {
            for (String ingredient : ingredientsList) {
                pantry.consumeIngredients(quantityNeeded, ingredient);
            }
            Pizza newPizza = new Pizza(pizzaName);;
            pizzasStack.add(newPizza);
            doneFeedback = "*DONE*";
        }
    }

    public int getPizzaStackSize()
    {
        return pizzasStack.size();
    }

}
