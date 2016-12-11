package util.dao;

import java.util.ArrayList;
import java.util.List;

public class RestrictionEquation {

    private final static String EMPTY_MEMBERS_ERR_MSG =
            "Equation member list is empty pleas initialize equation with proper value";
    private double freeCoefficient;
    private List<EquationMember> equationMembers;
    private boolean isInequaltityDirectionPositive;

    public RestrictionEquation() {
        equationMembers = new ArrayList<EquationMember>();
    }

    public double getFreeCoefficient() {
        return freeCoefficient;
    }

    public void setFreeCoefficient(double freeCoefficient) {
        this.freeCoefficient = freeCoefficient;
    }

    public List<EquationMember> getEquationMembers() {
        return new ArrayList<EquationMember>(equationMembers);
    }

    public void addEquationMember(final EquationMember equationMember) {
        this.equationMembers.add(equationMember);
    }

    public void setEquationMembers(List<EquationMember> equationMembers) {
        this.equationMembers = equationMembers;
    }

    public boolean isInequaltityDirectionPositive() {
        return isInequaltityDirectionPositive;
    }

    public void setInequaltityDirectionPositive(boolean inequaltityDirectionPositive) {
        isInequaltityDirectionPositive = inequaltityDirectionPositive;
    }

    public int getMaxMemberIndex() {
        if (this.equationMembers.size() < 1) {
            throw new IllegalStateException(EMPTY_MEMBERS_ERR_MSG);
        }

        int max = 0;
        for (EquationMember equationMember : this.equationMembers) {
            if (equationMember.getIndex() > max) {
                max = equationMember.getIndex();
            }
        }
        return max;
    }

}