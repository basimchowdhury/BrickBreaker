import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

public class DoubleGamePane extends Pane {


    private final double WIDTH;
    private final double HEIGHT;
    private final double PADDLE_W = 95;
    private final double PADDLE_H = 20;
    private final double BALL_S = 25;
    private final double BRICKS_START = 93.5;
    private final double BRICKS_START_Y = 80.5;

    private final int POINT_MULTIPLIER = 150;

    private Image bg;
    private Paddle paddle;
    private Paddle paddle2;
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
    private boolean ballReleased2 = true;
    private boolean keepPlaying = true;
    private boolean stop = false;

    public DoubleGamePane(double width, double height){
        WIDTH = width;
        HEIGHT = height;
        canvas = new Canvas(WIDTH, HEIGHT);

        setUp();
    }

    private void setUp(){
        bg = new Image(new File("bg.jpg").toURI().toString());
        paddle = new Paddle(PADDLE_W, PADDLE_H, (WIDTH/2)-(PADDLE_W/2), HEIGHT-(30+PADDLE_H/2), true);
        paddle2 = new Paddle(PADDLE_W, PADDLE_H, (WIDTH/2)-(PADDLE_W/2), 30+PADDLE_H/2, false);
        ball = new Ball(BALL_S, BALL_S, paddle.getPositionX()+(PADDLE_W/2)-(BALL_S/2), paddle.getPositionY()-BALL_S, true );
        setBricks();

        right = CollBorderFactory.getBorderRight(WIDTH, -1, 5, HEIGHT+2);
        left = CollBorderFactory.getBorderLeft(-5, -1, 5, HEIGHT);
        top = CollBorderFactory.getBorderTop(-1,-5,WIDTH+2, 5);
        bottom = CollBorderFactory.getBorderBottom(0, HEIGHT, WIDTH+2, 5);

        collidants.addAll(Arrays.asList(new CollBorder[]{right, left, top, bottom}));
        collidants.add(paddle);
        collidants.add(paddle2);

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

    public void setKeys(Scene scene){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT && paddle2.getPositionX() > -PADDLE_W/2) {
                        paddle2.setPositionX(paddle2.getPositionX() - 90);
                }
                if (event.getCode() == KeyCode.RIGHT && paddle2.getPositionX() < WIDTH-(PADDLE_W)) {
                        paddle2.setPositionX(paddle2.getPositionX() + 90);
                }
                if(event.getCode() == KeyCode.A) {
                    ballReleased2 = true;
                    ball.setPositionX(ball.getPositionX()+2);
                    ball.setPositionY(ball.getPositionY()+2);
                }


            }

        });
    }

    public void loop(ScoreBoard scoreBoard, BrickBreakerGUI main){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(!ballReleased)
                    ball.setPositionX(paddle.getPositionX()+(PADDLE_W/2)-(BALL_S/2));
                else if(!ballReleased2) {
                    ball.setPositionX(paddle2.getPositionX() + (PADDLE_W / 2) - (BALL_S / 2));
                    ball.setPositionY(paddle2.getPositionY() + BALL_S-5);
                }
                else {
                    if(ball.update(collidants, bricks) == false)
                        keepPlaying = false;
                }

                gc.drawImage(bg, 0, 0);
                paddle.render(gc);
                paddle2.render(gc);
                ball.render(gc);
                drawBricks();
                setScore(scoreBoard);

                if(!keepPlaying){
                    determineWinner(scoreBoard, main);
                    stop();
                }

                if(bricksEmpty()) {
                    setBricks2();
                    ballReleased2=false;
                }
            }
        }.start();

    }

    private boolean bricksEmpty() {
        for(int i=0;i<bricks.length;i++){
            for(int j=0;j<bricks[i].length;j++){
                if(bricks[i][j] != null)
                    return false;
            }
        }
        return true;
    }

    private void determineWinner(ScoreBoard scoreBoard, BrickBreakerGUI main) {
        int score1 = Integer.parseInt(scoreBoard.getScore().getText());
        int score2 = Integer.parseInt(scoreBoard.getScore2().getText());

        if(ball.getPm().didPlayer1Win())
            score1 += 5000;
        else
            score2 += 5000;

        if(score1 > score2) {
            main.twoPlayerWin(1);
            scoreBoard.setScore(Integer.toString(score1));
        }
        else if(score1 < score2) {
            main.twoPlayerWin(2);
            scoreBoard.setScore2(Integer.toString(score2));
        }
        else
            main.twoPlayerWin(0);


    }

    private void setBricks(){
        bricks[0][7] = BrickFactory.getGreenBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*0), BRICKS_START_Y+PADDLE_H*7);
        bricks[1][7] = BrickFactory.getGreenBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*1), BRICKS_START_Y+PADDLE_H*7);
        bricks[2][5] = BrickFactory.getYellowBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*2), BRICKS_START_Y+PADDLE_H*5);
        bricks[3][6] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*3), BRICKS_START_Y+PADDLE_H*6);
        bricks[4][5] = BrickFactory.getYellowBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*4), BRICKS_START_Y+PADDLE_H*5);
        bricks[5][7] = BrickFactory.getGreenBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*5), BRICKS_START_Y+PADDLE_H*7);
        bricks[6][7] = BrickFactory.getGreenBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*6), BRICKS_START_Y+PADDLE_H*7);
        bricks[2][9] = BrickFactory.getYellowBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*2), BRICKS_START_Y+PADDLE_H*9);
        bricks[3][8] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*3), BRICKS_START_Y+PADDLE_H*8);
        bricks[4][9] = BrickFactory.getYellowBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*4), BRICKS_START_Y+PADDLE_H*9);
    }

    private void setBricks2(){
        bricks[0][7] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*0), BRICKS_START_Y+PADDLE_H*4);
        bricks[0][8] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*0), BRICKS_START_Y+PADDLE_H*11);
        bricks[1][5] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*1), BRICKS_START_Y+PADDLE_H*4);
        bricks[1][6] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*1), BRICKS_START_Y+PADDLE_H*10);
        bricks[2][5] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*2), BRICKS_START_Y+PADDLE_H*5);
        bricks[2][7] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*2), BRICKS_START_Y+PADDLE_H*9);
        bricks[3][7] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*3), BRICKS_START_Y+PADDLE_H*7);
        bricks[3][9] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*3), BRICKS_START_Y+PADDLE_H*8);
        bricks[4][8] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*4), BRICKS_START_Y+PADDLE_H*6);
        bricks[4][9] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*4), BRICKS_START_Y+PADDLE_H*9);
        bricks[5][7] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*5), BRICKS_START_Y+PADDLE_H*5);
        bricks[5][9] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*5), BRICKS_START_Y+PADDLE_H*10);
        bricks[6][8] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*6), BRICKS_START_Y+PADDLE_H*4);
        bricks[6][9] = BrickFactory.getRedBrick(PADDLE_W, PADDLE_H, BRICKS_START+(PADDLE_W*6), BRICKS_START_Y+PADDLE_H*11);


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
        scoreBoard.setScore(Integer.toString(ball.getPm().getPlayer1Count()*POINT_MULTIPLIER));
        scoreBoard.setScore2(Integer.toString(ball.getPm().getPlayer2Count()*POINT_MULTIPLIER));
    }


}
