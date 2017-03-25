/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image.encryption;

/**
 *
 * @author Ashmin
 */
public class AES {

    public AES() {

    }

    public void encrypt() {

    }

    public void decrypt() {

    }

    public int[][] subBytes(int[][] state) {
        state = SBox.substituteState(state);
        return state;
    }

    public int[][] shiftRows(int[][] state) {

        return state;
    }

    public int[][] addRoundKey(int[][] state, int round) {
        if (round == Constant.ROUND_0) {
            state = SBox.substituteState(state);
        }

        return state;
    }

    private int[] shiftRows(int[] row, int time) {
        if (time < 1) {
            return row;
        } else {
            while (time > 0) {
                int temp = row[0];
                for (int i=1;i<row.length;i++ ) {
                    row[i-1]=row[i];
                }
                row[row.length-1]=temp;
                time--;
            }
        }
        return row;
    }

}
