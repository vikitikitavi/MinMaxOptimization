package kurs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tori on 009 09.12.16.
 */
public class Simplex {

    public static void main(String[] args) {
        Double[][] coefficients = {{2.5,1.5},{0.5,-1.5},{3.5,2.5},{1.5, -0.5}};
        String targetFunction = "3x1 + 2x2 -> max";
        String StargetFunction = "1x1 - 1x2 -> max";
        List<String> restrictions = new ArrayList<String>(Arrays.asList(
                "1x1 - 6x2 <= 3",
                "1x1 + 1x2 >= 10",
                "-2x1 + x2 <= 1",
                "0x1 + x2 <= 11",
                "2x1 + x2 <= 32"
        ));
        (new MultiTask()).multiTaskResult(targetFunction,StargetFunction,restrictions);
        new LamdaTask(coefficients, restrictions);
    }
}
