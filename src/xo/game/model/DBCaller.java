/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.model;

import xo.game.dataTypes.GamesHolder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import xo.game.controller.ReplayController;
import xo.game.dataTypes.Moves;
import xo.game.views.ReplayTableViewBase;

/**
 *
 * @author Ali
 */
public class DBCaller {

    private static DBCaller dbCaller;
    Connection con;

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

    // btat-call mn el single player controller 3shan t-save el moves fl db-----------Ibrahim
    public void insertMovesToDB(String gameType, Vector<Moves> allMovesForAGame) {
        int lastIndex = 0;
        try {

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xogames", "root", "Jrqr541&");

        } catch (SQLException ex) {
            System.out.println("error connecting to db in toDB fn in SinglePlayerController");
        }

        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO games(gameType) VALUE (?)");
            pstmt.setString(1, gameType);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();

            Statement stmt = con.createStatement();
            String queryString = new String("SELECT MAX(ID) FROM games");
            ResultSet rs = stmt.executeQuery(queryString);

            while (rs.next()) {
                lastIndex = rs.getInt(1);
            }

            stmt.close();

            for (Moves move : allMovesForAGame) {

                PreparedStatement pstmt1 = con.prepareStatement("INSERT INTO steps(player,x,y,ID,step,finalState) VALUE (?,?,?,?,?,?)");
                if (move.isPlayer()) {
                    pstmt1.setInt(1, 1);
                } else {
                    pstmt1.setInt(1, 0);
                }
                pstmt1.setInt(2, move.getX());
                pstmt1.setInt(3, move.getY());
                pstmt1.setInt(4, lastIndex);
                pstmt1.setInt(5, move.getStep());
                pstmt1.setInt(6, move.getFinalState());

                int rowsAffected1 = pstmt1.executeUpdate();
                pstmt1.close();

            }
            con.close();
            // System.out.println("elmafrood kda closed");
        } catch (SQLException ex) {
            System.out.println("error in executing insert in toDB fn in SinglePlayerController 2 " + ex);
            ex.printStackTrace();
        }

    }

    //btetcall mn el replay controller 3shan a5od el moves a7otaha f array of moves--------------Ibrahim
    public void mFromDB(int id) {

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/xogames", "root", "Jrqr541&");
            //System.out.println("con established ya 3m");

        } catch (SQLException ex) {
            System.out.println("error connecting to db in toDB fn in ReplayController");
        }

        try {
            PreparedStatement pstmt = con.prepareStatement("select * from steps where ID=(?) order by step");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                boolean player;
                if (rs.getInt(1) == 1) {
                    player = true;
                } else {
                    player = false;
                }
                Moves m = new Moves(rs.getInt(2), rs.getInt(3), player);
                m.setStep(rs.getInt(5));
                m.setFinalState(rs.getInt(6));
                ReplayController.getInstance().allMovesGetter().add(m);

                System.out.println("Populating moves");
            }

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error fl select " + e);
        }
    }

}
