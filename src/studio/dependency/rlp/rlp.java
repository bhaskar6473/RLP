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

    private static byte[] int2byte(
            int number) {
        if ((number & 0xFF) == number) {
            return new byte[]{(byte) (number)};
        }
        if ((number & 0xFFFF) == number) {
            return new byte[]{(byte) (number >>> 8),
                (byte) (number)};
        }
        if ((number & 0xFFFFFF) == number) {
            return new byte[]{(byte) (number >>> 16),
                (byte) (number >>> 8),
                (byte) (number)};
        }
        if ((number & 0xFFFFFFFF) == number) {
            return new byte[]{(byte) (number >>> 24),
                (byte) (number >>> 16),
                (byte) (number >>> 8),
                (byte) (number)};
        } else {
            return new byte[]{(byte) (0)};
        }
    }

    public static byte[] encode(byte[] bytAry) {
        if (bytAry.length <= 1) {
            //Comfirmed single byte, Now check if it is zero
            if ((bytAry[0] & 0xFF) == 0) {
                return new byte[]{(byte) 0x80};
            } else if ((bytAry[0] & 0xFF) <= 0x7F) {
                //if we reach here we confirm that number belowns to [0,127] "[]" represent closed interval
                return new byte[]{(byte) bytAry[0]};
            } else {
                return new byte[]{(byte) (0x80 + 1), bytAry[0]};
            }
        }
        if (bytAry.length == 2) {
            //Check if there is 2 bytes
            //System.out.print("short byte");
            return new byte[]{
                (byte) (0x80 + 2),//Prefix
                bytAry[0],
                bytAry[1]
            };
        }
        if (bytAry.length == 3) {
            //Check for int 
            //System.out.print("int 3 byte");
            return new byte[]{
                (byte) (0x80 + 3),//Prefix
                bytAry[0],
                bytAry[1],
                bytAry[2]
            };
        }
        if (bytAry.length == 4) {
            return new byte[]{
                (byte) (0x80 + 4),//Prefix
                bytAry[0],
                bytAry[1],
                bytAry[2],
                bytAry[3]
            };
        } else {
            return new byte[]{};
        }
    }
    
    public static String byteAryToHex(byte[] byteAry){
        final StringBuilder builder = new StringBuilder();
        for(byte b : byteAry){
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    //Test
    public static void main(String args[]) {
        int a = 1024;
        byte dst[] = null;
        byte b[] = encode(int2byte(a));
        System.out.println(byteAryToHex(b));
    }

}
