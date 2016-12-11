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

//        simple.table = table1;
//        simple.basis = basis1;
//        simple.aimFunk=aimFunk;
//        simple.valuesName =valueseName1;
//        simple.changeBasis("x4",0);
//        String element = simple.mainColumnMax();
//        System.out.println(simple.basis[0]+simple.basis[1]+simple.basis[2]+simple.basis[3]);
//        while (!element.equals("")){
//            Integer base = simple.elementInBasisToChange(element);
//            simple.changeBasis(element, base);
//            element = simple.mainColumnMax();
//            System.out.println(simple.basis[0]+simple.basis[1]+simple.basis[2]+simple.basis[3]);
//        }
//        System.out.println(table1[4][1]);
//        System.out.println(table1[3][1]);
//        System.out.println(table1[2][1]);
//        System.out.println(table1[1][1]);
//        System.out.println(table1[0][1]);
//        System.out.println(table1[4][6]);
//        System.out.println(table1[4][7]);
//        System.out.println();
//        System.out.println(simple.delta[0][1]+"   ");
//        System.out.println(simple.delta[0][2]+"   ");
//        System.out.println(simple.delta[0][3]+"    ");
//        System.out.println(simple.delta[0][4] + "   ");
//        System.out.println(simple.delta[0][5]);
//        System.out.println(simple.delta[0][6]);
//        System.out.println(simple.delta[0][7]);
//        System.out.println(simple.delta[0][8]);

        Double[] aimFunc = {7.0, 3.0};

        String targetFunction = "7x1 + 3x2 -> max";
        Double[] aimFuncParsed = getTargetFunctionCoefitients(targetFunction);
//        List<String> restrictions = new ArrayList<String>(Arrays.asList(
//                "-2.3x1 + x2 <= 1",
//                "x1 - 6x2 <= 3",
//                "x1 + x2 <= 10",
//                "x2 <= 11",
//                "2x1 + x2 <= 32"
//        ));

        //Double[][] table ={{0.0, 54.0, 21.0, 4.0, 3.0},{0.0, 5.0, 76.0, 3.0, -7.0},{0.0, 65.0, 76.0, 32.0, 66.0},{0.0, 2.0, 43.0, 5.0, -100.0},{0.0, 0.0, 0.0, 0.0, 0.0}};
        Double[][] table = {{0.0,5.0,65.0,1.0},{0.0, 6.0, 7.0, 8.0},{0.0, 0.0, 0.0, 0.0}};

        List<String> restrictions = new ArrayList<String>(Arrays.asList(
                "65x1 + x2 <= 5",
                "7x1 + 8x2 <= 6"
        ));
//        Double[][] tableParsed = getRestrictionElements(restrictions);

        LinkedList<String> eq = new LinkedList<String>();
        eq.add(">=");
        eq.add("<=");
//        eq.add("<=");
//        eq.add(">=");

        Double max = SimplexMaxMin.findMax(aimFunc, table, eq);
        System.out.println(max);
    }
}
