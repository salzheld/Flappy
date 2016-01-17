package de.salzheld.flappy;

import com.badlogic.gdx.Game;

public class FlappyBird extends Game {
	public static final int WIDTH = 300;
    public static final int HEIGHT = 480;
    public static final int GROUND_LEVEL = 95;
	
	@Override
	public void create () {
        de.salzheld.flappy.utils.Assets.load();
        setScreen(new GamePlayScreen(this));
	}

    @Override
    public void dispose() {
        super.dispose();
        de.salzheld.flappy.utils.Assets.dispose();
    }
}
