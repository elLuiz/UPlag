

#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include"SkipList.h"
#define NIVEL_MAX 7

Skiplist *cria_skiplist(Skiplist *lista) {

    no *cabe = (no *) malloc(sizeof(struct no));

    lista->cabe = cabe;
    cabe->chave = INT_MAX;
    cabe->proximo = (no **) malloc(sizeof(no*) * (NIVEL_MAX + 1));

        int i;

        //CRIAÇÃO DE UM CABEÇALHO PARA TODOS OS NÍVEIS DA SKIPLIST
        for (i = 0; i <= NIVEL_MAX; i++) {
            cabe->proximo[i] = lista->cabe;
        }

    lista->nivel = 1;

    return lista;
}

int nivel_aleatorio() {

    int nivel = 1;

    //ESCOLHA ALEATÓRIA DE UM NÍVEL ENTRE O PRIMEIRO E O NÍVEL MÁXIMO

    while (rand() < RAND_MAX / 2 && nivel < NIVEL_MAX)
        nivel++;

    return nivel;
}

int insere_skiplist(Skiplist *lista, int chave, int valor) {

    no *aux_troca[NIVEL_MAX + 1];
    no *x = lista->cabe;
    int i, nivel;

//O FOR IRA PERCORRER OS NIVEIS ENQUANTO O WHILE PERCORRE AS LISTA NOS RESPECTIVOS NIVEIS
    for (i = lista->nivel; i >= 1; i--) {
        while (x->proximo[i]->chave < chave)
            x = x->proximo[i];
        aux_troca[i] = x; //RECEBE O ÚLTIMO NÓ MENOR QUE O NÓ QUE SE DESEJA INSERIR
    }

    x = x->proximo[1];

// CASO AS CHAVES SEJAM IGUAIS OCORRE A SUBST. DOS VALORES
    if (chave == x->chave) {
        x->valor = valor;
        return 1;
    }
    else {
        nivel = nivel_aleatorio();
        if (nivel > lista->nivel) {
            for (i = lista->nivel + 1; i <= nivel; i++) {
                aux_troca[i] = lista->cabe;
            }
            lista->nivel = nivel;
        }

        //ALOCAÇÃO DO NOVO NÓ
        x = (no *) malloc(sizeof(no));
        x->chave = chave;
        x->valor = valor;
        x->proximo = (no **) malloc(sizeof(no*) * (nivel + 1));

        //O NOVO NÓ É INSERIDO APÓS O AUX TROCA , DEFINIDO ANTERIORMENTE
        for (i = 1; i <= nivel; i++) {
            x->proximo[i] = aux_troca[i]->proximo[i];
            aux_troca[i]->proximo[i] = x;
        }
    }
    return 1;
}

no *busca_skiplist(Skiplist *lista, int chave) {

    no *x = lista->cabe;
    int i;
    //PERCORRE-SE OS NIVEIS E AS LISTAS ATÉ ENCONTRAR UMA CHAVE QUE NAO SEJA MENOR QUE A QUE PROCURAMOS
    for (i = lista->nivel; i >= 1; i--) {
        while (x->proximo[i]->chave < chave)
            x = x->proximo[i];
    }
    //VERIFICA SE A PROXIMA CHAVE,OU SEJA,A CHAVE QUE NÃO É MENOR QUE A PROCURAMOS É IGUAL A ELA
    //SE SIM Á RETORNAMOS SE NÃO RETORNAMOS NULL
    if (x->proximo[1]->chave == chave) {
        return x->proximo[1];
    } else {
        return NULL;
    }
    return NULL;
}

 void libera_no(no *x) {

    if (x) {
        free(x->proximo);
        free(x);
    }
}

