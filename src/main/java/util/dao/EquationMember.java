package util.dao;

public class EquationMember {

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
