package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.Semaphore;

public class BucketThread extends Thread {
    private volatile boolean running = true; // Ensure visibility across threads

    private Texture bucketImage;
    private Vector2  position;
    //libgdx works with a bottom-left UV coordinate system

    private Semaphore semaphore;

    @Override
    public void run() {
        update();//what the thread is doing
        //System.out.print("thread is running!");
    }

    //default constructor
    public BucketThread(String imagePath, Vector2 position) {
        this.bucketImage = new Texture(imagePath);
        this.position = position;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.bucketImage, position.x, position.y);
    }

    public void update() {
        //move loop
        while (running) {
            try {
                //is not problematic to sleep for 16ms?
                Thread.sleep(16); // Approx 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.position.x += 100 * Gdx.graphics.getDeltaTime();
            //System.out.println(this.position.x);
        }
    }

    public void dispose() {
        bucketImage.dispose();
    }

    public void stopRunning() {
        running = false;
        //interrupt();
    }

    public void printPosition() {
        System.out.println(position.x);
    }

}
