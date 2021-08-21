/*  GrupoDP
 *  Integrantes:
 *  Aluno: Douglas Gomes de Paula       Matrícula: 11621BCC013
 *  Aluno: Pedro Henrique Rabis Diniz   Matrícula: 11811BCC024
 *  Material utilizado como base: http://www.ic.unicamp.br/~lee/mc202/skiplist.pdf
 */

#include <stdio.h>
#include <stdlib.h>
#include "GrupoDP.h"
#include<time.h>

#define MAX 20

SkipList* criaSkiplist(){
    SkipList *sl;
    //Alocando a estrutura do nó cabeça
    sl = (SkipList*) malloc(sizeof(SkipList));
    if(sl!=NULL){
        sl->valor = 0; //será o tamanho da SkipList
        sl->posicao_livre = 0; //Primeira posição livre do vetor de ponteiros (primeiro campo sem endereço)

        /* Definindo o tamanho do vetor de ponteiros do nó cabeça da SkipList
         * usando números aleatórios
         */
        srand(time(NULL));
        sl->altura = 5 + (rand() % MAX); //altura minima será 5 e máxima será 25
        //alocando vetor de ponteiros com o tamanho sorteado
        sl->prox = (SkipList**) malloc(sl->altura*sizeof(SkipList*));

        if(sl->prox != NULL){
            int i;
            //Todos os campos do vetor de ponteiros do nó aponta para NULL
            for(i=0;i<sl->altura;i++){
                sl->prox[i] = NULL;
            }
        }else{
            //Caso dê erro na alocação do vetor de ponteiros, o nó é liberado e retorna NULL
            free(sl);
            return NULL;
        }
        printf("SL altura: %i\n",sl->altura);
    }
    return sl;
}

SkipList* criaNo(SkipList* sl, int valor){
    SkipList *No;
    //Alocando a estrutura do nó
    No = (SkipList*) malloc(sizeof(SkipList));
    if(No != NULL){
        No->valor = valor; //guarda o valor do nó
        No->posicao_livre = 0;

        /* Definindo o tamanho do vetor de ponteiros do nó
         * usando números aleatórios e tamanho máximo limitado pela altura do nó cabeça
         */
        No->altura = 1 + (rand() % sl->altura); //altura minima será 1 e máxima será altura do nó cabeça
        No->prox = (SkipList**) malloc(No->altura*sizeof(SkipList*));
        if(No->prox!=NULL){
            int i;
            //Todos os campos do vetor de ponteiros do nó aponta para NULL
            for(i=0;i<No->altura;i++){
                No->prox[i] = NULL;
            }

        }else{
            //Caso dê erro na alocação do vetor de ponteiros, o nó é liberado e retorna NULL
            free(No);
            return NULL;
        }
        printf("No altura: %i\n",No->altura);
    }
    return No;
}

