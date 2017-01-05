package kurs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static util.InputParsingUtils.getInEqualityStates;
import static util.InputParsingUtils.getRestrictionElements;
import static util.InputParsingUtils.getTargetFunctionCoefficients;

/**
 * Created by tori on 028 28.12.16.
 */
public class Lamda {

    public static List<String> newRestrictionOptimistic(List<String> restrictions, double LAMBDA) {
        Double[][] table = getRestrictionElements(restrictions);
        List<String> newRestrictions = new ArrayList<String>();
        String restriction = new String();
        double a = 1.0 / 2.0;
        restriction += (table[0][2] * (1 + Math.sqrt(1.0 / (2.0 * LAMBDA) - 0.5))) + "x1";
        for (int index = 1; index < 6; index++) {
            restriction += " + " + (table[0][2 + index] * (1 + Math.sqrt(1.0 / (2.0 * LAMBDA) - 0.5))) + "x" + (index + 1);
        }
        for (int index = 6; index < 9; index++) {
            restriction += " + " + (table[0][2 + index] * (1 - Math.sqrt(1.0 / (2.0 * LAMBDA) - 0.5))) + "x" + (index + 1);
        }
        restriction += " = 0";
        newRestrictions.add(restriction);
        for (int index = 1; index < restrictions.size(); index++)
            newRestrictions.add(restrictions.get(index));
        return newRestrictions;
    }

    public static List<String> newRestrictionPesimistics(List<String> restrictions, double LAMBDA) {
        Double[][] table = getRestrictionElements(restrictions);
        List<String> newRestrictions = new ArrayList<String>();
        String restriction = new String();
        double a = 1.0 / 2.0;
        restriction += (table[0][2] * (1 - Math.sqrt(1.0 / (2.0 * LAMBDA) - 0.5))) + "x1";
        for (int index = 1; index < 6; index++) {
            restriction += " + " + (table[0][2 + index] * (1 - Math.sqrt(1.0 / (2.0 * LAMBDA) - 0.5))) + "x" + (index + 1);
        }
        for (int index = 6; index < 9; index++) {
            restriction += " + " + table[0][2 + index] * (1 + Math.sqrt(1.0 / (2.0 * LAMBDA) - 0.5)) + "x" + (index + 1);
        }
        restriction += " = 0";
        newRestrictions.add(restriction);
        for (int index = 1; index < restrictions.size(); index++)
            newRestrictions.add(restrictions.get(index));
        return newRestrictions;
    }

}