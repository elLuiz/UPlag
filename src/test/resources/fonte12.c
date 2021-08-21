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


Lista *criaLista(){ /* A ideia aqui é criar duas listas para servir como uma "parede", do lado esquerdo o menor interiro possivel e do direito o maior. Enquanto for criando pegar o Nó mais alto
                            da esquerda e retornar seu endereço e a medida que for inserindo os numeros ficarão entre essas paredes o q facilita e diminui os codigos condicionais (atua como
                                                                                                                                                                                    um cabeçalho) */
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

        N->baixo=NULL;/* o nó mais a esquerda recebe como endereço primeiramente o elemento mais a direita e abaixo NULL, a medida que formos inserindo os nós das camadas acima aponta
                        para a camada de baixo*/
        M->baixo=NULL;
        M->prox=NULL;
        N->prox=M;

        if(i==0){ /* Caso seja o primeiro nó salvamos o endereço para retornar no final da função*/
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

int insereSkipList(Lista *lst, int num){ /* Essa função funciona da seguinte maneira: sorteamos um numero, este numero será a altura do elemento na SkipList, depois tiramos da altura max
                                            para saber assim quantos degraus iremos descer, a partir dai percorrer a lista com um auxiliar para saber onde inserir (sempre parando no nó anterior)
                                            e a medida que formos inserindo no nó mais de cima descemos na lista até chegar em NULL*/

    int rdm,nivel,i;
    rdm=rand()%MAX+1;
    nivel = MAX-rdm;
    while(nivel--) lst=lst->baixo;/* calculo para saber o nivel que começaremos a inserir na lista*/
    Lista *aux;
    Lista *aux2;
    for(i=0;i<rdm;i++){ /* loop para quantidade de niveis que iremos inserir*/
    aux=lst;
        while(aux->prox->info < num) aux = aux->prox; /* caminhamos até o momento que o prox seja maior para podermos inserir o elemento (por questão da "parede" que contem o maior infinito
                                                         não necessitamos colocar condições adicionais como percorrer até NULL)*/
    Lista *N=(Lista*)malloc(sizeof(Lista));

    if(N==NULL) return 0;

    N->info=num; /* troca de ponteiros, fazer o prox do anterior ser o prox do nó que gostariamos de inserir*/
    N->baixo=NULL;
    N->prox=aux->prox;
    aux->prox=N;
    if(i!=0) /* caso seja o nivel mais alto do elemento que estamos inserindo criamos um auxiliar apontando para ele e assim podermos ligar o ponteiro "baixo" para conectarmos os nós de
                mesmo elemento*/
        aux2->baixo=N;
    aux2=N;
    lst=lst->baixo;
    }
    return 1;
}

int removeSkipList(Lista *lst, int num){ /* A ideia dessa função é percorrer a skiplist como se fosse uma matriz, deixaremos um aux na "parede" da skiplist para que possamos descer
                                            os niveis e assim retirar os elementos sempre parando no anterior*/
    if(vaziaSkipList(lst)){
        return 0;
    }else{
        while(lst!=NULL){
            Lista *aux=lst;
            while(aux->prox->info<num) aux=aux->prox; /*percorrer e parar no anterior ao nó que gostariamos de retirar*/
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

int buscaSkipList(Lista *lst,int num){ /*Na busca vamos caminhar até um maior elemento que o nosso, depois de achar passamos para o andar de baixo, e assim vamos caminhar, caso chegar no
                                        mais infinito e embaixo ser NULL sabemos que o numero nao existe*/
    if(vaziaSkipList(lst)){
        return 0;
    }else{
        while(1){
            while(lst->prox->info<num) lst=lst->prox; /*Anda até o maior elemento*/
                if(lst->prox->info>num) lst=lst->baixo;/*Desce*/
                if(lst->prox->info==num) return 1;
                if(lst->prox->info==INT_MAX && lst->baixo==NULL) return 0;
        }
    }
}

int tamanhoSkipList(Lista *lst){ /*Apenas andar pela skiplist como se fosse cada elemento da matriz e entao a cada elemento que passar somaremos 1 no tamanho (nro de nós)*/
    int tam=0;
    while(lst!=NULL){ /* anda pela primeira coluna*/
            Lista*aux=lst;
            while(aux!=NULL){/*anda pelas linhas*/
                aux=aux->prox;
                tam++;
            }
            lst=lst->baixo;
        }
    return tam - MAX*2; /*Tiramos max*2 pois contamos os nós "parede"*/
}

void liberaSkipList(Lista *lst){ /*A ideia aqui é usar 3 auxiliares, precisaremos de um para andar pelas linhas, outro que será dado o FREE e o terceiro usaremos para andar pelas colunas*/
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

int vaziaSkipList(Lista *lst){ /*A cada linha que passa checaremos se a informação do próximo elemento é o maior infinito, se for para todas as linhas então saberemos que é vazia*/
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
        while(lst->prox!=NULL){ /*andaremos até o prox ser diferente de NULL pois assim saberemos que estamos na parede e não necessitamos imprimir sua informação*/
            printf("%d\n", lst->info);
            lst = lst->prox;
        }
    }
}
