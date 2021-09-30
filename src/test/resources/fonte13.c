

#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <time.h>
#include "skiplist.h"
#define SKIPLIST_NIVEL_MAXIMO 6

struct no{
	int valor;
	struct no *prox;
	struct no *desce;
};

struct lista{
	struct no **vetor;
};

SkipList criaSkipList(){
// retorna vetor de ponteiros de tamanho SKIPLIST_NIVEL_MAXIMO, retorna NULL se falhar
	int j=0, i;
	No aux;
	SkipList Vetorzin;

	Vetorzin = (SkipList) malloc(sizeof(SkipList));
	// cria vetor de ponteiros do tipo Lista (alocando todos os niveis de 1 vez)
	Vetorzin->vetor = (No*) malloc(SKIPLIST_NIVEL_MAXIMO * sizeof(No));

	for(i=0; i<SKIPLIST_NIVEL_MAXIMO; i++){
        //  aloca um nó e coloca em cada posição (níveis) do vetor
		Vetorzin->vetor[i] = (No) malloc(sizeof(No));

		// verificando se o nó foi alocado
		if(Vetorzin->vetor[i]!=NULL){
            // ajustando os cabeçalhos (INT_MIN = - infinito)
			Vetorzin->vetor[i]->valor = INT_MIN;

			// ajustando o campo ->desce do nível 0
            if(i==0)
                Vetorzin->vetor[i]->desce = NULL;
            else
                Vetorzin->vetor[i]->desce=aux;

			Vetorzin->vetor[i]->prox = NULL;

			// ajustando o campo ->desce dos demais níveis
            aux = Vetorzin->vetor[i];

            // se chegou no final do vetor de listas, retorna a lista
			if(j==SKIPLIST_NIVEL_MAXIMO-1)
                return Vetorzin;

			j++;
		}
	}
	return NULL;
}

void liberaSkipList(SkipList Vetorzin){

     if(vaziaSkipList(Vetorzin)){
        printf("ERRO tamanho lista: 0");
        return;
     }

     int nivel_comeco = SKIPLIST_NIVEL_MAXIMO-1;
     // acha primeiro nivel com elementos começando por cima
     while(1){
        if(Vetorzin->vetor[nivel_comeco]->prox==NULL)
            nivel_comeco--;
        else
            break;
     }

     // loop que percorre os niveis
     while(1){
        // duas variaveis auxiliares para percorrer o nivel
        No aux=Vetorzin->vetor[nivel_comeco]->prox;
        No aux2=Vetorzin->vetor[nivel_comeco]->prox->prox;

        // loop que libera os elementos do nivel
        while(aux2!=NULL){
            free(aux);
            aux=aux2;
            aux2=aux2->prox;
        }

        // remove ultimo elem do nivel
        free(aux);
        nivel_comeco--;

        if(nivel_comeco==-1){
            break;
        }
     }

     int i;

     // libera nós com menos infinito dentro
     for(i=0;i<SKIPLIST_NIVEL_MAXIMO;i++){
        free(Vetorzin->vetor[i]);
     }

     // libera ponteiro da skiplist
     free(Vetorzin);
     return;
}

int insereSkipList(SkipList Vetorzin, int x){

    int nivel;
    No aux2;
    No aux;

    for(nivel=0; nivel<SKIPLIST_NIVEL_MAXIMO; nivel++){
        // aloca espaço para novo no
        No novo = (No) malloc(sizeof(struct no));

        // se novo no não foi criado, falha
        if(novo==NULL)
            return 0;

        if(nivel==0)
            novo->desce=NULL;
        novo->prox=NULL;
        novo->valor=x;

        // salva ultimo endereço do ultimo elem inserido
        if(nivel>0){
            aux2 = aux->prox;
        }
        aux = Vetorzin->vetor[nivel];

        // encontrando a posição na lista
        while(aux->prox!=NULL&&aux->prox->valor<x)
            aux=aux->prox;

        // parou pq a lista chegou no fim
        if(aux->prox==NULL){
            aux->prox=novo;

            //  se nivel eh maior que 0 entao ele teve que inserir acima
            if(nivel>0)
                aux->prox->desce=aux2;
            // decide se vai pro andar de cima
            if(num_aleatorio())
               continue;
            else
                return 1;
        } else {
            // inserindo no meio de dois elementos
            novo->prox=aux->prox;
            aux->prox=novo;

            if(nivel>0)
                aux->prox->desce=aux2;

            // decide se vai pro proximo nivel
            if(num_aleatorio())
                continue;
            else
                return 1;
        }
    }
    return 1;
}

