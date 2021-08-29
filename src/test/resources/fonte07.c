

#include <stdio.h>
#include <stdlib.h>
#include "GrupoLM.h"
#include <time.h>
#define MenosInf -2147483648
#define MAX 10

struct elem{ //Cada elemento possui um ponteiro indicando o próximo elemento e o elemento de baixo
    int num;
    struct elem *prox;
    struct elem *baixo;
};

Elem *criaSkipList( ){
    Elem *Skip;
    int cont;
    Elem *aux;
    for(cont=0; cont<MAX; cont++){              // Cria os nós iniciais com o menor inteiro possível de acordo com a altura máxima definida pelo MAX;
        Elem *no = (Elem*) malloc(sizeof(Elem));
        no->num = MenosInf; //define o menor inteiro
        no->baixo = NULL; //faz o ponteiro de baixo apontar para o NULL no menor nível
        no->prox = NULL;

        if(cont==0){
            Skip = no;
            aux = no;
        }else{ //Se o elemento não estiver no último nível da SkipList
            aux->baixo = no;
            aux = no;
        }
    }
    return Skip; // retorna o ponteiro para o primeiro elemento da SkipList
}

int insereSkipList(Elem* sl, int N)
{
    int random, level, i;
    random = rand()%MAX+1;
    level = MAX - random;   //Sorteia um número para a altura do novo nó

    while(level--)
        sl = sl->baixo; //reajustar o ponteiro da SkipList para a altura definida pelo nível

    Elem *aux, *aux2;

    for(i=0; i<random; i++){
        aux=sl;
        while(aux->prox != NULL && aux->prox->num < N)
            aux = aux->prox;

        Elem *no = (Elem*) malloc (sizeof(Elem));   //insere os elementos de cima para baixo na SkipList
        if(no==NULL) return 0;

        no->num = N;
        no->baixo = NULL;
        no->prox = aux->prox;
        aux->prox = no;
        if(i!=0)
            aux2->baixo = no;   //aponta o ponteiro (baixo) do elemento de cima para o novo nó
        aux2 = no;
        sl = sl->baixo;
    }
    return 1;
}

int removeSkipList(Elem* sl, int N){
    int removido = 0;
    if(vaziaSkipList(sl))
        return 0;
    else{
        while(sl!=NULL){ //Enquanto não chegar no último nível da SkipList
            Elem *aux = sl;
            while(aux->prox!=NULL && aux->prox->num < N)
                aux = aux->prox;

            if(aux->prox!=NULL && aux->prox->num==N){
                Elem *aux2 = aux->prox; //Define um aux2 apontando para o elemento a ser removido e depois da um free
                aux->prox = aux2->prox;
                free(aux2);
                removido++;
            }
            sl = sl->baixo;
        }
        if(removido>0) return 1;
        else return 0;
    }
}

int vaziaSkipList(Elem* sl){
    while(sl->baixo!=NULL){ //Vai até o nível mais baixo da SkipList e verifica se há algum elemento nela, caso não tenha retorna 1
        sl = sl->baixo;
    }
    if(sl->prox!=NULL)
        return 0;
    else
        return 1;
}

void imprimeSkipList(Elem* sl){

    if(vaziaSkipList(sl) == 0){
        while(sl->baixo != NULL) //Imprime todos os elemento da SkipList (incluindo as repetições dada pelas alturas de cada elemento)
            sl = sl->baixo;
        sl = sl->prox;
        while(sl!=NULL){
            printf("%d  ", sl->num);
            sl = sl->prox;
        }
    }
}

void imprimetudo(Elem* sl){ //imprime todos os elementos da SkipList, incluindo as repetições
    if(!vaziaSkipList(sl)){
        Elem *aux2 = sl;
        Elem *aux = aux2;
        while(aux2!=NULL){
            aux = aux->prox;
            while(aux!=NULL){
                printf("%d ", aux->num);
                aux = aux->prox;
            }
            puts("");
            aux2 = aux2->baixo;
            aux=aux2;
        }

    }
}


int buscaSkipList(Elem* sl, int N){ //Procura o elemento a começar pelo primeiro elemento da SkipList
    if(vaziaSkipList(sl))  return 0;
    Elem *aux = sl;

    while(aux!=NULL){
        while(aux->prox!=NULL && aux->prox->num <= N){
            aux = aux->prox;
        }

        if(aux->num == N)
            return 1;
        aux = aux->baixo; //Pula os elementos anteriores e começa a busca de um nível mais baixo
    }
    return 0;
}


int tamanhoSkipList(Elem *sl){ //Retorna o tamanho da SkipList (Todos os elementos incluindo as repetições da altura)
    int tam = 0;
    while(sl!=NULL){
        Elem* aux = sl;
        while(aux!=NULL){
            aux = aux -> prox;
            tam++; //inclementa a cada elemento encontrado
        }
        sl = sl->baixo;
    }
    return tam;
}

void liberaSkipList(Elem** sl){ //Percorre elemento a elemento e utiliza um ponteiro auxiliar para indicar o elemento a ser removido enquanto o ponteiro "original" continua pecorrendo a SkipList
    int fim = 0;
    while(1){
        Elem *aux = *sl;
        Elem *aux2 = aux;
        if((*sl)!=NULL)
            (*sl) = (*sl)->baixo;
        else fim=1;
        while(aux!=NULL){
            aux2=aux;
            aux=aux->prox;
            free(aux2);
        }
        if(fim) break;
    }
}
