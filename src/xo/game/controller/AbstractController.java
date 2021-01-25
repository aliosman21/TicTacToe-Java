/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.controller;

import java.util.Vector;
import xo.game.dataTypes.Moves;

/**
 *
 * @author Ali
 */
public abstract class AbstractController {

    //Final State represents if the game ended and how it ended
    //10-------------> The final state still has a default value and the game not over
    //0--------------> the game is a draw
    //1--------------> player 1 won
    //-1-------------> player 1 lost
    // public static String currentGameMode;
    public abstract void test();
    protected volatile int currentMove = 0;
    protected volatile Vector<Moves> allMovesForAGame = new Vector<Moves>();

    public void reset() {
        currentMove = 0;
        //System.out.println(m.getStep());
        allMovesForAGame.clear();
        System.out.println("All Move Are Reset");

    }

    public boolean checkState(int[][] gameState, Moves move) {
        boolean cont = true;
        for (int j = 0; j < 3; j++) {
            if ((gameState[j][0] == gameState[j][1]) && (gameState[j][0] == gameState[j][2]) && gameState[j][0] != 0) {
                cont = false;
            } else if ((gameState[0][j] == gameState[1][j]) && (gameState[0][j] == gameState[2][j]) && gameState[0][j] != 0) {
                cont = false;
            }
        }
        if ((gameState[0][0] == gameState[1][1]) && (gameState[0][0] == gameState[2][2]) && gameState[0][0] != 0) {
            cont = false;
        }
        if ((gameState[0][2] == gameState[1][1]) && (gameState[0][2] == gameState[2][0]) && gameState[0][2] != 0) {
            cont = false;
        }
        System.out.println("Checking The Game State");
        //Move should also has a set for final state
        move.setStep(currentMove);
        currentMove++;
        allMovesForAGame.add(move);
        //The condition still isn't complete needs the state check logic to be added
        if (currentMove < 9 && cont) {
            System.out.println("Game in progress");
            return true;
        } else {
            if (cont) {
                move.setFinalState(0);
            } else if (!cont) {
                if (move.isPlayer()) {
                    move.setFinalState(1);

                } else {
                    move.setFinalState(-1);
                }
            }
            System.out.println("Game ended");
            return false;
        }
    }

}
