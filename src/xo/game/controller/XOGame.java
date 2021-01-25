/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.controller;

import xo.game.views.MainMenuBase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ali
 */
public class XOGame extends Application {

    public static Scene mainMenuScene = new Scene(MainMenuBase.getInstance(), 600, 600);

    @Override
    public void start(Stage primaryStage) {

        MainMenuBase.getInstance().init(primaryStage);
        primaryStage.setOnHiding(event -> {
            System.exit(0);
        });
        primaryStage.setTitle("X-O Game");
        primaryStage.setScene(mainMenuScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
