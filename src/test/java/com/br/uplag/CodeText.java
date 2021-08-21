package com.br.uplag;

public class CodeText {
    public static final String code = "/*\n" +
            "Grupo GVY\n" +
            "Integrantes:\n" +
            "Gabriel Mendes de Souza Santiago - 11621BCC015\n" +
            "Vin�cius Guardieiro Sousa - 11811BCC008\n" +
            "Yan Lucas Damasceno Dias - 11621BCC029\n" +
            "*/\n" +
            "\n" +
            "#include<stdio.h>\n" +
            "#include<stdlib.h>\n" +
            "#include<math.h>\n" +
            "#include \"SkipList.h\"\n" +
            "#define MAX 509" +
            "\n" +
            "// Recebe uma skilist e o valor que deseja-se saber se pertence a ela.\n" +
            "// Retorna 0 caso ela nao tenha sido alocada ou esteja vazia.\n" +
            "// Retorna 1 caso o elemento esteja na lista.\n" +
            "int buscaSkipList(skiplist *sk, int chave){\n" +
            "\n" +
            "    // Verifica se a lista foi alocada e se est� vazia.\n" +
            "\tif(vaziaSkipList(sk)){\n" +
            "        printf(\"Lista nao alocada ou vazia.\\n\");\n" +
            "\t\treturn 0;\n" +
            "\t}\n" +
            "\n" +
            "    // Aponta para o n� mais acima do cabe�alho.\n" +
            "    no *x = sk->inicio->prox;\n" +
            "\n" +
            "    // Enquanto o n� for diferente de nulo percorrer a lista.\n" +
            "    while(x != NULL){\n" +
            "\n" +
            "        // Se a chave for maior que o valor do n� atual, ir para o proximo n�.\n" +
            "        while(x->prox != NULL && x->prox->info < chave)\n" +
            "            x = x->prox;\n" +
            "\n" +
            "        // Verifica se o valor do n� atual � igual ao valor da chave e retorna 1 caso seja.\n" +
            "        if(x->prox != NULL && x->prox->info == chave)\n" +
            "            return 1;\n" +
            "\n" +
            "        // Caso n�o satisfa�a nenhuma das condi��es acima, vai para o n� abaixo e repete o processo.\n" +
            "        x = x->abaixo;\n" +
            "    }\n" +
            "    return 0;\n" +
            "}\n" +
            "\n" +
            "// Cria a skiplist, alocando-a e alocando os n�s dela.\n" +
            "// Retorna a skiplist ou retorna nulo, caso n�o tenha\n" +
            "// sido poss�vel alocar mem�ria para ela ou para os n�s.\n" +
            "skiplist *criaSkipList(){\n" +
            "\n" +
            "    // Faz a aloca��o da skiplist e dos n�s.\n" +
            "    skiplist *sk = (skiplist*) malloc(sizeof(skiplist));\n" +
            "    no *cabecalhoPrimeiroNivel = (no*) malloc(sizeof(no));\n" +
            "    no *novo = (no*) malloc(sizeof(no));\n" +
            "\n" +
            "    // Verifica se a aloca��o ocorreu com sucesso.\n" +
            "    // Retorna nulo caso n�o tenha sido.\n" +
            "    if(cabecalhoPrimeiroNivel == NULL || sk == NULL || novo == NULL){\n" +
            "        printf(\"Ocorreu algum erro de alocacao.\\n\");\n" +
            "        return NULL;\n" +
            "    }\n" +
            "\n" +
            "    // Seta os valores iniciais para uma skiplist vazia e para seus respectivos n�s.\n" +
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
            "}\n" +
            "\n" +
            "// Recebe uma SkipList e imprime os elementos alocados nos n�s mais abaixo da lista.\n" +
            "void imprimeSkipList(skiplist *sk) {\n" +
            "\n" +
            "    // Verifica se a lista est� vazia.\n" +
            "    if(vaziaSkipList(sk)){\n" +
            "        printf(\"Lista vazia ou nao alocada!\\n\");\n" +
            "        return;\n" +
            "    }\n" +
            "\n" +
            "    // Aponta para o n� mais acima do cabe�alho.\n" +
            "    no *aux = sk->inicio->prox;\n" +
            "\n" +
            "    // Desce at� o n� mais abaixo do cabe�alho e\n" +
            "    // aponta para a primeira posi��o da lista.\n" +
            "    while(aux->abaixo != NULL)\n" +
            "        aux = aux->abaixo;\n" +
            "    aux = aux->prox;\n" +
            "\n" +
            "    // Imprime cada elemento at� chegar no �ltimo.\n" +
            "    while(aux != NULL){\n" +
            "        printf(\"%d\", aux->info);\n" +
            "        if(aux->prox != NULL){\n" +
            "            printf(\" -> \");\n" +
            "        }\n" +
            "        aux = aux->prox;\n" +
            "    }\n" +
            "\n" +
            "    printf(\"\\n\");\n" +
            "}\n" +
            "\n" +
            "// Fun��o auxiliar � insereSkipList, pois se um n� n�o pode ser alocado,\n" +
            "// os nos que foram alocados durante a execu��o dessa fun��o naquele\n" +
            "// momento devem ser liberados\n" +
            "void reseta(no **novo,skiplist *sk){\n" +
            "    int i = 0;\n" +
            "\n" +
            "    while(i < sk->level-1){\n" +
            "        if(novo[i]!= NULL)\n" +
            "            free(novo);\n" +
            "\n" +
            "        i++;\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "// Recebe uma skiplist e um n�mero e o insere na lista.\n" +
            "// Retorna 1 caso a inser��o tenha funcionado.\n" +
            "// Retorna 0 caso algo tenha dado errado.\n" +
            "int insereSkipList(skiplist *sk, int numero){\n" +
            "    if(sk == NULL){\n" +
            "        printf(\"Lista nao alocada.\\n\");\n" +
            "        return 0;\n" +
            "    }\n" +
            "\n" +
            "    int elementos = sk->inicio->info; // Pega a quantidade de elementos\n" +
            "    int tamanho = sk->level;\n" +
            "    int i;\n" +
            "\n" +
            "    no *salvaAnt[tamanho]; // Vetor para armazenar a posi��o anterior de onde o novo no ser� colcoado\n" +
            "    no *salvaNovo[tamanho]; // Vetor que salva a posi��o da memoria dos novos n�s\n" +
            "    no *aux3 = NULL; // N� auxiliar para corrigir o abaixo dos novos n�s\n" +
            "\n" +
            "    // Setar vetor em null para posterior verifica��o\n" +
            "    for(i = 0; i < tamanho+1; i++){\n" +
            "        salvaAnt[i] = NULL;\n" +
            "        salvaNovo[i] = NULL;\n" +
            "    }\n" +
            "\n" +
            "    // Verifica necessidade de criar uma nova camada\n" +
            "    if((elementos+1) % ((int)pow(2, sk->level)) == 0){\n" +
            "\n" +
            "        // Aloca o cabe�alho e um n� para adicionar na camada\n" +
            "        no *novo1 = (no *) malloc(sizeof(no));\n" +
            "        if(novo1 == NULL)\n" +
            "            return 0;\n" +
            "\n" +
            "        // Configura o cabe�alho\n" +
            "        novo1->info = sk->level;\n" +
            "        novo1->abaixo = sk->inicio->prox;\n" +
            "        novo1->prox = NULL;\n" +
            "\n" +
            "        // Corrige o sk->inicio->prox para apontar para a nova camada\n" +
            "        sk->inicio->prox = novo1;\n" +
            "\n" +
            "        // Aumenta a quantidade de nivel\n" +
            "        sk->level++;\n" +
            "    }\n" +
            "    int contador = sk->level-1;\n" +
            "    no *andarNos = sk->inicio->prox; // Aponta cabecalho\n" +
            "\n" +
            "    // Cria todos os novos n�s\n" +
            "    while(andarNos != NULL){\n" +
            "        int pot = (int) pow(2, contador);\n" +
            "\n" +
            "        // Verifica se h� necessidade de adicionar o n� na camada em que o andarNos est�\n" +
            "        if((elementos+1) % pot == 0){\n" +
            "\n" +
            "            // Aloca novo n�\n" +
            "            no *novo = (no *) malloc(sizeof(no));\n" +
            "            if(novo == NULL){\n" +
            "                reseta(salvaNovo, sk);\n" +
            "                return 0;\n" +
            "            }\n" +
            "\n" +
            "            // Coloca o n�mero a ser inserido no novo n�\n" +
            "            novo->info = numero;\n" +
            "            salvaNovo[contador] = novo;\n" +
            "        }\n" +
            "        andarNos = andarNos->abaixo;\n" +
            "        contador --;\n" +
            "    }\n" +
            "    andarNos = sk->inicio->prox;\n" +
            "    contador = sk->level-1;\n" +
            "\n" +
            "    // Anda na skiplist\n" +
            "    while(andarNos != NULL){\n" +
            "\n" +
            "        // Anda na horizontal para achar a posi��o\n" +
            "        while(andarNos->prox != NULL && andarNos->prox->info < numero){\n" +
            "            andarNos = andarNos->prox;\n" +
            "        }\n" +
            "\n" +
            "        // Verifica se o elemento j� existe\n" +
            "        if(andarNos->prox != NULL && andarNos->prox->info == numero){\n" +
            "            reseta(salvaNovo, sk);\n" +
            "            return 0;\n" +
            "        }\n" +
            "\n" +
            "        // Armazena a posi��o anterior que os novos n�s ir�o ficar\n" +
            "        if(salvaNovo[contador]!= NULL){\n" +
            "            salvaAnt[contador] = andarNos;\n" +
            "        }\n" +
            "\n" +
            "        // Anda na vertical\n" +
            "        andarNos = andarNos->abaixo;\n" +
            "        contador --;\n" +
            "    }\n" +
            "\n" +
            "    // Arruma o abaixo e os proxs dos novos n�s que est�o no vetor salvarNovo\n" +
            "    for(i = 0; i <= sk->level-1; i++){\n" +
            "        if(salvaNovo[i] == NULL)\n" +
            "            break;\n" +
            "        salvaNovo[i]->abaixo = aux3;\n" +
            "        salvaNovo[i]->prox = salvaAnt[i]->prox;\n" +
            "        salvaAnt[i]->prox = salvaNovo[i];\n" +
            "        aux3 = salvaNovo[i];\n" +
            "    }\n" +
            "\n" +
            "    // Aumenta a quantidade de elementos\n" +
            "    sk->inicio->info++;\n" +
            "\n" +
            "    return 1;\n" +
            "}\n" +
            "\n" +
            "// Recebe uma skiplist e libera a mem�ria alocada para ela.\n" +
            "void liberaSkipList(skiplist *sk){\n" +
            "    if(sk != NULL){\n" +
            "        no *aux = sk->inicio->prox;\n" +
            "        no *auxLiberar;\n" +
            "\n" +
            "        while(aux != NULL){\n" +
            "            auxLiberar = aux->prox;\n" +
            "\n" +
            "            // Anda pro lado liberando os n�s\n" +
            "            while(auxLiberar != NULL){\n" +
            "                no *aux2 = auxLiberar;\n" +
            "                auxLiberar = auxLiberar->prox;\n" +
            "                free(aux2);\n" +
            "            }\n" +
            "\n" +
            "            // Apos liberar todos na mesma linha, anda pra baixo\n" +
            "            no *aux3 = aux;\n" +
            "            aux = aux->abaixo;\n" +
            "            free(aux3);\n" +
            "        }\n" +
            "\n" +
            "        // Libera o aux e o ponteiro que apontava pro cabe�alho\n" +
            "        aux = sk->inicio;\n" +
            "        free(aux);\n" +
            "        free(sk);\n" +
            "        sk = NULL; // Coloca null para corrigir possiveis erros quando for checar se est� vazia.\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "// Recebe uma skiplist e um valor. Remove o valor e retorna 1 caso tenha removido, retorna 0 caso n�o tenha removido.\n" +
            "int removeSkipList(skiplist *sk, int valor){\n" +
            "    if(vaziaSkipList(sk)){\n" +
            "        printf(\"Lista nao alocada ou vazia.\\n\");\n" +
            "        return 0;\n" +
            "    }\n" +
            "\n" +
            "    no *andar = sk->inicio->prox;\n" +
            "    int removeu = 0; // Flag para ver se j� removeu\n" +
            "\n" +
            "    // Percorrer a skiplist\n" +
            "    while(andar != NULL){\n" +
            "\n" +
            "        // Percorre at� achar o elemento ou um elemento maior\n" +
            "        while(andar->prox != NULL && andar->prox->info < valor){\n" +
            "            andar = andar->prox;\n" +
            "        }\n" +
            "\n" +
            "        // Se achou o elemento, corrige para onde o anterior aponta e libera o no daquele elemento\n" +
            "        if(andar->prox != NULL && andar->prox->info == valor){\n" +
            "            no *aux = andar->prox;\n" +
            "            andar->prox = aux->prox;\n" +
            "            free(aux);\n" +
            "            removeu = 1;\n" +
            "        }\n" +
            "\n" +
            "        // Anda na vertical\n" +
            "        andar = andar->abaixo;\n" +
            "    }\n" +
            "\n" +
            "    if(sk->inicio->prox->prox == NULL){\n" +
            "        no* aux = sk->inicio->prox;\n" +
            "        sk->inicio->prox = aux->abaixo;\n" +
            "        free(aux);\n" +
            "        sk->level--;\n" +
            "    }\n" +
            "\n" +
            "    // Se removeu alguma vez, retorna 1\n" +
            "    if(removeu){\n" +
            "        sk->inicio->info--;\n" +
            "        return 1;\n" +
            "    }\n" +
            "\n" +
            "    return 0;\n" +
            "}\n" +
            "\n" +
            "// Recebe uma skiplist e retorna o tamanho dela. Retorna 0 caso ela n�o tenha sido alocada.\n" +
            "int tamanhoSkipList(skiplist *sk){\n" +
            "    if(sk == NULL)\n" +
            "        return 0;\n" +
            "\n" +
            "    return sk->inicio->info;\n" +
            "    return sk == NULL ? 0 : sk->inicio->info;\n" +
            "}\n" +
            "\n" +
            "// Recebe uma skiplist e verifica se est� vazia. Retorna 1 caso esteja. Retorna 0 caso n�o esteja vazia.\n" +
            "int vaziaSkipList(skiplist *sk){\n" +
            "    if(sk == NULL || sk->inicio->info == 0)\n" +
            "        return 1;\n" +
            "\n" +
            "    return 0;\n" +
            "    return sk == NULL || sk->inicio->info == 0 ? 1 : 0;\n" +
            "}\n";

