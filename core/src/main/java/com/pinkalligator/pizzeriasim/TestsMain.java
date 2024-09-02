package com.pinkalligator.pizzeriasim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class TestsMain extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    BucketThread myBucket;

    //Initialize and Load Content
    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        myBucket = new BucketThread("bucket.png", new Vector2(0,0));
    }

    //Update and Draw
    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();
        batch.draw(image, 140, 210);

        myBucket.draw(batch);
        myBucket.update();

        batch.end();
    }

    //Unload Content
    @Override
    public void dispose() {
        batch.dispose();

        myBucket.dispose();

        image.dispose();
    }
}
