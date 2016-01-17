package de.salzheld.flappy.utils;

import com.badlogic.gdx.math.MathUtils;

import de.salzheld.flappy.FlappyBird;

/**
 * Created by Joern on 04.01.2016.
 */
public class Utils {
    public static float getRandomYOpening() {
        return MathUtils.random(FlappyBird.HEIGHT * .15f, FlappyBird.HEIGHT * .85f);
    }
}