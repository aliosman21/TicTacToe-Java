/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game;

import java.util.Random;
import xo.game.dataTypes.Moves;
import xo.game.views.SinglePlayerXOBoardBase;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ali
 */
public class SinglePlayerController extends AbstractController {

    private static SinglePlayerController SPC;
    private Scene singlePlayerBoard = new Scene(SinglePlayerXOBoardBase.getInstance(), 600, 600);

    private SinglePlayerController() {
    }

    public void init(Stage primaryStage) {
        primaryStage.setScene(singlePlayerBoard);
        SinglePlayerXOBoardBase.getInstance().init(primaryStage);
        SinglePlayerXOBoardBase.getInstance().requestFocus();
    }

    //look synchronized
    public static SinglePlayerController getInstance() {
        if (SPC == null) {
            synchronized (SinglePlayerController.class) {
                if (SPC == null) {
                    SPC = new SinglePlayerController();
                }
            }
        }
        return SPC;
    }

    public void ComputerTurn(int[][] gameState) {
        Random r = new Random();
        int index;
        while (true) {
            index = r.nextInt(9) + 0;
            if (gameState[index / 3][index % 3] == 0) {
                gameState[index / 3][index % 3] = -1;
                break;
            } else {
                continue;
            }
        }
        Moves move = new Moves(index / 3, index % 3, false);

        System.out.println("The computer will play at: " + index / 3 + " and " + index % 3);
        SinglePlayerXOBoardBase.getInstance().ComputerPlays(index / 3, index % 3, checkState(gameState, move), move);

    }

    public void testing() {

        for (Moves m : allMoves) {
            System.out.println("Hello " + m.getY());
        }

    }

    @Override
    public void test() {
        System.out.println("SINGLE PLAYER CONTROLLER SPEAKING");
    }

}
