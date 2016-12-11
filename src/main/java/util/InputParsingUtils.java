package util;

import org.apache.commons.lang3.StringUtils;
import util.dao.EquationMember;
import util.dao.RestrictionEquation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParsingUtils {

    private static final String RESTRICTION_FORMAT_ERR_MSG
            = "Wrong input, please use 'Ax1 + Bx2 + ... + NxN <=/>= C' format";
    private static final String OPTIONAL_NUMBER_REGEX = "(-?\\d?)";
    private static final String GREEDY_NUMBER_REGEX = "(\\d+)";
    private static final String EQUATION_MEMBER_REGEX = OPTIONAL_NUMBER_REGEX + "(x)" + GREEDY_NUMBER_REGEX;

    private static final String EQUATION_MEMBER_PARSE_REGEX = "(-?\\d?\\.?\\d?)x([\\d+])";
    private static final String EQUATION_MEMBER_GET_REGEX = "(\\W?-?\\W?\\d?\\.?\\d?x\\d+)";
    private static final String RESTRICTION_PARTS_PARSE_REGEX = "(.*[^<>=])([<>=]+\\W?)(.*)";
    private static final int RESTRICTION_MEMBER_GROUP = 1;
    private static final int RESTRICTION_INEQUALITY_SIGN_GROUP = 2;
    private static final int RESTRICTION_FREE_COEFFICIENT_GROUP = 3;
    private static final double DEFAULT_ABSENT_COEFFICIENT_FOR_MEMBER  = 1.0d;
    private static final double DEFAULT_ABSENT_MEMBER_COEFFICIENT = 0.0d;

    public static Double[] getTargetFunctionCoefficients(final String targetFunction) {
        final String errorMsg = "Wrong input, please use 'Ax1 + Bx2 + ... + NxN -> max' format";

        String[] equationMembers;
        Double[] result;
        final Matcher functionMatcher = Pattern.compile("(.*?)(->)(.*)").matcher(targetFunction);

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
                        ? DEFAULT_ABSENT_COEFFICIENT_FOR_MEMBER : Double.parseDouble(parsedMemberCoefficient);
                final int memberXIndex = Integer.parseInt(memberMatcher.group(3));
                result[memberXIndex - 1] = memberCoefficient;
            } else {
                throw new IllegalStateException(errorMsg);
            }
        }

        return result;
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

    private static RestrictionEquation toRestrictionEquation(String restriction) {
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

    private static int getVectorSizeByEquationsMaxIndex(final List<RestrictionEquation> restrictionEquations) {
        int vectorSize = 0;
        for (RestrictionEquation restrictionEquation : restrictionEquations) {
            if (restrictionEquation.getMaxMemberIndex() > vectorSize) {
                vectorSize = restrictionEquation.getMaxMemberIndex();
            }
        }
        return vectorSize;
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
            em = new EquationMember(
                    StringUtils.isEmpty(memberParserMatcher.group(1))
                            ? DEFAULT_ABSENT_COEFFICIENT_FOR_MEMBER
                            : Double.parseDouble(memberParserMatcher.group(1)),
                    Integer.parseInt(memberParserMatcher.group(2)));
        } else {
            throw new IllegalStateException("Unable to parse equation member: " + equationMemberCandidate);
        }
        return em;
    }

    private static String getRestrictionPart(final String restriction, final int group) {
        final Matcher restrictionMatcher =  Pattern.compile(RESTRICTION_PARTS_PARSE_REGEX).matcher(restriction);
        if (restrictionMatcher.find()) {
            return restrictionMatcher.group(group);
        } else {
            throw new IllegalStateException(RESTRICTION_FORMAT_ERR_MSG);
        }
    }
}
