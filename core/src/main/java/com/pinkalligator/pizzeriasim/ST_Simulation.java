package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.concurrent.Semaphore;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class ST_Simulation extends ApplicationAdapter {

    private Semaphore semaphore;
    //Semaphore int permit = 1: only 1 thread at time can have permission
    //Default boolean to true, what means the threads are accessing it
    //with FIFO policy.

    private SpriteBatch batch; //default size of batch is 1000

    //Objects
    Pantry pantry;
    Supplier[] suppliers;
    Pizzaiolo[] pizzaiolos = new Pizzaiolo[3];

    //Initialize and Load Content
    @Override
    public void create() {
        semaphore = new Semaphore(1);
        batch = new SpriteBatch();

        //Initializing Objects
        pantry = new Pantry(0,0, 0, 100);
        int numberOfThings = 3;

        suppliers = new Supplier[numberOfThings];
        for (int i = 0; i < suppliers.length; i++) {
            suppliers[i] = new Supplier("Producer" + i, 1f, pantry);
        }

        pizzaiolos[0] = new Pizzaiolo("Thorfinn", "Tomato", pantry);
        pizzaiolos[1] = new Pizzaiolo("SumioMondo", "Tomato", pantry);
        pizzaiolos[2] = new Pizzaiolo("Christina", "pepperoni", pantry);


        //initialize  and start running threads
    }

    //Update and Draw
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        //Escape KEY
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Exit the application
        }


        //Define what each one will do for the sequential (will be on the 'run' in MT example)
        suppliers[0].produceIngredient(1, "dough");
        suppliers[1].produceIngredient(1, "tomato");
        suppliers[2].produceIngredient(1, "pepperoni");

        //when he's PRODUCING he already checks to CONSUME from pantry
        pizzaiolos[0].makeMyTagOfPizza();
        pizzaiolos[1].makeMyTagOfPizza();
        pizzaiolos[2].makeMyTagOfPizza();


        //semaphore here for the draw of multithreading
        batch.begin();


        batch.end();

        for (int i = 0; i < suppliers.length; i++) {
            Vector2 supplierPos = new Vector2(300,  450 - (i * 50) );
            Vector2 textPos = new Vector2(380, 500 - (i * 50) );
            suppliers[i].draw(supplierPos,textPos);
        }

        for (int i = 0; i < pizzaiolos.length; i++) {
            Vector2 pizzaioloPos = new Vector2(300,  280 - (i * 50) );
            Vector2 tagPos = new Vector2(350,  280 - (i * 50) );
            Vector2 textPos = new Vector2(420, 330 - (i * 50) );
            pizzaiolos[i].draw(pizzaioloPos, tagPos, textPos);
        }
    }

    //Unload Content
    @Override
    public void dispose() {
        batch.dispose();

        for (Supplier supplier : suppliers) {

            supplier.dispose();
        }


        //Dispose threads
    }

}
