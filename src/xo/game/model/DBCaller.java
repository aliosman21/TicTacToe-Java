/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.model;

import xo.game.dataTypes.GamesHolder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import xo.game.views.ReplayTableViewBase;

/**
 *
 * @author Ali
 */
public class DBCaller {

    private static DBCaller dbCaller;

    public GamesHolder[] gamesGetter() {
        GamesHolder[] games = null;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xogames", "root", "Jrqr541&");
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);) {
            int i = 0;
            int rowCount = 0;
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "select * from games;";
            ResultSet gamesResultSet = stmt.executeQuery(query);
            gamesResultSet.last();
            rowCount = gamesResultSet.getRow();
            gamesResultSet.beforeFirst();
            games = new GamesHolder[rowCount];

            while (gamesResultSet.next()) {

                GamesHolder g = new GamesHolder(gamesResultSet.getInt(1), gamesResultSet.getString(2));
                games[i] = g;
                i++;

            }
            System.out.println(games[0].getGameID() + " " + games[0].getGameType());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);

        }
        return games;

    }

    public static DBCaller getInstance() {
        if (dbCaller == null) {
            synchronized (DBCaller.class) {
                if (dbCaller == null) {
                    dbCaller = new DBCaller();
                }
            }
        }
        return dbCaller;
    }

}
