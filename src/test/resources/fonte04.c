
#include "Skip_List.h"
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <time.h>
#define nivel_max 4

struct nodulo{
    int valor;
    int nivel;
    struct nodulo **prox;
};

struct skip_list{
    int tam;
    int level;
    struct nodulo *cabeca;
};


//Função para gerar o level de um nódulo na inserção em uma SkipList.
//Basicamente é como jogar uma moeda e somar os sucessos(cara) até a ocorrência de um fracasso(coroa)
static int gerar_level(){
    int level = 1;

    while (rand() < RAND_MAX / 2 && level < nivel_max)
        level++;

    return level;
}

SkipList* criaSkipList(){
    int i;

    SkipList* skp;
    skp = (SkipList*)malloc(sizeof(SkipList)); //Alocando a SKip List

    No* cabecalho;
    cabecalho = (No*)malloc(sizeof(struct nodulo)); //Alocando um nódulo para servir de cabeçalho para a minha SkipList

    skp->cabeca = cabecalho;

    cabecalho->prox = (No**)malloc(sizeof(No*) * (nivel_max + 1)) ; //Este nódulo deve possuir um array de ponteiros que aponte para todas as camadas da SKipList

    for(i = 0; i < nivel_max; i++){
            cabecalho->prox[i] = NULL; //Inicializando todos os ponteiros do meu cabeçalho para NULL
        }

    skp->level = 0; //Setando o level da SkipList para 0 e seu tamanho para 0
    skp->tam = 0;

    return skp;
}

//Esta função checa o tamanho da SkipList para dizer se ela está vazia
int vaziaSkipList(SkipList* skp){
    if(skp->tam == 0)
        return 1;
    else
        return 0;
}


int insereSkipList(SkipList** skp, int chave)
{
    int x;
    int i;

    No* old[nivel_max]; //Este é meu nódulo de atualização
    No* N = (*skp)->cabeca; //Começo usando meu N como um nó auxiliar que aponta para meu nódulo cabeçalho da SkipList

    for(i = (*skp)->level; i >= 0; i--){
        while(N->prox[i] != NULL && chave > N->prox[i]->valor){
            N = N->prox[i];
        }
        old[i] = N;                           //Percorro minha SkipList e meu nódulo old assume a posição do nódulo que deve ter seu prox atualizado
    }

    x = gerar_level();


    if(x > (*skp)->level){
        for( i = (*skp)->level+1; i < x; i++)
            old[i] = (*skp)->cabeca;            //Caso o level do nódulo a ser inserido seja maior do que o level atual da SkipList
                                                //meu nódulo old assume as posições necessárias no nódulo cabeçalho (que está apontando para NULL)
        (*skp)->level = x;
    }


    N = (No*)malloc(sizeof(No)); //Aloco meu nódulo a ser inserido na SkipList e atualizo seu valor e nível
    N->valor = chave;
    N->nivel = x;

    N->prox = (No**)malloc(sizeof(No*) * (x + 1)); //Este nódulo deve ter um array de ponteiros também, para que possa apontar para seus
                                                   //seguintes nódulos em cada level da SkipList
    for(i = 0; i < x; i++){
        N->prox[i] = NULL;   //Ponteiros de N inicializados em NULL
    }


    for(i = x - 1; i >= 0; i--){
        N->prox[i] = old[i]->prox[i];       //Este loop atualiza a minha SKipList, fazendo com que o nódulo N seja inserido nela
        old[i]->prox[i] = N;
    }


    (*skp)->tam++;  //Tamanho da minha SkipList é incrementado em 1

    return 1;
}

