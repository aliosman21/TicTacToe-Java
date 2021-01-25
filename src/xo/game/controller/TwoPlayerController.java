/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import xo.game.model.DBCaller;
import xo.game.views.TwoPlayerXOBoardBase;

/**
 *
 * @author Ali
 */
public class TwoPlayerController extends AbstractController {

    private static TwoPlayerController TPC;
    private Scene twoPlayerBoard = new Scene(TwoPlayerXOBoardBase.getInstance(), 600, 600);

    private TwoPlayerController() {

    }

    @Override
    public void test() {
        System.out.println("TWO PLAYER CONTROLLER SPEAKING");
    }

    public static TwoPlayerController getInstance() {
        if (TPC == null) {
            synchronized (TwoPlayerController.class) {
                if (TPC == null) {
                    TPC = new TwoPlayerController();
                }
            }
        }
        return TPC;
    }

    public boolean switcher(boolean player) {
        TwoPlayerXOBoardBase.getInstance().requestFocus();
        return !player;
    }

    public void toDB(String gameType) {
        DBCaller.getInstance().insertMovesToDB(gameType, allMovesForAGame);
    }

    public void init(Stage primaryStage) {
        primaryStage.setScene(twoPlayerBoard);
        TwoPlayerXOBoardBase.getInstance().init(primaryStage);
        TwoPlayerXOBoardBase.getInstance().requestFocus();
    }

}
