#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "SL.h"
#define INT_MIN  (-2147483648)
#define INT_MAX   (2147483647)
#define MAX 5

/*
    Grupo IV
    Integrantes:
    Igor Blanco Toneti - 11811BCC010
    Vinicio Bernardes Silva - 11811BCC015
    */

struct no {
    int info;
    Lista *prox;
    Lista *baixo;
};


Lista *criaLista(){ /* A ideia aqui � criar duas listas para servir como uma "parede", do lado esquerdo o menor interiro possivel e do direito o maior. Enquanto for criando pegar o N� mais alto
                            da esquerda e retornar seu endere�o e a medida que for inserindo os numeros ficar�o entre essas paredes o q facilita e diminui os codigos condicionais (atua como
                                                                                                                                                                                    um cabe�alho) */
    Lista *P;
    int i;
    Lista *aux;
    for (i=0;i<MAX;i++){
        Lista *N=(Lista*)malloc(sizeof(Lista));
        if(N==NULL) return 0;

        Lista *M=(Lista*)malloc(sizeof(Lista));
        if(M==NULL) return 0;

        N->info=INT_MIN;
        M->info=INT_MAX;

        N->baixo=NULL;/* o n� mais a esquerda recebe como endere�o primeiramente o elemento mais a direita e abaixo NULL, a medida que formos inserindo os n�s das camadas acima aponta
                        para a camada de baixo*/
        M->baixo=NULL;
        M->prox=NULL;
        N->prox=M;

        if(i==0){ /* Caso seja o primeiro n� salvamos o endere�o para retornar no final da fun��o*/
            P=N;
            aux=N;
        }else{
            aux->baixo=N;
            aux->prox->baixo=M;
            aux=N;
        }
    }
    return P;
}

int insereSkipList(Lista *lst, int num){ /* Essa fun��o funciona da seguinte maneira: sorteamos um numero, este numero ser� a altura do elemento na SkipList, depois tiramos da altura max
                                            para saber assim quantos degraus iremos descer, a partir dai percorrer a lista com um auxiliar para saber onde inserir (sempre parando no n� anterior)
                                            e a medida que formos inserindo no n� mais de cima descemos na lista at� chegar em NULL*/

    int rdm,nivel,i;
    rdm=rand()%MAX+1;
    nivel = MAX-rdm;
    while(nivel--) lst=lst->baixo;/* calculo para saber o nivel que come�aremos a inserir na lista*/
    Lista *aux;
    Lista *aux2;
    for(i=0;i<rdm;i++){ /* loop para quantidade de niveis que iremos inserir*/
    aux=lst;
        while(aux->prox->info < num) aux = aux->prox; /* caminhamos at� o momento que o prox seja maior para podermos inserir o elemento (por quest�o da "parede" que contem o maior infinito
                                                         n�o necessitamos colocar condi��es adicionais como percorrer at� NULL)*/
    Lista *N=(Lista*)malloc(sizeof(Lista));

    if(N==NULL) return 0;

    N->info=num; /* troca de ponteiros, fazer o prox do anterior ser o prox do n� que gostariamos de inserir*/
    N->baixo=NULL;
    N->prox=aux->prox;
    aux->prox=N;
    if(i!=0) /* caso seja o nivel mais alto do elemento que estamos inserindo criamos um auxiliar apontando para ele e assim podermos ligar o ponteiro "baixo" para conectarmos os n�s de
                mesmo elemento*/
        aux2->baixo=N;
    aux2=N;
    lst=lst->baixo;
    }
    return 1;
}

int removeSkipList(Lista *lst, int num){ /* A ideia dessa fun��o � percorrer a skiplist como se fosse uma matriz, deixaremos um aux na "parede" da skiplist para que possamos descer
                                            os niveis e assim retirar os elementos sempre parando no anterior*/
    if(vaziaSkipList(lst)){
        return 0;
    }else{
        while(lst!=NULL){
            Lista *aux=lst;
            while(aux->prox->info<num) aux=aux->prox; /*percorrer e parar no anterior ao n� que gostariamos de retirar*/
            if(aux->prox->info==num){
                Lista *aux2= aux->prox;
                aux->prox=aux2->prox; /*Troca de ponteiros*/
                free(aux2);
            }
            lst=lst->baixo; /* descer pela skiplist*/
        }
        return 1;
    }
}

int buscaSkipList(Lista *lst,int num){ /*Na busca vamos caminhar at� um maior elemento que o nosso, depois de achar passamos para o andar de baixo, e assim vamos caminhar, caso chegar no
                                        mais infinito e embaixo ser NULL sabemos que o numero nao existe*/
    if(vaziaSkipList(lst)){
        return 0;
    }else{
        while(1){
            while(lst->prox->info<num) lst=lst->prox; /*Anda at� o maior elemento*/
                if(lst->prox->info>num) lst=lst->baixo;/*Desce*/
                if(lst->prox->info==num) return 1;
                if(lst->prox->info==INT_MAX && lst->baixo==NULL) return 0;
        }
    }
}

int tamanhoSkipList(Lista *lst){ /*Apenas andar pela skiplist como se fosse cada elemento da matriz e entao a cada elemento que passar somaremos 1 no tamanho (nro de n�s)*/
    int tam=0;
    while(lst!=NULL){ /* anda pela primeira coluna*/
            Lista*aux=lst;
            while(aux!=NULL){/*anda pelas linhas*/
                aux=aux->prox;
                tam++;
            }
            lst=lst->baixo;
        }
    return tam - MAX*2; /*Tiramos max*2 pois contamos os n�s "parede"*/
}

void liberaSkipList(Lista *lst){ /*A ideia aqui � usar 3 auxiliares, precisaremos de um para andar pelas linhas, outro que ser� dado o FREE e o terceiro usaremos para andar pelas colunas*/
    while(lst!=NULL){
        Lista *aux=lst; /*auxiliar para percorrer a linha*/
        Lista *aux2=aux;/*auxiliar que sera deletado*/
        lst=lst->baixo;
        while(aux!=NULL){
            aux2=aux;
            aux=aux->prox;
            free(aux2);
        }
    }
}

int vaziaSkipList(Lista *lst){ /*A cada linha que passa checaremos se a informa��o do pr�ximo elemento � o maior infinito, se for para todas as linhas ent�o saberemos que � vazia*/
    while(lst->baixo!=NULL){
        if(lst->prox->info!=INT_MAX){
            return 0;
        }
        lst=lst->baixo;
    }
    return 1;
}

void imprimeSkipList(Lista *lst){ /*mesma ideia de percorrimento de uma matriz*/
    if(!vaziaSkipList(lst)){
        while(lst->baixo!=NULL)lst = lst->baixo; /*a cada linha que passar printaremos os elementos das colunas*/
        lst = lst->prox;
        while(lst->prox!=NULL){ /*andaremos at� o prox ser diferente de NULL pois assim saberemos que estamos na parede e n�o necessitamos imprimir sua informa��o*/
            printf("%d\n", lst->info);
            lst = lst->prox;
        }
    }
}
