package de.salzheld.flappy;

import com.badlogic.gdx.utils.Align;

/**
 * Created by Joern on 04.01.2016.
 */
public class PipePair {

    public static final float STARTING_X_POSITION = 400f;
    public static final float GAP_SIZE = 130f;

    private de.salzheld.flappy.actors.Pipe topPipe;
    private de.salzheld.flappy.actors.Pipe bottomPipe;

    public PipePair(de.salzheld.flappy.actors.Pipe topPipe, de.salzheld.flappy.actors.Pipe bottomPipe) {
        this.topPipe = topPipe;
        this.bottomPipe = bottomPipe;
    }

    public void update() {
        if(topPipe.getX(Align.right) <= 0) {
            reInitPipes();
        }

    }

    public void reInitPipes() {
        float y = de.salzheld.flappy.utils.Utils.getRandomYOpening();
        float xDisplacement = GamePlayScreen.PIPE_SPACING * GamePlayScreen.PIPE_SETS;
        bottomPipe.setPosition(bottomPipe.getX() + xDisplacement, y - GAP_SIZE / 2, Align.topLeft);
        topPipe.setPosition(topPipe.getX() + xDisplacement, y + GAP_SIZE / 2, Align.bottomLeft);
    }

    public void initPipe(int pipe) {
        float y = de.salzheld.flappy.utils.Utils.getRandomYOpening();
        pipe--;
        bottomPipe.setPosition(STARTING_X_POSITION + (GamePlayScreen.PIPE_SPACING * pipe), y - GAP_SIZE / 2, Align.topLeft);
        topPipe.setPosition(STARTING_X_POSITION + (GamePlayScreen.PIPE_SPACING * pipe), y + GAP_SIZE / 2, Align.bottomLeft);
    }

    public de.salzheld.flappy.actors.Pipe getTopPipe() {
        return topPipe;
    }

    public void setTopPipe(de.salzheld.flappy.actors.Pipe topPipe) {
        this.topPipe = topPipe;
    }

    public de.salzheld.flappy.actors.Pipe getBottomPipe() {
        return bottomPipe;
    }

    public void setBottomPipe(de.salzheld.flappy.actors.Pipe bottomPipe) {
        this.bottomPipe = bottomPipe;
    }
}
