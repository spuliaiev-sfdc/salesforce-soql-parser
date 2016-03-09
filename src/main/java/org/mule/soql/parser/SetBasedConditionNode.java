package org.mule.soql.parser;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.tree.CommonTree;
import org.mule.soql.parser.utils.SOQLCommonTreeUtils;
import org.mule.soql.query.condition.ConditionField;
import org.mule.soql.query.condition.ConditionSet;
import org.mule.soql.query.condition.SetBasedCondition;
import org.mule.soql.query.condition.operator.SetOperator;

/**
 * Created by damianpelaez on 2/26/16.
 */
public class SetBasedConditionNode extends SOQLCommonTree {

    public SetBasedConditionNode(int tokenType) {
        super(new CommonToken(tokenType, "SET_BASED_CONDITION"));
    }

    @Override
    public SetBasedCondition createSOQLData() {
        SetBasedCondition setBasedCondition = new SetBasedCondition();

        this.processFirstNode(setBasedCondition);
        this.processSecondNode(setBasedCondition);
        this.processThirdNode(setBasedCondition);

        return setBasedCondition;
    }

    private void processFirstNode(SetBasedCondition setBasedCondition) {
        CommonTree child = (CommonTree) this.getChild(0);

        this.createConditionField(child, setBasedCondition);
    }

    private void processSecondNode(SetBasedCondition setBasedCondition) {
        CommonTree child = (CommonTree) this.getChild(1);

        this.createSetOperator(child, setBasedCondition);
    }

    private void processThirdNode(SetBasedCondition setBasedCondition) {
        CommonTree child = (CommonTree) this.getChild(2);

        this.createConditionSet(child, setBasedCondition);
    }

    private void createConditionField(CommonTree node, SetBasedCondition setBasedCondition) {
        if (!SOQLCommonTreeUtils.matchesAnyType(node, SOQLParser.FIELD, SOQLParser.FUNCTION_CALL)) { return; }

        SOQLCommonTree soqlNode = (SOQLCommonTree) node;

        setBasedCondition.setConditionField((ConditionField) soqlNode.createSOQLData());
    }

    private void createSetOperator(CommonTree node, SetBasedCondition setBasedCondition) {
        SetOperator setOperator = SetOperator.get(node.getText());

        if(setOperator == null) { return; }

        setBasedCondition.setOperator(setOperator);
    }

    private void createConditionSet(CommonTree node, SetBasedCondition setBasedCondition) {
        if (!SOQLCommonTreeUtils.matchesAnyType(node, SOQLParser.SOQL_QUERY, SOQLParser.SET_VALUES)) { return; }

        SOQLCommonTree soqlNode = (SOQLCommonTree) node;

        setBasedCondition.setSet((ConditionSet) soqlNode.createSOQLData());
    }

}
