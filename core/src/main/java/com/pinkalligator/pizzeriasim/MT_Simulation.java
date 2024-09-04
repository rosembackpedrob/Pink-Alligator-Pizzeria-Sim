package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.concurrent.Semaphore;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class MT_Simulation extends ApplicationAdapter {
    private Semaphore semaphore;
    //Semaphore int permit = 1: only 1 thread at time can have permission
    //Default boolean to true, what means the threads are accessing it
    //with FIFO policy.

    private SpriteBatch batch; //default size of batch is 1000

    int bucketThreads = 2;
    BucketThread[] myBuckets = new BucketThread[bucketThreads];

    //Initialize and Load Content
    @Override
    public void create() {
        semaphore = new Semaphore(1);
        batch = new SpriteBatch();

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

        batch.begin();


        //semaphore here for the draw of multithreads

        batch.end();
    }

    //Unload Content
    @Override
    public void dispose() {
        batch.dispose();

        //Dispose threads

    }

}
