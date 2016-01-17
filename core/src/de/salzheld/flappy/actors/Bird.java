package de.salzheld.flappy.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;

import de.salzheld.flappy.utils.Assets;
import de.salzheld.flappy.FlappyBird;

/**
 * Created by Joern on 03.01.2016.
 */
public class Bird extends Actor {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    public static final float GRAVITY = 780f;
    public static final float JUMP_VELOCITY = 310f;

    private Vector2 vel;
    private Vector2 accel;

    private TextureRegion region;

    private float time;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private State state;
    public enum State { alive, dead };

    private Rectangle bounds;

    public Bird() {
        region = new TextureRegion(Assets.bird);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        state = State.alive;

        vel = new Vector2(0, 0);
        accel = new Vector2(0, -GRAVITY);

        bounds = new Rectangle(0, 0, WIDTH, HEIGHT);

        // defines the center for rotation
        setOrigin(Align.center);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        switch(state) {
            case alive:

                actAlive(delta);
                time += delta;
                region = Assets.birdAnimation.getKeyFrame(time);
                break;

            case dead:
                vel.x = 0;
                accel = Vector2.Zero;
                accel.y = -GRAVITY;

                applyAccel(delta);
                updatePosition(delta);

                if(isBelowGround())
                    setY(FlappyBird.GROUND_LEVEL);

                break;
        }

        updateBounds();

    }

    private void updateBounds() {
        bounds.x = getX();
        bounds.y = getY();
    }

    private void actAlive(float delta) {
        applyAccel(delta);
        updatePosition(delta);

//        setRotation(MathUtils.clamp(vel.y / JUMP_VELOCITY * 45f, -90f, 45f));

        if(isBelowGround()) {
            setY(FlappyBird.GROUND_LEVEL);
            die();
        }

        if(isAboveCeiling()) {
            setPosition(getX(), FlappyBird.HEIGHT, Align.topLeft);
            die();
        }
    }

    public void die() {
        state = State.dead;
        clearActions();
        vel.y = 0;
    }

    private boolean isBelowGround() {
        return (getY(Align.bottom) <= FlappyBird.GROUND_LEVEL);
    }

    private boolean isAboveCeiling() {
        return (getY(Align.top) >= FlappyBird.HEIGHT);
    }

    private void updatePosition(float delta) {
        setX( getX() + vel.x * delta);
        setY( getY() + vel.y * delta);
    }

    private void applyAccel(float delta) {
        vel.add(accel.x * delta, accel.y * delta);
    }

    public void jump() {
        if(state == State.alive) {
            vel.y = JUMP_VELOCITY;

            clearActions();
            RotateToAction a1 = Actions.rotateTo(45, .1f);
            DelayAction a2 = Actions.delay(.2f);
            RotateToAction a3 = Actions.rotateTo(-90, .5f);

            SequenceAction sequenceAction = Actions.sequence(a1, a2, a3);
            addAction(sequenceAction);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());

    }


}
