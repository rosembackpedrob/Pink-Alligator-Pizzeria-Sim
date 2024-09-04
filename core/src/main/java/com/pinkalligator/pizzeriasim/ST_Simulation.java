package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;
import java.util.concurrent.Semaphore;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class ST_Simulation extends ApplicationAdapter {

    private long startSeqTime;
    private long elapsedTime;
    private long endSeqTime;
    private Semaphore semaphore;
    //Semaphore int permit = 1: only 1 thread at time can have permission
    //Default boolean to true, what means the threads are accessing it
    //with FIFO policy.

    private SpriteBatch batch; //default size of batch is 1000
    private Random randomProdTime = new Random();

    //Objects
    int numberOfEach;
    Pantry pantry;
    Supplier[] suppliers;
    Pizzaiolo[] pizzaiolos = new Pizzaiolo[5];

    //Initialize and Load Content
    @Override
    public void create() {
        semaphore = new Semaphore(1);
        batch = new SpriteBatch();

        //Initializing Objects
        pantry = new Pantry(0,0, 0, 100);
        numberOfEach = 3;

        suppliers = new Supplier[numberOfEach];
        for (int i = 0; i < suppliers.length; i++) {
            float prodTime = randomProdTime.nextFloat(0.5f, 2f);
            suppliers[i] = new Supplier("Producer" + i, prodTime, pantry);
        }

        pizzaiolos[0] = new Pizzaiolo("Thorfinn", "TomatoPizza", pantry);
        pizzaiolos[1] = new Pizzaiolo("SumioMondo", "TomatoPizza", pantry);
        pizzaiolos[2] = new Pizzaiolo("Christina", "PepperoniPizza", pantry);
        pizzaiolos[3] = new Pizzaiolo("CarlosRosa", "PepperoniPizza", pantry);
        pizzaiolos[4] = new Pizzaiolo("Rosaria", "PepperoniPizza", pantry);


    }

    //Update and Draw
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        //Escape KEY
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Exit the application
        }

        //Updates:
        startSeqTime = System.currentTimeMillis(); //tempo sequencial inicial

        //Try Make Pizza
        pizzaiolos[0].makeMyTagOfPizza(1f); //when he's MAKING he already checks to CONSUME from Pantry
        pizzaiolos[1].makeMyTagOfPizza(1f);
        pizzaiolos[2].makeMyTagOfPizza(5f);
        pizzaiolos[3].makeMyTagOfPizza(5f);
        pizzaiolos[4].makeMyTagOfPizza(5f);



        //At FIRST cycle there is NO ingredients, so you have
        //  to wait the NEXT ones to the Supplliers TO PRODUCE
        //Define what each SUPPLIER do for the sequential (will be on the 'run' in MT example)
        suppliers[0].produceIngredient(3, "dough");
        suppliers[1].produceIngredient(2, "tomato");
        suppliers[2].produceIngredient(1, "pepperoni");

        //check to stop app
        if((pizzaiolos[4].getPizzaStackSize() >= 1) ) {
            Gdx.app.exit();
        }

        //Draws: //semaphore here for the draw of multithreading
        batch.begin();
        batch.end();

        for (int i = 0; i < suppliers.length; i++) {
            Vector2 supplierPos = new Vector2(100,  450 - (i * 50) );
            Vector2 textPos = new Vector2(180, 500 - (i * 50) );
            suppliers[i].draw(supplierPos,textPos);
        }

        for (int i = 0; i < pizzaiolos.length; i++) {
            Vector2 pizzaioloPos = new Vector2(100,  200 - (i * 50) );
            Vector2 tagPos = new Vector2(150,  200 - (i * 50) );
            Vector2 textPos = new Vector2(220, 250 - (i * 50) );
            pizzaiolos[i].draw(pizzaioloPos, tagPos, textPos);
        }

        Vector2 pantryPos = new Vector2(600,  225);
        Vector2 pantryTextPos = new Vector2(680, 275);
        pantry.draw(pantryPos, pantryTextPos);



    }

    //Unload Content
    @Override
    public void dispose() {
        batch.dispose();

        for (Supplier supplier : suppliers) {

            supplier.dispose();
        }
        for (Pizzaiolo pizzaiolo : pizzaiolos) {
            pizzaiolo.dispose();
        }

        pantry.dispose();

        endSeqTime = System.currentTimeMillis();//fim da analise de tempo
        elapsedTime = endSeqTime - startSeqTime; //calc elapsedTime
        System.out.println(elapsedTime);

        //Dispose threads
    }

}
