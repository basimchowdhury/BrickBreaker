import java.util.LinkedList;
import java.util.Random;

public class PhysicsModule {

    private double velocityX;
    private double velocityY;
    private Random random = new Random();

    private boolean playingWithTwo;
    private boolean player1 = true;
    private boolean keepPlaying = true;
    private boolean player1Win;

    private int player1Count = 0;
    private int player2Count = 0;

    public PhysicsModule(){
        velocityX = 5.0;
        velocityY = -5.0;
    }
    public PhysicsModule(boolean two){
        velocityX = 5.0;
        velocityY = -5.0;
        playingWithTwo = two;
    }

    public boolean interact(CollBorder r, CollBorder l, CollBorder t, CollBorder b, LinkedList<Object> collidants){
        for(Object obj: collidants) {
            CollBorder border;
            if(obj instanceof Paddle) {
                bouncePaddle(r,l,t,b,(Paddle)obj);
            }
            else {
                border = (CollBorder) obj;
                bounce(r,l,t,b,border);
            }
            }

            return keepPlaying;
    }

    private void bouncePaddle(CollBorder r, CollBorder l, CollBorder t, CollBorder b, Paddle paddle){
        CollBorder border = paddle.getCollBorder();
        if (r.intersects(border) || l.intersects(border)) {
            velocityX = -velocityX;
            if(paddle.isPlayer1())
                player1 = true;
            else
                player1 = false;
        }else if (t.intersects(border) || b.intersects(border)) {
            velocityY = -velocityY;
            if(velocityX > 0)
                velocityX = (random.nextDouble()*4)+1;
            else
                velocityX = -(random.nextDouble()*4)-1;
            if(paddle.isPlayer1())
                player1 = true;
            else
                player1 = false;
        }
    }

    private boolean bounce(CollBorder r, CollBorder l, CollBorder t, CollBorder b, CollBorder border){
        if (r.intersects(border) || l.intersects(border)) {
            velocityX = -velocityX;
            return true;
        }else if (t.intersects(border) || b.intersects(border)) {
            velocityY = -velocityY;
            if(border.isBottom()) {
                keepPlaying = false;
                player1Win = false;
            }
            if(playingWithTwo && border.isTop()) {
                keepPlaying = false;
                player1Win = true;
            }
            return true;
        }
        return false;
    }


    public void interactBricks(CollBorder r, CollBorder l, CollBorder t, CollBorder b, Brick[][] bricks){

        for(int i=0;i<bricks.length;i++){
            for(int j=0;j<bricks[i].length;j++){
                Brick brick = bricks[i][j];
                if(brick!=null) {
                    if(bounce(r,l,t,b,brick.getCollBorder()) == true)
                    {
                        brick.damage();
                        givePoints();
                        if (brick.getLife() < 1) {
                            bricks[i][j] = null;
                        }
                    }
                }
            }
        }
    }

    private void givePoints() {
        if(player1)
            player1Count++;
        else
            player2Count++;
    }


    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isPlayer1() {
        return player1;
    }

    public void setPlayer1(boolean player1) {
        this.player1 = player1;
    }

    public int getPlayer1Count() {
        return player1Count;
    }

    public void setPlayer1Count(int player1Count) {
        this.player1Count = player1Count;
    }

    public int getPlayer2Count() {
        return player2Count;
    }

    public void setPlayer2Count(int player2Count) {
        this.player2Count = player2Count;
    }

    public boolean didPlayer1Win() {
        return player1Win;
    }

    public void setPlayer1Win(boolean player1Win) {
        this.player1Win = player1Win;
    }
}
