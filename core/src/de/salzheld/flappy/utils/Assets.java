package de.salzheld.flappy.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



/**
 * Created by Joern on 03.01.2016.
 */
public class Assets {

    // Disposeables
    public static TextureAtlas atlas;
    public static SpriteBatch batch;

    // Non-Disposables
    public static TextureRegion bird;
    public static TextureRegion bird1;
    public static TextureRegion bird2;
    public static TextureRegion bird3;
    public static TextureRegion background;
    public static TextureRegion ground;
    public static TextureRegion pipe;

    public static Animation birdAnimation;

    public static void load() {
        atlas = new TextureAtlas("pack.atlas");
        batch = new SpriteBatch();

        bird = atlas.findRegion("bird");

        bird1 = atlas.findRegion("bird-1");
        bird2 = atlas.findRegion("bird-2");
        bird3 = atlas.findRegion("bird-3");

        background = atlas.findRegion("bg");
        ground = atlas.findRegion("ground");
        pipe = atlas.findRegion("pipe");

        birdAnimation = new Animation(.1f, bird1, bird2, bird3);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

    }

    public static void dispose() {
        atlas.dispose();
        batch.dispose();
    }
}
