package util.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imps on 011 11.12.16.
 */
public class TargetFunctionEquation {

    private final static String EMPTY_MEMBERS_ERR_MSG =
            "Equation member list is empty pleas initialize equation with proper value";
    protected List<EquationMember> equationMembers;

    public TargetFunctionEquation() {
        equationMembers = new ArrayList<EquationMember>();
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
