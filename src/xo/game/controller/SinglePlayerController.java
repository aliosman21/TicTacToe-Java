/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.controller;

import java.util.Random;
import xo.game.dataTypes.Moves;
import xo.game.views.SinglePlayerXOBoardBase;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xo.game.model.DBCaller;

/**
 *
 * @author Ali
 */
public class SinglePlayerController extends AbstractController {

    private static SinglePlayerController SPC;
    private int bestAIMoveX;
    private int bestAIMoveY;
    private boolean isHard = true;
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
        if (isHard) {
            //int[][] localGameState = gameState.clone();
            bestMove(gameState);
            Moves move = new Moves(bestAIMoveX, bestAIMoveY, false);
            gameState[bestAIMoveX][bestAIMoveY] = -1;
            SinglePlayerXOBoardBase.getInstance().ComputerPlays(bestAIMoveX, bestAIMoveY, checkState(gameState, move), move);
        } else {
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
    }

    public void bestMove(int[][] gameState) {
        int bestVal = -1000;

        System.out.println("Local State");
        for (int[] row : gameState) {
            for (int cell : row) {
                System.out.print(" " + cell + " ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (gameState[i][j] == 0) {

                    gameState[i][j] = -1;

                    int moveVal = minMax(gameState, 0, false);

                    gameState[i][j] = 0;

                    if (moveVal > bestVal) {
                        bestAIMoveX = i;
                        bestAIMoveY = j;

                        bestVal = moveVal;
                    }
                }
            }
        }

        System.out.println("Best Move is " + bestAIMoveX + " and " + bestAIMoveY);

    }

    private int evaluate(int b[][]) {

        for (int row = 0; row < 3; row++) {
            if (b[row][0] == b[row][1]
                    && b[row][1] == b[row][2]) {
                if (b[row][0] == -1) {
                    return +10;
                } else if (b[row][0] == 1) {
                    return -10;
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            if (b[0][col] == b[1][col]
                    && b[1][col] == b[2][col]) {
                if (b[0][col] == -1) {
                    return +10;
                } else if (b[0][col] == 1) {
                    return -10;
                }
            }
        }

        if (b[0][0] == b[1][1] && b[1][1] == b[2][2]) {
            if (b[0][0] == -1) {
                return +10;
            } else if (b[0][0] == 1) {
                return -10;
            }
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0]) {
            if (b[0][2] == -1) {
                return +10;
            } else if (b[0][2] == 1) {
                return -10;
            }
        }

        return 0;
    }

    private Boolean isMovesLeft(int gameState[][]) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameState[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int minMax(int[][] localGameState, int depth, boolean isComputer) {
        int score = evaluate(localGameState);
        if (score == 10) {
            return score;
        }
        if (score == -10) {
            return score;
        }
        if (isMovesLeft(localGameState) == false) {
            return 0;
        }
        if (isComputer) {
            int best = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (localGameState[i][j] == 0) {
                        localGameState[i][j] = -1;
                        best = Math.max(best, minMax(localGameState,
                                depth + 1, !isComputer));
                        localGameState[i][j] = 0;
                    }
                }
            }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (localGameState[i][j] == 0) {
                        localGameState[i][j] = 1;
                        best = Math.min(best, minMax(localGameState,
                                depth + 1, !isComputer));
                        localGameState[i][j] = 0;
                    }
                }
            }
            return best;
        }
    }

    public void testing() {

        for (Moves m : allMovesForAGame) {
            System.out.println("Hello " + m.getY());
        }

    }

    @Override
    public void test() {
        System.out.println("SINGLE PLAYER CONTROLLER SPEAKING");
    }

    public void toDB(String gameType) {
        DBCaller.getInstance().insertMovesToDB(gameType, allMovesForAGame);
    }

}
