import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class Paddle extends Sprite{

    private boolean player1;

    public Paddle(double width, double height, double positionX, double positionY) {
        super(positionX, width, positionY, height);
        image = new Image(new File("paddle1.png").toURI().toString());

    }

    public Paddle(double width, double height, double positionX, double positionY, boolean player1) {
        super(positionX, width, positionY, height);
        image = new Image(new File("paddle1.png").toURI().toString());
        this.player1 = player1;
    }

    public CollBorder getCollBorder(){
        return CollBorderFactory.getBorderPaddle(positionX, positionY, width, height);
    }

    public boolean isPlayer1() {
        return player1;
    }

    public void setPlayer1(boolean player1) {
        this.player1 = player1;
    }
}
