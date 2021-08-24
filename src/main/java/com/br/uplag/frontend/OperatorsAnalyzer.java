package com.br.uplag.frontend;

import com.br.uplag.c.operators.LogicalOperatorsRegexEnum;
import com.br.uplag.c.operators.RelationOperatorRegex;



public class OperatorsAnalyzer extends FrontEND {
    private static OperatorsAnalyzer operatorsAnalyzer;
    private OperatorsAnalyzer(){}
    public static OperatorsAnalyzer getInstance() {
        if (operatorsAnalyzer == null)
            operatorsAnalyzer = new OperatorsAnalyzer();
        return operatorsAnalyzer;
    }

    public String tokenizeRelationalOperator(String codeText, RelationOperatorRegex relationalOperator) {
        return compileMatcher(codeText, relationalOperator.getRegex(), relationalOperator.getToken());
    }

    public String tokenizeLogicalOperators(String codeText, LogicalOperatorsRegexEnum logicalOperatorsRegexEnum) {
        return compileMatcher(codeText, logicalOperatorsRegexEnum.getRegex(), logicalOperatorsRegexEnum.getToken());
    }
}
