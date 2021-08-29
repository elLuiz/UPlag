#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "SkipListT.h"
#define INT_MIN  (-2147483648)
#define MAX 20

struct sl {
    int info;
    struct sl *prox;
    struct sl *baixo;
};

SkipList *criaSkipList(){
    //Cria-se uma "lista" de elementos e retorna o ponteiro que está no nivel mais acima
    SkipList *P;
    SkipList *aux;
    for (int i=0;i<MAX;i++){
        SkipList *N=(SkipList*)malloc(sizeof(SkipList));
        N->info  = INT_MIN; //menor inteiro negativo
        N->baixo = NULL;
        N->prox  = NULL;

        if(i==0){ //aponta o ponteiro para o primeiro elemento
            P=N;
            aux=N;
        }else{
            aux->baixo=N;
            aux=N;
        }
    }
    return P;
}

int insereSkipList(SkipList *SL, int elem){
    //sorteamos um numero aleatorio e tiramos ele do maximo para sabermos o nivel que começaremos a colocar o nó
    //a partir dai, criamos um loop com o nivel e percorremos todas as linhas parando no elemento anterior , para que assim,
    //possamos criar e inserir um novo nó
    int random,lvl;
    random=rand()%MAX+1;
    lvl = MAX-random; //level
    while(lvl--){
        SL=SL->baixo;
    }
    SkipList *aux;
    SkipList *aux2;
    for(int i=0;i<random;i++){
    aux=SL;
        while(aux->prox!=NULL && aux->prox->info < elem){
            aux = aux->prox;
        }
    SkipList *N=(SkipList*)malloc(sizeof(SkipList));

    if(N==NULL)
        return 0;

    N->info=elem;
    N->baixo=NULL;
    N->prox=aux->prox;
    aux->prox=N;
    if(i!=0)
        aux2->baixo=N;
    aux2=N;
    SL=SL->baixo;
    }
    return 1;
}

int removeSkipList(SkipList *SL, int elem){
    //temos que percorrer todas as linhas procurando pelo elemento requisitado
    //O nó para 1 antes do que queremos
    //um auxiliar2 é criado para deletar o nó e religar os ponteiros que sobraram
    //depois disso, descemos por toda a coluna, e , esse mesmo procedimento, é feito para todas as linhas.
    if(vaziaSkipList(SL)){
        return 0;
    }else{
        while(SL!=NULL){
            SkipList *aux=SL;
            while(aux->prox!=NULL && aux->prox->info<elem)
                aux=aux->prox;

            if(aux->prox!=NULL && aux->prox->info==elem){
                SkipList *aux2= aux->prox;
                aux->prox=aux2->prox;
                free(aux2);
            }
            SL=SL->baixo;
        }
        return 1;
    }
}

int vaziaSkipList(SkipList *SL){
    //percorremos a primeira coluna checando se o proximo é igual a null, se em todas as colunas
    //o proximo for igual a NULL, então a lista está vazia
    while(SL->baixo!=NULL){
        if(SL->prox!=NULL){
            return 0;
        }
        SL=SL->baixo;
    }
    return 1;
}

void imprimeSkipList(SkipList *SL){
    //O ponteiro desce até a ultima linha e, apos isso, percorre-se a base percorrendo todos os elementos,
    //pois todos os elementos tem nivel 1
    if(!vaziaSkipList(SL)){
        while(SL->baixo!=NULL)
            SL = SL->baixo;
        SL = SL->prox;
        while(SL->prox!=NULL){
            printf("%d ", SL->info);
            SL = SL->prox;
        }
        printf("%d\n", SL->info);
    }
}

int buscaSkipList(SkipList *SL,int elem){
    //percorremos cada linha da skiplist , checando se existe o elemento desejado.
    //Caso nao exista na linha, descemos com o ponteiro para a linha abaixo
    if(vaziaSkipList(SL)){
        return 0;
    }else{
        while(SL!=NULL){
            SkipList*aux=SL;
            while(aux->prox!=NULL && aux->info<elem)
                aux=aux->prox;

            if(aux->info==elem)
                return 1;

            SL=SL->baixo;
        }
        return 0;
    }
}

//int buscaSkipList(SkipList *lst,int num){
//    if(vaziaSkipList(lst)){
//        return 0;
//    }else{
//        while(1){
//            while(lst->prox != NULL && lst->prox->info<num)
//                lst=lst->prox;
//                if(lst->prox->info>num)
//                    lst=lst->baixo;
//
//                if(lst->baixo==NULL && lst->prox == NULL) return 0;
//                if(lst->prox->info==num) return 1;
//
//        }
//    }
//}

int tamanhoSkipList(SkipList *SL){
    int tam=0; //começa de cada nó , a partir do menor inteiro possivel(-infinito).
    //A partir disso, toda linha da skiplist é percorrida com um auxiliar. A cada passo,
    // adiciona-se 1 ao tamanho
    while(SL!=NULL){
            SkipList*aux=SL;
            while(aux!=NULL){
                aux=aux->prox;
                tam++;
            }
            SL=SL->baixo;
        }
    return tam;
}

void liberaSkipList(SkipList *SL){
    //Para essa, três auxiliares são utilizados. Um para marcar a primeira coluna e , os outros dois,
    //para percorrer as linhas e ir deletando.
    while(SL!=NULL){
        SkipList *aux=SL;
        SkipList *aux2=aux;
        SL=SL->baixo;
        while(aux!=NULL){
            aux2=aux;
            aux=aux->prox;
            free(aux2);
        }
    }
}
