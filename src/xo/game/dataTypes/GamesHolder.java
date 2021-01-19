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
public class GamesHolder {

    private int gameID;
    private String gameType;

    public GamesHolder(int ID, String type) {
        this.gameID = ID;
        this.gameType = type;

    }

    public int getGameID() {
        return gameID;
    }

    public String getGameType() {
        return gameType;
    }

}
