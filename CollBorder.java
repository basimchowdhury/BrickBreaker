import javafx.geometry.Rectangle2D;

public class CollBorder {

    private double x;
    private double y;
    private double w;
    private double h;

    private boolean right;
    private boolean left;
    private boolean top;
    private boolean bottom;

    private boolean paddle;
    private boolean brick;

    public CollBorder(double x, double y, double w, double h, boolean right, boolean left, boolean top, boolean bottom){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.setRight(right);
        this.setLeft(left);
        this.setTop(top);
        this.setBottom(bottom);
    }

    public CollBorder(double x, double y, double w, double h, boolean paddle, boolean brick ) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.paddle = paddle;
        this.brick = brick;
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(x, y, w, h);
    }

    public boolean intersects(CollBorder b){
        return b.getBoundary().intersects(this.getBoundary());
    }


    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public boolean isBrick() {
        return brick;
    }

    public void setBrick(boolean brick) {
        this.brick = brick;
    }

    public boolean isPaddle() {
        return paddle;
    }

    public void setPaddle(boolean paddle) {
        this.paddle = paddle;
    }
}
