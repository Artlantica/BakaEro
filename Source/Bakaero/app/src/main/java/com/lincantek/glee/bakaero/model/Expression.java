package com.lincantek.glee.bakaero.model;

/**
 * Created by luyen on 11/03/2017.
 */

public class Expression {
    private int operandOne;
    private int operandTwo;
    /**
     * Operator of expression, follow these order:
     *      +    -   x   /
     */
    private int operator;

    public Expression(int operandOne, int operandTwo, int operator) {
        this.operandOne = operandOne;
        this.operandTwo = operandTwo;
        this.operator = operator;
    }

    @Override
    public String toString(){

        return String.format("%d %s %d = ",operandOne,getOperator(),getOperandTwo());
    }

    public int getResult(){
        switch (operator){
            case 0:
                return (operandOne+operandTwo);
            case 1:
                return (operandOne-operandTwo);
            case 2:
                return (operandOne*operandTwo);
            case 3:
                return (operandOne/operandTwo);
            default:
                return (9999);
        }
    }

    public int getOperandOne() {
        return operandOne;
    }

    public int getOperandTwo() {
        return operandTwo;
    }

    public String getOperator() {
        switch (operator){
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "x";
            case 3:
                return "/";
            default:
                return "..";
        }
    }
}
