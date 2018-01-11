public class BrickFactory {

    public static Brick getGreenBrick(double width, double height, double positionX, double positionY){
        return new Brick(width,height, positionX, positionY, 1);
    }

    public static Brick getYellowBrick(double width, double height, double positionX, double positionY){
        return new Brick(width,height, positionX, positionY, 2);
    }

    public static Brick getRedBrick(double width, double height, double positionX, double positionY){
        return new Brick(width,height, positionX, positionY, 3);
    }

    public static Brick getBlackBrick(double width, double height, double positionX, double positionY){
        return new Brick(width,height, positionX, positionY, 4);
    }

}

