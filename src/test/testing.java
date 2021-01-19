/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Random;

/**
 *
 * @author Ali
 */
public class testing {

    public static void main(String[] args) {
        int[][] gameState = {{1, 0, 1}, {0, -1, -1}, {0, 1, 0}};
        Random r = new Random();
        int result;
        while (true) {
            result = r.nextInt(9) + 0;
            System.out.println(result);
            if (gameState[result / 3][result % 3] == 0) {
                break;
            } else {
                continue;
            }
        }
        System.out.println(result);
    }

}
