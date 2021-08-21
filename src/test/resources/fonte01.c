/*
Grupo GVY
Integrantes:
Gabriel Mendes de Souza Santiago - 11621BCC015
Vin�cius Guardieiro Sousa - 11811BCC008
Yan Lucas Damasceno Dias - 11621BCC029
*/

#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include "SkipList.h"

// Recebe uma skilist e o valor que deseja-se saber se pertence a ela.
// Retorna 0 caso ela nao tenha sido alocada ou esteja vazia.
// Retorna 1 caso o elemento esteja na lista.
int buscaSkipList(skiplist *sk, int chave){

    // Verifica se a lista foi alocada e se est� vazia.
	if(vaziaSkipList(sk)){
        printf("Lista nao alocada ou vazia.\n");
		return 0;
	}

    // Aponta para o n� mais acima do cabe�alho.
    no *x = sk->inicio->prox;

    // Enquanto o n� for diferente de nulo percorrer a lista.
    while(x != NULL){

        // Se a chave for maior que o valor do n� atual, ir para o proximo n�.
        while(x->prox != NULL && x->prox->info < chave)
            x = x->prox;

        // Verifica se o valor do n� atual � igual ao valor da chave e retorna 1 caso seja.
        if(x->prox != NULL && x->prox->info == chave)
            return 1;

        // Caso n�o satisfa�a nenhuma das condi��es acima, vai para o n� abaixo e repete o processo.
        x = x->abaixo;
    }
    return 0;
}

// Cria a skiplist, alocando-a e alocando os n�s dela.
// Retorna a skiplist ou retorna nulo, caso n�o tenha
// sido poss�vel alocar mem�ria para ela ou para os n�s.
skiplist *criaSkipList(){

    // Faz a aloca��o da skiplist e dos n�s.
    skiplist *sk = (skiplist*) malloc(sizeof(skiplist));
    no *cabecalhoPrimeiroNivel = (no*) malloc(sizeof(no));
    no *novo = (no*) malloc(sizeof(no));

    // Verifica se a aloca��o ocorreu com sucesso.
    // Retorna nulo caso n�o tenha sido.
    if(cabecalhoPrimeiroNivel == NULL || sk == NULL || novo == NULL){
        printf("Ocorreu algum erro de alocacao.\n");
        return NULL;
    }

    // Seta os valores iniciais para uma skiplist vazia e para seus respectivos n�s.
    sk->level = 1;
    sk->inicio = novo;

    novo->info = 0;
    novo->abaixo = NULL;
    novo->prox = cabecalhoPrimeiroNivel;

    cabecalhoPrimeiroNivel->info = 0;
    cabecalhoPrimeiroNivel->prox = NULL;
    cabecalhoPrimeiroNivel->abaixo = NULL;

    return sk;
}

// Recebe uma SkipList e imprime os elementos alocados nos n�s mais abaixo da lista.
void imprimeSkipList(skiplist *sk) {

    // Verifica se a lista est� vazia.
    if(vaziaSkipList(sk)){
        printf("Lista vazia ou nao alocada!\n");
        return;
    }

    // Aponta para o n� mais acima do cabe�alho.
    no *aux = sk->inicio->prox;

    // Desce at� o n� mais abaixo do cabe�alho e
    // aponta para a primeira posi��o da lista.
    while(aux->abaixo != NULL)
        aux = aux->abaixo;
    aux = aux->prox;

    // Imprime cada elemento at� chegar no �ltimo.
    while(aux != NULL){
        printf("%d", aux->info);
        if(aux->prox != NULL){
            printf(" -> ");
        }
        aux = aux->prox;
    }

    printf("\n");
}

// Fun��o auxiliar � insereSkipList, pois se um n� n�o pode ser alocado,
// os nos que foram alocados durante a execu��o dessa fun��o naquele
// momento devem ser liberados
void reseta(no **novo,skiplist *sk){
    int i = 0;

    while(i < sk->level-1){
        if(novo[i]!= NULL)
            free(novo);

        i++;
    }
}

