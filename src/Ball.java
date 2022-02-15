import acm.graphics.GCanvas;
import acm.graphics.GOval;

public class Ball extends GOval {

    private double deltaX = 1;
    private double deltaY = -1;
    private GCanvas screen;
    public boolean lost = false;
    private int atk = 1;

    public Ball(double x, double y, double size, GCanvas screen){
        super(x, y, size, size);
        setFilled(true);
        this.screen = screen;
    }

    public void handleMove(){
        // move the ball
        move(deltaX, -deltaY);

        // check to see if the ball is too high
        if(getY() <= 0){
            deltaY *= -1;
            // start moving down
        }

        // check to see if the ball is too low
        if(getY() >= screen.getHeight() - getHeight()){
            lost = true;
            deltaX = 1;
            deltaY = -1;
            // lose a life
        }

        // check to see if the ball hits the left side of the screen
        if(getX() <= 0){
            deltaX = 1;
            // start moving right
        }

        // check to see if the ball hits the right side of the screen
        if(getX() >= screen.getWidth()-getWidth()){
            deltaX = -1;
            // start moving left
        }
    }

    public void bounce(){
        //flip the sign of deltaY
        deltaY *= -1;
    }

    public void bounceLeft(){
        //flip the sign of deltaY
        deltaY *= -1;
        //set the sign to negative
        deltaX = -Math.abs(deltaX);
    }

    public void bounceRight(){
        //flip the sign of deltaY
        deltaY *= -1;
        //set the sign to positive
        deltaX = Math.abs(deltaX);
    }

    public int getATK(){
        return atk;
    }

    public void addAtk() {
        this.atk += 1;
    }
}
