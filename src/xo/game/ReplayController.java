/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game;

import javafx.scene.Scene;
import javafx.stage.Stage;
import xo.game.model.DBCaller;
import xo.game.dataTypes.GamesHolder;
import xo.game.views.ReplayTableViewBase;
import xo.game.views.ReplayXOBoardBase;
import xo.game.views.TwoPlayerXOBoardBase;

/**
 *
 * @author Ali
 */
public class ReplayController extends AbstractController {

    private static ReplayController RPC;
    private Stage primaryStage;
    private Scene replayXOBoardBase = new Scene(ReplayXOBoardBase.getInstance(), 600, 600);
    private Scene replayTableView = new Scene(ReplayTableViewBase.getInstance(), 600, 600);

    private ReplayController() {

    }

    @Override
    public void test() {
        System.out.println("Replay CONTROLLER SPEAKING");
    }

    public static ReplayController getInstance() {
        if (RPC == null) {
            synchronized (ReplayController.class) {
                if (RPC == null) {
                    RPC = new ReplayController();
                }
            }
        }
        return RPC;
    }

    public void getGames() {
        //will call the model to get games and populate the tableview
        ReplayTableViewBase.getInstance().init(primaryStage);
        System.out.println("Will invoke the model to send games to tableview");
        GamesHolder[] games = DBCaller.getInstance().gamesGetter();
        ReplayTableViewBase.getInstance().populateTable(games);
        primaryStage.setScene(replayTableView);

    }

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
