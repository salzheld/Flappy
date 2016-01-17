package de.salzheld.flappy.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.salzheld.flappy.utils.Assets;

/**
 * Created by Joern on 04.01.2016.
 */
public class Ground extends Actor {
    public static final int WIDTH = 321;
    public static final int HEIGHT = 96;

    public static final float MOVE_VELOCITY = 120f;

    private Vector2 vel;

    private TextureRegion region;

    private State state;
    private enum State { alive, dead };

    public Ground() {
        region = new TextureRegion(Assets.ground);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.alive;

        vel = new Vector2(-MOVE_VELOCITY, 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        switch(state) {
            case alive:
                actAlive(delta);
                break;

            case dead:
                vel = Vector2.Zero;
                break;
        }

    }

    private void actAlive(float delta) {
        updatePosition(delta);
        if(getX() <= -21f)
            setX(getX() + 21f);
    }

    private void updatePosition(float delta) {
        setX( getX() + vel.x * delta);
    }

    public void stop() {
        vel.x = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());

    }


}