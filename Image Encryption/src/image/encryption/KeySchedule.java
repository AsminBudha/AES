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
public class KeySchedule {

    private final int[][] initialKey = new int[][]{
        {0x2b, 0x28, 0xab, 0x09},
        {0x7e, 0xae, 0xf7, 0xcf},
        {0x15, 0xd2, 0x15, 0x4f},
        {0x16, 0xa6, 0x88, 0x3c}
    };
    //rcon for 128 bits key
    private static final int[][] rcon = new int[][]{
        {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36},
        {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00},
        {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00},
        {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}
    };

    //storage for round key other than initaila key
    private int[][] roundKey = new int[44][4];

    public KeySchedule() {
        generateRoundKey();
    }

    private void generateRoundKey() {
        //put initial key in a key schedule
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                roundKey[i][j] = initialKey[i][j];
            }
        }
        for (int j = 4; j < roundKey[0].length; j++) {
            if (j % 4 == 0) {//if col of key schedule is divisible by 4, rotate, sub byte and xor with rcon
                roundKey[j] = rotate(roundKey[j - 1]);
                roundKey[j] = SBox.substituteCol(roundKey[j]);
                int rconIndex=j/4;
                for(int i=0;i<roundKey[j].length;i++){
                    roundKey[j][i]^=rcon[rconIndex][i];
                }
            }
            for(int i=0;i<roundKey[j].length;i++){
                roundKey[j][i]^=roundKey[j-4][i];
            }
            
        }
    }

    private int[] rotate(int[] col) {

        int temp = col[0];

        for (int i = 1; i < col.length; i++) {
            col[i - 1] = col[i];
        }
        col[col.length-1]=temp;

        return col;
    }

    public int[][] getRoundKey(int round) {
        int col=round*4;
        int[][] state=new int[][]{
            roundKey[col],roundKey[col+1],roundKey[col+2],roundKey[col+3]
        };
        
        return state;
    }
    
}
