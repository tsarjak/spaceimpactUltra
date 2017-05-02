package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by sarjak on 20/4/17.
 */

public class MainScreen implements Screen{

    boolean playFlag = true;
    final MyGdxGame game;
    Texture background;
    Texture spaceShip, mute, unmute;
    int yPosition, xPosition;
    SpriteBatch batch;
    BitmapFont font;
    Music mainBackground;
    int timeDelay = 0;

    OrthographicCamera camera;

    public MainScreen(final MyGdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        background = new Texture("space_bg1.jpg");
        spaceShip = new Texture("fighter1.png");
        mute = new Texture("mute.png");
        unmute = new Texture("unmute.png");
        yPosition = spaceShip.getHeight()/2  - spaceShip.getHeight()/10;
        xPosition = 0;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5);
        mainBackground =  Gdx.audio.newMusic(Gdx.files.internal("mainBackground.ogg"));


        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800, 480);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        timeDelay += 1;
        //Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();
        //game.batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

        if(playFlag==true) {
            batch.draw(unmute, 900, 200, Gdx.graphics.getWidth() / 16, Gdx.graphics.getHeight() / 8);
            mainBackground.play();
        }else{
            batch.draw(mute, 900, 200, Gdx.graphics.getWidth() / 16, Gdx.graphics.getHeight() / 8);
            mainBackground.stop();
        }
        font.draw(batch, "Welcome to Space Impact Ultra!!! ", 500, 725);
        font.draw(batch, "Tap anywhere to begin!", 500, 575);
        font.draw(batch, "Get a Score of 2500 to finish level", 500, 425);
        batch.end();

        if (Gdx.input.isTouched()) {
            int iX = Gdx.input.getX();
            int iY = Gdx.input.getY();
            //Gdx.app.log("Position x, y:",String.valueOf(iX) + "," + String.valueOf(iY));
            if(iX > 800 && iX < 1200 && iY > 750 && iY < 1100) {


                if (timeDelay > 10) {
                    if (playFlag == true) {
                        playFlag = false;
                    } else {
                        playFlag = true;
                    }
                timeDelay = 0;
                }
            }
            else {
                    game.setScreen(new GameScreen(game));
                    mainBackground.stop();
            }

        }
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
