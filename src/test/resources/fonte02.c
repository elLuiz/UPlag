

#include <stdio.h>
#include <stdlib.h>
#include "GrupoDP.h"
#include<time.h>

#define MAX 20

SkipList* criaSkiplist(){
    SkipList *sl;
    //Alocando a estrutura do n� cabe�a
    sl = (SkipList*) malloc(sizeof(SkipList));
    if(sl!=NULL){
        sl->valor = 0; //ser� o tamanho da SkipList
        sl->posicao_livre = 0; //Primeira posi��o livre do vetor de ponteiros (primeiro campo sem endere�o)

        /* Definindo o tamanho do vetor de ponteiros do n� cabe�a da SkipList
         * usando n�meros aleat�rios
         */
        srand(time(NULL));
        sl->altura = 5 + (rand() % MAX); //altura minima ser� 5 e m�xima ser� 25
        //alocando vetor de ponteiros com o tamanho sorteado
        sl->prox = (SkipList**) malloc(sl->altura*sizeof(SkipList*));

        if(sl->prox != NULL){
            int i;
            //Todos os campos do vetor de ponteiros do n� aponta para NULL
            for(i=0;i<sl->altura;i++){
                sl->prox[i] = NULL;
            }
        }else{
            //Caso d� erro na aloca��o do vetor de ponteiros, o n� � liberado e retorna NULL
            free(sl);
            return NULL;
        }
        printf("SL altura: %i\n",sl->altura);
    }
    return sl;
}

