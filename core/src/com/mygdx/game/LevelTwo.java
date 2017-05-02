package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

/**
 * Created by sarjak on 22/4/17.
 */

public class LevelTwo implements Screen {

    final MyGdxGame gam;



    SpriteBatch batch;
    Texture background;
    Texture spaceShip;
    Texture meteors[];
    int meteorFlag, meteorPos[];
    Random randomGenerator;

    //Position Variables and flags
    int yPosition, xPosition;


    //Shapes and Shape renderer for collision detection
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    Circle spaceShipCircle = new Circle();

    public void initializePositions(){
        yPosition = spaceShip.getHeight()/2  - spaceShip.getHeight()/10;
        xPosition = 0;

    }

    public void initializeMeteors(){
        meteors = new Texture[11];
        for(int i=1;i<10;i++){
            meteors[i-1] = new Texture("meteors/meteor000" + i + ".png");
        }
        //meteors[9] = new Texture("meteors/meteor0010");
        //meteors[10] = new Texture("meteors/meteor0011");

        meteorPos = new int[11];


        meteorFlag = 0;
    }

    @Override
    public void show() {

    }

    public LevelTwo(final MyGdxGame game){
        this.gam = game;

        batch = new SpriteBatch();
        background = new Texture("space_bg1.jpg");
        spaceShip = new Texture("fighter1.png");
        randomGenerator = new Random();
        initializeMeteors();
    }

    @Override
    public void render(float delta) {

        meteorFlag += 1;
        boolean left = false, right = false, up = false, down = false;

        //Begin Texture rendering
        batch.enableBlending();
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        spaceShipCircle.set(xPosition + spaceShip.getWidth() / 12, yPosition + spaceShip.getHeight() / 12, spaceShip.getWidth() / 16);

        if(meteorFlag/40==1){

        }

        //Getting touch Inputs
        for (int j = 0; j < 2; j++) {
            if (Gdx.input.isTouched(j)) {
                int iX = Gdx.input.getX(j);
                int iY = Gdx.input.getY(j);

                if (iX > 1100 && iX < 1500) {
                    left = true;
                } else if (iX > 1500 && iX < 1900) {
                    right = true;
                } else if (iX > 0 && iX < 800 && iY < Gdx.graphics.getHeight() / 2) {
                    up = true;
                } else if (iX > 0 && iX < 800 && iY > Gdx.graphics.getHeight() / 2) {
                    down = true;
                }
            }
        }

        if (left) {
            if (xPosition > 0) { //43 inputs at +=40 will move the spaceship to the end of the screen
                xPosition -= 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

            }
        }

        if (right) {
            if (xPosition < 43 * 39) {
                xPosition += 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

            }
        }

        if (up) {
            if (yPosition < Gdx.graphics.getHeight() - spaceShip.getHeight() / 5) {
                yPosition += 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

            }
        }

        if (down) {
            if (yPosition > 0) {
                yPosition -= 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

            }
        }

        batch.end();

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
