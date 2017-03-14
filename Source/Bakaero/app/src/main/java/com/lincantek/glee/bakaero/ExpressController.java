package com.lincantek.glee.bakaero;

import com.lincantek.glee.bakaero.model.Expression;
import com.lincantek.glee.bakaero.model.Range;

import java.util.Random;

import static com.lincantek.glee.bakaero.Utilities.isPrime;

/**
 * Created by luyen on 11/03/2017.
 */
public class ExpressController {


    private Random rand;

    public Expression createExpression(int numQuest){

        // set range for two operators
        int lv = -1;
        do {
            lv++;
            // default 3 question for each level
            numQuest-=3;
        } while (numQuest>0 && lv<9);

        // has 30% to get a question with difficult lower than present
        lv = (rand(new Range(1,3)))==0? rand(new Range(1, lv-1)) : lv;

        int opaOne, opaTwo;
        int operator = rand(sampRange[lv].getOperator());

        switch (operator){
            case 1:
                /* make sure that result of sub(-) is not negative */
                opaOne = rand(sampRange[lv].getOperOne());
                opaTwo = rand(new Range(sampRange[lv].getOperTwo().getLower(), opaOne));
                break;
            case 3:
                /* make sure that the div is divisible */
                opaOne = rand(sampRange[lv].getOperOne());
                if (opaOne==0){
                    opaTwo = rand(new Range(1, sampRange[lv].getOperTwo().getUpper()));
                }else if (isPrime(opaOne)){
                    // if opaOne is a prime, opaTwo = 1 or opaOne
                    opaTwo= (rand(new Range(0,1))==1) ? 1 : opaOne;
                }else{
                    do{
                        opaTwo=rand(new Range( 2, (int) Math.sqrt(opaOne)));
                    }while (opaOne % opaTwo !=0 );
                }
                break;
            default:
                opaOne = rand(sampRange[lv].getOperOne());
                opaTwo = rand(sampRange[lv].getOperTwo());
        }
        return new Expression(opaOne, opaTwo, operator);
    }


    private int rand(Range range) {
        if (range.getUpper()<range.getLower()) return range.getLower();
        return rand.nextInt(range.getUpper()-range.getLower()+1)+range.getLower();
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

    private final ExpressRange[] sampRange = {
            // kind 1: a +/- b
            new ExpressRange(Range.AddnSub, Range.OneDigit, Range.OneDigit),
            // kind 2: a +/- bc
            new ExpressRange(Range.AddnSub, Range.OneDigit, Range.SmallNumber),
            // kind 3: a x b
            new ExpressRange(Range.Mul, Range.OneDigit, Range.OneDigit),
            // kind 4: ab +/- cd
            new ExpressRange(Range.AddnSub, Range.SmallNumberHigherTen, Range.SmallNumberHigherTen),
            // kind 5: a x bc
            new ExpressRange(Range.Mul, Range.OneDigit, Range.SmallNumber),
            // kind 6: ab / c
            new ExpressRange(Range.Div, Range.SmallNumberHigherTen, Range.OneDigit),
            // kind 7: ab +/- cd (high)
            new ExpressRange(Range.AddnSub, Range.MediumNumber, Range.MediumNumber),
            // kind 8: a x bc (high)
            new ExpressRange(Range.Mul, Range.OneDigit, Range.MediumNumber),

            // kind 9: ab x bc (high)
            new ExpressRange(Range.Div, Range.SmallNumberHigherTen, Range.MediumNumber)
    };

    class ExpressRange{
        Range operOne;
        Range operTwo;
        Range operator;

        ExpressRange(Range operator, Range operOne, Range operTwo) {
            this.operOne = operOne;
            this.operTwo = operTwo;
            this.operator = operator;
        }

        public Range getOperOne() {
            return operOne;
        }

        public Range getOperTwo() {
            return operTwo;
        }

        public Range getOperator() {
            return operator;
        }
    }


}