int removeSkipList(SkipList Vetorzin, int x){
    int nivel = SKIPLIST_NIVEL_MAXIMO-1;

    // Se lista estiver vazia, remoção falha
    if(vaziaSkipList(Vetorzin))
        return 0;

    // encontra o primeiro nível com elementos
    while(Vetorzin->vetor[nivel]->prox==NULL){
        nivel--;
    }

    No aux = Vetorzin->vetor[nivel];
    // do primeiro nível com elementos, verifica se possui o elemento
    while(aux->prox->valor < x){
        aux = aux->prox;
        if(aux->prox==NULL){
            nivel--;
            aux = Vetorzin->vetor[nivel];
        }
    }

    // variavel auxiliar para percorrer skiplist
    No aux2;

    // percorre os niveis para remover o elemento em todos
    while(nivel >= 0){
        aux = Vetorzin->vetor[nivel];

        // procura o elemento na lista
        while(aux->prox != NULL && aux->prox->valor < x){
            aux = aux->prox;
        }
        // encontrou o elemento, arruma ponteiros e libera no
        if(aux->prox->valor==x){
           aux2 = aux->prox;
           aux->prox = aux2->prox;
           free(aux2);
        }

        nivel--;
    }

    return 1;
}

int buscaSkipList(SkipList Vetorzin, int x){
// retorna 1 para elemento encontrado e 0 para falha
	int nivel_comeco = SKIPLIST_NIVEL_MAXIMO-1;

	if(vaziaSkipList(Vetorzin))
        return 0;

    // encontra o primeiro nivel com elementos para começar a busca
	while(1){
		if(Vetorzin->vetor[nivel_comeco]->prox==NULL)
            nivel_comeco--;
		else
            break;
    }
    while(1){
        if(Vetorzin->vetor[nivel_comeco]->prox->valor>x && x>0)
            nivel_comeco--;
        else
            break;
    }

    // variavel auxiliar para percorrer a skiplist
    No aux = Vetorzin->vetor[nivel_comeco];

    while(1){
        // se valor do no atual for menor que o valor procurado, vai para proximo no
        if(aux->prox!=NULL && x>=aux->prox->valor){
            aux=aux->prox;
            continue;
        }

        // se no atual não for o ultimo da lista
        if(aux->prox!=NULL){
             // se o valor do no atual for igual ao valor procurado, retorna 1 (valor encontrado)
             if(aux->valor==x){
                return 1;
            }else{
                // desce para proximo nivel
                if(aux->desce!=NULL){
                    aux=aux->desce;
                    continue;
                }else
                    return 0;
            }
              // se no atual for o ultimo da lista
        }else if(aux->prox==NULL && aux->desce!=NULL){
            // numero procurado nao encontrado, desce
            if(aux->valor!=x)
                aux=aux->desce;
                // numero procurado encontrado, retorna 1
            else if(aux->valor==x)
                return 1;
            continue;
        }else
            return 0;
    }
}

int tamanhoSkipList(SkipList Vetorzin){
// retorno a quantidade de nos da lista, incluindo todos os niveis
	if(vaziaSkipList(Vetorzin)){
		//printf("Tamanho lista: 0");
		return 0;
	}

	int tamanho_max = 0;
	int tamanho_por_nivel = 0;
	int nivel_comeco = SKIPLIST_NIVEL_MAXIMO-1;

    // encontra o primeiro nivel com elementos
	while(1){
		if(Vetorzin->vetor[nivel_comeco]->prox==NULL)
            nivel_comeco--;
		else break;
	}

	while(1){
		No aux = Vetorzin->vetor[nivel_comeco]->prox;

		while(aux->prox!=NULL){
			tamanho_por_nivel++;
			aux = aux->prox;
		}

		tamanho_por_nivel++;
		//printf("Tamanho do nivel[%d]: %d\n", nivel_comeco, tamanho_por_nivel);

		tamanho_max = tamanho_max+tamanho_por_nivel;
		tamanho_por_nivel = 0;
		nivel_comeco--;

		if(nivel_comeco==-1){
			//printf("Tamanho lista total: %d", tamanho_max);
			return tamanho_max;
		}
		continue;
	}
}

int vaziaSkipList(SkipList Vetorzin){
// retorna 1 se lista vazia e 0 para lista nao vazia
	if(Vetorzin->vetor[0]->prox == NULL||Vetorzin==NULL)
        return 1;
	else
        return 0;
}

void imprimeSkipList(SkipList Vetorzin){
	if(vaziaSkipList(Vetorzin)){
		printf("{}\n");
	}

    int i;
    No aux;

    for(i=SKIPLIST_NIVEL_MAXIMO-2;i>=0;i--){

        if(Vetorzin->vetor[i]->prox==NULL){
            printf("{}\n");
        }

        aux = Vetorzin->vetor[i]->prox;

		printf("{");
		// loop para imprimir o nivel
		while(aux->prox!=NULL){
			printf("%d, ", aux->valor);
			aux = aux->prox;
		}
		// perfumaria para imprimir o ultimo elem do nivel
		printf("%d}\n", aux->valor);
    }
}

int num_aleatorio(){
// retorna um número aleatótio (0 ou 1)
	int x = rand()%2;
	return x;
}
