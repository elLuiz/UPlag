#include <stdio.h>
#include <stdlib.h>
#include <SkipList.h>
#include <limits.h>
#include "SkipList.h"
#define alturamax 6



struct no {
    int key;
    int info;
    struct NO **prox;
};

struct skiplist {
    int altura;
    struct NO *novo;
};

SkipList* criaSkipList(SkipList *ls) {
    int i;
    NO *novo = (NO*) malloc(sizeof(struct NO));
    ls->novo = novo;
    novo->key = INT_MAX;
    novo->prox = (NO**) malloc(sizeof(NO)*(alturamax + 1));
    for (i = 0; i <= alturamax; i++) {
        novo->prox[i] = ls->novo;
    }
    ls->altura = 1;
    return list;
}

static int rand_altura() {
    int altura = 1;
    while (rand() < RAND_MAX / 2 && altura < alturamax)
        altura++;
    return altura;
}

int insereSkipList(SkipList *ls, int key, int info) {
    NO *update[alturamax + 1];
    NO *x = ls->novo;
    int i, altura;
    for (i = ls->altura; i >= 1; i--) {
        while (x->prox[i]->key < key)
            x = x->prox[i];
            update[i] = x;
    }
    x = x->prox[1];

    if (key == x->key) {
        x->info = info;
        return 0;
    } else {
        altura = rand_altura();
        if (altura > ls->altura) {
            for (i = ls->altura + 1; i <= altura; i++) {
                update[i] = ls->novo;
            }
            ls->altura = altura;
        }

        x = (NO*) malloc(sizeof(NO));
        x->key = key;
        x->info = info;
        x->prox = (NO**) malloc(sizeof(NO)*(altura + 1));
        for (i = 1; i <= altura; i++) {
            x->prox[i] = update[i]->prox[i];
            update[i]->prox[i] = x;
        }
    }
    return 0;
}

int *buscaSkipList(SkipList *ls, int key) {
    NO *x = ls->novo;
    int i;
    for (i = ls->altura; i >= 1; i--) {
        while (x->prox[i]->key < key)
            x = x->prox[i];
    }
    if (x->prox[1]->key == key) {
        return 1; // busca obteve sucesso
    } else {
        return 0; // busca falhou
    }

}

static void limpa_no(NO *x) {
    if (x) {
        free(x->prox);
        free(x);
    }
}

int removeSkipList(SkipList *ls, int key) {
    int i;
    NO *update[alturamax + 1];
    NO *x = ls->novo;
    for (i = ls->altura; i >= 1; i--) {
        while (x->prox[i]->key < key)
            x = x->prox[i];
        update[i] = x;
    }

    x = x->prox[1];
    if (x->key == key) {
        for (i = 1; i <= ls->altura; i++) {
            if (update[i]->prox[i] != x)
                break;
            update[i]->prox[i] = x->prox[i];
        }
        limpa_no(x);

        while (ls->altura > 1 && ls->novo->prox[ls->altura] == ls->novo)
            ls->altura--;
        return 0;
    }
    return 1;
}

void liberaSkipList(SkipList *ls){
    NO *atual = ls->novo->prox[1];
    while(atual != ls->novo) {
        NO *prox_no = atual->prox[1];
        free(atual->prox);
        free(atual);
        atual = prox_no;
    }
    free(atual->prox);
    free(atual);
    free(ls);
}

static void imprimeSkipList(SkipList *ls) {
    NO *x = ls->novo;
    while (x && x->prox[1] != ls->novo) {
        printf("%d[%d]->", x->prox[1]->key, x->prox[1]->info);
        x = x->prox[1];
    }
    printf("NIL\n");
}

int tamanhoSkipList(SkipList *ls) {
    NO *x = ls->novo;
    int cont = 0;
    while (x && x->prox[1] != ls->novo) {
        cont++;
        x = x->prox[1];
    }
    return cont;
}
