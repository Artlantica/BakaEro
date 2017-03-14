package com.lincantek.glee.bakaero;

/**
 * Created by luyen on 11/03/2017.
 */

public class Utilities {


    /**
     * Check a number if it's prime, assume that 1 is a prime
     * @param num
     * @return true if num is a prime
     */
    public static boolean isPrime(int num){
        if (num==0) return false;
        if (num<=3) return true;

        for (int i=2; i<=Math.sqrt(num); i++){
            if (num%i==0){
                return false;
            }
        }
        return true;
    }

    /**
     * @param temp
     * @param base
     * @return 1: equal; -1: not equal; 0: invalid
     */
    public static int specCompare(int temp, int base) {
        String strTemp = "" + temp;
        String strBase = "" + base;

        if (strTemp.length() == strBase.length()) {
            return strBase.equals(strTemp) ? 1 : -1;
        }

        for (int i = 0; i < strTemp.length(); i++) {
            if (strBase.charAt(i) != strTemp.charAt(i)){
                return -1;
            }
        }

        return 0;
    }
}
