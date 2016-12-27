package kurs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static util.InputParsingUtils.getInEqualityStates;
import static util.InputParsingUtils.getRestrictionElements;
import static util.InputParsingUtils.getTargetFunctionCoefficients;

/**
 * Created by tori on 011 11.12.16.
 */
public class MultiTask {
    static final Double WEIGHT_COFFITIENT = 0.5;
    static int kIndex;
    private String newRestriction(String targetFunction, List<String> restrictions){
        kIndex = 0;
        Double[] aimFuncParsed = getTargetFunctionCoefficients(targetFunction);
        Double[][] tableParsed = getRestrictionElements(restrictions);
        LinkedList<String> eq = getInEqualityStates(restrictions);
        String out = "F = " + aimFuncParsed[0].toString() + "x1";
        for(int index = 1; index < aimFuncParsed.length; index++)
            out+= " + "+aimFuncParsed[0].toString() + "x" + (index + 1);
            System.out.println(out);
        for(int index = 0; index < restrictions.size(); index++)
            System.out.println(restrictions.get(index));
        System.out.println();
        Double max = SimplexMaxMin.findMax(aimFuncParsed, tableParsed, eq);

        Double min = SimplexMaxMin.findMin(aimFuncParsed, tableParsed, eq);

        String newRestriction = WEIGHT_COFFITIENT * (aimFuncParsed[0])/(max - min) + "x" + 1;
        for(int index = 1; index < aimFuncParsed.length; index++)
            newRestriction += " + "+ WEIGHT_COFFITIENT * (aimFuncParsed[index])/(max - min) + "x" + (index + 1);
        if((aimFuncParsed.length + 1) > kIndex)
            kIndex = aimFuncParsed.length + 1;
        newRestriction+= " + x" + kIndex + " >= " + WEIGHT_COFFITIENT * max/(max - min);
        return newRestriction;
    }

    private List<String> restrictionsForK(String firstTargetFunction, String secondTargetFunctions, List<String> restrictions){
        List<String> result = new ArrayList<String>();
        String first = newRestriction(firstTargetFunction, restrictions);
        String second = newRestriction(secondTargetFunctions, restrictions);
        for(int index=0; index < restrictions.size(); index++)
            result.add(restrictions.get(index));
        result.add(first);
        result.add(second);
        return result;
    }

    private String newTargetFunction(){
        return new String ("x"+ kIndex +" -> max");
    }

    public void multiTaskResult(String firstTargetFunction, String secondTargetFunctions, List<String> restrictions){

        List<String> restrictionsForTarget = restrictionsForK(firstTargetFunction,secondTargetFunctions,restrictions);
        String target = newTargetFunction();
        Double[] aimFuncParsed = getTargetFunctionCoefficients(target);
        Double[][] tableParsed = getRestrictionElements(restrictionsForTarget);
        LinkedList<String> eq = getInEqualityStates(restrictionsForTarget);
        System.out.println("F = x"+ kIndex +" -> min");
        for(int index = 0; index < restrictionsForTarget.size(); index++)
            System.out.println(restrictionsForTarget.get(index));
        System.out.println("K0 = " + SimplexMaxMin.findMin(aimFuncParsed,tableParsed,eq));

    }
}
