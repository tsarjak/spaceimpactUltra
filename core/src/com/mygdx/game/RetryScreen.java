package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by sarjak on 20/4/17.
 */

public class RetryScreen implements Screen{

    final MyGdxGame game;
    SpriteBatch batch;
    int delay=0;
    OrthographicCamera camera;
    Texture background;
    Texture spaceShip;
    int yPosition, xPosition;
    BitmapFont font;

    public RetryScreen(final MyGdxGame game) {
        this.game = game;

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        background = new Texture("space_bg1.jpg");
        spaceShip = new Texture("fighter1.png");
        yPosition = spaceShip.getHeight()/2  - spaceShip.getHeight()/10;
        xPosition = 0;

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();
        //game.batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        font.draw(batch, "Game Over", 500, 725);
        font.draw(batch, "Score: " + String.valueOf(GameScreen.score), 500, 575);
        font.draw(batch, "Tap to Retry!", 500, 425);
        batch.end();

        if(delay>10) {
            if (Gdx.input.isTouched()) {

                game.setScreen(new GameScreen(game));
                dispose();

            }
        }
        delay++;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
