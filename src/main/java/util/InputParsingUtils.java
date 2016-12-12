package util;

import org.apache.commons.lang3.StringUtils;
import util.dao.EquationMember;
import util.dao.RestrictionEquation;
import util.dao.TargetFunctionEquation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParsingUtils {

    private static final String RESTRICTION_FORMAT_ERR_MSG =
            "Wrong input, please use 'Ax1 +/- Bx2 +/- ... +/- NxN <=/>= C' format";
    private static final String TARGET_FUNCTION_FORMAT_ERR_MSG =
            "Wrong input, please use 'Ax1 +/- Bx2 +/- ... +/- NxN -> max' format";

    private static final String EQUATION_MEMBER_PARSE_REGEX = "(-?\\d?\\.?\\d?)x(\\d+)";
    private static final String EQUATION_MEMBER_GET_REGEX = "(\\W?-?\\W?\\d?\\.?\\d?x\\d+)";
    private static final String RESTRICTION_PARTS_PARSE_REGEX = "(.*[^<>=])([<>=]+\\W?)(.*)";
    private static final String TARGET_FUNCTION_PARTS_PARSE_REGEX = "(.*)\\W?->\\W?.*";
    private static final String MINUS_ONE_COEFFICIENT_PATTERN = "-";

    private static final int RESTRICTION_MEMBER_GROUP = 1;
    private static final int RESTRICTION_INEQUALITY_SIGN_GROUP = 2;
    private static final int RESTRICTION_FREE_COEFFICIENT_GROUP = 3;
    private static final double DEFAULT_ABSENT_COEFFICIENT_FOR_MEMBER  = 1.0d;
    private static final double DEFAULT_NEGATIVE_ABSENT_COEFFICIENT_FOR_MEMBER  = -1.0d;
    private static final double DEFAULT_ABSENT_MEMBER_COEFFICIENT = 0.0d;

    public static Double[] getTargetFunctionCoefficients(final String targetFunction) {
        return transformToTargetFunctionArray(getTargetFunction(targetFunction));
    }

    public static Double[][] getRestrictionElements(final List<String> restrictions) {

        List<RestrictionEquation> restrictionEquations = new ArrayList<RestrictionEquation>();
        List<Double[]> resultList = new ArrayList<Double[]>();

        for (String restriction : restrictions) {
            restrictionEquations.add(toRestrictionEquation(restriction));
        }

        int vectorSize = getVectorSizeByEquationsMaxIndex(restrictionEquations);

        if (vectorSize < 1 ) {
            throw new IllegalStateException("Vector size can not be less than 1. Double check your restrictions please");
        }

        for (RestrictionEquation restrictionEquation : restrictionEquations) {
            resultList.add(transformToResultArray(vectorSize, restrictionEquation));
        }

        Double[] redundantZeroFilledResultPart = new Double[vectorSize + 2];
        Arrays.fill(redundantZeroFilledResultPart, 0.0d);
        resultList.add(redundantZeroFilledResultPart);

        Double[][] result = new Double[resultList.size()][vectorSize + 2];
        return resultList.toArray(result);
    }

    public static LinkedList<String> getInEqualityStates(final List<String> restrictions) {
        List<RestrictionEquation> restrictionEquations = new ArrayList<RestrictionEquation>();
        LinkedList<String> result = new LinkedList<String>();

        for (String restriction : restrictions) {
            result.add(getRestrictionPart(restriction, RESTRICTION_INEQUALITY_SIGN_GROUP).trim());
        }

        return result;
    }

    private static TargetFunctionEquation getTargetFunction(final String targetFunction) {
        TargetFunctionEquation te = new TargetFunctionEquation();
        final String equationMemberPart = getTargetFunctionEquation(targetFunction);
        for (String equationMemberCandidate : getEquationMemberCandidates(equationMemberPart)) {
            te.addEquationMember(toEquationMember(equationMemberCandidate));
        }
        return te;
    }

    private static RestrictionEquation toRestrictionEquation(final String restriction) {
        RestrictionEquation restrictionEquation = new RestrictionEquation();

        restrictionEquation.setFreeCoefficient(
                Double.parseDouble(getRestrictionPart(restriction, RESTRICTION_FREE_COEFFICIENT_GROUP).trim()));
        restrictionEquation.setInequaltityDirectionPositive(
                getRestrictionPart(restriction, RESTRICTION_INEQUALITY_SIGN_GROUP).contains(">"));

        final String equationMemberPart = getRestrictionPart(restriction, RESTRICTION_MEMBER_GROUP);
        for (String equationMemberCandidate : getEquationMemberCandidates(equationMemberPart)) {
            restrictionEquation.addEquationMember(toEquationMember(equationMemberCandidate));
        }
        return restrictionEquation;
    }

    private static String getTargetFunctionEquation(final String targetFunction) {
        final Matcher restrictionMatcher =  Pattern.compile(TARGET_FUNCTION_PARTS_PARSE_REGEX).matcher(targetFunction);
        if (restrictionMatcher.find()) {
            return restrictionMatcher.group(1);
        } else {
            throw new IllegalStateException(TARGET_FUNCTION_FORMAT_ERR_MSG);
        }
    }

    private static String getRestrictionPart(final String restriction, final int group) {
        final Matcher restrictionMatcher =  Pattern.compile(RESTRICTION_PARTS_PARSE_REGEX).matcher(restriction);
        if (restrictionMatcher.find()) {
            return restrictionMatcher.group(group);
        } else {
            throw new IllegalStateException(RESTRICTION_FORMAT_ERR_MSG);
        }
    }

    private static List<String> getEquationMemberCandidates(final String membersPartOfEquation) {
        List<String> equationMemberCandidates = new ArrayList<String>();
        final Matcher memberMatcher = Pattern.compile(EQUATION_MEMBER_GET_REGEX).matcher(membersPartOfEquation);
        while (memberMatcher.find()) {
            equationMemberCandidates.add(memberMatcher.group(1));
        }
        return equationMemberCandidates;
    }

    private static EquationMember toEquationMember(final String equationMemberCandidate) {
        EquationMember em;

        final Matcher memberParserMatcher = Pattern.compile(EQUATION_MEMBER_PARSE_REGEX)
                .matcher(equationMemberCandidate.replaceAll(" ", "").replace("+", ""));
        if (memberParserMatcher.find()) {
            Double coefficient = DEFAULT_ABSENT_COEFFICIENT_FOR_MEMBER;
            if (!StringUtils.isEmpty(memberParserMatcher.group(1))) {
                if (memberParserMatcher.group(1).equals(MINUS_ONE_COEFFICIENT_PATTERN)) {
                    coefficient = DEFAULT_NEGATIVE_ABSENT_COEFFICIENT_FOR_MEMBER;
                } else {
                    coefficient = Double.parseDouble(memberParserMatcher.group(1));
                }
            }
            em = new EquationMember(coefficient, Integer.parseInt(memberParserMatcher.group(2)));
        } else {
            throw new IllegalStateException("Unable to parse equation member: " + equationMemberCandidate);
        }
        return em;
    }

    private static int getVectorSizeByEquationsMaxIndex(final List<RestrictionEquation> restrictionEquations) {
        int vectorSize = 0;
        for (RestrictionEquation restrictionEquation : restrictionEquations) {
            if (restrictionEquation.getMaxMemberIndex() > vectorSize) {
                vectorSize = restrictionEquation.getMaxMemberIndex();
            }
        }
        return vectorSize;
    }

    private static Double[] transformToTargetFunctionArray(final TargetFunctionEquation equation) {
        Double[] equationResult = new Double[equation.getEquationMembers().size()];
        for (EquationMember equationMember : equation.getEquationMembers()) {
            equationResult[equationMember.getIndex() - 1] = equationMember.getCoefficient();
        }
        return equationResult;
    }

    private static Double[] transformToResultArray(final int vectorSize, final RestrictionEquation equation) {
        Double[] equationResult = new Double[vectorSize + 2];
        equationResult[0] = 0.0d;
        equationResult[1] = equation.getFreeCoefficient();

        for (EquationMember equationMember : equation.getEquationMembers()) {
            equationResult[equationMember.getIndex() + 1] = equationMember.getCoefficient();
        }

        for (int i = 0; i < equationResult.length; i++) {
            if (null == equationResult[i]) {
                equationResult[i] = DEFAULT_ABSENT_MEMBER_COEFFICIENT;
            }
        }
        return equationResult;
    }

}
