/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.controller;

import java.sql.Connection;
import java.util.Vector;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xo.game.model.DBCaller;
import xo.game.dataTypes.GamesHolder;
import xo.game.dataTypes.Moves;
import xo.game.views.ReplayTableViewBase;
import xo.game.views.ReplayXOBoardBase;

/**
 *
 * @author Ali
 */
public class ReplayController extends AbstractController {

    private static ReplayController RPC;
    private Stage primaryStage;
    private Scene replayXOBoardBase = new Scene(ReplayXOBoardBase.getInstance(), 600, 600);
    private Scene replayTableView = new Scene(ReplayTableViewBase.getInstance(), 600, 600);

    Connection con; // ibrahim ----------------------

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
        ReplayTableViewBase.getInstance().init(primaryStage);
        System.out.println("Will invoke the model to send games to tableview");
        GamesHolder[] games = DBCaller.getInstance().gamesGetter();
        ReplayTableViewBase.getInstance().populateTable(games);
        primaryStage.setScene(replayTableView);

    }

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //ibrahim ------------------------------------------------
    public void getMovesFromDb(int id) {

        //System.out.println("HELLO");
        //3ayez afta7 el replayxoboard
        primaryStage.setScene(replayXOBoardBase);
        ReplayXOBoardBase.getInstance().init(primaryStage);
        ReplayXOBoardBase.getInstance().requestFocus();
        ReplayController RPC = ReplayController.getInstance();
        RPC.init(primaryStage);
        DBCaller.getInstance().mFromDB(id);
    }

    public void oneByOne() {
        int xxx = 1000;
        for (Moves move : allMovesForAGame) {

            tCreator(move, xxx);

            xxx += 1000;
        }
    }

    public Vector<Moves> allMovesGetter() {
        return allMovesForAGame;
    }

    public void tCreator(Moves move, int xxx) {

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(xxx);
            } catch (InterruptedException ex) {
                System.out.println("error at sleep");
            }
            ReplayXOBoardBase.getInstance().draw(move);
        });
        t.start();

    }

}
