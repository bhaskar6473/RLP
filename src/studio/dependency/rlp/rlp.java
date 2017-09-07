/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studio.dependency.rlp;

/**
 *
 * @author Bhaskar Singh
 */
public class rlp {
    public static byte[] encode(byte byt){
        if((byt & 0xFF) == byt){
            System.out.print("single byte" );
            //Comfirmed single byte, Now check if it is zero
            if((byt & 0xFF) == 0){
                return new byte[]{(byte) 0x80};
            }
            else if((byt & 0xFF) <= 0x7F){ 
                //if we reach here we confirm that number belowns to [0,127] "[]" represent closed interval
                return new byte[]{(byte) byt};
            } else{
                return new byte[]{(byte)(0x80 + 1), byt};
            }
        }
        //Check if there is 2 bytes
        if((byt >> 8 & 0xFF) != 0){
            System.out.print("short byte" );
            return new byte[]{
                (byte) (0x80 + 2),//Prefix
                (byte) (byt >> 8 & 0xFF),// shift the first byte, and check
                (byte) byt// shit the last(small, according to big_endian), here
            };
        }
        //Check for int 
        if((byt & 0xFFFFFF) == byt){
            System.out.print("int 3 byte" );
            return new byte[]{
                (byte) (0x80 + 3),//Prefix
                (byte) (byt >>> 16 & 0xFF),// shift the first byte, and fill zeros
                (byte) (byt >>> 8 & 0xFF),
                (byte) byt
            };
        }
        if((byt & 0xFFFFFFFF) == byt){
            return new byte[]{
                (byte) (0x80 + 4),//Prefix
                (byte) (byt >>> 24 & 0xFF),// shift the first byte, and fill zeros
                (byte) (byt >>> 16 & 0xFF),
                (byte) (byt >>> 8 & 0xFF),
                (byte) byt
            };
        }
        else{
            return new byte[]{};
        }
    }
    //Test
    /*public static void main(String args[]){
        short a = 128; short shrt = 6550;
        byte b = (byte) a;
        byte[] c = encode(b);
        for (int i = 0; i < c.length; i++){
            System.out.print(c[i] + " " );
        }
    }*/
    
}
