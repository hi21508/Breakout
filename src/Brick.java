import acm.graphics.GRect;
import java.awt.*;

public class Brick extends GRect{

    private Ball ball;
    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;
    public static final Color[] HPCOLORS = {
            Color.black,
            new Color(120, 0,0),
            new Color(190, 50,0),
            new Color(170, 100,0),
            new Color(160, 150,0),
            new Color(0, 120, 30)
    };
    private int hp;

    public Brick (double x, double y, Color color, int hitpoints, Ball ball){
        super(x, y, WIDTH, HEIGHT);
        this.setFilled(true);
        this.setFillColor(color);
        this.setColor(HPCOLORS[hitpoints]);
        this.ball = ball;
        hp = hitpoints;
    }

    public void takeDamage(){
        hp -= ball.getATK();
        if (hp<0){
            hp = 0;
        }
        this.setColor(HPCOLORS[hp]);
        if(getFillColor()==Color.red){
            hp = 0;
            ball.addAtk();
        }
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public int getHp(){
        return hp;
    }

}
