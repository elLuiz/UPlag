/*
Grupo LMT
Integrantes:
Leonardo Santos Baia - 11621BCC021
Matheus Henrique Ferreira Prot�sio - 11521BCC020
Thiago Duarte Brito - 11611BCC019
*/

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "skiplist.h"

#define P 0.5
#define MAX_LEVEL 5


struct NO{
    int N;
    int level; //n�vel do n�
    struct NO** proximo;
};

struct skiplist {
    struct NO* cabecalho; //n� cabe�alho da skiplist
    int level;
};

//Gerar n�meros alet�rios entre 0 e 1.
float random(){
    float result = 0.0;

    result = ((float)rand()) / (float)(RAND_MAX);

    return result;
}


//Criar um novo n�vel aleat�rio.
int random_level(){
    int result = 1;

    while(random() < P && result < MAX_LEVEL){
        result++;
    }

    return result;
}


struct NO* criar_NO(int level, int* N){
    struct NO* result = 0;

    // Alocando mem�ria para armazenar uma inst�ncia de um no.
     result = malloc(sizeof(struct NO));

    //Atribuindo ao novo n� o seu level.
    result->level = level;

    result->proximo = malloc( (level + 1) * sizeof(struct NO*));

    //Configurar todas as posi��es do proximo com 0 (null).
    int indice = 0;
    while(indice < (result->level + 1)){

        result->proximo[indice] = 0;

        indice++;
    }

    //Atribuindo o valor do novo n�.
    result->N=N;

    return result;
}


struct skiplist* criaSkipList(){
    struct skiplist* result = 0;

    result = malloc(sizeof(struct skiplist));

    // Criando o n� cabecalho, sendo seu n�vel o valor m�ximo.
    result->cabecalho = criar_NO(MAX_LEVEL,NULL);

    // Configurando level da skip list como zero.
    result->level = 0;

    return result;
}


//Procurar na skip list por N e retornar o n� onde esse valor est�.
struct NO* procurar(struct skiplist* sl, int* N){

    // x incialmente aponta para o cabecalho da skiplist.
    struct NO* x = sl->cabecalho;

    // N�vel inicial.
    int _level = x->level;

    // Iniciar pelo topo, descendo at� o n�vel mais baixo.
    for(int i = _level; i >= 0; i--){
        while((x->proximo[i] != 0) && (x->proximo[i]->N < N)){
            // Andar para frente.
            x = x->proximo[i];
        }
    }

    /* Nesse ponto existem 3 possibilidades para o valor de x, s�o elas:
     * - x est� apontando para o valor que estava sendo procurado;
     * - x est� apontando para um valor maior que o procurado;
     * - x est� null.
    */

    x = x->proximo[0];

    return (struct NO*)x;
}

int buscaSkipList(struct skiplist* sl, int* N){
    int result = 0;

    struct NO* encontrado = procurar(sl, N);
    if(encontrado != 0){
        if(encontrado->N == N){
            // Existe
            result = 1;
        }
    }

    return result;
}


void insereSkipList(struct skiplist* sl, int* N){
    struct NO** update = malloc( (MAX_LEVEL + 1) * sizeof(struct NO*) );

    //x aponta para o cabe�alho da skip list.
    struct NO* x = sl->cabecalho;

    int _level = x->level;

    // Iniciar pelo topo, descendo at� o n�vel mais baixo.
    for(int i = _level; i >= 0; i--){
        while((x->proximo[i] != 0) && (x->proximo[i]->N < N)){
            // Andar para frente.
             x = x->proximo[i];
        }
        //Guardar em update o n� que ser� atualizado.
        update[i] = x;
    }

    /* Nesse ponto existem 3 possibilidades para o valor de x, s�o elas:
     * - x est� apontando para o valor que estava sendo procurado;
     * - x est� apontando para um valor maior que o procurado;
     * - x est� null.
     */
    x = x->proximo[0];

    //Caso x seja null ou x->N n�o seja igual � N
    if((x == 0) || (x->N)!= N){

        // Obter o n�vel do n� novo.
        int newLevel = random_level();

        // Caso o n�vel do n� novo seja maior que o n�vel da skiplist
        if(newLevel > _level){
            for(int indice = _level + 1; indice <= newLevel; indice++){
                update[indice] = sl->cabecalho;
            }

            // Atualizar o n�vel da skip list.
            sl->level = newLevel;
        }

        x = criar_NO(newLevel, N);

        // Inserindo o n� novo na skiplist.
        for(int indice = 0; indice <= newLevel; indice++){
            x->proximo[indice] = update[indice]->proximo[indice];
            update[indice]->proximo[indice] = x;
        }
    }
}

int removeSkipList(struct skiplist* sl, int* N){
    struct NO* x = sl->cabecalho;
    int _level = x->level;

    int i;

    for(i = _level - 1; i >= 0; i--) {
        while(x->proximo[i] != 0 && x->proximo[i]->N < N) {
            x = x->proximo[i];
        }
    }


    x = x->proximo[0];

    if(x == 0 || x->N != N) {
        return 0;
    }

    /* depois de encontrado separamos */
    for(i = _level- 1; i >= 0; i--) {
        while(sl->proximo[i]!=0 && sl->proximo[i]->N < N) {
            sl = sl->proximo[i];
        }

        if(sl->proximo[i] == x) {
            sl->proximo[i] = x->proximo[i];
        }
    }

    free(x);
    return 1;
}
