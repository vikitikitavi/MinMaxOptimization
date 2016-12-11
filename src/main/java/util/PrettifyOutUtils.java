package util;

import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestLine;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import kurs.SimplexTable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by imps on 011 11.12.16.
 */
public class PrettifyOutUtils {

    public static void printSimplexTable(final SimplexTable simplexTable) {
        V2_AsciiTable at = new V2_AsciiTable();

        LinkedList<String> headers = simplexTable.getValuesName();
        headers.set(0, "");
        headers.set(1, "Bx");
        headers.add(2, "A0");

        List<String> targetFuncHeaderCoefficients = new ArrayList<String>();
        targetFuncHeaderCoefficients.add("");
        targetFuncHeaderCoefficients.add("");
        targetFuncHeaderCoefficients.add("0.0");

        for (int i = 3; i < headers.size(); i++) {
            String value = Double.MIN_VALUE == simplexTable.aimFunk.get(headers.get(i)) ? "-M" :
                    Double.MAX_VALUE == simplexTable.aimFunk.get(headers.get(i)) ? "M" :
                            simplexTable.aimFunk.get(headers.get(i)).toString();

            targetFuncHeaderCoefficients.add(value);
        }

        String[] headersArr = new String[headers.size()];
        headers.toArray(headersArr);
        String[] targetFuncHeaderCoefficientsArr = new String[targetFuncHeaderCoefficients.size()];
        targetFuncHeaderCoefficients.toArray(targetFuncHeaderCoefficientsArr);

        at.addRule();
        at.addRow(targetFuncHeaderCoefficientsArr);
        at.addRule();
        at.addRow(headersArr);
        at.addRule();

        for (int i = 0; i < simplexTable.table.length - 1; i++) {
            List<String> row = new ArrayList<String>();
            String firstColumn = Double.MIN_VALUE == simplexTable.table[i][0] ? "-M" :
                    Double.MAX_VALUE == simplexTable.table[i][0] ? "M" :
                            simplexTable.table[i][0].toString();
            row.add(firstColumn);
            String Bx = simplexTable.basis[i];
            row.add(Bx);
            String a0 = simplexTable.table[i][1].toString();
            row.add(a0);
            for (int j = 2; j < simplexTable.table[i].length; j++) {
                row.add(simplexTable.table[i][j].toString());
            }
            String[] rowArr = new String[row.size()];
            row.toArray(rowArr);
            at.addRow(rowArr);
            at.addRule();
        }

        List<String> deltaRow = new ArrayList<String>();
        deltaRow.add("");
        deltaRow.add("");
        for (int i = 1; i < headers.size() - 1; i++) {
            String mPart = simplexTable.delta[1][i].toString().replace(".0","")+"M";
            if (mPart.equals("0M")) {
                mPart = "";
            }
            String constPart = simplexTable.delta[0][i].toString().replace(".0","");

            if(!StringUtils.isEmpty(mPart) & constPart.equals("0")) {
                deltaRow.add(mPart);
            } else if (mPart.equals("0M") && constPart.equals("0")) {
                deltaRow.add("0");
            } else {
                deltaRow.add(mPart + " " + constPart);
            }

        }
        String[] deltaRowArr = new String[deltaRow.size()];
        deltaRow.toArray(deltaRowArr);
        at.addRow(deltaRowArr);
        at.addRule();

        V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
        rend.setTheme(V2_E_TableThemes.PLAIN_7BIT.get());
        rend.setWidth(new WidthLongestLine());
        RenderedTable rt = rend.render(at);

        System.out.println(rt);
    }

    public static void printTableVariableValues(final SimplexTable simplexTable) {
        V2_AsciiTable at = new V2_AsciiTable();
        at.addRule();

        LinkedList<String> headers = simplexTable.getValuesName();
        headers.remove(0);
        headers.remove(0);
        String[] headersArr = new String[headers.size()];
        headers.toArray(headersArr);
        at.addRow(headersArr);

        at.addRule();

        List<String> values = new ArrayList<String>();
        for (String header : headers) {
            if (Arrays.asList(simplexTable.basis).contains(header)) {
                values.add(simplexTable.table[Arrays.asList(simplexTable.basis).indexOf(header)][1].toString());
            } else {
                values.add("0");
            }
        }

        String[] valuesArr = new String[values.size()];
        values.toArray(valuesArr);
        at.addRow(valuesArr);

        at.addRule();

        V2_AsciiTableRenderer rend = new V2_AsciiTableRenderer();
        rend.setTheme(V2_E_TableThemes.PLAIN_7BIT.get());
        rend.setWidth(new WidthLongestLine());
        RenderedTable rt = rend.render(at);

        System.out.println(rt);
    }

}