void liberaSkipList(SkipList* sl){
    if(vaziaSkipList(sl)==1){
        free(sl->prox);
        free(sl);
    }else{
        SkipList* aux1;
        SkipList* aux2;
        aux1 = sl->prox[0];
        do{
            aux2 = aux1;
            aux1 = aux1->prox[0];
            free(aux2->prox);
            free(aux2);
        }while(aux1!=NULL);
        free(sl->prox);
        free(sl);
    }
}
int insereSkipList(SkipList* sl, int N){
    if(sl==NULL){
        return 0;
    }
    SkipList* No;
    //criando nó com a função auxiliar
    No = criaNo(sl,N);
    //caso dê erro na alocação do nó pela função auxiliar
    if(No==NULL){
        return 0;
    }
    int i;
    if(vaziaSkipList(sl)==1 || N <= sl->prox[0]->valor){
    /* O caso em que a SkipList está vazia ou
     * o caso em que o valor a ser inserido é menor ou igual ao primeiro valor apontado pela skipList
     * são os mesmos, trados pelo mesmo treço de código
     */
        for(i=0;i<No->altura;i++){
            No->prox[i] = sl->prox[i];
            sl->prox[i] = No;
        }
        if(No->altura > sl->posicao_livre)
           sl->posicao_livre = No->altura;

    }else{
        //Caso Geral
        int pos;

        /* Caso o nó tenha mais níveis (maior altura) do que está sendo apontado pela Skiplist pela posicao_livre,
         * a posição livre na cabeça da SkipList é alterada antes de começar a
         * percorrer em busca da posição de inserção
         */
        if(No->altura > sl->posicao_livre)
            sl->posicao_livre = No->altura;

        //o pos inicial é o primeiro diferente de null de cima para baixo
        pos = sl->posicao_livre - 1;
        SkipList* aux;
        aux = sl;

        //Percorrimento da SkipList em busca da posição
        while((aux->prox[pos] != NULL && N >= aux->prox[pos]->valor) || (pos > 0)){
        /* Caso a próx posição apontada seja nula ou o elemento seja menor do que o guardado pelo nó
         * apontado, a função desce um nível
         */
            if((aux->prox[pos] == NULL) || (N < aux->prox[pos]->valor)){

                if(pos < No->altura){
                /* Correção dos ponteiros durante a busca da posição de inserção
                 */
                    No->prox[pos] = aux->prox[pos];
                    aux->prox[pos] = No;
                }
                //desce um nível
                pos = pos - 1;
            }else{
                /* Se o caso anterior falhar, apenas avanço para o próx nó
                 * ainda na mesma posição pos
                 */
                aux = aux->prox[pos];
            }
        }
        //Correção do ponteiro que falta corrigir ao encontrar a posição adequada
        No->prox[pos] = aux->prox[pos];
        aux->prox[pos] = No;
    }

    //Incrementa o tamanho da SkipList guardada na cabeça sl
    sl->valor++;
    return 1;
}
int removeSkipList(SkipList* sl, int N){
    if(sl == NULL || vaziaSkipList(sl)==1)
        return 0;

    SkipList* aux;
    SkipList* aux2;
    aux = sl;
    //o pos inicial é o primeiro diferente de null de cima para baixo
    int pos = sl->posicao_livre - 1;

    //Percorrimento da SkipList em busca do elemento
    while((aux->prox[pos] != NULL && N > aux->prox[pos]->valor)||(pos > 0)){
        /* Caso a próx posição apontada seja nula ou o elemento seja menor do que o guardado pelo nó
         * apontado, a função desce um nível
         */
        if((aux->prox[pos] == NULL) || (N < aux->prox[pos]->valor)){
            pos = pos - 1;
        }else{
            //Caso o aux na posição pos esteja apontando para o elemento a ser removido:
            if(aux->prox[pos]->valor == N){
                //É colocado um ponteiro para o nó que contém o elemento a ser removido
                aux2 = aux->prox[pos];

                    /* Ajuste dos ponteiros do aux na pos atual passa a apontar para o que o no a ser removido
                     * está apontando
                     */
                    if(pos < aux2->altura){
                        aux->prox[pos] = aux2->prox[pos];

                        /* Caso o ponteiro do nó a ser removido esteja apontado para nulo
                         * a posição livre deve ser atualizada
                         */
                        if(aux->prox[pos] == NULL)
                            aux->posicao_livre = pos;

                        //desce um nível
                        pos = pos - 1;
                    }

            }else aux = aux->prox[pos]; //avanço para o próximo nó
        }
    }
    //Encontrou o valor, falta apenas uma ultima posição para corrigir o ponteiro (pos = 0)
    if(aux->prox[pos] != NULL && aux->prox[pos]->valor == N){
        //corrigindo o ultimo no na pos = 0
        aux->prox[pos] = aux2->prox[pos];

        //Liberando o vetor de ponteiros do nó
        free(aux2->prox);

        //liberando o nó
        free(aux2);

        //decrementando o tamanho da SkipList
        sl->valor--;

        return 1;
    }else{
        //Se o aux parou e o nó seguinte não é o nó que foi removido
        return 0;
    }
}
int buscaSkipList(SkipList* sl, int N){
    if(sl == NULL || vaziaSkipList(sl)==1)
        return 0;

    SkipList* aux;
    aux = sl;

    int pos;
    pos = sl->posicao_livre - 1;

    while((aux->prox[pos] != NULL && N > aux->prox[pos]->valor) || (pos > 0)){
        /* Caso a próx posição apontada seja nula ou o elemento seja menor do que o guardado pelo nó
         * apontado, a função desce um nível
         */
        if((aux->prox[pos] == NULL) || (N < aux->prox[pos]->valor)){
            pos = pos - 1;
        }else{
            //Imprimo o nível visitado durante a busca
            if(aux!=NULL) printf("Visitei: %i no indice %i\n",aux->prox[pos]->valor,pos);
            //Se achou o valor, a busca acaba
            if(aux->prox[pos]->valor == N)
                return 1;
            //Caso não tenha achado, avança para o próximo nó
            aux = aux->prox[pos];
        }

    }
    /* Se o aux parou no valor onde o elemento está presente e
     * o elemento nao é o valor na cabeça da SkipList (guardamos o tamanho da lista na cabeça, se N == tamanho,
     * tamanho nao pode ser considerado como um elemento da skiplist na busca), por isso aux deve ser diferente de sl
     */
    if(aux->valor == N && aux != sl)
        return 1;
    else
        return 0;
}
int tamanhoSkipList(SkipList* sl){
    //se a lista não for alocada, -1 simboliza o erro p/ teste no main
    if(sl==NULL){
        return -1;
    }
    //A cabeça da SkipList contém a quantidade de elementos no campo valor
    return sl->valor;
}
int vaziaSkipList(SkipList* sl){
    //se a lista não for alocada, -1 simboliza o erro p/ teste no main
    if(sl == NULL)
        return -1;

    if(tamanhoSkipList(sl)==0){
        return 1;
    }else{
        return 0;
    }
}
void imprimeSkipList(SkipList* sl){
    if(sl != NULL){
        if(vaziaSkipList(sl)){
            printf("SkipList => Vazia...\n");
        }else{
            SkipList* aux;
            aux = sl->prox[0];
            printf("SkipList => ");

            while(aux!=NULL){
                printf("%i ",aux->valor);
                aux = aux->prox[0];
            }

            int tamanho = tamanhoSkipList(sl);
            printf("\nTamanho: %i\n",tamanho);

        }
    }else{
        printf("SkipList nao foi criada...\n");
    }
}
