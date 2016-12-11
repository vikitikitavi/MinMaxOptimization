package util.dao;

import java.util.ArrayList;

public class RestrictionEquation extends TargetFunctionEquation {

    private boolean isInequaltityDirectionPositive;
    private double freeCoefficient;

    public RestrictionEquation() {
        equationMembers = new ArrayList<EquationMember>();
    }

    public boolean isInequaltityDirectionPositive() {
        return isInequaltityDirectionPositive;
    }

    public void setInequaltityDirectionPositive(boolean inequaltityDirectionPositive) {
        isInequaltityDirectionPositive = inequaltityDirectionPositive;
    }

    public double getFreeCoefficient() {
        return freeCoefficient;
    }

    public void setFreeCoefficient(double freeCoefficient) {
        this.freeCoefficient = freeCoefficient;
    }

}