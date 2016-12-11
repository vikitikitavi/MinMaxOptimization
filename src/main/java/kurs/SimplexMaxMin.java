package kurs;

import java.util.LinkedList;
import java.util.Map;

import static util.PrettifyOutUtils.printTableVariableValues;
import static util.PrettifyOutUtils.printSimplexTable;

/**
 * Created by tori on 010 10.12.16.
 */
public class SimplexMaxMin{
        public static  Double findMax(Double[] aimFunction, Double[][] restrictionsCoefs, LinkedList<String> inequality){
            SimplexTable simple = new SimplexTable(aimFunction, restrictionsCoefs, inequality);

            printSimplexTable(simple);
            printTableVariableValues(simple);

            printSimplex(simple.table, simple.aimFunk, simple.getValuesName(), simple.basis, simple.delta);
            String element = simple.mainColumnMax();
            while (!element.equals("")){
                Integer base = simple.elementInBasisToChange(element);
                simple.changeBasis(element, base);
                element = simple.mainColumnMax();
                printSimplex(simple.table, simple.aimFunk, simple.getValuesName(), simple.basis, simple.delta);
            }
            return  simple.delta[0][1];
        }



    public static  Double findMin(Double[] aimFunction, Double[][] restrictionsCoefs, LinkedList<String> inequality){
        SimplexTable simple = new SimplexTable(aimFunction, restrictionsCoefs, inequality);
        //simple.setMaxOrMin(1);
        printSimplex(simple.table, simple.aimFunk, simple.getValuesName(), simple.basis, simple.delta);
        String element = simple.mainColumnMin();
        while (!element.equals("")){
            Integer base = simple.elementInBasisToChange(element);
            simple.changeBasis(element, base);
            element = simple.mainColumnMin();
            printSimplex(simple.table, simple.aimFunk, simple.getValuesName(), simple.basis, simple.delta);
        }
        return  simple.delta[0][1];
    }
        private static void printSimplex(Double[][] table, Map<String, Double> aimFunk, LinkedList<String> values, String[] basis, Double[][] delta){

            for(int string = 0; string < table.length - 1; string++){
                System.out.print(table[string][0] + "\t" + basis[string] + "\t");
                for(int column = 1; column < table[0].length ; column++)
                    System.out.print(table[string][column] + "\t");
                System.out.println();
            }

            System.out.print("\t\t");
            for (int index = 1; index< delta[0].length; index++)
                System.out.print(delta[0][index] +"+"+ (delta[1][index]).toString()+"M"+"\t");
            System.out.println();
            System.out.println();
        }

        public void printAimFuncktion(){

        }
}
