
#include "skiplist.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define MAXN 15




/*Grupo GLM
Integrantes:
Gabriel Fernandes Vieira - 11811BCC022
Lucas do Nascimento Macedo - 11811BCC044
Marcos Felipe Belisário Costa - 11811BCC020
*/

//Define a estrutura da skiplist
struct skiplist {
    int val, size, lvl;
    struct skiplist **vet;
};

//Criaremos a skiplist com um "nó-fantasma" para facilitar, ele terá a quantidade máxima de níveis
SkipList* criaSkipList(){
    SkipList* sl = (SkipList*)malloc(sizeof(SkipList));
    if(sl == NULL){
	   	return NULL;
	}
	//aloca a quantidade máxima de níveis ao "nó-fantasma"
    sl->vet = (SkipList**)malloc(MAXN * sizeof(SkipList*));
    if(sl->vet == NULL) return NULL;
    for (int i=0; i<MAXN; i++) sl->vet[i] = NULL;
    //O valor do nó fantasma deve ser irrelevante, por isso adotaremos -1
    sl->val = -1;
    //No campo "size" do nó fantasma será salvo o tamanho de toda a SkipList, assim conseguiremos esse tamanho em O(1)
    sl->size = 0;
    return sl;
}

//Recebe um ponteiro pra ponteiro para poder liberar e marcar como NULL o primeiro nó
void liberaSkipList(SkipList **sl){
	if(sl == NULL || (*sl) == NULL || (*sl)->vet == NULL) {
		return ;
	}
	liberaSkipList(&((*sl)->vet[0]));
	for (int i=0; i<(*sl)->lvl; i++){
		free((*sl)->vet[i]);
		(*sl)->vet[i] = NULL;
	}
	free((*sl)->vet);
	(*sl)->vet = NULL;
	free((*sl));
	(*sl) = NULL;
}


//Gera a quantidade de níveis do vértice a ser inserido aleatoriamente
int geraNivel(){
	int lvl = 1+rand()%MAXN;
	return lvl;
}

//"Constrói" o novo nó, o alocando e definindo o seu valor
SkipList* buildNode(int val){
    SkipList* newNode = (SkipList*)malloc(sizeof(SkipList));
    int lvl = geraNivel();
    newNode->val = val;
    newNode->lvl = val;
    printf("val: %d | lvl: %d\n", val, lvl);
    return newNode;
}

//Insere um novo nó na skiplist (desde que não já haja um nó com esse mesmo "val")
int insereSkipList(SkipList* sl, int val){
    SkipList *node = buildNode(val);
    if(node == NULL) return 0;
    int lvl = node->lvl;
	//Aloca a quantidade de níveis que esse novo nó terá
    node->vet = (SkipList**)malloc(lvl * sizeof(SkipList*));
    for (int i=0; i<lvl; i++) node->vet[i] = NULL;

    if(node == NULL || sl == NULL){
		return 0;
    }
    SkipList *cab = sl;
    //Insere o nó na posição correta em cada nível da SkipList
    for (int i = MAXN-1; i >= 0; i--){
        while(sl->vet[i] != NULL && sl->vet[i]->val <= val){
			sl = sl->vet[i];
        }
        if(sl->vet[i] != NULL && sl->vet[i]->val == val){
			free(node);
			return 0;
		}
       	if(lvl > i) {
       		node->vet[i] = sl->vet[i];
       		sl->vet[i] = node;
       	}
	}
	//Se chegar até aqui conseguimos inserir, então incrementamos no tamanho da skiplist (atualizando o campo "size" do "nó-fantasma")
    cab->size++;
    return 1;
}

//Função que remove um nó da skiplist (caso ele exista)
int removeSkipList(SkipList* sl, int val){
	if(sl == NULL){
		return 0;
	}
	SkipList *cab = sl;
	int flag = 0;
	for (int i=MAXN-1; i >= 0; i--){
		while(sl->vet[i] != NULL && sl->vet[i]->val < val) {
			sl = sl->vet[i];
		}
		if(sl->vet[i] != NULL && sl->vet[i]->val == val){
			//Marca que um nó foi excluído
			flag = 1;
			SkipList *aux = sl->vet[i];
			sl->vet[i] = aux->vet[i];
			//Só posso liberar quando chegar no último nível
			if(i == 0) free(aux);
		}
	}
	//Se a operação de remoção foi bem sucedida, isto é flag == 1, devo diminuir o tamanho da skiplist
	if(flag) cab->size--;
	return flag;
}

//Checa a existencia de determinado valor na skiplist
int buscaSkipList(SkipList* sl, int val){
	if(sl == NULL){
		return 0;
	}
	//Ando o máximo possível em uma autura da árvore até encontrar o nó desejado (então parar) ou algum nó maior do que ele (e então descer de nível)
	for (int i=MAXN-1; i >= 0; i--){
		while (sl->vet[i] != NULL && sl->vet[i]->val < val) {
			sl = sl->vet[i];
		}
		if(sl->vet[i] != NULL && sl->vet[i]->val == val)
			return 1;
	}
	return 0;
}

//Retorna o tamanho da skiplist (valor salvo no campo "size" do nó-fantasma da skiplist)
int tamanhoSkipList(SkipList *sl){
	if(sl == NULL) return 0;
	return sl->size;
}

//Se o tamanho da skiplist for igual a 0 retorna verdadeiro, pois a skiplist está vazia, caso contrario retorna falso
int vaziaSkipList(SkipList* sl){
	return (tamanhoSkipList(sl) == 0);
}

//imprime todos os valores presentes atualmente na skiplist
void imprimeSkipList(SkipList *sl){
	if(sl == NULL || sl->vet[0] == NULL){
		puts("SkipList vazia ou nao criada!");
		return ;
	}
	printf("%d", sl->vet[0]->val);
	sl = sl->vet[0];
	while (sl->vet[0] != NULL){
		printf(" -> %d", sl->vet[0]->val);
		sl = sl->vet[0];
	}
	puts("");
}
