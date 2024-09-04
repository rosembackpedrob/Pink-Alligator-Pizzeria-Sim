package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;
import java.util.concurrent.Semaphore;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class MT_Simulation extends ApplicationAdapter {

    private long startMultTime;
    private long elapsedTime;
    private long endMultTime;
    private Semaphore semaphore;
    //Semaphore int permit = 1: only 1 thread at time can have permission
    //Default boolean to true, what means the threads are accessing it
    //with FIFO policy.

    private SpriteBatch batch; //default size of batch is 1000
    private Random randomProdTime = new Random();

    //Objects
    int numberofSupplier;
    Pantry pantry;
    MT_Supplier[] suppliers;
    MT_Pizzaiolo[] pizzaiolos = new MT_Pizzaiolo[5];

    //Initialize and Load Content
    @Override
    public void create() {
        semaphore = new Semaphore(1);
        batch = new SpriteBatch();

        //Constructing Objects
        pantry = new Pantry(0,0, 0, 100);
        numberofSupplier = 3;

        //test start
        startMultTime = System.currentTimeMillis(); //tempo sequencial inicial

        suppliers = new MT_Supplier[numberofSupplier];
        float prodTime = randomProdTime.nextFloat(0.5f, 2f);
        suppliers[0] = new MT_Supplier("DoughProducer", prodTime, pantry,3,"dough");
        suppliers[1] = new MT_Supplier("TomatoProducer", prodTime, pantry,2,"tomato");
        suppliers[2] = new MT_Supplier("PepperoniProducer", prodTime, pantry,1,"pepperoni");


        pizzaiolos[0] = new MT_Pizzaiolo("Thorfinn", "TomatoPizza", pantry);
        pizzaiolos[1] = new MT_Pizzaiolo("SumioMondo", "TomatoPizza", pantry);
        pizzaiolos[2] = new MT_Pizzaiolo("Christina", "PepperoniPizza", pantry);
        pizzaiolos[3] = new MT_Pizzaiolo("CarlosRosa", "PepperoniPizza", pantry);
        pizzaiolos[4] = new MT_Pizzaiolo("Rosaria", "PepperoniPizza", pantry);


        for (MT_Supplier supplier : suppliers) {
            supplier.start();
        }
        for (MT_Pizzaiolo pizzaiolo : pizzaiolos) {
            pizzaiolo.start();
        }

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
        //check to stop app
        if((pizzaiolos[4].getPizzaStackSize() == 1)) {
            endMultTime = System.currentTimeMillis();
            elapsedTime = endMultTime - startMultTime;
            //Gdx.app.exit();
            //System.out.println(elapsedTime);
        }
        long printElapsedTime = elapsedTime; //elapsedTime at the EXACT moment it entered the if
        BitmapFont font = new BitmapFont();
        Vector2 fontPos = new Vector2(600f,80f);
        batch.begin();
        font.draw(batch,"Sequential Time: " + printElapsedTime, fontPos.x, fontPos.y);
        batch.end();

        //Draws: //semaphore here for the draw of multithreading
        batch.begin();
        batch.end();

        //semaphores for draw recommended
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        semaphore.release();
    }

    //Unload Content
    @Override
    public void dispose() {
        batch.dispose();

        pantry.dispose();

        for (MT_Supplier supplier : suppliers) {
            supplier.stopRunning();
            supplier.dispose();
            try {
                supplier.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (MT_Pizzaiolo pizzaiolo : pizzaiolos) {
            pizzaiolo.stopRunning();
            pizzaiolo.dispose();
            try {
                pizzaiolo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //Dispose threads
    }

}
