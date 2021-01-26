package xo.game.views;

import xo.game.dataTypes.Moves;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;

import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import xo.game.controller.ReplayController;
import xo.game.controller.SinglePlayerController;
import xo.game.controller.XOGame;

public class ReplayXOBoardBase extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final ColumnConstraints columnConstraints0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final Button r0c0 = new Button();
    protected final Button r0c1 = new Button();
    protected final Button r0c2 = new Button();
    protected final Button r1c0 = new Button();
    protected final Button r1c1 = new Button();
    protected final Button r1c2 = new Button();
    protected final Button r2c0 = new Button();
    protected final Button r2c1 = new Button();
    protected final Button r2c2 = new Button();
    private Stage primaryStage;
    private Text endGameText;
    private static ReplayXOBoardBase singlePlayerBoard;

    // Game state gives a state about a given button if;
    // 0-------------> the button is free;
    // 1-------------> the button has an X or is played by player1 ;
    //-1------------> the button has an O or is played by player 2
    //===========================================================================
    protected int[][] gameState = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    public Button[][] btns = {
        {r0c0, r0c1, r0c2},
        {r1c0, r1c1, r1c2},
        {r2c0, r2c1, r2c2}
    };

    private ReplayXOBoardBase() {

        columnConstraints = new ColumnConstraints();
        columnConstraints0 = new ColumnConstraints();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(600.0);
        setPrefWidth(600.0);
        setHgap(10.0);
        setVgap(10.0);
        setStyle("-fx-background-color:#efefef ; -fx-background-radius:10px");

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(100.0);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(100.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        r0c0.setMnemonicParsing(false);
        r0c0.setPrefHeight(405.0);
        r0c0.setPrefWidth(340.0);

        GridPane.setColumnIndex(r0c1, 1);
        r0c1.setMnemonicParsing(false);
        r0c1.setPrefHeight(265.0);
        r0c1.setPrefWidth(363.0);

        GridPane.setColumnIndex(r0c2, 2);
        r0c2.setMnemonicParsing(false);
        r0c2.setPrefHeight(227.0);
        r0c2.setPrefWidth(234.0);

        GridPane.setRowIndex(r1c0, 1);
        r1c0.setMnemonicParsing(false);
        r1c0.setPrefHeight(231.0);
        r1c0.setPrefWidth(291.0);

        GridPane.setColumnIndex(r1c1, 1);
        GridPane.setRowIndex(r1c1, 1);
        r1c1.setMnemonicParsing(false);
        r1c1.setPrefHeight(211.0);
        r1c1.setPrefWidth(317.0);

        GridPane.setColumnIndex(r1c2, 2);
        GridPane.setRowIndex(r1c2, 1);
        r1c2.setMnemonicParsing(false);
        r1c2.setPrefHeight(255.0);
        r1c2.setPrefWidth(267.0);

        GridPane.setRowIndex(r2c0, 2);
        r2c0.setMnemonicParsing(false);
        r2c0.setPrefHeight(228.0);
        r2c0.setPrefWidth(305.0);

        GridPane.setColumnIndex(r2c1, 1);
        GridPane.setRowIndex(r2c1, 2);
        r2c1.setMnemonicParsing(false);
        r2c1.setPrefHeight(274.0);
        r2c1.setPrefWidth(260.0);

        GridPane.setColumnIndex(r2c2, 2);
        GridPane.setRowIndex(r2c2, 2);
        r2c2.setMnemonicParsing(false);
        r2c2.setPrefHeight(232.0);
        r2c2.setPrefWidth(252.0);

        getColumnConstraints().add(columnConstraints);
        getColumnConstraints().add(columnConstraints0);
        getColumnConstraints().add(columnConstraints1);
        getRowConstraints().add(rowConstraints);
        getRowConstraints().add(rowConstraints0);
        getRowConstraints().add(rowConstraints1);
        getChildren().add(r0c0);
        getChildren().add(r0c1);
        getChildren().add(r0c2);
        getChildren().add(r1c0);
        getChildren().add(r1c1);
        getChildren().add(r1c2);
        getChildren().add(r2c0);
        getChildren().add(r2c1);
        getChildren().add(r2c2);
    }

    public static ReplayXOBoardBase getInstance() {
        if (singlePlayerBoard == null) {
            synchronized (ReplayXOBoardBase.class) {
                if (singlePlayerBoard == null) {
                    singlePlayerBoard = new ReplayXOBoardBase();
                }
            }
        }
        return singlePlayerBoard;
    }

    private void Ending(Moves move) {
        Text text = new Text();
        endGameText = text;
        text.setFont(new Font("forte", 100));
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        text.setRotate(180);
        disableAllButtons();

        switch (move.getFinalState()) {
            case 0:
                final KeyFrame textAnimationDraw = new KeyFrame(Duration.seconds(0), e -> {
                    text.setText("It's a Draw");

                    SequentialTransition sequentialTransition = new SequentialTransition(text, translateEndText(text), rotateEndText(text));
                    sequentialTransition.play();
                    this.getChildren().addAll(text);
                });
                final KeyFrame alertfunctionCallDraw = new KeyFrame(Duration.seconds(5), e -> {
                    Platform.runLater(() -> {
                        endReplay();
                    });
                });
                final Timeline timelineDraw = new Timeline(textAnimationDraw, alertfunctionCallDraw);
                Platform.runLater(timelineDraw::play);
                break;
            case 1:
                final KeyFrame textAnimationWin = new KeyFrame(Duration.seconds(0), e -> {
                    text.setText("You Win");

                    SequentialTransition sequentialTransition = new SequentialTransition(text, translateEndText(text), rotateEndText(text));
                    sequentialTransition.play();
                    this.getChildren().addAll(text);
                });
                final KeyFrame alertfunctionCallWin = new KeyFrame(Duration.seconds(5), e -> {
                    Platform.runLater(() -> {
                        endReplay();
                    });
                });
                final Timeline timelineWin = new Timeline(textAnimationWin, alertfunctionCallWin);
                Platform.runLater(timelineWin::play);
                break;
            case -1:
                final KeyFrame textAnimationLose = new KeyFrame(Duration.seconds(0), e -> {
                    text.setText("You Lose");

                    SequentialTransition sequentialTransition = new SequentialTransition(text, translateEndText(text), rotateEndText(text));
                    sequentialTransition.play();
                    this.getChildren().addAll(text);
                });
                final KeyFrame alertfunctionCallLose = new KeyFrame(Duration.seconds(5), e -> {
                    Platform.runLater(() -> {
                        endReplay();
                    });
                });
                final Timeline timelineLose = new Timeline(textAnimationLose, alertfunctionCallLose);
                Platform.runLater(timelineLose::play);
                break;
            default:
                break;
        }

    }

    private TranslateTransition translateEndText(Text text) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(1.5));
        translateTransition.setNode(text);
        translateTransition.setByY(220);
        translateTransition.setByX(40);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(true);

        return translateTransition;
    }

    private RotateTransition rotateEndText(Text text) {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.seconds(0.25));
        rotateTransition.setNode(text);
        rotateTransition.setByAngle(-180);
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(false);
        return rotateTransition;
    }

    private void endReplay() {
        this.getChildren().remove(endGameText);
        Reset();
        primaryStage.setScene(XOGame.mainMenuScene);

    }

    private void disableAllButtons() {
        for (Button[] row : btns) {
            for (Button column : row) {
                column.setDisable(true);
                column.setStyle("-fx-background-color:  grey");

            }
        }
    }

    private void enableAllButtons() {
        for (Button[] row : btns) {
            for (Button column : row) {
                column.setDisable(false);
                column.setStyle("-fx-background-color:  cornsilk");

            }
        }
    }

    private void Reset() {

        ReplayController.getInstance().reset();
        System.out.println("Reset function called");
        for (Button[] row : btns) {
            for (Button column : row) {
                column.setText("");
            }
        }
        ResetGameState();
    }

    private void ResetGameState() {
        for (int[] row : gameState) {
            Arrays.fill(row, 0);
        }
        for (int[] row : gameState) {
            for (int cell : row) {
                System.out.println(cell);

            }
        }
    }

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        enableAllButtons();
        Stop[] stops = new Stop[]{new Stop(0, Color.PLUM), new Stop(1, Color.DARKMAGENTA)};
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 0.5, true, CycleMethod.REFLECT, stops);
        for (Button[] row : btns) {
            for (Button column : row) {
                column.setTextFill(lg1);
                column.setFont(new Font("Forte", 90.0));
                column.setStyle("-fx-background-color:lightskyblue");

            }
        }
    }

