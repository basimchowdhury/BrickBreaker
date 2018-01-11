import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class BrickBreakerGUI extends Application{


    private final double WIDTH = 852;
    private final double HEIGHT = 480;
//    private static MediaPlayer mediaPlayer;


    private BorderPane pane = new BorderPane();
    private DoubleGamePane doublePane;

    private Scene scene;
    private boolean keepPlaying;



    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        mainMenu();

        scene = new Scene(pane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Brick Breaker");
        primaryStage.setResizable(false);
        primaryStage.show();


    }

    public void mainMenu(){
        if(pane.getCenter() instanceof GamePane){
            ((GamePane) pane.getCenter()).setStop(true);
        }


//        Media song = new Media(new File("song.mp3").toURI().toString());
//        if(mediaPlayer == null)
//            mediaPlayer = new MediaPlayer(song);
//        mediaPlayer.play();
//        mediaPlayer.setOnEndOfMedia(new Runnable() {
//            @Override
//            public void run() {
//                mediaPlayer.seek(Duration.ZERO);
//            }
//        });

        ImageView img = new ImageView(new Image(new File("main-bg.jpg").toURI().toString()));
        img.setFitHeight(HEIGHT);
        img.setFitWidth(WIDTH);

        ImageView sgButton = new ImageView(new Image(new File("1.png").toURI().toString()));
        sgButton.setFitWidth(230);
        sgButton.setFitHeight(40);
        sgButton.setOnMouseClicked(event -> playSingle());

        ImageView dgButton = new ImageView(new Image(new File("2.png").toURI().toString()));
        dgButton.setFitWidth(230);
        dgButton.setFitHeight(40);
        dgButton.setOnMouseClicked(event -> playDouble());


        StackPane base = new StackPane();
        VBox buttons = new VBox(50, sgButton, dgButton);

        buttons.setTranslateX(600);
        buttons.setTranslateY(300);

        base.getChildren().addAll(img, buttons);

        pane.setCenter(base);

        Pane padding = new Pane();
        padding.setMinSize(120,480);
        pane.setRight(padding);

    }

    public void loseScreen(){
        ImageView retryButton = new ImageView(new Image(new File("tryagain.png").toURI().toString()));
        retryButton.setFitWidth(230);
        retryButton.setFitHeight(40);
        retryButton.setOnMouseClicked(event -> playSingle());

        ImageView mmButton = new ImageView(new Image(new File("mainmenu.png").toURI().toString()));
        mmButton.setFitWidth(230);
        mmButton.setFitHeight(40);
        mmButton.setOnMouseClicked(event -> mainMenu());

        ImageView gameOver = new ImageView(new Image(new File("gameOver.png").toURI().toString()));
        gameOver.setFitWidth(300);
        gameOver.setFitHeight(60);


        StackPane base = new StackPane();
        base.setStyle("-fx-background-color: black");
        VBox buttons = new VBox(50, retryButton, mmButton);

        buttons.setTranslateX(600);
        buttons.setTranslateY(300);



        base.getChildren().addAll(gameOver, buttons);

        pane.setCenter(base);

    }

    public void winScreen() {
        ImageView retryButton = new ImageView(new Image(new File("playAgain.png").toURI().toString()));
        retryButton.setFitWidth(230);
        retryButton.setFitHeight(40);
        retryButton.setOnMouseClicked(event -> playSingle());

        ImageView mmButton = new ImageView(new Image(new File("mainmenu.png").toURI().toString()));
        mmButton.setFitWidth(230);
        mmButton.setFitHeight(40);
        mmButton.setOnMouseClicked(event -> mainMenu());

        ImageView gameOver = new ImageView(new Image(new File("youWin.png").toURI().toString()));
        gameOver.setFitWidth(300);
        gameOver.setFitHeight(60);


        StackPane base = new StackPane();
        base.setStyle("-fx-background-color: black");
        VBox buttons = new VBox(50, retryButton, mmButton);

        buttons.setTranslateX(600);
        buttons.setTranslateY(300);


        base.getChildren().addAll(gameOver, buttons);

        pane.setCenter(base);

        Pane padding = new Pane();
        padding.setMinSize(120, 480);
        pane.setRight(padding);
    }

    public void twoPlayerWin(int situation){
        ImageView retryButton = new ImageView(new Image(new File("playAgain.png").toURI().toString()));
        retryButton.setFitWidth(230);
        retryButton.setFitHeight(40);
        retryButton.setOnMouseClicked(event -> playDouble());

        ImageView mmButton = new ImageView(new Image(new File("mainmenu.png").toURI().toString()));
        mmButton.setFitWidth(230);
        mmButton.setFitHeight(40);
        mmButton.setOnMouseClicked(event -> mainMenu());

        ImageView gameOver;
        if(situation == 1) {
            gameOver = new ImageView(new Image(new File("player1.png").toURI().toString()));
        }else if(situation == 2){
           gameOver = new ImageView(new Image(new File("player2.png").toURI().toString()));
        }else
            gameOver = new ImageView(new Image(new File("draw.png").toURI().toString()));
        gameOver.setFitWidth(300);
        gameOver.setFitHeight(60);


        StackPane base = new StackPane();
        base.setStyle("-fx-background-color: black");
        VBox buttons = new VBox(50, retryButton, mmButton);

        buttons.setTranslateX(600);
        buttons.setTranslateY(300);


        base.getChildren().addAll(gameOver, buttons);

        pane.setCenter(base);

    }


    private void playSingle() {
        ScoreBoard scoreBoard = new ScoreBoard(false, this);
        GamePane gamePane = new GamePane(WIDTH, HEIGHT);

        pane.setLeft(null);
        pane.setRight(scoreBoard);
        pane.setCenter(gamePane);
        gamePane.loop(scoreBoard, this);

    }

    private void playDouble(){
        ScoreBoard scoreBoard = new ScoreBoard(true, this);
        doublePane = new DoubleGamePane(WIDTH, HEIGHT);

        pane.setLeft(null);
        pane.setRight(scoreBoard);
        pane.setCenter(doublePane);
        doublePane.setKeys(scene);
        doublePane.loop(scoreBoard, this);
    }


}
