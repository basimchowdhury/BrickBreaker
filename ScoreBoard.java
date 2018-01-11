
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreBoard extends Pane {

    private Label score;
    private Label score2;
    private Button button;

    private boolean twoPlayer;


    public ScoreBoard(boolean two, BrickBreakerGUI main){
        score = new Label();
        twoPlayer = two;
        if(twoPlayer)
            setUpTwo(main);
        else
            setUp(main);
    }

    private void setUp(BrickBreakerGUI main) {
        Label label = new Label("Bricks Left:    ");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        score.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        label.setTextFill(Color.WHITE);
        score.setTextFill(Color.WHITE);

        button = new Button("Back");
        button.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        button.setTextFill(Color.BLACK);
        button.setOnAction(event -> main.mainMenu());

        VBox box = new VBox(50,label, score, button);
        box.setTranslateY(100);
        setStyle("-fx-background-color: black");

        getChildren().add(box);
    }

    private void setUpTwo(BrickBreakerGUI main) {
        score2 = new Label();
        Label label = new Label("Player 1 \n Score:              ");
        Label label2 = new Label("Player 2 \n Score:             ");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        label2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        score.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        score2.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        label.setTextFill(Color.WHITE);
        label2.setTextFill(Color.WHITE);
        score.setTextFill(Color.WHITE);
        score2.setTextFill(Color.WHITE);

        Button button = new Button("Back");
        button.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        button.setTextFill(Color.BLACK);
        button.setOnAction(event -> main.mainMenu());

        VBox box = new VBox(50,label, score, label2, score2, button);
        box.setTranslateY(100);
        setStyle("-fx-background-color: black");

        getChildren().add(box);
    }

    public boolean isTwoPlayer() {
        return twoPlayer;
    }

    public void setTwoPlayer(boolean twoPlayer) {
        this.twoPlayer = twoPlayer;
    }

    public Label getScore() {
        return score;
    }

    public Label getScore2(){
        return score2;
    }

    public void setScore(String score) {
        this.score.setText(score);
    }

    public void setScore2(String score){
        this.score2.setText(score);
    }

    public void removeBack(){
        getChildren().remove(button);
    }
}
