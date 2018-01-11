import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public class GamePane extends Pane{

    private final double WIDTH;
    private final double HEIGHT;
    private final double PADDLE_W = 95;
    private final double PADDLE_H = 20;
    private final double BALL_S = 25;
    private final double BRICKS_START = 93.5;

    private Image bg;
    private Paddle paddle;
    private Ball ball;

    private CollBorder right;
    private CollBorder left;
    private CollBorder top;
    private CollBorder bottom;

    private LinkedList<Object> collidants = new LinkedList<Object>();
    private Brick[][] bricks = new Brick[7][15];


    private Canvas canvas;
    private GraphicsContext gc;

    private boolean ballReleased = false;
    private boolean keepPlaying = true;
    private boolean stop = false;

    public GamePane(double width, double height){
        WIDTH = width;
        HEIGHT = height;
        canvas = new Canvas(WIDTH, HEIGHT);

        setUp();
    }

    private void setUp(){
        setStyle("-fx-background-color: black");
        bg = new Image(new File("bg.jpg").toURI().toString());
        paddle = new Paddle(PADDLE_W, PADDLE_H, (WIDTH/2)-(PADDLE_W/2), HEIGHT-(30+PADDLE_H/2));
        ball = new Ball(BALL_S, BALL_S, paddle.getPositionX()+(PADDLE_W/2)-(BALL_S/2), paddle.getPositionY()-BALL_S );
        setBricks();

        right = CollBorderFactory.getBorderRight(WIDTH, -1, 5, HEIGHT+2);
        left = CollBorderFactory.getBorderLeft(-5, -1, 5, HEIGHT);
        top = CollBorderFactory.getBorderTop(-1,-5,WIDTH+2, 5);
        bottom = CollBorderFactory.getBorderBottom(0, HEIGHT, WIDTH+2, 5);

        collidants.addAll(Arrays.asList(new CollBorder[]{right, left, top, bottom}));
        collidants.add(paddle);

        gc = canvas.getGraphicsContext2D();

        getChildren().addAll(canvas);

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!ballReleased) {
                    ballReleased = true;
                    ball.setPositionX(ball.getPositionX()+2);
                    ball.setPositionY(ball.getPositionY()-2);
                }
            }
        });

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double pos = event.getX()-(PADDLE_W/2);
                paddle.setPositionX(pos);
            }
        });
    }

    public void loop(ScoreBoard scoreBoard, BrickBreakerGUI main){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(!ballReleased)
                    ball.setPositionX(paddle.getPositionX()+(PADDLE_W/2)-(BALL_S/2));
                else {
                    if(ball.update(collidants, bricks) == false)
                        keepPlaying = false;
                }


                gc.drawImage(bg, 0, 0);
                paddle.render(gc);
                ball.render(gc);
                drawBricks();
                setScore(scoreBoard);

                if(Integer.parseInt(scoreBoard.getScore().getText()) < 1) {
                    main.winScreen();
                    stop();
                }

                if(!keepPlaying) {
                    main.loseScreen();
                    stop();
                }
                if(stop)
                    stop();




            }
        }.start();

    }






    private void setBricks(){
        bricks[0][5] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*0), PADDLE_H*5);
        bricks[1][5] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*1), PADDLE_H*5);
        bricks[1][4] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*1), PADDLE_H*4);
        bricks[2][4] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*2), PADDLE_H*4);
        bricks[2][3] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*2), PADDLE_H*3);
        bricks[3][2] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*3), PADDLE_H*2);
        bricks[4][3] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*4), PADDLE_H*3);
        bricks[4][4] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*4), PADDLE_H*4);
        bricks[5][4] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*5), PADDLE_H*4);
        bricks[5][5] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*5), PADDLE_H*5);
        bricks[6][5] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*6), PADDLE_H*5);
        bricks[2][7] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*2), PADDLE_H*7);
        bricks[3][7] = BrickFactory.getGreenBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*3), PADDLE_H*7);
        bricks[4][7] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*4), PADDLE_H*7);

    }

    protected void drawBricks(){
        for(int i=0;i<bricks.length; i++){
            for(int j=0;j<bricks[i].length;j++){
                Brick b = bricks[i][j];
                if(b != null)
                    b.render(gc);
            }
        }
    }

    protected void setScore(ScoreBoard scoreBoard){
        int s = 0;
        for(int i=0;i<bricks.length;i++){
            for(int j=0;j<bricks[i].length;j++){
                if(bricks[i][j]!= null)
                    s++;
            }
        }
        scoreBoard.setScore(Integer.toString(s));

    }


    public double getWIDTH() {
        return WIDTH;
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    public double getPADDLE_W() {
        return PADDLE_W;
    }

    public double getPADDLE_H() {
        return PADDLE_H;
    }

    public double getBALL_S() {
        return BALL_S;
    }

    public double getBRICKS_START() {
        return BRICKS_START;
    }

    public Image getBg() {
        return bg;
    }

    public void setBg(Image bg) {
        this.bg = bg;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public CollBorder getRight() {
        return right;
    }

    public void setRight(CollBorder right) {
        this.right = right;
    }

    public CollBorder getLeft() {
        return left;
    }

    public void setLeft(CollBorder left) {
        this.left = left;
    }

    public CollBorder getTop() {
        return top;
    }

    public void setTop(CollBorder top) {
        this.top = top;
    }

    public CollBorder getBottom() {
        return bottom;
    }

    public void setBottom(CollBorder bottom) {
        this.bottom = bottom;
    }

    public LinkedList<Object> getCollidants() {
        return collidants;
    }

    public void setCollidants(LinkedList<Object> collidants) {
        this.collidants = collidants;
    }

    public Brick[][] getBricks() {
        return bricks;
    }

    public void setBricks(Brick[][] bricks) {
        this.bricks = bricks;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public boolean isBallReleased() {
        return ballReleased;
    }

    public void setBallReleased(boolean ballReleased) {
        this.ballReleased = ballReleased;
    }


    public boolean isKeepPlaying() {
        return keepPlaying;
    }

    public void setKeepPlaying(boolean keepPlaying) {
        this.keepPlaying = keepPlaying;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}

