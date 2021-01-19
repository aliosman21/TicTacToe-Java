/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.dataTypes;

/**
 *
 * @author Ali
 */
public class Moves {

    private int x;
    private int y;
    private int step;
    private boolean player;
    private int finalState;

    public Moves(int x, int y, boolean player) {
        this.x = x;
        this.y = y;
        this.player = player;
        this.finalState = 10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStep() {
        return step;
    }

    public boolean isPlayer() {
        return player;
    }

    public int getFinalState() {
        return finalState;
    }

    public void setFinalState(int finalState) {
        this.finalState = finalState;
    }

    public void setStep(int step) {
        this.step = step;
    }

}