int remove_skiplist(Skiplist *lista, int chave) {

    int i;
    no *aux_troca[NIVEL_MAX + 1];
    no *x = lista->cabe;

    //PERCORRE-SE OS NIVEIS E AS LISTAS D CADA NIVEL ATE ENCONTRAR UMA CHAVE QUE NAO SEJA MENOR QUE A QUE DESEJAMOS EXCLUIR
    //GUARDAMOS ESTE NO NO VETOR AUX_TROCA
    for (i = lista->nivel; i >= 1; i--) {
        while (x->proximo[i]->chave < chave)
            x = x->proximo[i];
        aux_troca[i] = x;
    }

    //DESLOCA-SE X PARA A SUPOSTA POSICAO DA CHAVE QUE QUEREMOS REMOVER
    x = x->proximo[1];

    //SE A CHAVE DA POSIÇÃO X FOR IGUAL A QUE QUEREMOS REMOVER
    //FAZEMOS AS DEVIDAS ALTERAÇOES USANDO O VETOR AUX_TROCA QUE CONTEM O NO ANTERIOR AO QUE DESEJAMOS REMOVER
    if (x->chave == chave) {
        for (i = 1; i <= lista->nivel; i++) {
            if (aux_troca[i]->proximo[i] != x)
                break;
            if(x->proximo == NULL){
                aux_troca[i]->proximo = NULL;
                break;
            }
            else{
                aux_troca[i]->proximo[1] = x->proximo[i];
                break;
            }
        }
        libera_no(x);
    //CASO NAO HAJA NENHUM ELEMENTO EM ALGUM NIVEL DIMINUIMOS O NIVEL DA LISTA
        while (lista->nivel > 1 && lista->cabe->proximo[lista->nivel]== lista->cabe)
            lista->nivel--;
        return 0;
    }
    return 1;
}

 void imprime_skiplist(Skiplist *lista) {

    no *x = lista->cabe;

    int i;

    if(x->proximo[1] == x)
    {
        printf("Skiplist Vazia\n");

    }

    else {

    //IMPRESSÃO DE TODOS OS VALORES DA SKIPLIST , PERCORRENDO AS LISTAS EM TODOS OS NIVEIS
    for(i = lista->nivel ; i>= 1;i--){

        if(x == lista->cabe){
            printf("[Nivel: %d]",i);
            x = x->proximo[i];
        }

        while (x != lista->cabe) {

            printf("[%d|%d] -> ", x->chave, x->valor);//[CHAVE|VALOR]

            x = x->proximo[i];
        }

        x = lista->cabe;

        printf("NULL\n");
        printf("\n");

    }
    }
}

//UTILIZANDO A MESMA ESTRATÉGIA DA IMPRESSÃO, PERCORREMOS A SKIPLIST NO NIVEL 1 E AQUI IMPLEMETAMOS UM CONTADOR
//NESTE CASO CONSIDERAMOS O TAMANHO A QUANTIDADE DE ELEMENTOS NA LISTA, CONSIDERANDO APENAS O 1 NIVEL
 int tamanho_skiplist(Skiplist *lista) {
    no *x = lista->cabe;

    int contador = 0;

    while (x->proximo[1] != lista->cabe) {
        contador++;
        x = x->proximo[1];
    }
    return contador;
}

//O FINAL DE CADA NIVEL DA SKIPLIST APONTA PARA O CABEÇALHO ENTAO SE O PROXIMO DO CABEÇALHO FOR O PROPRIO CABEÇALHO A LISTA ESTA VAZIA;

int vazia_skiplist(Skiplist *lista){

    if(lista->cabe->proximo[lista->nivel]== lista->cabe){

        return 1;

    }
    else
        return 0;
}

//PERCORRE-SE TODOS OS VALORES DA SKIPLIST E OS REMOVE UM A UM, LIBERANDO A MEMÓRIA DE SEUS NÓS

 void libera_skiplist(Skiplist *lista){

    int i;

    no *x = lista->cabe;
    no *aux;

    for(i = lista->nivel; i>=1;i--){
        while(x->proximo[i] != lista->cabe){
            aux = x->proximo[i];
            libera_no(x);
            x = aux;
        }

    }

}



