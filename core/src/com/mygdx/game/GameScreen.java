package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


import java.util.Random;
/**
 * Created by sarjak on 20/4/17.
 */

public class GameScreen implements Screen {

    final MyGdxGame gam;

    //Variable needed for every function
    //Textures and backgrounds
    long id;
    SpriteBatch batch;
    Texture background;
    Texture spaceShip;
    Texture planets[], controls[], levelComplete[];
    Sprite controlSprite[], levelCompleteSprite[];
    static int score;
    int scoreCount;
    Music spaceShipSound;

    //Position Variables and flags
    int yPosition, xPosition;
    int xPos[],yPos[];
    int total, count, timeCount;
    boolean flag, levelFlag, upDownFlag;
    float controlAlpha, levelCompleteAlpha;

    //Random number generator
    Random randomGenerator;

    //Shapes and Shape renderer for collision detection
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    Circle spaceShipCircle = new Circle();
    Circle planetCircle[];

    BitmapFont font;


    //Initializing variables for spaceship
    public void initializePositions(){
        yPosition = spaceShip.getHeight()/2  - spaceShip.getHeight()/10;
        xPosition = 0;

    }

    //Initializing the variables for planets
    public void initialisePlanets(int last) {
        count = 0;
        flag = false;
        xPos = new int[20];
        yPos = new int[20];
        for (int i = 0; i < 20; i++) {
            if(i>12){
                int k = i - 12;
                planets[i] = new Texture("planet" + k + ".png");
            } else {
                planets[i] = new Texture("planet" + i + ".png");
            }
            //Gdx.app.log("Trial", "planet"+(i+1)+".png");
        }
        total = -1000;
        for (int i = 0; i < 20; i++) {
            xPos[i] = 1920;
            yPos[i] = randomGenerator.nextInt(11) * 100;
        }
    }

    //Initialising shapes for collision detection
    public void initialiseShapes(){
        for(int i=0;i<20;i++){
            planetCircle[i] = new Circle();
        }
    }

    public void initialiseControls(){
        controls[0] = new Texture("up.png");
        controls[1] = new Texture("down.png");
        controls[2] = new Texture("left.png");
        controls[3] = new Texture("right.png");
        controls[4] = new Texture("MoveSpaceship.png");
        controls[5] = new Texture("collision.png");
        controlAlpha = 0.4f;

        for(int i=0;i<6;i++){
            controlSprite[i] = new Sprite(controls[i]);
        }

        controlSprite[0].setPosition(500f,700f);
        controlSprite[1].setPosition(500f,250f);
        controlSprite[2].setPosition(1250f,200f);
        controlSprite[3].setPosition(1750f,200f);
        controlSprite[4].setPosition(200f,950f);
        controlSprite[5].setPosition(200f,800f);
    }

    public void initialiseLevelComplete(){
        levelComplete = new Texture[3];
        levelCompleteSprite = new Sprite[3];

        levelComplete[0] = new Texture("levelCompleted/l1comp.png");
        levelComplete[1] = new Texture("levelCompleted/goodJob.png");
        levelComplete[2] = new Texture("levelCompleted/tapToProceed.png");

        for(int i=0;i<3;i++){
            levelCompleteSprite[i] = new Sprite(levelComplete[i]);
        }

        levelCompleteSprite[0].setPosition(250f,800f);
        levelCompleteSprite[1].setPosition(250f,600f);
        levelCompleteSprite[2].setPosition(250f,400f);

        levelCompleteAlpha = 0.8f;
        upDownFlag = true;
        levelFlag = false;
    }

    public GameScreen(final MyGdxGame game) {
        this.gam = game;

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(4);
        score = 0;
        scoreCount=0;
        timeCount=0;
        batch = new SpriteBatch();
        background = new Texture("space_bg1.jpg");
        spaceShip = new Texture("fighter1.png");
        planets = new Texture[20];
        controls = new Texture[6];
        controlSprite = new Sprite[6];
        planetCircle = new Circle[20];
        randomGenerator = new Random();

        spaceShipSound = Gdx.audio.newMusic(Gdx.files.internal("spaceSound.mp3"));
        spaceShipSound.setLooping(true);
        spaceShipSound.play();
        if(spaceShipSound.isPlaying()){
            Gdx.app.log("Music", "Playing");
        }

        initialisePlanets(20); //To store planet positions randomly and fill the textures
        initializePositions(); //Resize spaceship according to the screen and position it
        initialiseShapes(); //Initialise shapes for collision detection
        initialiseControls(); //First few seconds controls on screen display
        initialiseLevelComplete(); // Initialise textures and Sprites for Level Complete Screen
    }

    @Override
    public void show() {

    }

    public void detectedCollision(){
        gam.setScreen(new RetryScreen(gam));
    }