    public static final String binaryTreeCode = "#include <stdio.h>\n" +
            "#include <stdlib.h>\n" +
            "#include \"GrupoGGHHeader.h\" /*inclui os Prot�tipos*/\n" +
            "#define NUMELM 3\n" +
            "\n" +
            "struct NO{\n" +
            "    int info[NUMELM];\n" +
            "    struct NO *filhos[NUMELM+1];\n" +
            "    int raiz;\n" +
            "};\n" +
            "\n" +
            "ArvB* cria_ArvB(){\n" +
            "    ArvB* raiz = (ArvB*) malloc(sizeof(ArvB));\n" +
            "    if(raiz != NULL)\n" +
            "        *raiz = NULL;\n" +
            "    return raiz;\n" +
            "}\n" +
            "\n" +
            "ArvB CriaIniNo(){\n" +
            "    int cont=0;\n" +
            "    struct NO* novo;\n" +
            "    novo = (struct NO*) malloc(sizeof(struct NO));\n" +
            "    if(novo == NULL)\n" +
            "        return NULL;\n" +
            "    for (cont=0;cont<=NUMELM;cont++)\n" +
            "        (novo)->info[cont]=-1;\n" +
            "\n" +
            "\n" +
            "    for (cont=0;cont<=NUMELM+1;cont++)\n" +
            "        (novo)->filhos[cont]=NULL;\n" +
            "\n" +
            "    (novo)->raiz=0;\n" +
            "return novo;}\n" +
            "\n" +
            "void libera_NO(struct NO* no){\n" +
            "    printf(\"Liberando NO\\n\");\n" +
            "    int cont=0;\n" +
            "    if(no == NULL)\n" +
            "        return;\n" +
            "\n" +
            "    for (cont=0;cont<NUMELM;cont++)\n" +
            "        libera_NO(no->filhos[cont]);\n" +
            "\n" +
            "    free(no);\n" +
            "    no = NULL;\n" +
            "}\n" +
            "\n" +
            "void libera_ArvB(ArvB* raiz){\n" +
            "    if(raiz == NULL)\n" +
            "        return;\n" +
            "    libera_NO(*raiz);/*libera cada n�*/\n" +
            "    free(raiz);/*libera a raiz*/\n" +
            "    raiz = NULL;\n" +
            "}\n" +
            "\n" +
            "int insere_ArvB(ArvB* raiz, int valor){\n" +
            "    printf(\"Inserindo %d na arvore...\\n\", valor);\n" +
            "    int cont=0, peninse=0;\n" +
            "    if(raiz == NULL)\n" +
            "        return 0;\n" +
            "    /*tratar para somente executar linhas abaixo quando n�o existir n� nenhum*/\n" +
            "    if(*raiz == NULL){\n" +
            "        struct NO* novo;\n" +
            "        novo = CriaIniNo();\n" +
            "        (novo)->info[0]=valor;\n" +
            "        (novo)->raiz=1;\n" +
            "        *raiz = novo;\n" +
            "    }else{\n" +
            "        // Criado ponteiro auxiliar que ir� percorrer a arvore\n" +
            "        struct NO* aux = (struct NO*) malloc(sizeof(struct NO));\n" +
            "        insereRecursivo_ArvB(raiz, aux, &valor, &peninse);\n" +
            "    }\n" +
            "    printf(\"O valor %d foi inserido com sucesso.\\n\",valor);\n" +
            "    return 1;\n" +
            "}\n" +
            "\n" +
            "int insereRecursivo_ArvB(ArvB *arvore, ArvB *aux, int *valor, int *peninse){\n" +
            "int i=0,cont=0, cont1=0;\n" +
            "// caso tenha chegado no ultimo n�vel para ser alocado o valor.\n" +
            "if ((*arvore)==NULL){\n" +
            "   (*peninse) = 1;\n" +
            "   (*aux) = NULL;\n" +
            "   return 1;\n" +
            "}else{\n" +
            "   // Tratamento para verificar se valor j� existe e pegar posi��o que ir� descer (maior que o da direita e menor que o da esqueda) ou posi��o que ser� colocado no vetor de valores\n" +
            "    if (BuscaChaveNo(arvore, *valor, &i)==1){\n" +
            "        (*peninse) = 0;\n" +
            "        return 0;\n" +
            "    }else{\n" +
            "        // Inserindo recusivamente\n" +
            "        if(insereRecursivo_ArvB(&((*arvore)->filhos[i]),aux,valor,peninse)==1){\n" +
            "            if((*peninse)==1){\n" +
            "                InsereNovoValorEFilhoNo(arvore, *aux, valor, i);\n" +
            "                if (VerEstNo(arvore)==0){\n" +
            "                    (*peninse) = 0;\n" +
            "                }else{\n" +
            "                    //Tratei para funcionar apenas no meu caso de 3 informa��es no vetor (se cria dois n�s e o do meio sobe). Para funcionar com mais elementos (grau) seria necess�rio criar uma subfun��o e um 'for' que seria respons�vel pela cria��o dos n�s\n" +
            "                    // situa��o em qua a raiz precisa ser tratada\n" +
            "                    if ((*arvore)->raiz ==1){\n" +
            "                        struct NO* novo;\n" +
            "                       novo = CriaIniNo();\n" +
            "                       (novo)->info[0]=(*arvore)->info[1];\n" +
            "                       (novo)->raiz=1;\n" +
            "\n" +
            "                        struct NO* dir;\n" +
            "                        dir = CriaIniNo();\n" +
            "                       (dir)->info[0]=(*arvore)->info[0];\n" +
            "                       for (cont=0;cont<=1;cont++)\n" +
            "                           (dir)->filhos[cont]=(*arvore)->filhos[cont];\n" +
            "\n" +
            "                       struct NO* esq;\n" +
            "                        esq = CriaIniNo();\n" +
            "                       (esq)->info[0]=(*arvore)->info[2];\n" +
            "                       for (cont=2;cont<=3;cont++)\n" +
            "                           (dir)->filhos[cont]=(*arvore)->filhos[cont];\n" +
            "\n" +
            "                       (novo)->filhos[0]=dir;\n" +
            "                       (novo)->filhos[1]=esq;\n" +
            "                       *aux=novo;}\n" +
            "                    // situa��o em que o valor vai subir e os novos n�s precisam ser conectados no n� pai.\n" +
            "                    else{\n" +
            "                        // novo no com todas as conex�es feitas pelo no anterior\n" +
            "                        struct NO* novo;\n" +
            "                        novo = (*aux);\n" +
            "                       (novo)->info[0]=(*arvore)->info[1];\n" +
            "\n" +
            "                        // varre todos os filhos (do pai) afim de n�o ter perda de dados\n" +
            "                        while ((*arvore)->filhos[cont]!=NULL){\n" +
            "                            (novo)->filhos[cont+2]=(*arvore)->filhos[cont];\n" +
            "                            cont++;}\n" +
            "\n" +
            "                        // cria os dois nos (esquerdo e direito)\n" +
            "                        struct NO* esq;\n" +
            "                        esq = CriaIniNo();\n" +
            "\n" +
            "                        struct NO* dir;\n" +
            "                        dir = CriaIniNo();\n" +
            "\n" +
            "                         // coloca informa��es em cada um dos n�s criados (pegando os valores dos cantos, afim do valor do meio subir)\n" +
            "                        (esq)->info[0]=(*arvore)->info[0]; // elemento mais a esquerda\n" +
            "                        (dir)->info[0]=(*arvore)->info[2]; // elemento mais a direita\n" +
            "\n" +
            "                        (novo)->filhos[0] = esq;\n" +
            "                        (novo)->filhos[1] = dir;\n" +
            "\n" +
            "                        // vari�vel que controla se ser� necess�rio inserir algo.\n" +
            "                       (*peninse) = 1;\n" +
            "                    }\n" +
            "                }\n" +
            "            return 1;\n" +
            "            }\n" +
            "        }\n" +
            "        return 0;\n" +
            "    }\n" +
            "}\n" +
            "}\n" +
            "\n" +
            "int VerEstNo(ArvB *arvore ){\n" +
            "int cont=0;\n" +
            "// percorre at� achar uma posi��o vazia ou o valor maximos de elementos serem alcan�ados\n" +
            "while ((*arvore)->info[cont]!=-1&&cont<NUMELM){\n" +
            "    cont++;\n" +
            "}\n" +
            "\n" +
            "if (cont==NUMELM)\n" +
            "    return 1;\n" +
            "return 0;\n" +
            "}\n" +
            "\n" +
            "int BuscaChaveNo(ArvB *arvore, int valor, int *i){\n" +
            "int cont=0;\n" +
            "for(cont=0;cont<NUMELM;cont++){\n" +
            "    // Tratamento para n� com vazio (iniciado com 0) ou se ele � menor que o valor j� existe\n" +
            "    if (((*arvore)->info[cont]==-1) || ((valor<((*arvore)->info[cont])))){\n" +
            "        *i = cont;\n" +
            "        break;\n" +
            "    }\n" +
            "    else if (valor==(*arvore)->info[cont])\n" +
            "        return 1;\n" +
            "}\n" +
            "return 0;\n" +
            "}\n" +
            "\n" +
            "void InsereNovoValorEFilhoNo(ArvB *arvore, ArvB *aux, int *valor, int i){\n" +
            "int cont=0, areaRealoca=0, cont1=0;\n" +
            "aux = *arvore;\n" +
            "if ((*arvore)->info[i]!=-1){\n" +
            "    for (cont=0;cont<NUMELM;cont++){\n" +
            "        if ((*arvore)->info[cont]==-1)\n" +
            "           break;\n" +
            "    }\n" +
            "    // trata caso exista valor, passo os valores para a direita afim de deixar o espa�o do novo valor\n" +
            "    for (cont1=cont;cont1>=i;cont1--){\n" +
            "        (*arvore)->info[cont1]=(*arvore)->info[cont1-1];\n" +
            "    }\n" +
            "}\n" +
            "(*arvore)->info[i]=*valor;\n" +
            "// tratamento para subir o valor do meio\n" +
            "if (i==NUMELM-1)\n" +
            "    *valor = (*arvore)->info[1];\n" +
            "// Guarda o n� anterior na auxiliar\n" +
            "}\n" +
            "\n" +
            "int busca_ArvB(ArvB *raiz, int valor){\n" +
            "int nivel = 1, i=0;\n" +
            "ArvB *aux;\n" +
            "aux = raiz;\n" +
            "if (aux==NULL)\n" +
            "   return 0;\n" +
            "while(aux!=NULL){\n" +
            "    while(((*aux)->info[i]<valor))\n" +
            "        i++;\n" +
            "    if (((*aux)->info[i] == valor))\n" +
            "        return(1);\n" +
            "    else{\n" +
            "         if ((*aux)->filhos[i]!=NULL){\n" +
            "            aux = &((*raiz)->filhos[i]);\n" +
            "            i=0;\n" +
            "            nivel++;\n" +
            "         } else\n" +
            "              return 0;  /* nao conseguiu descer nivel */\n" +
            "        }\n" +
            "}\n" +
            "return 0;\n" +
            "}\n" +
            "\n" +
            "int ehVazia_ArvB(ArvB *raiz){\n" +
            "    if(raiz == NULL)\n" +
            "        return 1;\n" +
            "    if(*raiz == NULL)\n" +
            "        return 1;\n" +
            "    return 0;\n" +
            "}\n" +
            "\n" +
            "int totalNO_ArvB(ArvB *raiz){\n" +
            "    int cont=0, QtdNo=0;\n" +
            "    if (raiz == NULL)\n" +
            "        return 0;\n" +
            "    if (*raiz == NULL)\n" +
            "        return 0;\n" +
            "    // enquanto tiver n�s abaixo ele continuar� a recurss�o\n" +
            "    while((*raiz)->filhos[cont]!=NULL){\n" +
            "        QtdNo = totalNO_ArvB(&((*raiz)->filhos[cont]));\n" +
            "        cont++;\n" +
            "    }\n" +
            "    cont+=QtdNo;\n" +
            "  //  ap�s isso � somado tudo\n" +
            "    return(cont + 1);\n" +
            "}\n" +
            "\n" +
            "int remove_ArvB(ArvB *raiz, int valor){\n" +
            "    int cont=0;\n" +
            "    if(raiz == NULL)\n" +
            "        return 0;\n" +
            "    struct NO* atual = *raiz;\n" +
            "    while(atual != NULL){\n" +
            "        for (cont=0;cont<NUMELM;cont++){\n" +
            "            if(valor == atual->info[cont])\n" +
            "               atual->info[cont]=-1;\n" +
            "        }\n" +
            "        for (cont=0;cont<=NUMELM;cont++)\n" +
            "        // retorna chave q ir� descer\n" +
            "        BuscaChaveNo(atual, valor, &cont);\n" +
            "        atual = atual->filhos[cont];\n" +
            "    }\n" +
            "    return 0;\n" +
            "}\n" +
            "\n" +
            "void emOrdem_ArvB(ArvB *raiz){\n" +
            "    printf(\"\\nImprimindo arvore...\\n\");\n" +
            "    int cont=0, QtdNo=0;\n" +
            "    if (raiz == NULL)\n" +
            "        return 0;\n" +
            "    if (*raiz == NULL)\n" +
            "        return 0;\n" +
            "    while((*raiz)->filhos[cont]!=NULL){\n" +
            "        QtdNo = totalNO_ArvB(&((*raiz)->filhos[cont]));\n" +
            "        cont++;\n" +
            "    }\n" +
            "    for (cont=0;cont<NUMELM;cont++){\n" +
            "        if ((*raiz)->info[cont]!=-1)\n" +
            "        printf(\"Valor %d: %d\\n\", cont+1, (*raiz)->info[cont] );\n" +
            "    }\n" +
            "    printf(\"Arvore impressa com sucesso\\n\");\n" +
            "}";
}
