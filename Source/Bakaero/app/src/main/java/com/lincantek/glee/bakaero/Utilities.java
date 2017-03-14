package com.lincantek.glee.bakaero;

/**
 * Created by luyen on 11/03/2017.
 */

public class Utilities {


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
