

#include<stdio.h>
#include<stdlib.h>
#include<math.h>
#include "SkipList.h"

// Recebe uma skilist e o valor que deseja-se saber se pertence a ela.
// Retorna 0 caso ela nao tenha sido alocada ou esteja vazia.
// Retorna 1 caso o elemento esteja na lista.
int buscaSkipList(skiplist *sk, int chave){

    // Verifica se a lista foi alocada e se está vazia.
	if(vaziaSkipList(sk)){
        printf("Lista nao alocada ou vazia.\n");
		return 0;
	}

    // Aponta para o nó mais acima do cabeçalho.
    no *x = sk->inicio->prox;

    // Enquanto o nó for diferente de nulo percorrer a lista.
    while(x != NULL){

        // Se a chave for maior que o valor do nó atual, ir para o proximo nó.
        while(x->prox != NULL && x->prox->info < chave)
            x = x->prox;

        // Verifica se o valor do nó atual é igual ao valor da chave e retorna 1 caso seja.
        if(x->prox != NULL && x->prox->info == chave)
            return 1;

        // Caso não satisfaça nenhuma das condições acima, vai para o nó abaixo e repete o processo.
        x = x->abaixo;
    }
    return 0;
}

// Cria a skiplist, alocando-a e alocando os nós dela.
// Retorna a skiplist ou retorna nulo, caso não tenha
// sido possível alocar memória para ela ou para os nós.
skiplist *criaSkipList(){

    // Faz a alocação da skiplist e dos nós.
    skiplist *sk = (skiplist*) malloc(sizeof(skiplist));
    no *cabecalhoPrimeiroNivel = (no*) malloc(sizeof(no));
    no *novo = (no*) malloc(sizeof(no));

    // Verifica se a alocação ocorreu com sucesso.
    // Retorna nulo caso não tenha sido.
    if(cabecalhoPrimeiroNivel == NULL || sk == NULL || novo == NULL){
        printf("Ocorreu algum erro de alocacao.\n");
        return NULL;
    }

    // Seta os valores iniciais para uma skiplist vazia e para seus respectivos nós.
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

// Recebe uma SkipList e imprime os elementos alocados nos nós mais abaixo da lista.
void imprimeSkipList(skiplist *sk) {

    // Verifica se a lista está vazia.
    if(vaziaSkipList(sk)){
        printf("Lista vazia ou nao alocada!\n");
        return;
    }

    // Aponta para o nó mais acima do cabeçalho.
    no *aux = sk->inicio->prox;

    // Desce até o nó mais abaixo do cabeçalho e
    // aponta para a primeira posição da lista.
    while(aux->abaixo != NULL)
        aux = aux->abaixo;
    aux = aux->prox;

    // Imprime cada elemento até chegar no último.
    while(aux != NULL){
        printf("%d", aux->info);
        if(aux->prox != NULL){
            printf(" -> ");
        }
        aux = aux->prox;
    }

    printf("\n");
}

// Função auxiliar à insereSkipList, pois se um nó não pode ser alocado,
// os nos que foram alocados durante a execução dessa função naquele
// momento devem ser liberados
void reseta(no **novo,skiplist *sk){
    int i = 0;

    while(i < sk->level-1){
        if(novo[i]!= NULL)
            free(novo);

        i++;
    }
}

// Recebe uma skiplist e um número e o insere na lista.
// Retorna 1 caso a inserção tenha funcionado.
// Retorna 0 caso algo tenha dado errado.
int insereSkipList(skiplist *sk, int numero){
    if(sk == NULL){
        printf("Lista nao alocada.\n");
        return 0;
    }

    int elementos = sk->inicio->info; // Pega a quantidade de elementos
    int tamanho = sk->level;
    int i;

    no *salvaAnt[tamanho]; // Vetor para armazenar a posição anterior de onde o novo no será colcoado
    no *salvaNovo[tamanho]; // Vetor que salva a posição da memoria dos novos nós
    no *aux3 = NULL; // Nó auxiliar para corrigir o abaixo dos novos nós

    // Setar vetor em null para posterior verificação
    for(i = 0; i < tamanho+1; i++){
        salvaAnt[i] = NULL;
        salvaNovo[i] = NULL;
    }

    // Verifica necessidade de criar uma nova camada
    if((elementos+1) % ((int)pow(2, sk->level)) == 0){

        // Aloca o cabeçalho e um nó para adicionar na camada
        no *novo1 = (no *) malloc(sizeof(no));
        if(novo1 == NULL)
            return 0;

        // Configura o cabeçalho
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

    // Cria todos os novos nós
    while(andarNos != NULL){
        int pot = (int) pow(2, contador);

        // Verifica se há necessidade de adicionar o nó na camada em que o andarNos está
        if((elementos+1) % pot == 0){

            // Aloca novo nó
            no *novo = (no *) malloc(sizeof(no));
            if(novo == NULL){
                reseta(salvaNovo, sk);
                return 0;
            }

            // Coloca o número a ser inserido no novo nó
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

        // Anda na horizontal para achar a posição
        while(andarNos->prox != NULL && andarNos->prox->info < numero){
            andarNos = andarNos->prox;
        }

        // Verifica se o elemento já existe
        if(andarNos->prox != NULL && andarNos->prox->info == numero){
            reseta(salvaNovo, sk);
            return 0;
        }

        // Armazena a posição anterior que os novos nós irão ficar
        if(salvaNovo[contador]!= NULL){
            salvaAnt[contador] = andarNos;
        }

        // Anda na vertical
        andarNos = andarNos->abaixo;
        contador --;
    }

    // Arruma o abaixo e os proxs dos novos nós que estão no vetor salvarNovo
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

// Recebe uma skiplist e libera a memória alocada para ela.
void liberaSkipList(skiplist *sk){
    if(sk != NULL){
        no *aux = sk->inicio->prox;
        no *auxLiberar;

        while(aux != NULL){
            auxLiberar = aux->prox;

            // Anda pro lado liberando os nós
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

        // Libera o aux e o ponteiro que apontava pro cabeçalho
        aux = sk->inicio;
        free(aux);
        free(sk);
        sk = NULL; // Coloca null para corrigir possiveis erros quando for checar se está vazia.
    }
}

// Recebe uma skiplist e um valor. Remove o valor e retorna 1 caso tenha removido, retorna 0 caso não tenha removido.
int removeSkipList(skiplist *sk, int valor){
    if(vaziaSkipList(sk)){
        printf("Lista nao alocada ou vazia.\n");
        return 0;
    }

    no *andar = sk->inicio->prox;
    int removeu = 0; // Flag para ver se já removeu

    // Percorrer a skiplist
    while(andar != NULL){

        // Percorre até achar o elemento ou um elemento maior
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

// Recebe uma skiplist e retorna o tamanho dela. Retorna 0 caso ela não tenha sido alocada.
int tamanhoSkipList(skiplist *sk){
    if(sk == NULL)
        return 0;

    return sk->inicio->info;
}

// Recebe uma skiplist e verifica se está vazia. Retorna 1 caso esteja. Retorna 0 caso não esteja vazia.
int vaziaSkipList(skiplist *sk){
    if(sk == NULL || sk->inicio->info == 0)
        return 1;

    return 0;
}
