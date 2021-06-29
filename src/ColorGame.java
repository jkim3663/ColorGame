import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Random;
import java.util.ArrayList;

/**
 * Driver class that use all classes and methods.
 *
 * @author Jun Yeop Kim
 * @version 1.0
 */
public class ColorGame extends Application {
    private int score;
    private int cnt = 0;

    private String name;
    private String begin;

    private Button color1;
    private Button color2;
    private Button color3;
    private Button color4;

    private TextField tfMessage;

    private Label scoreLabel;
    private Label btmText;
    private Label nameLabel;
    private ArrayList<Button> buttons;

    /**
     * Main method to interact with program.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method that makes JavaFX work.
     *
     * @param primaryStage the Stage for the GUI program
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Color Game!");
        name = "None";
        score = 0;
        Insets insets = new Insets(10);

        //Layout 1
        Image image = new Image("colors.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(210);
        Label intro = new Label("Welcome to the Color Game!");
        Label help = new Label("Inside the game, the top box is the 'question' box!");
        Label help2 = new Label("Inside the game, boxes below are the 'answer' boxes!");
        Label help3 = new Label("The text from the question box should match the answer box's color!");
        Label help4 = new Label("There could be multiple answers, or 'none' !");
        Label help5 = new Label("Please enjoy the game!");
        intro.setAlignment(Pos.CENTER);
        intro.setFont(Font.font("Arial", 12));
        help.setFont(Font.font("Arial", 12));
        help2.setFont(Font.font("Arial", 12));
        help3.setFont(Font.font("Arial", 12));
        help4.setFont(Font.font("Arial", 12));
        help5.setFont(Font.font("Arial", 12));

        Button change = new Button("Click here to join the game!");
        change.setFont(Font.font("Arial", 12));
        change.setAlignment(Pos.CENTER);
        change.setTextFill(Color.BLUEVIOLET);
        VBox hiBox = new VBox();
        hiBox.setAlignment(Pos.CENTER);
        hiBox.getChildren().addAll(intro, help, help2, help3, help4, help5, change, imageView);
        hiBox.setSpacing(20);
        VBox.setMargin(hiBox, insets);
        Scene tempScene = new Scene(hiBox, 400, 400);
        primaryStage.setScene(tempScene);

        TopPane tp = new TopPane();
        CenterPane cp = new CenterPane();
        BottomPane bp = new BottomPane();
        bp.setSpacing(10);


        BorderPane root = new BorderPane();
        BorderPane.setMargin(tp, insets);
        BorderPane.setMargin(cp, insets);
        BorderPane.setMargin(bp, insets);

        root.setTop(tp);
        root.setCenter(cp);
        root.setBottom(bp);

        Scene scene = new Scene(root, 300, 250);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                nameLabel.setText("Name: " + tfMessage.getText());
            }
        });

        // Switching scenes
        change.setOnAction(e -> primaryStage.setScene(scene));

        primaryStage.show();
    }


    class TopPane extends VBox {
        TopPane() {
            nameLabel = new Label("Name: " + name);
            nameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            setSpacing(10);

            getChildren().add(nameLabel);
            setAlignment(Pos.CENTER);
        }
    }

    class CenterPane extends VBox {
        CenterPane() {

            Button enter = new Button("Enter");

            tfMessage = new TextField("Enter your name here");
            tfMessage.setEditable(true);
            tfMessage.setFont(Font.font("Arial", 11));
            tfMessage.setStyle("-fx-text-fill: gray");
            tfMessage.setAlignment(Pos.BASELINE_LEFT);

            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER);
            hbox.getChildren().addAll(tfMessage, enter);

            scoreLabel = new Label("\nScore: " + score);
            scoreLabel.setFont(Font.font("Arial", 12));
            getChildren().addAll(hbox, scoreLabel);
            setAlignment(Pos.TOP_CENTER);

            enter.setOnAction(e -> {
                nameLabel.setText("Name: " + tfMessage.getText());
            });

            enter.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    nameLabel.setText("Name: " + tfMessage.getText());
                }
            });
        }
    }

    class BottomPane extends VBox {
        BottomPane() {
            buttons = new ArrayList<>();
//            cnt = 0;
            begin = "Choose an answer to begin!";

            color1 = new Button(getRandomColor());
            color1.setStyle("-fx-background-color: " + getRandomColor());
            color1.setTextFill(getTextColor());

            color2 = new Button(getRandomColor());
            color2.setStyle("-fx-background-color: " + getRandomColor());
            color2.setTextFill(getTextColor());
            color2.setOnAction(e -> handleButton2());

            color3 = new Button(getRandomColor());
            color3.setStyle("-fx-background-color: " + getRandomColor());
            color3.setTextFill(getTextColor());
            color3.setOnAction(e -> handleButton3());

            color4 = new Button(getRandomColor());
            color4.setStyle("-fx-background-color: " + getRandomColor());
            color4.setTextFill(getTextColor());
            color4.setOnAction(e -> handleButton4());

            buttons.add(color2);
            buttons.add(color3);
            buttons.add(color4);

            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setFont(Font.font("Arial", 11));
                if (buttons.get(i).getStyle().equals(("-fx-background-color: " + color1.getText()))) {
                    cnt++;
                }
            }

            Button reset = new Button("Reset");
            reset.setOnAction(e -> reset());
            reset.setOnKeyPressed(e -> {
                if (e.getCode().equals("Enter")) {
                    reset();
                }
            });

            Button none = new Button("None");
            none.setOnAction(e -> handleNone());

            btmText = new Label(begin);
            btmText.setFont(Font.font("Arial", 12));

            HBox hbox1 = new HBox(color1, reset);
            hbox1.setAlignment(Pos.CENTER);
            hbox1.setSpacing(10);

            HBox hbox2 = new HBox(color2, color3, color4, none);
            hbox2.setAlignment(Pos.CENTER);
            hbox2.setSpacing(10);

            HBox hbox3 = new HBox(btmText);
            hbox3.setAlignment(Pos.CENTER);
            hbox3.setSpacing(10);

            getChildren().addAll(hbox1, hbox2, hbox3);

        }
    }

    /**
     * Method for handling button when pressed.
     */
    public void handleButton2() {
        if (color2.getStyle().equals(("-fx-background-color: " + color1.getText()))) {
            score++;
            scoreLabel.setText("\nScore: " + score);
            getBtmText().setText("Correct!");

            cnt = 0;
            color1.setStyle("-fx-background-color: " + getRandomColor());
            color1.setText(getRandomColor());
            color1.setTextFill(getTextColor());
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setStyle("-fx-background-color: " + getRandomColor());
                buttons.get(i).setText(getRandomColor());
                buttons.get(i).setTextFill(getTextColor());
            }

            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getStyle().equals(("-fx-background-color: " + color1.getText()))) {
                    cnt++;
                }
            }
        } else {
            score = 0;
            scoreLabel.setText("\nScore: " + score);
            begin = "Incorrect :(";
            getBtmText().setText(begin);
        }
    }

    /**
     * Method for handling button when pressed.
     */
    public void handleButton3() {
        if (color3.getStyle().equals(("-fx-background-color: " + color1.getText()))) {
            score++;
            scoreLabel.setText("\nScore: " + score);
            getBtmText().setText("Correct!");

            cnt = 0;
            color1.setStyle("-fx-background-color: " + getRandomColor());
            color1.setText(getRandomColor());
            color1.setTextFill(getTextColor());
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setStyle("-fx-background-color: " + getRandomColor());
                buttons.get(i).setText(getRandomColor());
                buttons.get(i).setTextFill(getTextColor());
            }

            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getStyle().equals(("-fx-background-color: " + color1.getText()))) {
                    cnt++;
                }
            }
        } else {
            score = 0;
            scoreLabel.setText("\nScore: " + score);
            begin = "Incorrect :(";
            getBtmText().setText(begin);
        }
    }

    /**
     * Method for handling button when pressed.
     */
    public void handleButton4() {
        if (color4.getStyle().equals(("-fx-background-color: " + color1.getText()))) {
            score++;
            scoreLabel.setText("\nScore: " + score);
            getBtmText().setText("Correct!");

            cnt = 0;
            color1.setStyle("-fx-background-color: " + getRandomColor());
            color1.setText(getRandomColor());
            color1.setTextFill(getTextColor());
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setStyle("-fx-background-color: " + getRandomColor());
                buttons.get(i).setText(getRandomColor());
                buttons.get(i).setTextFill(getTextColor());
            }

            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getStyle().equals(("-fx-background-color: " + color1.getText()))) {
                    cnt++;
                }
            }
        } else {
            score = 0;
            scoreLabel.setText("\nScore: " + score);
            begin = "Incorrect :(";
            getBtmText().setText(begin);
        }
    }

    /**
     * Method for handling button when pressed.
     */
    public void handleNone() {
        if (cnt == 0) {
            score++;
            getScoreLabel().setText("\nScore: " + score);
            getBtmText().setText("Correct! (none)");
            cnt = 0;
            color1.setStyle("-fx-background-color: " + getRandomColor());
            color1.setText(getRandomColor());
            color1.setTextFill(getTextColor());

            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setStyle("-fx-background-color: " + getRandomColor());
                buttons.get(i).setText(getRandomColor());
                buttons.get(i).setTextFill(getTextColor());
            }

            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getStyle().equals(("-fx-background-color: " + color1.getText()))) {
                    cnt++;
                }
            }
        } else {
            score = 0;
            begin = "Incorrect :(";
            getScoreLabel().setText("\nScore: " + score);
            getBtmText().setText(begin);
        }
    }

    /**
     * Method for resetting the variables.
     */
    public void reset() {
        score = 0;
        begin = "Choose an answer to begin!";
        name = "None";
        cnt = 0;
        getScoreLabel().setText("\nScore: " + score);
        getBtmText().setText(begin);
        getNameLabel().setText("Name: " + name);
        getTextField().setText("Enter your name here");

        color1.setStyle("-fx-background-color: " + getRandomColor());
        color1.setTextFill(getTextColor());
        color1.setText(getRandomColor());

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setStyle("-fx-background-color: " + getRandomColor());
            buttons.get(i).setText(getRandomColor());
            buttons.get(i).setTextFill(getTextColor());
        }

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getStyle().equals(("-fx-background-color: " + color1.getText()))) {
                cnt++;
            }
        }
    }

    /**
     * Returns the string representation of random color.
     *
     * @return random string color
     */
    public String getRandomColor() {
        Random rd = new Random();
        String[] colors = {"Red", "Orange", "Yellow", "Purple", "Green"};
        int x = rd.nextInt(colors.length);
        return colors[x];
    }

    /**
     * Returns the random text color.
     *
     * @return random string color
     */
    public Color getTextColor() {
        Random rd = new Random();
        Color[] colors = {Color.BLACK, Color.BROWN, Color.BLACK, Color.GRAY, Color.LIGHTSKYBLUE};
        int x = rd.nextInt(colors.length);
        return colors[x];
    }

    /**
     * Returns the TextField that enters name.
     *
     * @return TextField for name.
     */
    public TextField getTextField() {
        return tfMessage;
    }

    /**
     * Returns the Label for score.
     *
     * @return Label for score
     */
    public Label getScoreLabel() {
        return scoreLabel;
    }

    /**
     * Returns the Label for bottom text.
     *
     * @return Label for bottom text
     */
    public Label getBtmText() {
        return btmText;
    }

    /**
     * Returns the Label for name.
     *
     * @return Label for name
     */
    public Label getNameLabel() {
        return nameLabel;
    }

}
