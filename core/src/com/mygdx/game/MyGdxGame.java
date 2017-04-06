package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture spaceShip;
    int xPosition, yPosition;
    Texture planets[];
    int xPos[],yPos[];
    int total;
    Random randomGenerator;


    //Initializing variables for spaceship
    public void initializePositions(){
        xPosition = spaceShip.getHeight()/2  - spaceShip.getHeight()/10;
        yPosition = 0;

    }

    //Initializing the variables for planets
    public void initialisePlanets(){
        xPos = new int[10];
        yPos = new int[10];
        for(int i=0;i<6;i++){
            planets[i] = new Texture("planet"+i+".png");
            //Gdx.app.log("Trial", "planet"+(i+1)+".png");
        }
        //planets[0] = new Texture("planet1.png");
        total = 0;
        for(int i=0;i<10;i++){
            xPos[i] = 1920;
            yPos[i] = randomGenerator.nextInt(11) * 100;
        }
    }


    //Drawing planets that move from right to right
    public void drawPlanets(){

    }

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("space_bg1.jpg");
		spaceShip = new Texture("fighter1.png");
        planets = new Texture[10];
        randomGenerator = new Random();
        initialisePlanets();
        initializePositions();
	}

	@Override
	public void render () {
        boolean left = false, right = false, up = false, down = false;
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(spaceShip,yPosition,xPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);


            int i=0;
            batch.draw(planets[i],xPos[i],yPos[i],planets[i].getWidth()/2, planets[i].getHeight()/2);
            xPos[i] = xPos[i] - 20;
           // total += 20;


        /*batch.draw(planets[1],xPos[1],yPos[1],planets[1].getWidth()/2, planets[1].getHeight()/2);
        xPos[1] = xPos[1] - 20;
        */batch.draw(planets[2],xPos[2],yPos[2],planets[2].getWidth()/2, planets[2].getHeight()/2);
        xPos[2] = xPos[2] - 10;
        /*batch.draw(planets[3],xPos[3],yPos[3],planets[3].getWidth()/2, planets[3].getHeight()/2);
        xPos[1] = xPos[3] - 20;*/


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
                if (yPosition > 0) { //43 inputs at +=40 will move the spaceship to the end of the screen
                        yPosition -= 10;
                        batch.draw(spaceShip, yPosition, xPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
                }
            }

            if(right){
                if (yPosition < 43 * 39){
                        yPosition += 10;
                        batch.draw(spaceShip, yPosition, xPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

                }
            }

            if(up){
            if (xPosition < Gdx.graphics.getHeight() - spaceShip.getHeight()/5){
                xPosition += 10;
                batch.draw(spaceShip, yPosition, xPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

                }
            }

            if(down){
            if (xPosition > 0){
                xPosition -= 10;
                batch.draw(spaceShip, yPosition, xPosition, Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);

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
