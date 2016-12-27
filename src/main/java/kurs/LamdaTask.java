package kurs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static util.InputParsingUtils.getInEqualityStates;
import static util.InputParsingUtils.getRestrictionElements;
import static util.InputParsingUtils.getTargetFunctionCoefficients;

/**
 * Created by tori on 012 12.12.16.
 */
public class LamdaTask {
    String[] targetFunctions = new String[4];
    final String LAMBDA;

    LamdaTask(Double[][] coefficients,List<String> restrictions){
        LAMBDA = "x" + (coefficients[0].length + 1) + "-> max";
        for(int targetFunc =0; targetFunc < coefficients.length; targetFunc++) {
            String func = new String();
            func += coefficients[targetFunc][0] + "x" + 1;
            for (int variableCoef = 1; variableCoef < coefficients[0].length; variableCoef++) {
                func += "+"+ coefficients[targetFunc][variableCoef] + "x" + (variableCoef + 1);
            }
            func+=" -> max";
            targetFunctions[targetFunc] = func;
        }
        findRestrctionsForLambda(restrictions);
    }
    private String newRestriction(String targetFunction, List<String> restrictions){
        Double[] aimFuncParsed = getTargetFunctionCoefficients(targetFunction);
        Double[][] tableParsed = getRestrictionElements(restrictions);
        LinkedList<String> eq = getInEqualityStates(restrictions);

        String out = "F = " + aimFuncParsed[0].toString() + "x1";
        for(int index = 1; index < aimFuncParsed.length; index++)
            out+= " + "+aimFuncParsed[0].toString() + "x" + (index + 1);
        System.out.println(out);
        for(int index = 0; index < restrictions.size(); index++)
            System.out.println(restrictions.get(index));

        Double max = SimplexMaxMin.findMax(aimFuncParsed, tableParsed, eq);
        Double min = SimplexMaxMin.findMin(aimFuncParsed, tableParsed, eq);
//        System.out.println(min);
//        System.out.println(max);

        String newRestriction = new String();

        if(min <= 0) {
            String coefficients = aimFuncParsed[0] + "x1";
            for (int index = 1; index < aimFuncParsed.length; index++)
                coefficients += " + " + aimFuncParsed[index] + "x"+(index + 1);
            newRestriction = coefficients + " + "+ (max - min) + "x" + (aimFuncParsed.length + 1) + " <= " + (-min);
        }
        else {
            String coefficients = aimFuncParsed[0] + "x1";
            for (int index = 1; index < aimFuncParsed.length; index++)
                coefficients += " + " + ((-1) * aimFuncParsed[index]) + "x"+(index + 1);
            newRestriction = coefficients +" + "+ (min - max) + "x" + (aimFuncParsed.length + 1) + " >= " + min;
        }
        return newRestriction;
    }

    private void findRestrctionsForLambda(List<String> restrictions){
        List<String> lambdaRestrictions = new ArrayList<String>();
        for(int index=0; index < restrictions.size(); index++)
            lambdaRestrictions.add(restrictions.get(index));
        for(int function = 0; function < targetFunctions.length; function++)
            lambdaRestrictions.add(newRestriction(targetFunctions[function], restrictions));
        Double[] aimFuncParsed = getTargetFunctionCoefficients(LAMBDA);
        Double[][] tableParsed = getRestrictionElements(lambdaRestrictions);
        LinkedList<String> eq = getInEqualityStates(lambdaRestrictions);
        System.out.println("F = LAMBDA -> min");
        for(int index = 0; index < lambdaRestrictions.size(); index++)
            System.out.println(lambdaRestrictions.get(index));
        System.out.println("LAMBDA = " + SimplexMaxMin.findMax(aimFuncParsed, tableParsed, eq));

        System.out.println(lambdaRestrictions);
    }
}
