package kurs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by tori on 009 09.12.16.
 */
public class NormalViewRestrictions {
    private LinkedList<String> variables = new LinkedList<String>();
    private Double[][] restrictionsCoefs;
    private LinkedList<String> basis = new LinkedList<String>();
    private Map<String, Double> normalAimFunction = new HashMap<String, Double>();
    private Double[][] normalRestrictionsCoefs;
    LinkedList<String> inequality = new LinkedList<String>();
    private int maxOrMin;

    NormalViewRestrictions(Double[] aimFunction, Double[][] restrictionsCoefs, LinkedList<String> inequality, int maxOrMin) {
        this.maxOrMin = maxOrMin;
        this.inequality = inequality;
        normalAimFunction.put("01", 0.0);
        normalAimFunction.put("02", 0.0);
        variables.add("01");
        variables.add("02");
        for (int index = 0; index < aimFunction.length; index++) {
            normalAimFunction.put("x" + (index + 1), aimFunction[index]);
            variables.add("x" + (index + 1));
        }
        this.restrictionsCoefs = restrictionsCoefs;
        setNormalRestrictionsCoefs();
    }

    public String[] getBasis() {
        String[] base = new String[basis.size()];
        for(int index = 0; index<base.length; index++)
            base[index] = basis.get(index);
        return base;
    }

    public Double[][] getNormalRestrictionsCoefs() {
        return normalRestrictionsCoefs;
    }

    public LinkedList<String> getVariables() {
        return variables;
    }

    public Map<String, Double> getNormalAimFunction() {
        return normalAimFunction;
    }

    private void setNormalRestrictionsCoefs() {

        int oneMoreVariable = 0;
        for (String element : inequality) {
            if (element.equals(">="))
                oneMoreVariable++;
        }

        normalRestrictionsCoefs = new Double[restrictionsCoefs.length][restrictionsCoefs[0].length + restrictionsCoefs.length + oneMoreVariable - 1];
        for (int column = 0; column < restrictionsCoefs[0].length; column++)
            for (int string = 0; string < restrictionsCoefs.length; string++)
                normalRestrictionsCoefs[string][column] = restrictionsCoefs[string][column];

        int column = restrictionsCoefs[0].length;
        for (int index = 0; index < inequality.size(); index++) {
            if (inequality.get(index).equals(">=")) {
                for (int string = 0; string < normalRestrictionsCoefs.length; string++)
                    if (string == index) {
                        normalRestrictionsCoefs[string][column] = -1.0;
                        variables.add("x" + (column - 1));
                        normalAimFunction.put("x" + (column - 1), 0.0);
                    } else normalRestrictionsCoefs[string][column] = 0.0;
                column++;
                for (int string = 0; string < normalRestrictionsCoefs.length; string++)
                    if (string == index) {
                    if(maxOrMin == -1){
                        normalRestrictionsCoefs[string][column] = 1.0;
                        basis.add("-M" + (column - 1));
                        variables.add("-M" + (column - 1));
                        normalAimFunction.put("-M" + (column - 1), Double.MIN_VALUE);
                    }
                    else {
                        normalRestrictionsCoefs[string][column] = 1.0;
                        basis.add("M" + (column - 1));
                        variables.add("M" + (column - 1));
                        normalAimFunction.put("M" + (column - 1), Double.MAX_VALUE);
                    }
                    } else normalRestrictionsCoefs[string][column] = 0.0;


            } else for (int string = 0; string < normalRestrictionsCoefs.length; string++)
                if (index == string) {
                    normalRestrictionsCoefs[string][column] = 1.0;
                    basis.add("x" + (column - 1));
                    variables.add("x" + (column - 1));
                    normalAimFunction.put("x" + (column - 1), 0.0);
                } else normalRestrictionsCoefs[string][column] = 0.0;

            column++;

        }

        for (int index = 0; index < basis.size(); index++) {
            normalRestrictionsCoefs[index][0] = normalAimFunction.get(basis.get(index));
        }
    }

}