//    public void replay(Moves[] m){
//        for(Moves move:m){
//            if (move.isPlayer()){
//                btns[move.getX()][move.getY()].setText("x");
//                btns[move.getX()][move.getY()].setStyle("-fx-background-color: #03DAC6; -fx-text-fill:black ;-fx-background-radius:7px");
//            }
//            else{
//                System.out.println(move.getX()+move.getY()+"\n");
//                btns[move.getX()][move.getY()].setText("o");
//                btns[move.getX()][move.getY()].setStyle("-fx-background-color: #FF0266; -fx-text-fill:black ;-fx-background-radius:7px");
//            }
//        }
//    }
    public void draw(Moves move) {

        try {
            Platform.runLater(() -> {
                if (move.isPlayer()) {
                    btns[move.getX()][move.getY()].setText("x");
                    gameState[move.getX()][move.getY()] = 1;
                    btns[move.getX()][move.getY()].setStyle("-fx-background-color: #03DAC6; -fx-text-fill:black ;-fx-background-radius:7px");
                } else {
                    // System.out.println(move.getX() + move.getY() + "\n");
                    gameState[move.getX()][move.getY()] = -1;
                    btns[move.getX()][move.getY()].setText("o");
                    btns[move.getX()][move.getY()].setStyle("-fx-background-color: #FF0266; -fx-text-fill:black ;-fx-background-radius:7px");
                }
                System.out.println("Step is " + move.getStep() + " Final State is " + move.getFinalState());
                if (move.getFinalState() != 10) {

                    Ending(move);
                }
            });

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
