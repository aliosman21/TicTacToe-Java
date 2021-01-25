/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xo.game.controller;

import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;
import static java.lang.Integer.max;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xo.game.model.DBCaller;
import xo.game.views.LanXOBoardBase;

/**
 *
 * @author Ali
 */
public class LanController extends AbstractController {

    private static LanController LPC;
    private Scene lanBoard = new Scene(LanXOBoardBase.getInstance(), 600, 600);

    private LanController() {
    }

    public void toDB(String gameType) {
        DBCaller.getInstance().insertMovesToDB(gameType, allMovesForAGame);
    }

    public void initJoin(Stage primaryStage, String socket, String incomingPort) {
        String ip = socket.substring(1);
        ip = ip.substring(0, ip.indexOf(":"));
        //int port = Integer.parseInt(socket.substring(socket.indexOf(":") + 1));
        int port = Integer.parseInt(incomingPort);
        System.out.println("IP is " + ip + " " + "Port is " + port);

        primaryStage.setScene(lanBoard);
        LanXOBoardBase.getInstance().init(primaryStage, ip, port);
        LanXOBoardBase.getInstance().requestFocus();
        // System.out.println("Joining");
        // LanXOBoardBase.getInstance().waitOtherPlayerTurn();
    }

    public void initHost(Stage primaryStage) {

        try {
            LanXOBoardBase.getInstance().initHost(primaryStage);
            primaryStage.setScene(lanBoard);
            LanXOBoardBase.getInstance().requestFocus();
            // System.out.println("Hosting");
        } catch (Exception ex) {
            System.out.println("Caught it " + ex);
        }
    }

    public void joinGame(Stage primaryStage) {
        try {
            System.out.println("I am Joining Game");
            DatagramSocket socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.setBroadcast(true);
            int freePort = ThreadLocalRandom.current().nextInt(30000, 60000);
            while (!available(freePort)) {
                freePort = ThreadLocalRandom.current().nextInt(30000, 60000);
            }
            socket.bind(new InetSocketAddress(InetAddress.getLocalHost(), freePort));
            // DatagramSocket socket = new DatagramSocket(5001, InetAddress.getLocalHost());
            socket.setSoTimeout(5000);
            // socket.setBroadcast(true);
            byte[] data = "GameRequest".getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), 8002);
            socket.send(packet);
            byte[] response = new byte["GameResponse".length()];
            DatagramPacket responsePacket = new DatagramPacket(response, response.length);
            socket.receive(responsePacket);
            String port = new String(responsePacket.getData(), responsePacket.getOffset(), responsePacket.getLength());
            System.out.println("Socket is  " + port);

            initJoin(primaryStage, responsePacket.getSocketAddress().toString(), port);
        } catch (UnknownHostException ex) {
            System.out.println("UNknown Host");

        } catch (SocketException ex) {
            System.out.println(ex);
            System.out.println("Socket Closed");

        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("IO Exception");
        }
    }

    private static boolean available(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    public void hostGame(Stage primaryStage) {
        try {
            System.out.println("I am Hosting the  Game");
            LanXOBoardBase.getInstance().hostSocket = new ServerSocket();
            DatagramSocket receiveSocket = new DatagramSocket(null);
            receiveSocket.setReuseAddress(true);
            receiveSocket.setBroadcast(true);
            receiveSocket.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 8002));
            // DatagramSocket receiveSocket = new DatagramSocket(8002, InetAddress.getByName("0.0.0.0"));
            byte[] buffer = "GameRequest".getBytes();
            receiveSocket.setSoTimeout(5000);
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            receiveSocket.receive(packet);
            String data = new String(packet.getData(), packet.getOffset(), packet.getLength()).trim();
            if (data.equals("GameRequest")) {

                int freePort = ThreadLocalRandom.current().nextInt(30000, 60000);
                while (!available(freePort)) {
                    freePort = ThreadLocalRandom.current().nextInt(30000, 60000);
                }

                if (LanXOBoardBase.getInstance().hostSocket.isClosed()) {
                    System.out.println("THE SOCKET IS FUCKING CLOSED");
                }
                System.out.println("Free port is " + freePort);
                byte[] response = new byte[Integer.toString(freePort).length()];
                response = Integer.toString(freePort).getBytes();
                DatagramPacket responsePacket = new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort());
                receiveSocket.send(responsePacket);
                String ip = packet.getSocketAddress().toString().substring(1);
                ip = ip.substring(0, ip.indexOf(":"));
                // int port = Integer.parseInt(packet.getSocketAddress().toString().substring(packet.getSocketAddress().toString().indexOf(":") + 1));
                // System.out.println("IP is " + ip + " " + "Port is " + port);

                System.out.println("BINDING");
                LanXOBoardBase.getInstance().hostSocket.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), freePort));

                initHost(primaryStage);
                // System.out.println("Socket is  " + packet.getSocketAddress());
                //LanXOBoardBase.connectedSocket;

                //  System.out.println("SOCKET SHOULD BE CLOSED");
            } else {
                System.err.println("Error, not valid package!" + packet.getAddress() + ":" + packet.getPort());
            }
        } catch (UnknownHostException ex) {
            System.out.println("UNknown Host");

        } catch (SocketException ex) {
            ex.printStackTrace();
            //System.out.println(ex);
            System.out.println("Socket isn't Here");

        } catch (IOException ex) {
            System.out.println(ex);
            System.out.println("IO Exception");
        }
        //   System.out.println("DONE");
    }

    //look synchronized
    public static LanController getInstance() {
        if (LPC == null) {
            synchronized (LanController.class) {
                if (LPC == null) {
                    LPC = new LanController();
                }
            }
        }
        return LPC;
    }

    @Override
    public void test() {
        System.out.println("LAN PLAYER CONTROLLER SPEAKING");
    }

}
