import javafx.scene.image.Image;

import java.io.File;

public class Brick extends Sprite{


    private Image[] sprites = new Image[4];

    private int life;

    public Brick(double width, double height, double positionX, double positionY, int type){
        super(positionX, width, positionY, height);
        sprites[0] = new Image(new File("greenbrick.png").toURI().toString());
        sprites[1] = new Image(new File("yellowbrick.png").toURI().toString());
        sprites[2] = new Image(new File("redbrick.png").toURI().toString());
        sprites[3] = new Image(new File("blackbrick.png").toURI().toString());

        life = type;
        image = sprites[type-1];
    }

    public CollBorder getCollBorder(){
        return CollBorderFactory.getBorderPaddle(positionX, positionY, width, height);
    }

    public void damage(){
        if(life < 4) {
            life--;
            System.out.print("Life: " + life);
            updateSprite();
        }
    }

    private void updateSprite(){
        if(life>=1)
            image = sprites[life -1];
    }


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
