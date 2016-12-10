package util;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParsingUtils {

    private static final String OPTIONAL_NUMBER_REGEX = "(\\-?\\d?)";
    private static final String GREEDY_NUMBER_REGEX = "(\\d+)";
    private static final String EQUATION_MEMBER_REGEX = OPTIONAL_NUMBER_REGEX + "(x)" + GREEDY_NUMBER_REGEX;
    private static final String EQUATION_MEMBER_PARSE_REGEX = "(\\-?\\d?\\.?\\d?)x([\\d+])";
    private static final String EQUATION_MEMBER_GET_REGEX = "(\\W?-?\\W?\\d?\\.?\\d?x\\d+)";
    private static final String INEQUALITY_SIGN_REGEX = "\\W?([\\<\\>\\=])\\W?";
    private static final String MEMBER_DIVISOR_REGEX = "[^\\d^][\\W\\+\\-]+";
    private static final double DEFAULT_ZERO_MEMBER_COEEFICIENT  = 1.0d;

    public static Double[] getTargetFunctionCoefitients(final String targetFunction) {
        final String errorMsg = "Wrong input, please use 'Ax1 + Bx2 + ... + NxN -> max' format";

        String[] equationMembers;
        Double[] result;
        final Matcher functionMatcher = Pattern.compile("(.*?)(\\-\\>)(.*)").matcher(targetFunction);

        if (functionMatcher.find()) {
            equationMembers = functionMatcher.group(1).trim().split(" \\+ ");
        } else {
            throw new IllegalStateException(errorMsg);
        }

        result = new Double[equationMembers.length];

        for (String equationMember : equationMembers) {
            final Matcher memberMatcher = Pattern.compile(EQUATION_MEMBER_REGEX).matcher(equationMember);
            if (memberMatcher.find()) {
                final String parsedMemberCoefficient = memberMatcher.group(1);
                final Double memberCoefficient = StringUtils.isEmpty(parsedMemberCoefficient)
                        ? DEFAULT_ZERO_MEMBER_COEEFICIENT : Double.parseDouble(parsedMemberCoefficient);
                final int memberXIndex = Integer.parseInt(memberMatcher.group(3));
                result[memberXIndex - 1] = memberCoefficient;
            } else {
                throw new IllegalStateException(errorMsg);
            }
        }

        return result;
    }

/*              "-2.3x1 + x2 <= 1",
                "x1 - 6x2 <= 3",
                "x1 + x2 <= 10",
                "x2 <= 11",
                "2x1 + x2 <= 32"*/

//    {0.0, 3.0, 1.0, -6.0}



    public static Double[] getRestrictionElements(final List<String> restrictions) {
        final String errorMsg = "Wrong input, please use 'Ax1 + Bx2 + ... + NxN <=/>= C' format";

//        Set<Integer> equationCandidates = new HashSet<Integer>();
        List<RestrictionEquation> restrictionEquations = new ArrayList<RestrictionEquation>();
        List<EquationMember> equationMembers = new ArrayList<EquationMember>();
        Double[] result;

        for (String restriction : restrictions) {
            String[] equation = restriction.split(INEQUALITY_SIGN_REGEX);
            String membersPartOfEquation = equation[0];
            String freeCoeeficientPartOfEquation = equation[1];
            RestrictionEquation re = new RestrictionEquation();
            re.setFreeCoefficient(Integer.parseInt(freeCoeeficientPartOfEquation.trim()));

            List<String> equationMemberCandidates = new ArrayList<String>();
            final Matcher memberMatcher = Pattern.compile(EQUATION_MEMBER_GET_REGEX).matcher(membersPartOfEquation);

            while (memberMatcher.find()) {
                equationMemberCandidates.add(memberMatcher.group(1));
            }

            re.setEquationMembers(equationMemberCandidates);
            restrictionEquations.add(re);
        }

//        for (String equationMember : equationMemberCandidates) {
//            Matcher memberParserMatcher = Pattern.compile(EQUATION_MEMBER_PARSE_REGEX)
//                    .matcher(equationMember.replaceAll(" ", "").replace("+", ""));
//            if (memberParserMatcher.find()) {
//                equationMembers.add(new EquationMember(
//                        StringUtils.isEmpty(memberParserMatcher.group(1))
//                                ? 1.0d : Double.parseDouble(memberParserMatcher.group(1)),
//                        Integer.parseInt(memberParserMatcher.group(2)))
//                );
//            }
//        }
//
//        int vectorSize = 0;
//        for (EquationMember equationMember : equationMembers) {
//            if (equationMember.getIndex() > vectorSize) {
//                vectorSize = equationMember.getIndex();
//            }
//        }
//
//        if (vectorSize < 1 ) {
//            throw new IllegalStateException("Vector size can not be less than 1. Dobule check your restrictions please");
//        }



        int i = 0;

        return new Double[1];
    }

//        Matcher matcher = Pattern.compile("(.*?)"+INEQUALITY_SIGN_REGEX+"(.*)").matcher(restriction);
//        if (matcher.find()) {
//            equationMembers = matcher.group(1).trim().split(" \\+ ");
//        } else {
//            throw new IllegalStateException(errorMsg);
//        }
//
//        result = new Double[equationMembers.length];
//
//        for (String equationMember : equationMembers) {
//            Matcher memberMatcher = Pattern.compile(OPTIONAL_NUMBER_REGEX+"(x)"+GREEDY_NUMBER_REGEX)
//                    .matcher(equationMember);
//            if (memberMatcher.find()) {
//                String parsedMemberCoefficient = memberMatcher.group(1);
//                Double memberCoefficient = StringUtils.isEmpty(parsedMemberCoefficient)
//                        ? DEFAULT_ZERO_MEMBER_COEEFICIENT : Double.parseDouble(parsedMemberCoefficient);
//                int memberXIndex = Integer.parseInt(memberMatcher.group(3));
//                result[memberXIndex - 1] = memberCoefficient;
//            } else {
//                throw new IllegalStateException(errorMsg);
//            }
//        }
//
//        return result;
//    }


    private static class RestrictionEquation {

        private int freeCoefficient;
        private List<String> equationMembers;

        public RestrictionEquation() {
        }

        public int getFreeCoefficient() {
            return freeCoefficient;
        }

        public void setFreeCoefficient(int freeCoefficient) {
            this.freeCoefficient = freeCoefficient;
        }

        public List<String> getEquationMembers() {
            return equationMembers;
        }

        public void setEquationMembers(List<String> equationMembers) {
            this.equationMembers = equationMembers;
        }
    }

    private static class EquationMember {

        private int index;
        private double coefficient;

        public EquationMember(double coefficient, int index) {
            this.index = index;
            this.coefficient = coefficient;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public double getCoefficient() {
            return coefficient;
        }

        public void setCoefficient(double coefficient) {
            this.coefficient = coefficient;
        }
    }

}
