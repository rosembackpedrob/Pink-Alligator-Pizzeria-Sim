package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.concurrent.Semaphore;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class MT_TestsMain extends ApplicationAdapter {
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
        for (int i = 0; i < bucketThreads; i++) {
            myBuckets[i] =
                new BucketThread("bucket.png", new Vector2(0f, (float)i * 80) );
            myBuckets[i].start();
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

        batch.begin();

        //was recommended to add a semaphore in the draw here
        // and (not in the thead class)
        //but then the buckets start getting one behind of another
        //and I think they shouln't, at least for when they're in
        // the threads size of computer.
        //but checking by the printPosition it may be correct,
        // 'cause sometimes they do get ahead of each other
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bucketThreads; i++) {
            myBuckets[i].draw(batch);
            //myBuckets[i].printPosition();
        }
        semaphore.release();

        batch.end();
    }

    //Unload Content
    @Override
    public void dispose() {
        batch.dispose();

        //Dispose threads
        for (int i = 0; i < bucketThreads; i++) {
            myBuckets[i].stopRunning();
            myBuckets[i].dispose();

            //Join Threads After Disposing
            try {
                myBuckets[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
