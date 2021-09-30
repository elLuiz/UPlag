#include <stdlib.h>
#include <stdio.h>
#include "Skiplist.h"
#include <limits.h>

#define SKIPLIST_MAXIMO 6

skiplist *criaSkipList() {
    skiplist *list=(skiplist*)malloc(sizeof(skiplist));
    int i;
    node *header = (node *) malloc(sizeof(struct node));
    list->header = header;
    header->chave = INT_MAX;
    header->next = (node **) malloc(
            sizeof(node*) * (SKIPLIST_MAXIMO + 1));
    for (i = 0; i <= SKIPLIST_MAXIMO; i++) {
        header->next[i] = list->header;
    }

    list->level = 1;
    list->size = 0;

    return list;
}

void liberaSkipList(skiplist *sl)
{
    if(!sl)
        return;

    node *q=sl->header;
	node *next;
	while(q)
    {
		next=q->next[0];
		free(q);
		q=next;
    }
    free(sl);
}

int rand_level() {
    int level = 1;
    while (rand() < RAND_MAX / 2 && level < SKIPLIST_MAXIMO)
        level++;
    return level;
}

int insereSkipList(skiplist *list, int chave, int info) {
    node *update[SKIPLIST_MAXIMO + 1];
    node *x = list->header;
    int i, level;
    for (i = list->level; i >= 1; i--) {
        while (x->next[i]->chave < chave)
            x = x->next[i];
        update[i] = x;
    }
    x = x->next[1];

    if (chave == x->chave) {
        x->info = info;
        return 0;
    } else {
        level = rand_level();
        if (level > list->level) {
            for (i = list->level + 1; i <= level; i++) {
                update[i] = list->header;
            }
            list->level = level;
        }

        x = (node *) malloc(sizeof(node));
        x->chave = chave;
        x->info = info;
        x->next = (node **) malloc(sizeof(node*) * (level + 1));
        for (i = 1; i <= level; i++) {
            x->next[i] = update[i]->next[i];
            update[i]->next[i] = x;
        }
    }
    return 0;
}

node *buscaSkipList(skiplist *list, int chave) {
    node *x = list->header;
    int i;
    for (i = list->level; i >= 1; i--) {
        while (x->next[i]->chave < chave)
            x = x->next[i];
    }
    if (x->next[1]->chave == chave) {
        return x->next[1];
    } else {
        return NULL;
    }
    return NULL;
}

void liberaNoSkipList(node *x) {
    if (x) {
        free(x->next);
        free(x);
    }
}

int removeSkipList(skiplist *list, int chave) {
    int i;
    node *update[SKIPLIST_MAXIMO + 1];
    node *x = list->header;
    for (i = list->level; i >= 1; i--) {
        while (x->next[i]->chave < chave)
            x = x->next[i];
        update[i] = x;
    }

    x = x->next[1];
    if (x->chave == chave) {
        for (i = 1; i <= list->level; i++) {
            if (update[i]->next[i] != x)
                break;
            update[i]->next[1] = x->next[i];
        }
        liberaNoSkipList(x);

        while (list->level > 1 && list->header->next[list->level]
                == list->header)
            list->level--;
        return 0;
    }
    return 1;
}

void imprimeSkipList(skiplist *list){
    node *x = list->header;
    while (x && x->next[1] != list->header) {
        printf("%d[%d]->", x->next[1]->chave, x->next[1]->info);
        x = x->next[1];
    }
    printf("NULL\n");
}



