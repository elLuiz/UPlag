package com.br.uplag;

import frontend.AssignmentLexicalAnalyzer;
import frontend.CFrontEnd;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import token.VariableTokens;

@RunWith(JUnit4.class)
public class AssignmentLexicalAnalyzerTest {
    private final String code = "skiplist *criaSkipList(){\n" +
            "\n" +
            "    skiplist *sk = (skiplist*) malloc(sizeof(skiplist));\n" +
            "    no *cabecalhoPrimeiroNivel = (no*) malloc(sizeof(no));\n" +
            "    no *novo = (no*) malloc(sizeof(no));\n" +
            "\n" +
            "    if(cabecalhoPrimeiroNivel == NULL || sk == NULL || novo == NULL){\n" +
            "        printf(\"Ocorreu algum erro de alocacao.\\n\");\n" +
            "        return NULL;\n" +
            "    }\n" +
            "\n" +
            "    sk->level = 1;\n" +
            "    sk->inicio = novo;\n" +
            "\n" +
            "    novo->info = 0;\n" +
            "    novo->abaixo = NULL;\n" +
            "    novo->prox = cabecalhoPrimeiroNivel;\n" +
            "\n" +
            "    cabecalhoPrimeiroNivel->info = 0;\n" +
            "    cabecalhoPrimeiroNivel->prox = NULL;\n" +
            "    cabecalhoPrimeiroNivel->abaixo = NULL;\n" +
            "\n" +
            "    return sk;\n" +
            "}";

    @Test
    public void shouldAnalyzeNormalVariableAssignment() {
        AssignmentLexicalAnalyzer assignmentLexicalAnalyzer = new AssignmentLexicalAnalyzer();
        String result = assignmentLexicalAnalyzer.analyzeCommonVariableAssignment(code, CFrontEnd.VARIABLE_ASSIGNMENT_REGEX, VariableTokens.ASSIGN);
        result = assignmentLexicalAnalyzer.analyzeCommonVariableAssignment(result, CFrontEnd.VARIABLE_ASSIGNMENT_WITH_CASTING, VariableTokens.CAST);
        Assert.assertEquals("ASSIGN CAST", result);
    }
}
