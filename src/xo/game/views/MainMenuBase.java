package xo.game.views;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import xo.game.controller.ReplayController;
import xo.game.controller.SinglePlayerController;
import xo.game.controller.TwoPlayerController;

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
    protected final DropShadow dropShadow3;
    private static MainMenuBase mainMenu;

    public MainMenuBase() {

        singlePlayer = new Button();
        dropShadow = new DropShadow();
        twoPlayers = new Button();
        dropShadow0 = new DropShadow();
        lan = new Button();
        dropShadow1 = new DropShadow();
        replay = new Button();
        dropShadow2 = new DropShadow();
        dropShadow3 = new DropShadow();

        setId("AnchorPane");
        setPrefHeight(594.0);
        setPrefWidth(594.0);
        getStyleClass().add("bodybg");
        getStylesheets().add("/xo/game/resources/main.css");

        singlePlayer.setLayoutX(189.0);
        singlePlayer.setLayoutY(337.0);
        singlePlayer.setMnemonicParsing(false);
        singlePlayer.setPrefHeight(45.0);
        singlePlayer.setPrefWidth(224.0);
        singlePlayer.getStyleClass().add("button1");
        singlePlayer.getStylesheets().add("/xo/game/resources/main.css");
        singlePlayer.setText("Single Player");
        singlePlayer.setFont(new Font("System Bold Italic", 21.0));

        dropShadow.setHeight(37.5);
        dropShadow.setRadius(18.875);
        dropShadow.setSpread(0.12);
        dropShadow.setWidth(40.0);
        singlePlayer.setEffect(dropShadow);

        twoPlayers.setLayoutX(188.0);
        twoPlayers.setLayoutY(396.0);
        twoPlayers.setMnemonicParsing(false);
        twoPlayers.setPrefHeight(45.0);
        twoPlayers.setPrefWidth(224.0);
        twoPlayers.getStyleClass().add("button2");
        twoPlayers.getStylesheets().add("/xo/game/resources/main.css");
        twoPlayers.setText("Two Players");
        twoPlayers.setFont(new Font("System Bold Italic", 21.0));

        dropShadow0.setHeight(0.0);
        dropShadow0.setRadius(9.5);
        dropShadow0.setSpread(0.12);
        dropShadow0.setWidth(40.0);
        twoPlayers.setEffect(dropShadow0);

        lan.setLayoutX(189.0);
        lan.setLayoutY(456.0);
        lan.setMnemonicParsing(false);
        lan.setPrefHeight(45.0);
        lan.setPrefWidth(224.0);
        lan.getStyleClass().add("button3");
        lan.setText("LAN");
        lan.setFont(new Font("System Bold Italic", 21.0));

        dropShadow1.setHeight(0.0);
        dropShadow1.setRadius(9.5);
        dropShadow1.setSpread(0.12);
        dropShadow1.setWidth(40.0);
        lan.setEffect(dropShadow1);

        replay.setLayoutX(190.0);
        replay.setLayoutY(518.0);
        replay.setMnemonicParsing(false);
        replay.setPrefHeight(45.0);
        replay.setPrefWidth(224.0);
        replay.getStyleClass().add("button4");
        replay.setText("Replay");
        replay.setFont(new Font("System Bold Italic", 21.0));

        dropShadow2.setHeight(35.0);
        dropShadow2.setRadius(18.25);
        dropShadow2.setWidth(40.0);
        replay.setEffect(dropShadow2);

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
        getChildren().add(replay);
        getChildren().add(lan);

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
