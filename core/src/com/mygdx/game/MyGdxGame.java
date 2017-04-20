package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Random;

public class MyGdxGame extends Game {

    //Needed variables
    SpriteBatch batch;
	Texture background;
	Texture spaceShip;
    int yPosition, xPosition;
    Texture planets[];
    int xPos[],yPos[];
    int total, count;
    int flag;
    Random randomGenerator;

    //Shapes for collision detection
   /* Circle spaceeShipCircle = new Circle();
    ShapeRenderer shapeRenderer = new ShapeRenderer();*/


    //Initializing variables for spaceship
    public void initializePositions(){
        yPosition = spaceShip.getHeight()/2  - spaceShip.getHeight()/10;
        xPosition = 0;

    }

    //Initializing the variables for planets
    public void initialisePlanets(int last){
        count = 0;
        flag = 1;
        xPos = new int[10];
        yPos = new int[10];
        for(int i=0;i<7;i++){
            planets[i] = new Texture("planet"+i+".png");
            //Gdx.app.log("Trial", "planet"+(i+1)+".png");
        }
        total = 0;
        for(int i=0;i<10;i++){
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

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("space_bg1.jpg");
		spaceShip = new Texture("fighter1.png");
        planets = new Texture[10];
        randomGenerator = new Random();
        initialisePlanets(7);
        initializePositions();
	}

	@Override
	public void render () {

        boolean left = false, right = false, up = false, down = false;
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(spaceShip,xPosition,yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

            //Will add more planets in the array, hence the first array will work fine (no need for complex calculations)
            for(int i=0;i<=total/750;i++) {
                if(i<7) {
                    batch.draw(planets[i], xPos[i], yPos[i], (float) (planets[i].getWidth() / 1.5), (float) (planets[i].getHeight() / 1.5));
                    xPos[i] = xPos[i] - 15;
                }
            }
            total += 15;

            /*if(total>5249 || flag == 1){
                if(flag == 0){
                    initializePlanetPosition(0,5);
                    total = 0;
                }
                flag = 1;
                for(int j=5;j<7;j++){
                    batch.draw(planets[j], xPos[j], yPos[j], (float) (planets[j].getWidth() / 1.5), (float) (planets[j].getHeight() / 1.5));
                    xPos[j] = xPos[j] - 15;
                }
            }*/

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
                }
            }

            if(right){
                if (xPosition < 43 * 39){
                        xPosition += 10;
                        batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

                }
            }

            if(up){
            if (yPosition < Gdx.graphics.getHeight() - spaceShip.getHeight()/5){
                yPosition += 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

                }
            }

            if(down){
            if (yPosition > 0){
                yPosition -= 10;
                batch.draw(spaceShip, xPosition, yPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

                }
            }

        batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		spaceShip.dispose();
	}
}