SkipList* criaNo(SkipList* sl, int valor){
    SkipList *No;
    //Alocando a estrutura do n�
    No = (SkipList*) malloc(sizeof(SkipList));
    if(No != NULL){
        No->valor = valor; //guarda o valor do n�
        No->posicao_livre = 0;

        /* Definindo o tamanho do vetor de ponteiros do n�
         * usando n�meros aleat�rios e tamanho m�ximo limitado pela altura do n� cabe�a
         */
        No->altura = 1 + (rand() % sl->altura); //altura minima ser� 1 e m�xima ser� altura do n� cabe�a
        No->prox = (SkipList**) malloc(No->altura*sizeof(SkipList*));
        if(No->prox!=NULL){
            int i;
            //Todos os campos do vetor de ponteiros do n� aponta para NULL
            for(i=0;i<No->altura;i++){
                No->prox[i] = NULL;
            }

        }else{
            //Caso d� erro na aloca��o do vetor de ponteiros, o n� � liberado e retorna NULL
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
    //criando n� com a fun��o auxiliar
    No = criaNo(sl,N);
    //caso d� erro na aloca��o do n� pela fun��o auxiliar
    if(No==NULL){
        return 0;
    }
    int i;
    if(vaziaSkipList(sl)==1 || N <= sl->prox[0]->valor){
    /* O caso em que a SkipList est� vazia ou
     * o caso em que o valor a ser inserido � menor ou igual ao primeiro valor apontado pela skipList
     * s�o os mesmos, trados pelo mesmo tre�o de c�digo
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

        /* Caso o n� tenha mais n�veis (maior altura) do que est� sendo apontado pela Skiplist pela posicao_livre,
         * a posi��o livre na cabe�a da SkipList � alterada antes de come�ar a
         * percorrer em busca da posi��o de inser��o
         */
        if(No->altura > sl->posicao_livre)
            sl->posicao_livre = No->altura;

        //o pos inicial � o primeiro diferente de null de cima para baixo
        pos = sl->posicao_livre - 1;
        SkipList* aux;
        aux = sl;

        //Percorrimento da SkipList em busca da posi��o
        while((aux->prox[pos] != NULL && N >= aux->prox[pos]->valor) || (pos > 0)){
        /* Caso a pr�x posi��o apontada seja nula ou o elemento seja menor do que o guardado pelo n�
         * apontado, a fun��o desce um n�vel
         */
            if((aux->prox[pos] == NULL) || (N < aux->prox[pos]->valor)){

                if(pos < No->altura){
                /* Corre��o dos ponteiros durante a busca da posi��o de inser��o
                 */
                    No->prox[pos] = aux->prox[pos];
                    aux->prox[pos] = No;
                }
                //desce um n�vel
                pos = pos - 1;
            }else{
                /* Se o caso anterior falhar, apenas avan�o para o pr�x n�
                 * ainda na mesma posi��o pos
                 */
                aux = aux->prox[pos];
            }
        }
        //Corre��o do ponteiro que falta corrigir ao encontrar a posi��o adequada
        No->prox[pos] = aux->prox[pos];
        aux->prox[pos] = No;
    }

    //Incrementa o tamanho da SkipList guardada na cabe�a sl
    sl->valor++;
    return 1;
}
int removeSkipList(SkipList* sl, int N){
    if(sl == NULL || vaziaSkipList(sl)==1)
        return 0;

    SkipList* aux;
    SkipList* aux2;
    aux = sl;
    //o pos inicial � o primeiro diferente de null de cima para baixo
    int pos = sl->posicao_livre - 1;

    //Percorrimento da SkipList em busca do elemento
    while((aux->prox[pos] != NULL && N > aux->prox[pos]->valor)||(pos > 0)){
        /* Caso a pr�x posi��o apontada seja nula ou o elemento seja menor do que o guardado pelo n�
         * apontado, a fun��o desce um n�vel
         */
        if((aux->prox[pos] == NULL) || (N < aux->prox[pos]->valor)){
            pos = pos - 1;
        }else{
            //Caso o aux na posi��o pos esteja apontando para o elemento a ser removido:
            if(aux->prox[pos]->valor == N){
                //� colocado um ponteiro para o n� que cont�m o elemento a ser removido
                aux2 = aux->prox[pos];

                    /* Ajuste dos ponteiros do aux na pos atual passa a apontar para o que o no a ser removido
                     * est� apontando
                     */
                    if(pos < aux2->altura){
                        aux->prox[pos] = aux2->prox[pos];

                        /* Caso o ponteiro do n� a ser removido esteja apontado para nulo
                         * a posi��o livre deve ser atualizada
                         */
                        if(aux->prox[pos] == NULL)
                            aux->posicao_livre = pos;

                        //desce um n�vel
                        pos = pos - 1;
                    }

            }else aux = aux->prox[pos]; //avan�o para o pr�ximo n�
        }
    }
    //Encontrou o valor, falta apenas uma ultima posi��o para corrigir o ponteiro (pos = 0)
    if(aux->prox[pos] != NULL && aux->prox[pos]->valor == N){
        //corrigindo o ultimo no na pos = 0
        aux->prox[pos] = aux2->prox[pos];

        //Liberando o vetor de ponteiros do n�
        free(aux2->prox);

        //liberando o n�
        free(aux2);

        //decrementando o tamanho da SkipList
        sl->valor--;

        return 1;
    }else{
        //Se o aux parou e o n� seguinte n�o � o n� que foi removido
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
        /* Caso a pr�x posi��o apontada seja nula ou o elemento seja menor do que o guardado pelo n�
         * apontado, a fun��o desce um n�vel
         */
        if((aux->prox[pos] == NULL) || (N < aux->prox[pos]->valor)){
            pos = pos - 1;
        }else{
            //Imprimo o n�vel visitado durante a busca
            if(aux!=NULL) printf("Visitei: %i no indice %i\n",aux->prox[pos]->valor,pos);
            //Se achou o valor, a busca acaba
            if(aux->prox[pos]->valor == N)
                return 1;
            //Caso n�o tenha achado, avan�a para o pr�ximo n�
            aux = aux->prox[pos];
        }

    }
    /* Se o aux parou no valor onde o elemento est� presente e
     * o elemento nao � o valor na cabe�a da SkipList (guardamos o tamanho da lista na cabe�a, se N == tamanho,
     * tamanho nao pode ser considerado como um elemento da skiplist na busca), por isso aux deve ser diferente de sl
     */
    if(aux->valor == N && aux != sl)
        return 1;
    else
        return 0;
}
int tamanhoSkipList(SkipList* sl){
    //se a lista n�o for alocada, -1 simboliza o erro p/ teste no main
    if(sl==NULL){
        return -1;
    }
    //A cabe�a da SkipList cont�m a quantidade de elementos no campo valor
    return sl->valor;
}
int vaziaSkipList(SkipList* sl){
    //se a lista n�o for alocada, -1 simboliza o erro p/ teste no main
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
