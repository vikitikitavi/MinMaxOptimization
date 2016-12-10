import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by tori on 009 09.12.16.
 */
public class NormalView {
    private LinkedList<String> variables = new LinkedList<String>();
    private Double[][] restrictionsCoefs;
    private LinkedList<String> basis = new LinkedList<String>();
    private Map<String, Double> normalAimFunction = new HashMap<String, Double>();
    private Double[][] normalRestrictionsCoefs;

    NormalView(Double[] aimFunction, Double[][] restrictionsCoefs){
        normalAimFunction.put("01", 0.0);
        normalAimFunction.put("02", 0.0);
        variables.add("01");
        variables.add("02");
        for(int index = 0; index < aimFunction.length; index++) {
            normalAimFunction.put("x" + (index + 1), aimFunction[index]);
            variables.add("x" + (index + 1));
        }
        this.restrictionsCoefs = restrictionsCoefs;
        setNormalRestrictionsCoefs();
    }

    public LinkedList<String> getBasis(){
        return basis;
    }

    public Double[][] getNormalRestrictionsCoefs(){
        return normalRestrictionsCoefs;
    }

    public LinkedList<String> getVariables(){
        return variables;
    }

    public  Map<String, Double> getNormalAimFunction(){
        return normalAimFunction;
    }

    private void setNormalRestrictionsCoefs(){
        normalRestrictionsCoefs = new Double[restrictionsCoefs.length][restrictionsCoefs[0].length + restrictionsCoefs.length - 1];
        for(int column = 0; column < restrictionsCoefs[0].length; column++)
            for (int string = 0; string < restrictionsCoefs.length; string++)
                normalRestrictionsCoefs[string][column] = restrictionsCoefs[string][column];
        for(int column = restrictionsCoefs[0].length; column < normalRestrictionsCoefs[0].length; column++)
                for (int string = 0; string < normalRestrictionsCoefs.length; string++)
                    if(column - restrictionsCoefs[0].length == string) {
                        normalRestrictionsCoefs[string][column] = 1.0;
                        basis.add("x"+ (column - 1));
                        variables.add("x" +(column - 1));
                        normalAimFunction.put("x"+ (column - 1), 0.0);
                    }
                else  normalRestrictionsCoefs[string][column] = 0.0;
    }

//    private Double[] toNormalRestrictionCoefs(){
//        for (int colunn = 0; colunn < restrictionsCoefs[0].length; colunn++) {
//            int counter = 0;
//            int index = -1;
//             for(int string = 0; string < restrictionsCoefs.length; string++)
//             {
//                if (restrictionsCoefs[string][colunn] != 0)
//                    counter++;
//                    index = string;
//             }
//            if(counter == 1) {
//                Double divider = restrictionsCoefs[index][colunn];
//                for (int column = 0; column < restrictionsCoefs[0].length; column++)
//                    restrictionsCoefs[index][column] = restrictionsCoefs[index][column]/divider;
//                basis.add("x"+index);
//            }
//        }
//
//        normalRestrictionsCoefs = new Double[restrictionsCoefs.length + restrictionsCoefs.length - basis.size()][restrictionsCoefs[0].length];
//        for(int column = 0; column < restrictionsCoefs[0].length; column++)
//            for (int string = 0; string < restrictionsCoefs.length; string++)
//                normalRestrictionsCoefs[string][column] = restrictionsCoefs[string][column];
//
//        if(basis.size() != restrictionsCoefs.length){
//            for(int column = restrictionsCoefs[0].length; column < normalRestrictionsCoefs[0].length; column++)
//                for (int string = restrictionsCoefs.length; string < normalRestrictionsCoefs.length; string++)
//                    normalRestrictionsCoefs[string][column] = 0.0;
//        }
//        for(String elements : basis)
//
//
//
//    }
}
