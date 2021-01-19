package xo.game.views;

import java.io.File;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import xo.game.SinglePlayerController;
import xo.game.XOGame;

public class SinglePlayerXOBoardBase extends GridPane {

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
    private static SinglePlayerXOBoardBase singlePlayerBoard;

    // Game state gives a state about a given button if;
    // 0-------------> the button is free;
    // 1-------------> the button has an X or is played by player1 ;
    //-1------------> the button has an O or is played by player 2
    //===========================================================================
    protected int[][] gameState = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    protected Button[][] btns = {
        {r0c0, r0c1, r0c2},
        {r1c0, r1c1, r1c2},
        {r2c0, r2c1, r2c2}
    };

    private SinglePlayerXOBoardBase() {

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
        setHgap(20.0);
        setVgap(20.0);
        setStyle("-fx-background-color: #9a0000 ; -fx-background-radius:10px");

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

        r0c0.setOnAction((ActionEvent event) -> {
            if ("".equals(r0c0.getText())) {
                r0c0.setText("X");
                gameState[0][0] = 1;
                Moves move = new Moves(0, 0, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {
                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }
        });
        r0c1.setOnAction((ActionEvent event) -> {
            if ("".equals(r0c1.getText())) {
                r0c1.setText("X");
                gameState[0][1] = 1;
                Moves move = new Moves(0, 1, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {
                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }
        });
        r0c2.setOnAction((ActionEvent event) -> {
            if ("".equals(r0c2.getText())) {
                r0c2.setText("X");
                gameState[0][2] = 1;
                Moves move = new Moves(0, 2, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {
                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }
        });
        r1c0.setOnAction((ActionEvent event) -> {
            if ("".equals(r1c0.getText())) {
                r1c0.setText("X");
                gameState[1][0] = 1;
                Moves move = new Moves(1, 0, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {
                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }
        });
        r1c1.setOnAction((ActionEvent event) -> {
            if ("".equals(r1c1.getText())) {
                r1c1.setText("X");
                gameState[1][1] = 1;
                Moves move = new Moves(1, 1, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {

                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }
        });
        r1c2.setOnAction((ActionEvent event) -> {
            if ("".equals(r1c2.getText())) {
                r1c2.setText("X");
                gameState[1][2] = 1;
                Moves move = new Moves(1, 2, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {
                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }
        });
        r2c0.setOnAction((ActionEvent event) -> {
            if ("".equals(r2c0.getText())) {
                r2c0.setText("X");
                gameState[2][0] = 1;
                Moves move = new Moves(2, 0, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {
                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }
        });
        r2c1.setOnAction((ActionEvent event) -> {
            if ("".equals(r2c1.getText())) {
                r2c1.setText("X");
                gameState[2][1] = 1;
                Moves move = new Moves(2, 1, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {
                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }
        });
        r2c2.setOnAction((ActionEvent event) -> {
            if ("".equals(r2c2.getText())) {
                r2c2.setText("X");
                gameState[2][2] = 1;
                Moves move = new Moves(2, 2, true);
                if (SinglePlayerController.getInstance().checkState(gameState, move)) {
                    SinglePlayerController.getInstance().ComputerTurn(gameState);

                } else {
                    System.out.println("The games final state is: " + move.getFinalState());
                    Ending(move);
                }
            }

        });

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

    public static SinglePlayerXOBoardBase getInstance() {
        if (singlePlayerBoard == null) {
            synchronized (SinglePlayerXOBoardBase.class) {
                if (singlePlayerBoard == null) {
                    singlePlayerBoard = new SinglePlayerXOBoardBase();
                }
            }
        }
        return singlePlayerBoard;
    }

    private void Ending(Moves move) {
        Text text = new Text();
        endGameText = text;
        text.setFont(new Font("forte", 135));
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        text.setRotate(180);
        Image image = new Image(getClass().getResource("r.jpg").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        disableAllButtons();

        switch (move.getFinalState()) {
            case 0:
                final KeyFrame textAnimationDraw = new KeyFrame(Duration.seconds(0), e -> {
                    text.setText("Draw");

                    SequentialTransition sequentialTransition = new SequentialTransition(text, translateEndText(text), rotateEndText(text));
                    sequentialTransition.play();
                    this.getChildren().addAll(text);
                });
                final KeyFrame alertfunctionCallDraw = new KeyFrame(Duration.seconds(5), e -> {
                    Platform.runLater(() -> {
                        saveGame();
                    });
                });
                final Timeline timelineDraw = new Timeline(textAnimationDraw, alertfunctionCallDraw);
                Platform.runLater(timelineDraw::play);
                break;
            case 1:
                Media media = new Media(getClass().getResource("tt.mp4").toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                mediaPlayer.setOnEndOfMedia(() -> {
                    System.out.println("Done.");
                });
                mediaPlayer.setOnStopped(() -> {
                    mediaView.setVisible(false);
                });

                mediaPlayer.setOnStalled(() -> {
                    System.out.println("JHE");
                });
                mediaPlayer.setOnPlaying(() -> {
                    System.out.println("cru");
                });
                final KeyFrame textAnimationWin = new KeyFrame(Duration.seconds(0), e -> {
                    text.setText("You Win");

                    SequentialTransition sequentialTransition = new SequentialTransition(text, translateEndText(text), rotateEndText(text));
                    sequentialTransition.play();
                    this.getChildren().addAll(text);

                });

                final KeyFrame videoWin = new KeyFrame(Duration.seconds(2), e -> {

                    //Image image = new Image(getClass().getResource("r.jpg").toExternalForm());
                    //ImageView imageView = new ImageView(image);
                    SequentialTransition sequentialTransition = new SequentialTransition(imageView, translateImg(imageView));
                    sequentialTransition.play();
                    this.getChildren().addAll(imageView);

                    mediaView.setTranslateX(50);
                    mediaView.setTranslateY(200);
                    mediaView.setFitHeight(500);
                    mediaView.setFitWidth(500);
                    this.getChildren().addAll(mediaView);
                    mediaPlayer.play();

                });

                final KeyFrame alertfunctionCallWin = new KeyFrame(Duration.seconds(4), e -> {
                    Platform.runLater(() -> {
                        this.getChildren().remove(imageView);
                        mediaPlayer.stop();
                        saveGame();
                    });
                });
                final Timeline timelineWin = new Timeline(textAnimationWin, videoWin, alertfunctionCallWin);
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
                        saveGame();
                    });
                });
                final Timeline timelineLose = new Timeline(textAnimationLose, alertfunctionCallLose);
                Platform.runLater(timelineLose::play);
                break;
            default:
                break;
        }

    }

    private TranslateTransition translateImg(ImageView imageView) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(1));
        translateTransition.setNode(imageView);
        translateTransition.setFromX(50);
        translateTransition.setFromY(600);
        translateTransition.setToY(0);
        translateTransition.setAutoReverse(true);

        return translateTransition;
    }

    private TranslateTransition translateEndText(Text text) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.5));
        translateTransition.setNode(text);
        translateTransition.setByY(220);
        translateTransition.setByX(70);
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

    private void saveGame() {
        try {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Save Game ?");
            alert.setContentText("Do you wish to save this game?");

            ButtonType yesBtn = new ButtonType("Yes");
            ButtonType noBtn = new ButtonType("No");

            Image image = new Image(getClass().getResource("toe.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            alert.setGraphic(imageView);
            alert.getDialogPane().setStyle("-fx-background-color:  cornsilk");

            alert.getButtonTypes().setAll(yesBtn, noBtn);
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> {
                window.hide();
            });

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesBtn) {

                SinglePlayerController.getInstance().testing();
                //will send array of moves from abstract controller to be saved
                //with game state
                PlayAgain();
            } else if (result.get() == noBtn) {
                //will do nothing
                PlayAgain();

            }
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
    }

    private void PlayAgain() {
        try {
            Alert alert = new Alert(AlertType.NONE);
            alert.setTitle("Play Again");
            alert.setContentText("Choose your option.");

            ButtonType playAgainBtn = new ButtonType("Play Again");
            ButtonType mainMenuBtn = new ButtonType("Main Menu");

            Image image = new Image(getClass().getResource("toe.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            alert.setGraphic(imageView);
            //String path = "/JAVA/XO Game/src/xo/game/views/t2.mp4";

            alert.getDialogPane().setStyle("-fx-background-color:  cornsilk");

            alert.getButtonTypes().setAll(playAgainBtn, mainMenuBtn);
            Window window = alert.getDialogPane().getScene().getWindow();
            window.setOnCloseRequest(event -> {
                window.hide();
            });

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == playAgainBtn) {
                enableAllButtons();
                Reset();
            } else if (result.get() == mainMenuBtn) {
                Reset();
                primaryStage.setScene(XOGame.mainMenuScene);
            }
        } catch (NoSuchElementException e) {
            System.exit(0);
        }

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
        this.getChildren().remove(endGameText);
        SinglePlayerController.getInstance().reset();
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

    public void ComputerPlays(int x, int y, boolean game, Moves move) {
        btns[x][y].setText("O");
        if (game) {
            SinglePlayerXOBoardBase.getInstance().requestFocus();

        } else {
            System.out.println("The game has ended and computer has won");
            Ending(move);
        }
    }

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        enableAllButtons();
        Stop[] stops = new Stop[]{new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 0, 0.5, true, CycleMethod.REFLECT, stops);
        for (Button[] row : btns) {
            for (Button column : row) {
                column.setTextFill(lg1);
                column.setFont(new Font("Forte", 90.0));
                column.setStyle("-fx-background-color:  cornsilk");

            }
        }
    }

}
