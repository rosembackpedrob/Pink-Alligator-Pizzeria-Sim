package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BucketThread extends Thread {
    private volatile boolean running = true; // Ensure visibility across threads

    private Texture bucketImage;
    private Vector2  position;
    //libgdx works with a bottom-left UV coordinate system

    //question? I really need a run method in this case? for why?
    //and should I do a Join on the Main while these threads run?
    //I believe it is a problem if we do the start on the Initialize because
    //then the run will only happen one time, 'cause it's out of the game loop
    //am I right?
    //but when I put it on render it crashes
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
                Thread.sleep(16); // Approx 60 FPS
                synchronized (position) {
                    this.position.x += 100 * Gdx.graphics.getDeltaTime();
                    //System.out.println(this.position.x);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt(); //Interrupt if exception
            }
        }
    }

    public void dispose() {
        bucketImage.dispose();
    }

    public void stopRunning() {
        running = false;
        interrupt();
    }

    public void printPosition() {
        System.out.println(position.x);
    }


}
