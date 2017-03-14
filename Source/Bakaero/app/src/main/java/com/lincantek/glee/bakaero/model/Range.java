package com.lincantek.glee.bakaero.model;

/**
 * Created by luyen on 14/03/2017.
 */

public class Range {
    private int upper;
    private int lower;

    public static Range OneDigit = new Range(0,9);
    public static Range SmallNumberHigherTen = new Range(10,29);
    public static Range SmallNumber = new Range(1,29);
    public static Range MediumNumber = new Range(25, 49);

    public static Range AddnSub = new Range(0,1);
    public static Range Mul = new Range(2,2);
    public static Range Div = new Range(3,3);

    public Range(int lower, int upper){
        this.lower = lower;
        this.upper = upper;
    }

    public int getUpper() {
        return upper;
    }

    public int getLower() {
        return lower;
    }
}
