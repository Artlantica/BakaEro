package com.lincantek.glee.bakaero;

import com.lincantek.glee.bakaero.model.Expression;

import java.util.Random;

/**
 * Created by luyen on 11/03/2017.
 */
public class ExpressController {


    private Random rand;

    public Expression createExpression(int lv){

        int min = 0;        int max = 50;

        int opaOne=0, opaTwo=0;
        int operator = rand(0,3);

        switch (operator){
            case 1: // make sure that result of sub(-) is not negative
                opaOne = rand(min,max);
                opaTwo = rand(min,opaOne);
                break;
            case 3: // make sure that the div is divisible
                if (opaOne==0){
                    opaTwo = rand(1, max);
                }else if (isPrime(opaOne)){
                    // if opaOne is a prime, opaTwo = 1 or opaOne
                    opaTwo= (rand(0,1)==1) ? 1 : opaOne;
                }else{
                    do{
                        opaTwo=rand( 2, (int) Math.sqrt(opaOne));
                    }while (opaOne % opaTwo !=0 );
                }
                break;
            default:
                opaOne = rand(min,max);
                opaTwo = rand(min,max);
        }
        return new Expression(opaOne, opaTwo, operator);
    }


    private boolean isPrime(int num){
        // default: 1 is a prime.
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
     * @param upper
     * @param lower
     * @return A random integer between lower and upper
     */
    private int rand(int lower, int upper) {
        if (upper<lower) return lower;
        return rand.nextInt(upper-lower+1)+lower;
    }


    /**
     * Singleton
     */
    private static ExpressController ourInstance = new ExpressController();

    public static ExpressController getInstance() {
        return ourInstance;
    }

    private ExpressController() {
        rand = new Random();
    }
}
