package kurs;

import java.util.LinkedList;
import java.util.Map;

public class SimplexTable {
    public String[] basis;
    public Double[][] table; // размерность таблицы определяется количеством ограничений(строки) и переменных(столбцы) + строка выхода
    private LinkedList<String> valuesName;
    public Map<String, Double> aimFunk;
    public Double[][] delta;
    private int maxOrMin = -1;

    SimplexTable(Double[] aimFunction, Double[][] restrictionsCoefs, LinkedList<String> inequality, int maxOrMin) {
        this.maxOrMin = maxOrMin;
        NormalViewRestrictions n = new NormalViewRestrictions(aimFunction, restrictionsCoefs, inequality, maxOrMin);
        table = n.getNormalRestrictionsCoefs();
        basis = n.getBasis();
        valuesName = n.getVariables();
        aimFunk = n.getNormalAimFunction();
        delta = new Double[2][table[0].length];
        countDelta();
    }

    public void changeBasis(String name, Integer number) {

        basis[number] = name;
        Integer mainColumn = valuesName.indexOf(name);

        //меняем ведущую строку
        Double elToDivide = table[number][mainColumn];
        for (int column = 1; column < table[0].length; column++)
            table[number][column] = table[number][column] / elToDivide;

        //перестраиваем симплекс таблицу относительно ведущей строки
        for (int string = 0; string < table.length - 1; string++) {
            if (string != number) {
                Double takeAwayMulti = table[string][mainColumn];
                for (int column = 1; column < table[0].length; column++)
                    table[string][column] = table[string][column] - table[number][column] * takeAwayMulti;
            }
        }

        // изменяем значения коеф для нововведеной базисной переменной
        table[number][0] = aimFunk.get(name);

        countDelta();
    }

    public String mainColumnMax() {
        Double[] minDelta = {0.0, 0.0};
        String mainColumn = "";
        for (int column = 2; column < delta[0].length; column++)
            if (((minDelta[0] > delta[0][column]) && (minDelta[1] >= delta[1][column]))
                    || ((minDelta[0].equals(delta[0][column])) && (minDelta[1] > delta[1][column]))
                    || ((minDelta[0] < delta[0][column]) && (minDelta[1] > delta[1][column]))) {
                minDelta[0] = delta[0][column];
                minDelta[1] = delta[1][column];
                mainColumn = valuesName.get(column);
            }
        return mainColumn;
    }

    public Integer elementInBasisToChange(String mainColumn) {
        Integer mainCol = valuesName.indexOf(mainColumn);
        Double min = Double.MAX_VALUE;
        Integer result = -1;
        for (int string = 0; string < table.length - 1; string++) {
            if ((table[string][mainCol] > 0) && (min > Math.abs((double) (table[string][1] / table[string][mainCol])))) {
                result = string;
                min = Math.abs((double) (table[string][1] / table[string][mainCol]));
            }
        }
        return result;
    }

    public String mainColumnMin() {
        Double[] minDelta = {0.0, 0.0};
        String mainColumn = "";
        for (int column = 2; column < delta[0].length; column++)
            if (((minDelta[0] < delta[0][column]) && (minDelta[1] <= delta[1][column]))
                    || ((minDelta[0].equals(delta[0][column])) && (minDelta[1] < delta[1][column]))
                    || ((minDelta[0] > delta[0][column]) && (minDelta[1] < delta[1][column]))) {
                minDelta[0] = delta[0][column];
                minDelta[1] = delta[1][column];
                mainColumn = valuesName.get(column);
            }
        return mainColumn;
    }

    public void countDelta() {
        for (int column = 1; column < delta[0].length; column++)
            for (int string = 0; string < delta.length; string++)
                delta[string][column] = 0.0;

        // подсчет оценок отдельным масивом
        for (int column = 1; column < delta[0].length; column++) {
            for (int string = 0; string < table.length - 1; string++) {
                if ((table[string][0] == Double.MAX_VALUE) || (table[string][0] == Double.MIN_VALUE))
                    delta[1][column] = delta[1][column] + maxOrMin * table[string][column];
                else delta[0][column] = delta[0][column] + table[string][column] * table[string][0];
            }
            if (aimFunk.get(valuesName.get(column)) == Double.MIN_VALUE)
                delta[1][column] -= maxOrMin;
            else if (aimFunk.get(valuesName.get(column)) == Double.MAX_VALUE)
                delta[1][column] -= maxOrMin;
            else delta[0][column] -= aimFunk.get(valuesName.get(column));
        }
    }

    public void setMaxOrMin(int maxOrMin) {
        this.maxOrMin = maxOrMin;
    }

    public LinkedList<String> getValuesName() {
        return new LinkedList<String>(valuesName);
    }

}
