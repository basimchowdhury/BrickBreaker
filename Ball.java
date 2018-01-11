import javafx.scene.image.Image;
import java.io.File;
import java.util.LinkedList;


public class Ball extends Sprite {

    private PhysicsModule pm;





    public Ball(double width, double height, double positionX, double positionY){
        super(positionX, width, positionY, height);
        image = new Image(new File("aqua-ball.png").toURI().toString());
        pm = new PhysicsModule();

    }

    public Ball(double width, double height, double positionX, double positionY, boolean two){
        super(positionX, width, positionY, height);
        image = new Image(new File("aqua-ball.png").toURI().toString());
        pm = new PhysicsModule(two);
    }

    public boolean update(LinkedList<Object> collidants, Brick[][] bricks){
        positionX += pm.getVelocityX();
        positionY += pm.getVelocityY();

        if(pm.interact(getRight(), getLeft(), getTop(), getBottom(), collidants) == false)
            return false;
        pm.interactBricks(getRight(), getLeft(), getTop(), getBottom(), bricks);


        return true;
    }

    private CollBorder getRight(){
        return CollBorderFactory.getBorderRight(positionX+width, positionY+6.5, 0, height-13);
    }

    private CollBorder getLeft(){
        return CollBorderFactory.getBorderLeft(positionX, positionY+6.5, 0, height-13);
    }

    private CollBorder getTop(){
        return CollBorderFactory.getBorderTop(positionX+6.5, positionY, width-13, 0);
    }

    private CollBorder getBottom(){
        return CollBorderFactory.getBorderBottom(positionX+6.5, positionY+height, width-13, 0);
    }


    public PhysicsModule getPm() {
        return pm;
    }

    public void setPm(PhysicsModule pm) {
        this.pm = pm;
    }
}
