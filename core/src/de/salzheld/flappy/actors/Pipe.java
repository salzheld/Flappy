package de.salzheld.flappy.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import de.salzheld.flappy.utils.Assets;

/**
 * Created by Joern on 04.01.2016.
 */
public class Pipe extends Actor {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 400;

    public static final float MOVE_VELOCITY = 120f;

    private Vector2 vel;
    private TextureRegion region;
    private Rectangle bounds;

    public State state;
    public enum State { alive, dead;};

    public Pipe() {
        region = new TextureRegion(Assets.pipe);
        setWidth(WIDTH);
        setHeight(HEIGHT);

        bounds = new Rectangle(0,0, WIDTH, HEIGHT);

        state = State.alive;

        vel = new Vector2(-MOVE_VELOCITY, 0);

        // defines the center for rotation
        setOrigin(Align.center);
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

        updateBounds();
    }

    private void updateBounds() {
        bounds.x = getX();
        bounds.y = getY();
    }

    private void actAlive(float delta) {
        updatePosition(delta);
    }

    private void updatePosition(float delta) {
        setX( getX() + vel.x * delta);
        setY(getY() + vel.y * delta);
    }

    public void setState(State state) {
        this.state = state;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());

    }


}
