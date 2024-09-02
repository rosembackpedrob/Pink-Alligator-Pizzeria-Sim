package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class MT_TestsMain extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    int numThreads = 4;
    BucketThread[] myBuckets = new BucketThread[numThreads];

    //Initialize and Load Content
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");

        //initialize threads
        for (int i = 0; i < numThreads; i++) {
            Vector2 defaultPosition = new Vector2(0, 0);
            myBuckets[i] =
                new BucketThread("bucket.png", new Vector2(0f, (float)i * 80) );
        }

        //start running the threads
        for (int i = 0; i < numThreads; i++) {
            myBuckets[i].start();
        }
    }

    //Update and Draw
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit(); // Exit the application
        }

        batch.begin();
        batch.draw(image, 140, 210);

        //If we did the below without the loop on Bucket Thread
        //It didn't move, because the run, only ran 1 time
        //And also, if we just used the myBuckets.update method directly
        //we wouldn't be concurrent anymore.
        for (int i = 0; i < numThreads; i++) {
            myBuckets[i].draw(batch);
            //myBuckets[i].printPosition();
        }

        //the below, with join, is not effective because it will not load.
        //why exactly? Is because it's stoping this code until all threads
        //are done, but they are NEVER done because of the loop?
        /*
        for (int i = 0; i < numThreads; i++) {
            try {
                carThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            myBuckets[i].draw(batch);
            //myBuckets[i].printPosition();
        }
        */
        batch.end();
    }

    //Unload Content
    @Override
    public void dispose() {
        batch.dispose();

        for (int i = 0; i < numThreads; i++) {
            myBuckets[i].dispose();
            myBuckets[i].stopRunning();

            //recommended to join after stopping
            //question: all the RED exceptions about interruptions are fine?
            try {
                myBuckets[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        image.dispose();
    }
}
