import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import java.util.Random;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends GraphicsProgram {

    /*
    lives
    number of hits
    labels for lives and points
    when you run out of lives
    how to tell if a brick was hit

    powerups
    levels
    animation
     */

    private Ball ball;
    private Paddle paddle;
    private int lives;
    private GLabel gLives;
    private Random rand;
    private int bricksremaining = 0;

    private int numBricksInRow;

    private Color[] rowColors = {Color.CYAN,Color.CYAN,Color.orange, Color.orange, Color.gray, Color.gray, Color.blue, Color.blue, Color.darkGray, Color.darkGray};

    @Override
    public void init(){

        rand = new Random();

        numBricksInRow = getWidth() / (Brick.WIDTH + 5);

        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {

                double brickX = 10 + col * (Brick.WIDTH +5);
                double brickY = Brick.HEIGHT + row *(Brick.HEIGHT+5);

                int hp;
                switch (row) {
                    case 0: hp = rand.nextInt(2)+1;
                        break;
                    case 1: hp = rand.nextInt(2)+1;
                        break;
                    case 2: hp = 1;
                        break;
                    case 3: hp = 1;
                        break;
                    case 4: hp = rand.nextInt(2)+ rand.nextInt(2)+1;
                        break;
                    case 5: hp = rand.nextInt(2)+ rand.nextInt(2)+1;
                        break;
                    case 6: hp = 1;
                        break;
                    case 7: hp = 1;
                        break;
                    case 8: hp = rand.nextInt(4)+2;
                        break;
                    case 9: hp = rand.nextInt(4)+2;
                        break;
                    default: hp = 1;
                }

                Brick brick = new Brick(brickX,brickY, rowColors[row], hp, ball);
                if (rand.nextInt(40)==1) {
                    brick.setFillColor(Color.red);
                }
                add(brick);
                bricksremaining += 1;

            }
        }
        lives = 5;

        gLives = new GLabel(lives+" Lives");
        add(gLives,5,12);

        paddle = new Paddle(230, 430, 50 ,10);
        add(paddle);

    }

    @Override
    public void run(){
        addMouseListeners();
        pause(1000);
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me){
        // make sure that the paddle doesn't go offscreen
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth() / 2)){
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }

    // runs the main game loop
    private void gameLoop(){
        while (true){
            // move the ball
            ball.handleMove();
            handleCollisions();
            pause(5);

            //checks if the ball is lost
            if(ball.lost){
                handleLoss();
            }
            //
        }
    }


    private void handleLoss() {
        lives -= 1;
        gLives.setLabel(lives+" Lives");
        if(lives == 0){
            youLose();
        }
        ball.lost = false;
        reset();
    }

    private void youLose(){
        removeAll();

        GLabel losslabel = new GLabel("Game Over");
        losslabel.setFont("Impact-BOLD-48");

        add(losslabel,getWidth()/2-losslabel.getWidth()/2, getHeight()/2-losslabel.getHeight()/2);
    }

    private void youWin(){
        removeAll();

        GLabel winlabel = new GLabel("You Win! Click to continue.");
        winlabel.setFont("Impact-BOLD-30");

        add(winlabel, getWidth()/2-winlabel.getWidth()/2, getHeight()/2-winlabel.getHeight()/2);
        waitForClick();
        remove(winlabel);
        init();
    }

    private void handleCollisions(){
        //create a container
        GObject obj = null;
        int ObjectHit;

        //see if we hit something
        if (obj == null) {
            //check upper right corner
            obj = this.getElementAt(ball.getX() + ball.getWidth(), +ball.getY());
        }
        //check upper left corner
        if(obj==null) {
            obj = this.getElementAt(ball.getX(), +ball.getY());
        }
            //check bottom right corner
        if (obj == null) {
            obj = this.getElementAt(ball.getX() + ball.getWidth(), +ball.getY() + ball.getHeight());
        }
            //check bottom left corner
        if(obj == null){
            obj = this.getElementAt(ball.getX(),+ball.getY()+ball.getHeight());
        }
        //find out what that was
        if(obj != null){
            if (obj instanceof Paddle){

                if (ball.getX()< (paddle.getX()+paddle.getWidth()*.2)){
                    ball.bounceLeft();
                }else if(ball.getX()>(paddle.getX()+paddle.getWidth()*.8)){
                    ball.bounceRight();
                }else{
                    ball.bounce();
                }
            }

            if (obj instanceof Brick){
                ((Brick) obj).setBall(ball);
                //ball bounces
                ball.bounce();
                //destroy brick
                ((Brick) obj).takeDamage();
                if(((Brick) obj).getHp() < 1) {
                    this.remove(obj);
                    bricksremaining -= 1;
                    if(bricksremaining <=4){
                        youWin();
                    }
                }
            }
        }


        //react accordingly

        //if obj is still null, we hit nothing
    }

    private void reset(){
        //reset the ball position
        ball.setLocation(getWidth()/2,350 );
        // reset the paddle position
        paddle.setLocation(230, 430);
        //wait for  the next click
        waitForClick();
    }

    public static void main(String[] args) {
        new Breakout().start();
    }

}