    @Override
    public void render(float delta) {
        //For controls
        boolean left = false, right = false, up = false, down = false;

        //Begin Texture rendering
        batch.enableBlending();
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //Drawing the controls
        for(int i=0;i<6;i++){
            if(controlAlpha>0) {
                controlSprite[i].setAlpha(controlAlpha);
                if(i<4) {
                    controlSprite[i].setScale(6f, 6f);
                }
                controlSprite[i].draw(batch);
            }
        }
        if(controlAlpha>0) {
            controlAlpha -= 0.002;
        }//Reducing the Alpha value


        //Drawing the level Complete Screen
        if(levelFlag){
            if(levelCompleteAlpha == 0.8f) upDownFlag=true;
            else if(levelCompleteAlpha == 0.4f) upDownFlag=false;

            for(int i=0;i<3;i++){
                levelCompleteSprite[i].setAlpha(levelCompleteAlpha);
                levelCompleteSprite[i].draw(batch);
            }

            if(upDownFlag) levelCompleteAlpha-=0.002;
            else if(!upDownFlag) levelCompleteAlpha+=0.002;

            if (Gdx.input.isTouched()) {
                Gdx.app.log("Tapped","True");
                gam.setScreen(new LevelTwo(gam));
                dispose();
            }

        }


        if(flag == false) {
            batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
            spaceShipCircle.set(xPosition + spaceShip.getWidth() / 12, yPosition + spaceShip.getHeight() / 12, spaceShip.getWidth() / 16);
            //shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);

            //Will add more planets in the array, hence the first array will work fine (no need for complex calculations)
            //UPDATE: Added
            for (int i = 0; i <= total / 750; i++) {
                if (i < 20) {
                    xPos[i] = xPos[i] - 20;
                    batch.draw(planets[i], xPos[i], yPos[i], (float) (planets[i].getWidth() / 1.2), (float) (planets[i].getHeight() / 1.2));
                    planetCircle[i].set((float) (xPos[i] + planets[i].getWidth() / 2.4), (float) (yPos[i] + planets[i].getHeight() / 2.4), (float) (planets[i].getHeight() / 2.4));

                    //shapeRenderer.circle(planetCircle[i].x,planetCircle[i].y,planetCircle[i].radius);

                    if (Intersector.overlaps(spaceShipCircle, planetCircle[i])) {
                        Gdx.app.log("Collision", "True");
                        Gdx.input.vibrate(1000);
                        //spaceShipSound.play(1.0f);
                        spaceShipSound.stop();
                        flag = true;
                        //Call the function to restart the entire game or show level end screen whatever
                    }
                }
            }
            total += 20;
            scoreCount+=1;
            if(!levelFlag) {
                if (scoreCount > 35) {
                    scoreCount = 0;
                    score += 100;
                }
            }
            font.draw(batch,"Score: " + String.valueOf(score),100,100);

            if (total > 17000) {
                levelFlag = true;
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
                    spaceShipCircle.set(xPosition + spaceShip.getWidth() / 12, yPosition + spaceShip.getHeight() / 12, spaceShip.getWidth() / 12);
                    //shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);
                }
            }

            if (right) {
                if (xPosition < 43 * 39) {
                    xPosition += 10;
                    batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
                    spaceShipCircle.set(xPosition + spaceShip.getWidth() / 12, yPosition + spaceShip.getHeight() / 12, spaceShip.getWidth() / 12);
                    //shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);
                }
            }

            if (up) {
                if (yPosition < Gdx.graphics.getHeight() - spaceShip.getHeight() / 5) {
                    yPosition += 10;
                    batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
                    spaceShipCircle.set(xPosition + spaceShip.getWidth() / 12, yPosition + spaceShip.getHeight() / 12, spaceShip.getWidth() / 12);
                    //shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);
                }
            }

            if (down) {
                if (yPosition > 0) {
                    yPosition -= 10;
                    batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
                    spaceShipCircle.set(xPosition + spaceShip.getWidth() / 12, yPosition + spaceShip.getHeight() / 12, spaceShip.getWidth() / 12);
                    //shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);
                }
            }
        }

        else {
            batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
            for (int i = 0; i <= total / 750; i++) {
                if (i < 20) {
                    batch.draw(planets[i], xPos[i], yPos[i], (float) (planets[i].getWidth() / 1.2), (float) (planets[i].getHeight() / 1.2));
                    planetCircle[i].set((float) (xPos[i] + planets[i].getWidth() / 3), (float) (yPos[i] + planets[i].getHeight() / 3), (float) (planets[i].getHeight() / 3));

                }
            }
            timeCount++;

            if(timeCount>30){
                detectedCollision();
            }
        }
            //End texture Rendering
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
        batch.dispose();
        background.dispose();
        spaceShip.dispose();
    }
}