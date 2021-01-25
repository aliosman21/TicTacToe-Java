package xo.game.views;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import xo.game.controller.LanController;
import xo.game.controller.XOGame;

public class LanGameType extends AnchorPane {

    protected Stage primaryStage;
    protected final Button host;
    protected final DropShadow dropShadow;
    protected final Button join;
    protected final DropShadow dropShadow0;
    protected final Button back;
    protected final DropShadow dropShadow1;
    protected final DropShadow dropShadow3;
    private static LanGameType lanSelectionMenu;
    public static Scene lanSelectionMenuScene = new Scene(lanSelectionMenu.getInstance(), 600, 600);

    private LanGameType() {

        host = new Button();
        dropShadow = new DropShadow();
        join = new Button();
        dropShadow0 = new DropShadow();
        back = new Button();
        dropShadow1 = new DropShadow();
        dropShadow3 = new DropShadow();

        setId("AnchorPane");
        setPrefHeight(600.0);
        setPrefWidth(600.0);
        getStyleClass().add("bodybg");
        getStylesheets().add("/xo/game/resources/main.css");

        host.setAlignment(javafx.geometry.Pos.CENTER);
        host.setLayoutX(94.0);
        host.setLayoutY(345.0);
        host.setMnemonicParsing(false);
        host.setPrefHeight(41.0);
        host.setPrefWidth(417.0);
        host.getStylesheets().add("/xo/game/resources/main.css");
        host.setText("Host a Game");
        host.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        host.setTextFill(javafx.scene.paint.Color.valueOf("#b50b0b"));
        host.setTextOverrun(javafx.scene.control.OverrunStyle.CLIP);
        host.setStyle("-fx-background-color:lightskyblue ");
        host.setFont(new Font("System Bold Italic", 21.0));

        dropShadow.setColor(javafx.scene.paint.Color.RED);
        dropShadow.setHeight(40.0);
        dropShadow.setRadius(19.5);
        dropShadow.setSpread(0.16);
        dropShadow.setWidth(40.0);
        host.setEffect(dropShadow);

        join.setAlignment(javafx.geometry.Pos.CENTER);
        join.setLayoutX(92.0);
        join.setLayoutY(407.0);
        join.setMnemonicParsing(false);
        join.setPrefHeight(41.0);
        join.setPrefWidth(417.0);
        join.getStylesheets().add("/xo/game/resources/main.css");
        join.setText("Join a Game");
        join.setTextFill(javafx.scene.paint.Color.valueOf("#b50b0b"));
        join.setStyle("-fx-background-color:#ffbf00 ");
        join.setFont(new Font("System Bold Italic", 21.0));;
        dropShadow0.setColor(javafx.scene.paint.Color.RED);
        dropShadow0.setHeight(40.0);
        dropShadow0.setRadius(19.5);
        dropShadow0.setSpread(0.16);
        dropShadow0.setWidth(40.0);
        join.setEffect(dropShadow0);

        back.setAlignment(javafx.geometry.Pos.CENTER);
        back.setLayoutX(92.0);
        back.setLayoutY(465.0);
        back.setMnemonicParsing(false);
        back.setPrefHeight(41.0);
        back.setPrefWidth(417.0);
        back.getStylesheets().add("/xo/game/resources/main.css");
        back.setText("Main Menu");
        back.setFont(new Font("System Bold Italic", 21.0));
        back.setStyle("-fx-background-color:#884EA0 ");

        dropShadow1.setColor(javafx.scene.paint.Color.RED);
        dropShadow1.setHeight(40.0);
        dropShadow1.setRadius(19.5);
        dropShadow1.setSpread(0.16);
        dropShadow1.setWidth(40.0);
        back.setEffect(dropShadow1);

        dropShadow3.setColor(javafx.scene.paint.Color.RED);
        dropShadow3.setHeight(50.65);
        dropShadow3.setRadius(19.8825);
        dropShadow3.setSpread(0.17);
        dropShadow3.setWidth(30.88);

        host.setOnAction((ActionEvent event) -> {
            LanController.getInstance().hostGame(primaryStage);

        }
        );

        join.setOnAction((ActionEvent event) -> {
            LanController.getInstance().joinGame(primaryStage);
        });
        back.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(XOGame.mainMenuScene);
        }
        );

        getChildren()
                .add(host);
        getChildren()
                .add(join);
        getChildren()
                .add(back);

    }

    public void init(Stage primaryStage) {
        primaryStage.setScene(lanSelectionMenuScene);
        this.primaryStage = primaryStage;

    }

    public static LanGameType
            getInstance() {
        if (lanSelectionMenu == null) {
            synchronized (LanGameType.class) {
                if (lanSelectionMenu == null) {
                    lanSelectionMenu = new LanGameType();
                }
            }
        }
        return lanSelectionMenu;
    }
}
