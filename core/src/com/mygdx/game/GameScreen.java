package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
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
    SpriteBatch batch;
    Texture background;
    Texture spaceShip;
    Texture planets[];

    //Position Variables and flags
    int yPosition, xPosition;
    int xPos[],yPos[];
    int total, count;
    int flag;

    //Random number generator
    Random randomGenerator;

    //Shapes and Shape renderer for collision detection
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    Circle spaceShipCircle = new Circle();
    Circle planetCircle = new Circle();


    //Initializing variables for spaceship
    public void initializePositions(){
        yPosition = spaceShip.getHeight()/2  - spaceShip.getHeight()/10;
        xPosition = 0;

    }

    //Initializing the variables for planets
    public void initialisePlanets(int last){
        count = 0;
        flag = 1;
        xPos = new int[13];
        yPos = new int[13];
        for(int i=0;i<13;i++){
            planets[i] = new Texture("planet"+i+".png");
            //Gdx.app.log("Trial", "planet"+(i+1)+".png");
        }
        total = 0;
        for(int i=0;i<13;i++){
            xPos[i] = 1920;
            yPos[i] = randomGenerator.nextInt(11) * 100;
        }
    }

    public void initializePlanetPosition(int first, int last){
        for(int i=first;i<last;i++){
            xPos[i] = 1920;
            yPos[i] = randomGenerator.nextInt(11) * 100;
        }
    }


    //Drawing planets that move from right to left
    public void drawPlanets(){

    }

    public GameScreen(final MyGdxGame game) {
        this.gam = game;

        batch = new SpriteBatch();
        background = new Texture("space_bg1.jpg");
        spaceShip = new Texture("fighter1.png");
        planets = new Texture[13];
        randomGenerator = new Random();
        initialisePlanets(13);
        initializePositions();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //For controls
        boolean left = false, right = false, up = false, down = false;

        //Start shape rendering
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //Begin Texture rendering
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        batch.draw(spaceShip,xPosition,yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
        spaceShipCircle.set(xPosition + spaceShip.getWidth()/12, yPosition + spaceShip.getHeight()/12, spaceShip.getWidth()/12);
        shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);

        //Will add more planets in the array, hence the first array will work fine (no need for complex calculations)
        //UPDATE: Added
        for(int i=0;i<=total/750;i++) {
            if(i<13) {
                xPos[i] = xPos[i] - 15;
                batch.draw(planets[i], xPos[i], yPos[i], (float) (planets[i].getWidth() / 1.5), (float) (planets[i].getHeight() / 1.5));
                planetCircle.set((float) (xPos[i] + planets[i].getWidth()/3), (float) (yPos[i] + planets[i].getHeight()/3),(float) (planets[i].getHeight()/3));
                shapeRenderer.circle(planetCircle.x,planetCircle.y,planetCircle.radius);
            }
        }
        total += 15;

        if(total > 10920){
            //Show monster and start the fight!
        }


        //Getting touch Inputs
        for(int j=0;j<2;j++) {
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

        if(left) {
            if (xPosition > 0) { //43 inputs at +=40 will move the spaceship to the end of the screen
                xPosition -= 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
                spaceShipCircle.set(xPosition + spaceShip.getWidth()/12, yPosition + spaceShip.getHeight()/12, spaceShip.getWidth()/12);
                shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);
            }
        }

        if(right){
            if (xPosition < 43 * 39){
                xPosition += 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
                spaceShipCircle.set(xPosition + spaceShip.getWidth()/12, yPosition + spaceShip.getHeight()/12, spaceShip.getWidth()/12);
                shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);
            }
        }

        if(up){
            if (yPosition < Gdx.graphics.getHeight() - spaceShip.getHeight()/5){
                yPosition += 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
                spaceShipCircle.set(xPosition + spaceShip.getWidth()/12, yPosition + spaceShip.getHeight()/12, spaceShip.getWidth()/12);
                shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);
            }
        }

        if(down){
            if (yPosition > 0){
                yPosition -= 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
                spaceShipCircle.set(xPosition + spaceShip.getWidth()/12, yPosition + spaceShip.getHeight()/12, spaceShip.getWidth()/12);
                shapeRenderer.circle(spaceShipCircle.x,spaceShipCircle.y,spaceShipCircle.radius);
            }
        }

        //End texture Rendering
        batch.end();



        //shapeRenderer.setColor(Color.BLUE);





        //End Shape Rendering
        shapeRenderer.end();
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