// Recebe uma skiplist e um n�mero e o insere na lista.
// Retorna 1 caso a inser��o tenha funcionado.
// Retorna 0 caso algo tenha dado errado.
int insereSkipList(skiplist *sk, int numero){
    if(sk == NULL){
        printf("Lista nao alocada.\n");
        return 0;
    }

    int elementos = sk->inicio->info; // Pega a quantidade de elementos
    int tamanho = sk->level;
    int i;

    no *salvaAnt[tamanho]; // Vetor para armazenar a posi��o anterior de onde o novo no ser� colcoado
    no *salvaNovo[tamanho]; // Vetor que salva a posi��o da memoria dos novos n�s
    no *aux3 = NULL; // N� auxiliar para corrigir o abaixo dos novos n�s

    // Setar vetor em null para posterior verifica��o
    for(i = 0; i < tamanho+1; i++){
        salvaAnt[i] = NULL;
        salvaNovo[i] = NULL;
    }

    // Verifica necessidade de criar uma nova camada
    if((elementos+1) % ((int)pow(2, sk->level)) == 0){

        // Aloca o cabe�alho e um n� para adicionar na camada
        no *novo1 = (no *) malloc(sizeof(no));
        if(novo1 == NULL)
            return 0;

        // Configura o cabe�alho
        novo1->info = sk->level;
        novo1->abaixo = sk->inicio->prox;
        novo1->prox = NULL;

        // Corrige o sk->inicio->prox para apontar para a nova camada
        sk->inicio->prox = novo1;

        // Aumenta a quantidade de nivel
        sk->level++;
    }
    int contador = sk->level-1;
    no *andarNos = sk->inicio->prox; // Aponta cabecalho

    // Cria todos os novos n�s
    while(andarNos != NULL){
        int pot = (int) pow(2, contador);

        // Verifica se h� necessidade de adicionar o n� na camada em que o andarNos est�
        if((elementos+1) % pot == 0){

            // Aloca novo n�
            no *novo = (no *) malloc(sizeof(no));
            if(novo == NULL){
                reseta(salvaNovo, sk);
                return 0;
            }

            // Coloca o n�mero a ser inserido no novo n�
            novo->info = numero;
            salvaNovo[contador] = novo;
        }
        andarNos = andarNos->abaixo;
        contador --;
    }
    andarNos = sk->inicio->prox;
    contador = sk->level-1;

    // Anda na skiplist
    while(andarNos != NULL){

        // Anda na horizontal para achar a posi��o
        while(andarNos->prox != NULL && andarNos->prox->info < numero){
            andarNos = andarNos->prox;
        }

        // Verifica se o elemento j� existe
        if(andarNos->prox != NULL && andarNos->prox->info == numero){
            reseta(salvaNovo, sk);
            return 0;
        }

        // Armazena a posi��o anterior que os novos n�s ir�o ficar
        if(salvaNovo[contador]!= NULL){
            salvaAnt[contador] = andarNos;
        }

        // Anda na vertical
        andarNos = andarNos->abaixo;
        contador --;
    }

    // Arruma o abaixo e os proxs dos novos n�s que est�o no vetor salvarNovo
    for(i = 0; i <= sk->level-1; i++){
        if(salvaNovo[i] == NULL)
            break;
        salvaNovo[i]->abaixo = aux3;
        salvaNovo[i]->prox = salvaAnt[i]->prox;
        salvaAnt[i]->prox = salvaNovo[i];
        aux3 = salvaNovo[i];
    }

    // Aumenta a quantidade de elementos
    sk->inicio->info++;

    return 1;
}

// Recebe uma skiplist e libera a mem�ria alocada para ela.
void liberaSkipList(skiplist *sk){
    if(sk != NULL){
        no *aux = sk->inicio->prox;
        no *auxLiberar;

        while(aux != NULL){
            auxLiberar = aux->prox;

            // Anda pro lado liberando os n�s
            while(auxLiberar != NULL){
                no *aux2 = auxLiberar;
                auxLiberar = auxLiberar->prox;
                free(aux2);
            }

            // Apos liberar todos na mesma linha, anda pra baixo
            no *aux3 = aux;
            aux = aux->abaixo;
            free(aux3);
        }

        // Libera o aux e o ponteiro que apontava pro cabe�alho
        aux = sk->inicio;
        free(aux);
        free(sk);
        sk = NULL; // Coloca null para corrigir possiveis erros quando for checar se est� vazia.
    }
}

// Recebe uma skiplist e um valor. Remove o valor e retorna 1 caso tenha removido, retorna 0 caso n�o tenha removido.
int removeSkipList(skiplist *sk, int valor){
    if(vaziaSkipList(sk)){
        printf("Lista nao alocada ou vazia.\n");
        return 0;
    }

    no *andar = sk->inicio->prox;
    int removeu = 0; // Flag para ver se j� removeu

    // Percorrer a skiplist
    while(andar != NULL){

        // Percorre at� achar o elemento ou um elemento maior
        while(andar->prox != NULL && andar->prox->info < valor){
            andar = andar->prox;
        }

        // Se achou o elemento, corrige para onde o anterior aponta e libera o no daquele elemento
        if(andar->prox != NULL && andar->prox->info == valor){
            no *aux = andar->prox;
            andar->prox = aux->prox;
            free(aux);
            removeu = 1;
        }

        // Anda na vertical
        andar = andar->abaixo;
    }

    if(sk->inicio->prox->prox == NULL){
        no* aux = sk->inicio->prox;
        sk->inicio->prox = aux->abaixo;
        free(aux);
        sk->level--;
    }

    // Se removeu alguma vez, retorna 1
    if(removeu){
        sk->inicio->info--;
        return 1;
    }

    return 0;
}

// Recebe uma skiplist e retorna o tamanho dela. Retorna 0 caso ela n�o tenha sido alocada.
int tamanhoSkipList(skiplist *sk){
    if(sk == NULL)
        return 0;

    return sk->inicio->info;
    return sk == NULL ? 0 : sk->inicio->info;
}

// Recebe uma skiplist e verifica se est� vazia. Retorna 1 caso esteja. Retorna 0 caso n�o esteja vazia.
int vaziaSkipList(skiplist *sk){
    if(sk == NULL || sk->inicio->info == 0)
        return 1;

    return 0;
    return sk == NULL || sk->inicio->info == 0 ? 1 : 0;
}
