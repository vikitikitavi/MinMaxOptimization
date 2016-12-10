import java.util.*;

import static util.InputParsingUtils.getRestrictionElements;
import static util.InputParsingUtils.getTargetFunctionCoefitients;

/**
 * Created by tori on 009 09.12.16.
 */
public class Simplex {

    public static void main(String[] args) {

        Double[][] table1 = {{0.0, 54.0, 21.0, 4.0, 3.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, 5.0, 76.0, 3.0, -7.0, 0.0, 1.0, 0.0, 0.0},
                {0.0, 65.0, 76.0, 32.0, 66.0, 0.0, 0.0, 1.0, 0.0},
                {0.0, 2.0, 43.0, 5.0, -100.0, 0.0, 0.0, 0.0, 1.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};
        LinkedList<String> valueseName1 = new LinkedList<String>();
        valueseName1.add("01");
        valueseName1.add("02");
        valueseName1.add("x1");
        valueseName1.add("x2");
        valueseName1.add("x3");
        valueseName1.add("x4");
        valueseName1.add("x5");
        valueseName1.add("x6");
        valueseName1.add("x7");
        String[] basis1 ={"x4","x5","x6","x7"};
        Map<String, Double> aimFunk = new HashMap<String, Double>();
        aimFunk.put("01",0.0);
        aimFunk.put("02",0.0);
        aimFunk.put("x1",45.0);
        aimFunk.put("x2",2.0);
        aimFunk.put("x3",5.0);
        aimFunk.put("x4",0.0);
        aimFunk.put("x5",0.0);
        aimFunk.put("x6",0.0);
        aimFunk.put("x7",0.0);

        SimplexTable simple = new SimplexTable();
        simple.table = table1;
        simple.basis = basis1;
        simple.aimFunk=aimFunk;
        simple.valuesName =valueseName1;
        simple.changeBasis("x4",0);
        String element = simple.mainColumnMax();
        System.out.println(simple.basis[0]+simple.basis[1]+simple.basis[2]+simple.basis[3]);
        while (!element.equals("")){
            Integer base = simple.elementInBasisToChange(element);
            simple.changeBasis(element, base);
            element = simple.mainColumnMax();
            System.out.println(simple.basis[0]+simple.basis[1]+simple.basis[2]+simple.basis[3]);
        }


        Double[] aimFunc = {3.0,2.0};

        String targetFunction = "x1 + 2x2 -> max";
        Double[] asd = getTargetFunctionCoefitients(targetFunction);
        List<String> restrictions = new ArrayList<String>(Arrays.asList(
                "-2.3x1 + x2 <= 1",
                "x1 - 6x2 <= 3",
                "x1 + x2 <= 10",
                "x2 <= 11",
                "2x1 + x2 <= 32"
        ));

        getRestrictionElements(restrictions);

        Double[][] table ={{0.0, 3.0, 1.0, -6.0},{0.0, 10.0, 1.0, 1.0},{0.0, 1.0, -2.0, 1.0},{0.0, 11.0, 0.0, 1.0},{0.0, 0.0, 0.0, 0.0}};
        NormalView n = new NormalView(aimFunc, table);
        Double[][] a = n.getNormalRestrictionsCoefs();
        for(int string = 0; string < a.length; string++) {
            for (int column = 0; column < a[0].length; column++) {
                System.out.print(a[string][column]+" ");
            }
            System.out.println();
        }
        System.out.println();
        LinkedList<String> a2 = n.getBasis();
        System.out.println(a2+" ");

        System.out.println();
        LinkedList<String> a3 = n.getVariables();
        System.out.println(a3+" ");
        Map<String, Double> a4 = n.getNormalAimFunction();
        System.out.println(a4);

    }
}