int removeSkipList(SkipList** skp, int chave){
    //Esta primeira parte do meu código, até depois do laço For, tem mais ou menos o mesmo intuito do início da inserção, que é achar o nó a ser removido
    int i;
    No* old[nivel_max];
    No* N;

    if(vaziaSkipList(*skp) == 1)
        return 0;

    for(i = (*skp)->level; i >= 0; i--){
        while(N->prox[i] != NULL && chave != N->prox[i]->valor){
            N = N->prox[i];
        }
        old[i] = N;
    }

    N = N->prox[0]; //Aponto meu No auxiliar para A primeira camada para checar se encontrei meu no com a chave desejada

    if(N->valor == chave){
        for(i = 0; i < (*skp)->level; i++){
            if(old[i]->prox[i] != N){      //Caso eu chegue a um nivel onde meu no de atualizacao
                break;                           //nao aponte mais para o no que eu desejo remover, eu dou um break no loop
            }
            old[i]->prox[i] = N->prox[i];
        }
        free(N->prox);                     //Free no array de ponteiros e no nódulo em si
        free(N);

        while((*skp)->level > 0 && (*skp)->cabeca->prox[(*skp)->level - 1] == NULL){
            (*skp)->level--;                                                           //Caso existam leveis vazios na SkipList, seu level é decrementado
        }

        return 1;
    }

    return 0;
}

int buscaSkipList(SkipList* skp, int chave){
    No* N = skp->cabeca;

    if(vaziaSkipList(skp) == 1){
        printf("Skip List Vazia!!\n");
        return 0;
    }

    //Percorre a camada inicial [0] da minha SkipList a partir do meu nódulo cabeçalho e me diz se o elemento sendo procurado existe na minha SkipList
    while(N->prox[0] != NULL && N->prox[0]->valor < chave)
            N = N->prox[0];
    if(N->prox[0] == NULL){
        printf("O numero %d nao existe na Skip List\n", chave);
        return 0;
    }else if(N->prox[0]->valor == chave){
                printf("O numero %d existe na Skip List e ele possui %d niveis!\n", chave, N->prox[0]->nivel);
                return 1;
    }

}


void liberaSkipList(SkipList** skp){
    free((*skp)->cabeca->prox);
    free((*skp)->cabeca);           //Free na SkipList e seus ponteiros
    free(*skp);
    printf("Skip list liberada da memoria!\n");
}


void imprimeSkipList(SkipList* skp){
    int i;
    No* N = skp->cabeca;       //Nódulo auxiliar N recebe o cabeçalho da SkipList

    if(vaziaSkipList(skp) == 1){
        printf("Skip List Vazia!!");
    }

        //Percorre a skipList, de seu level mais acima até o level [0], imprimindo cada level
        for(i = skp->level - 1; i >= 0; i--){
        printf("Skip List[%d]: ", i);
        while(N->prox[i] != NULL){
            printf("%d -> ", N->prox[i]->valor);
            N = N->prox[i];
        }
        N = skp->cabeca;                //O nódulo auxiliar N recebe o cabeçalho para que possa imprimir o proximo level do começo
        printf("NULL\n");
        }
    printf("\n");
}
























// int insereSkipList(SkipList* skp, int chave){
//     No** N;
//     int x = gerar_level();
//     int i;

//     N = (No**)malloc(x * sizeof(No*));

//     (*N)->nivel = x;
//     (*N)->valor = chave;

//     if(vaziaSkipList(skp) == 1){
//         skp->cabeca = *N;
//         (*N)->prox[0] = NULL;
//         skp->tam++;

//         for(i = 1; i < x; i++){
//             (*N)->prox[i] = NULL;
//             skp->tam++; //incrementa o tamanho da skip list
//         }
//         return 1;
//     }

    // No N = *skp;

    // if(chave < (*skp)->cabeca->valor){
    //     N[0]->prox = (*skp)->cabeca;
    //     (*skp)->cabeca = N[0];
    //     (*skp)->tam++;

    //     for(i = 1; i < nivel; i++){
    //         N[i]->valor = chave;
    //        // N[i]->prox = ;
    //         (*skp)->tam++; //incrementa o tamanho da skip list
    //     }

    // }
//     return 0;
// }

// void leSkipList(SkipList skp){
//     if(vaziaSkipList (skp) == 1){
//         printf("Skip List vazia!!");
//     }

//     int i;
//     No *N;
//     N = (No*)malloc(nivel_max * sizeof(No));

//     N[0] = skp;

//     for(i = 0; N[i]->valor != '\0'; i++){
//         printf("Level [%d]: ", i+1);

//         while(N[i]->prox != NULL ){
//             printf("%d ", N[i]->valor);
//             N[i] = N[i]->prox;
//         }
//         printf("\n");
//     }
// }
