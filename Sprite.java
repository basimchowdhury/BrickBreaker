import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class Sprite {

    protected Image image;
    protected double width;
    protected double height;
    protected double positionX;
    protected double positionY;

    public Sprite(double positionX, double width, double positionY, double height) {

        this.positionX = positionX;
        this.width = width;
        this.positionY = positionY;
        this.height = height;
    }

    public void render(GraphicsContext gc){
        gc.drawImage(image, positionX, positionY, width, height);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
        this.image = image;
    }
}
