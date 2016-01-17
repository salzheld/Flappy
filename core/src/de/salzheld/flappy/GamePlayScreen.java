package de.salzheld.flappy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.salzheld.flappy.actors.Ground;
import de.salzheld.flappy.actors.Pipe;


/**
 * Created by Joern on 03.01.2016.
 */
public class GamePlayScreen extends ScreenAdapter {
    public static final float PIPE_SPACING = 200f;
    public static final int PIPE_SETS = 3;

    protected FlappyBird game;
    protected OrthographicCamera camera;

    private Stage gameplayStage;

    private State state = State.PLAYING;
    private enum State {PLAYING, DEAD};

    private de.salzheld.flappy.actors.Bird bird;
    private Image background;
    private de.salzheld.flappy.actors.Ground ground, ground2;

    private Array<PipePair> pipePairs;


    public GamePlayScreen(FlappyBird game) {
        this.game = game;

        pipePairs = new Array<PipePair>();
        camera = new OrthographicCamera(FlappyBird.WIDTH, FlappyBird.HEIGHT);
        gameplayStage = new Stage(new StretchViewport(FlappyBird.WIDTH, FlappyBird.HEIGHT));

        bird = new de.salzheld.flappy.actors.Bird();
        bird.setPosition(FlappyBird.WIDTH * .25f, FlappyBird.HEIGHT / 2, Align.center);

        initSetOfPipes(3);

        background = new Image(de.salzheld.flappy.utils.Assets.background);
        background.setFillParent(true);
        ground = new Ground();

        gameplayStage.addActor(background);
        addPairsToStage(gameplayStage);
        gameplayStage.addActor(ground);
        gameplayStage.addActor(bird);

        camera.setToOrtho(false, FlappyBird.WIDTH, FlappyBird.HEIGHT);

        initInputProcessor();
    }

    private void addPairsToStage(Stage gameplayStage) {
        for (int i = 0; i < pipePairs.size ; i++) {
            PipePair pair = pipePairs.get(i);
            gameplayStage.addActor(pair.getBottomPipe());
            gameplayStage.addActor(pair.getTopPipe());
        }
    }

    private void initSetOfPipes(int pipes) {
        for (int i = 0; i < pipes; i++) {
            de.salzheld.flappy.actors.Pipe topPipe = new de.salzheld.flappy.actors.Pipe();
            de.salzheld.flappy.actors.Pipe bottomPipe = new de.salzheld.flappy.actors.Pipe();
            topPipe.setRotation(180);
            PipePair pair = new PipePair(topPipe, bottomPipe);
            pair.initPipe(i + 1);
            pipePairs.add(pair);
        }
    }

    private void initInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                bird.jump();
                return true;
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        de.salzheld.flappy.utils.Assets.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1f);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        switch(state) {
            case PLAYING:
                updatePipePairs();
                gameplayStage.act();
                checkCollissions();
                gameplayStage.draw();
                break;

            case DEAD:
                gameplayStage.act();
                gameplayStage.draw();
                break;

        }
    }

    private void checkCollissions() {
        if(bird.getState() == de.salzheld.flappy.actors.Bird.State.dead) {
            stopTheWorld();
        }

        for (PipePair pair : pipePairs) {
            if(pair.getBottomPipe().getBounds().overlaps(bird.getBounds())) {
                stopTheWorld();
            }
            if(pair.getTopPipe().getBounds().overlaps(bird.getBounds())) {
                stopTheWorld();
            }
        }
    }

    private void stopTheWorld() {
        bird.die();
        killPipePairs();
        killGround();
        state = State.DEAD;
    }

    private void killGround() {
        ground.stop();
    }
    private void killPipePairs() {
        for (int i = 0; i < pipePairs.size ; i++) {
          PipePair pair = pipePairs.get(i);

          pair.getBottomPipe().setState(de.salzheld.flappy.actors.Pipe.State.dead);
          pair.getTopPipe().setState(Pipe.State.dead);
        }
    }

    private void updatePipePairs() {
        for (int i = 0; i < pipePairs.size; i++) {
            pipePairs.get(i).update();
        }
            
    }
}
