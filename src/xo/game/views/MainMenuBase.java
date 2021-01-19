package xo.game.views;

import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import xo.game.ReplayController;
import xo.game.SinglePlayerController;
import xo.game.TwoPlayerController;

public class MainMenuBase extends AnchorPane {

    protected Stage primaryStage;
    protected final Button singlePlayer;
    protected final DropShadow dropShadow;
    protected final Button twoPlayers;
    protected final DropShadow dropShadow0;
    protected final Button lan;
    protected final DropShadow dropShadow1;
    protected final Button replay;
    protected final DropShadow dropShadow2;
    protected final Label label;
    protected final DropShadow dropShadow3;
    private static MainMenuBase mainMenu;

    private MainMenuBase() {

        singlePlayer = new Button();
        dropShadow = new DropShadow();
        twoPlayers = new Button();
        dropShadow0 = new DropShadow();
        lan = new Button();
        dropShadow1 = new DropShadow();
        replay = new Button();
        dropShadow2 = new DropShadow();
        label = new Label();
        dropShadow3 = new DropShadow();

        setId("AnchorPane");
        setPrefHeight(600.0);
        setPrefWidth(600.0);
        getStyleClass().add("bodybg");
        getStylesheets().add("/xo/game/views/main.css");

        singlePlayer.setAlignment(javafx.geometry.Pos.CENTER);
        singlePlayer.setLayoutX(94.0);
        singlePlayer.setLayoutY(345.0);
        singlePlayer.setMnemonicParsing(false);
        singlePlayer.setPrefHeight(41.0);
        singlePlayer.setPrefWidth(417.0);
        singlePlayer.getStylesheets().add("/xo/game/views/main.css");
        singlePlayer.setText("Single Player");
        singlePlayer.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        singlePlayer.setTextFill(javafx.scene.paint.Color.valueOf("#b50b0b"));
        singlePlayer.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        singlePlayer.setFont(new Font(18.0));

        dropShadow.setColor(javafx.scene.paint.Color.RED);
        dropShadow.setHeight(40.0);
        dropShadow.setRadius(19.5);
        dropShadow.setSpread(0.16);
        dropShadow.setWidth(40.0);
        singlePlayer.setEffect(dropShadow);

        twoPlayers.setAlignment(javafx.geometry.Pos.CENTER);
        twoPlayers.setLayoutX(92.0);
        twoPlayers.setLayoutY(407.0);
        twoPlayers.setMnemonicParsing(false);
        twoPlayers.setPrefHeight(41.0);
        twoPlayers.setPrefWidth(417.0);
        twoPlayers.getStylesheets().add("/xo/game/views/main.css");
        twoPlayers.setText("Two Players");
        twoPlayers.setTextFill(javafx.scene.paint.Color.valueOf("#b50b0b"));
        twoPlayers.setFont(new Font(18.0));

        dropShadow0.setColor(javafx.scene.paint.Color.RED);
        dropShadow0.setHeight(40.0);
        dropShadow0.setRadius(19.5);
        dropShadow0.setSpread(0.16);
        dropShadow0.setWidth(40.0);
        twoPlayers.setEffect(dropShadow0);

        lan.setAlignment(javafx.geometry.Pos.CENTER);
        lan.setLayoutX(92.0);
        lan.setLayoutY(465.0);
        lan.setMnemonicParsing(false);
        lan.setPrefHeight(41.0);
        lan.setPrefWidth(417.0);
        lan.getStylesheets().add("/xo/game/views/main.css");
        lan.setText("LAN");
        lan.setTextFill(javafx.scene.paint.Color.valueOf("#b50b0b"));
        lan.setFont(new Font(18.0));

        dropShadow1.setColor(javafx.scene.paint.Color.RED);
        dropShadow1.setHeight(40.0);
        dropShadow1.setRadius(19.5);
        dropShadow1.setSpread(0.16);
        dropShadow1.setWidth(40.0);
        lan.setEffect(dropShadow1);

        replay.setAlignment(javafx.geometry.Pos.CENTER);
        replay.setLayoutX(92.0);
        replay.setLayoutY(520.0);
        replay.setMnemonicParsing(false);
        replay.setPrefHeight(41.0);
        replay.setPrefWidth(417.0);
        replay.getStylesheets().add("/xo/game/views/main.css");
        replay.setText("Replay");
        replay.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        replay.setTextFill(javafx.scene.paint.Color.valueOf("#b50b0b"));
        replay.setFont(new Font(18.0));

        dropShadow2.setColor(javafx.scene.paint.Color.RED);
        dropShadow2.setHeight(40.0);
        dropShadow2.setRadius(19.5);
        dropShadow2.setSpread(0.16);
        dropShadow2.setWidth(40.0);
        replay.setEffect(dropShadow2);

        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(102.0);
        label.setLayoutY(21.0);
        label.setPrefHeight(72.0);
        label.setPrefWidth(402.0);
        label.getStyleClass().add("title");
        label.getStylesheets().add("/xo/game/views/main.css");
        label.setText("Tic Tac Toe");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        label.setTextFill(javafx.scene.paint.Color.valueOf("#b50b0b"));
        label.setFont(new Font("Bell MT", 70.0));

        dropShadow3.setColor(javafx.scene.paint.Color.RED);
        dropShadow3.setHeight(50.65);
        dropShadow3.setRadius(19.8825);
        dropShadow3.setSpread(0.17);
        dropShadow3.setWidth(30.88);
        label.setEffect(dropShadow3);

        singlePlayer.setOnAction((ActionEvent event) -> {
            // AbstractController.currentGameMode = "Single";
            System.out.println("Single Player Controller Started");
            SinglePlayerController SPC = SinglePlayerController.getInstance();
            SPC.init(primaryStage);
        });

        twoPlayers.setOnAction((ActionEvent event) -> {
            // AbstractController.currentGameMode = "TwoPlayers";
            System.out.println("TWO Player Controller Started");
            TwoPlayerController TPC = TwoPlayerController.getInstance();
            TPC.init(primaryStage);
        });
        lan.setOnAction((ActionEvent event) -> {
            // AbstractController.currentGameMode = "TwoPlayers";
            System.out.println("LAN Menu Started");
            LanGameType.getInstance().init(primaryStage);
        });
        replay.setOnAction((ActionEvent event) -> {
            // AbstractController.currentGameMode = "TwoPlayers";
            System.out.println("Replay Controller Started");
            ReplayController.getInstance().init(primaryStage);
            ReplayController.getInstance().getGames();

        });

        getChildren().add(singlePlayer);
        getChildren().add(twoPlayers);
        getChildren().add(lan);
        getChildren().add(replay);
        getChildren().add(label);

    }

    public void init(Stage primaryStage) {

        this.primaryStage = primaryStage;

    }

    public static MainMenuBase getInstance() {
        if (mainMenu == null) {
            synchronized (MainMenuBase.class) {
                if (mainMenu == null) {
                    mainMenu = new MainMenuBase();
                }
            }
        }
        return mainMenu;
    }
}
