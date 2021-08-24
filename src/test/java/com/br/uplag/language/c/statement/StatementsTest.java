package com.br.uplag.language.c.statement;

import com.br.uplag.c.conditionals.ConditionalsRegex;
import com.br.uplag.c.operators.ArithmeticOperatorsRegex;
import com.br.uplag.c.statements.StatementsRegex;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(JUnit4.class)
public class StatementsTest {
    private final String code = "int buscaSkipList(SkipList* sl, int N){\n" +
            "    if(sl == NULL || vaziaSkipList(sl)==1)\n" +
            "        return 0;\n" +
            "\n" +
            "    SkipList* aux;\n" +
            "    aux = sl;\n" +
            "\n" +
            "    int pos;\n" +
            "    pos = sl->posicao_livre - 1;\n" +
            "\n" +
            "    while((aux->prox[pos] != NULL && N > aux->prox[pos]->valor) || (pos > 0)){\n" +
            "        /* Caso a pr�x posi��o apontada seja nula ou o elemento seja menor do que o guardado pelo n�\n" +
            "         * apontado, a fun��o desce um n�vel\n" +
            "         */\n" +
            "        if((aux->prox[pos] == NULL) || (N < aux->prox[pos]->valor)){\n" +
            "            pos = pos - 1;\n" +
            "        }else{\n" +
            "            //Imprimo o n�vel visitado durante a busca\n" +
            "            if(aux!=NULL) printf(\"Visitei: %i no indice %i\\n\",aux->prox[pos]->valor,pos);\n" +
            "            //Se achou o valor, a busca acaba\n" +
            "            if(aux->prox[pos]->valor == N)\n" +
            "                return 1;\n" +
            "            //Caso n�o tenha achado, avan�a para o pr�ximo n�\n" +
            "            aux = aux->prox[pos];\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "    /* Se o aux parou no valor onde o elemento est� presente e\n" +
            "     * o elemento nao � o valor na cabe�a da SkipList (guardamos o tamanho da lista na cabe�a, se N == tamanho,\n" +
            "     * tamanho nao pode ser considerado como um elemento da skiplist na busca), por isso aux deve ser diferente de sl\n" +
            "     */\n" +
            "    if(aux->valor == N && aux != sl)\n" +
            "        return 1;\n" +
            "    else\n" +
            "        return 0;\n" +
            "}";

    @Test
    public void shouldConvertReturnCallsToReturnToken() {
        Assert.assertEquals(true, code.replaceAll(StatementsRegex.JUMP.getRegex(), StatementsRegex.JUMP.getToken()).contains(StatementsRegex.JUMP.getToken()));
    }

    @Test
    public void shouldConvertMultipleRegexesInTheSameText() {
        StringBuilder codeText = new StringBuilder();
        Pattern pattern = Pattern.compile(StatementsRegex.JUMP.getRegex(), Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            matcher.appendReplacement(codeText, StatementsRegex.JUMP.getToken());
        }

        pattern = Pattern.compile( ArithmeticOperatorsRegex.SUB.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        while (matcher.find()) {
            codeText.replace(matcher.start(), matcher.end(), ArithmeticOperatorsRegex.SUB.getToken());
        }

        pattern = Pattern.compile( ConditionalsRegex.IF_CONDITIONAL_STATEMENT.getRegex(), Pattern.MULTILINE);
        matcher = pattern.matcher(codeText);
        while (matcher.find()) {
            codeText.replace(matcher.start(), matcher.end(), ConditionalsRegex.IF_CONDITIONAL_STATEMENT.getToken());
        }

        System.out.println(codeText);
    }
}
