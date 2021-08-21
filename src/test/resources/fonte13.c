/*
Grupo HXX
Integrantes:
Higor Raphael Faria e Sousa - 11811BCC014
*/
#include <stdlib.h>
#include <stdio.h>
#include "GrupoHXX.h"

SkipList* criaSkipList(){//cria uma coluna de 20 n�veis que apontam com o prox para NULL
    SkipList* p = malloc(sizeof(SkipList));//aloca o primeiro no
    if(p==NULL){//erro
        return p;
    }
    p->info=-2147483648;
    p->prox=NULL;
    SkipList* aux=p;
    for(int i=0;i<19;i++){//aloca os outros 19 nos
        SkipList* no = malloc(sizeof(SkipList));
        if(no==NULL){//erro -> libera todos os nos alocados anteriormente
            aux = p;
            for(int o=0;o<=i;o++){//percorre a coluna liberando os nos
                SkipList* aux2=aux;
                aux=aux2->baixo;
                free(aux2);
            }
            return no;//NULL
        }
        no->info=-2147483648;
        no->prox=NULL;
        aux->baixo = no;//liga o que se tem da coluna at� ent�o ao novo no
        aux = no;
    }
    aux->baixo= NULL;//ultimo no aponta com baixo para NULL
    return p;
}

int vaziaSkipList(SkipList* sl){
    if(sl==NULL){
        return -1;//lista n�o existe
    }
    int boolean = 1;//seta a lista como vazia
    SkipList* aux = sl;
    for(int i=0;i<20;i++){
        if(aux->prox!=NULL){
            boolean=0;//lista n�o � vazia
            return boolean;
        }
        aux=aux->baixo;
    }
    return boolean;
}

void liberaSkipList(SkipList** sl){
    if((*sl)==NULL){//lista n�o existe, nada � feito
    }else{
    SkipList* aux = (*sl);
    if(vaziaSkipList(*sl)==0){//lista n�o est� vazia, libera os n�s dos n�veis que n�o pertencem � coluna principal da skiplist
        while(aux!=NULL){ //percorre cada n�vel
            SkipList* aux2=aux->prox;
            while(aux2!=NULL){ //libera cada elemento do n�vel
                SkipList* aux3=aux2;
                aux2 = aux3->prox;
                free (aux3);
            }
            aux->prox=NULL;
            aux=aux->baixo;
        }
        aux=(*sl);
    }
    //lista est� vazia
    while(aux!=NULL){//libera a coluna dos n�veis
        SkipList* aux2=aux;
        aux=aux2->prox;
        free(aux2);
    }
    (*sl)=NULL;
    }
}

void imprimeSkipList(SkipList* sl){
    if(sl==NULL){
        printf("Lista nao existe!\n");
    }else{
        if(vaziaSkipList(sl)==1){
            printf("A lista esta vazia!\n");
        }else{
            SkipList* aux = sl;//primeiro n�vel
            SkipList* aux2;
            for(int i=0;i<20;i++){//percorre a coluna dos n�veis
                aux2=aux->prox;
                if(aux2==NULL){
                    printf("NIVEL VAZIO");
                }else{
                    while(aux2!=NULL){//percorre o n�vel printando elemento por elemento
                    printf("%d ",aux2->info);
                    aux2=aux2->prox;
                    }
                }
                printf("\n");//pula linha
                aux=aux->baixo;//vai para o pr�ximo n�vel
            }
        }
    }
}

int tamanhoSkipList(SkipList* sl){
    if(sl==NULL)
        return -1;//lista n�o existe
    if(vaziaSkipList(sl)==1)
        return 0;//lista vazia
    SkipList* aux = sl;
    while(aux->baixo!=NULL)//vai at� o �ltimo n�vel da skiplist
        aux=aux->baixo;
    aux=aux->prox;
    int cont = 0;
    while(aux!=NULL){//percorre o �ltimo n�vel incrementando o contador
        cont++;
        aux=aux->prox;
    }
    return cont;//retorna o tamanho
}

int buscaSkipList(SkipList* sl,int N){
    if(sl==NULL){//lista n�o existe
        return -1;
    }
    if(vaziaSkipList(sl)==1){//lista vazia
        return 0;
    }
    SkipList* aux=sl;
    while(aux->baixo!=NULL&&(aux->prox==NULL||aux->prox->info>N)){//pula os n�veis vazios e os come�ados em valores maiores que N
        aux=aux->baixo;
        }
    aux=aux->prox;
    while(aux->baixo!=NULL){//percorre os n�veis um a um at� chegar no �ltimo
        while(aux->prox!=NULL&&aux->prox->info<=N){//percorre cada n�vel exceto o �ltimo
            aux=aux->prox;//percorre a lista at� chegar em seu �ltimmo elemento ou no �ltimo elemento <=N
        }
        if(aux->info==N)//testa elemento
            return 1;//retorna 1 se aux->info for igual a N
        aux=aux->baixo;//pula pro n�vel inferior
    }
    while(aux->prox!=NULL&&aux->prox->info<=N)//testa o �ltimo n�vel
        aux=aux->prox;//pr�ximo elemento
    if(aux->info==N)//testa elemento (que � >=N ou o �ltimo elemento da lista)
        return 1;//retorna 1 se aux->info==N
    return 0;//retorna 0 pois N n�o pertence � lista
}

int insereSkipList(SkipList* sl, int N){
    if(sl==NULL){//lista n�o existe retorna -1
        return -1;
    }
    int nro_niveis = 1+(rand()%20);//define o n�mero de n�veis e o n�vel m�ximo em que esse n�mero estar� presente na skiplist
    SkipList* aux = sl;
    SkipList* primeira_alocacao;//guarda o endere�o do primeiro no do numero alocado
    SkipList* aux_alocacao;//usado para acompanhar os nos que serao alocados para ligalos
    int possui_no = 0;//informa se o no j� tem no alocado em nivel anterior, se 0 ent�o n�o t� alocado
    int nivel = 20;//come�a com o n�vel mais alto
    //trabalho com os n�veis vazios
    while(aux->prox==NULL&&aux->baixo!=NULL){
        if(nivel<=nro_niveis){//cria no se ele deve ser criado
            SkipList* no = malloc(sizeof(SkipList));
            if(no==NULL){//se falhou a aloca��o libera todas as aloca��es feitas para esse N e retorna -1
                if(possui_no==1){
                    SkipList* auxremocao1=primeira_alocacao;
                    while(auxremocao1!=NULL){
                        SkipList* auxremocao2=auxremocao1;
                        auxremocao1=auxremocao2->baixo;
                        free(auxremocao2);
                    }
                }
                return -1;//houve erro na inser��o
            }
            no->info=N;
            no->prox=NULL;//n�o h� outro elemento
            aux->prox=no;//"ponteiro" do n�vel aponta para no
            if(possui_no==0){//se for a primeira aloca��o o endere�o � salvo caso seja necess�rio apagar o n�
                possui_no=1;
                primeira_alocacao=no;
                aux_alocacao=no;
                }
            if(possui_no==1){
                aux_alocacao->baixo = no;
                aux_alocacao=no;
            }
        }
        aux=aux->baixo;
        nivel--;
    }
    //�ltimo n�vel
    if(nivel==1){
        SkipList* no = malloc(sizeof(SkipList));
        if(no==NULL){//se falhou a aloca��o libera todas as aloca��es feitas para esse N e retorna -1
            if(possui_no==1){
                SkipList* auxremocao1=primeira_alocacao;
                while(auxremocao1!=NULL){
                    SkipList* auxremocao2 = auxremocao1;
                    auxremocao1=auxremocao2->baixo;
                    free(auxremocao2);
                }
            }
            return -1;//houve erro na inser��o
        }
        no->info=N;
        while(aux->prox!=NULL&&aux->prox->info<N){
            aux=aux->prox;
        }
            no->prox=aux->prox;//no aponta para onde o no aux aponta
            aux->prox=no;//aux aponta para no
            if(possui_no==1){
                aux_alocacao->baixo = no;
                aux_alocacao=no;
            }
            no->baixo=NULL;
            return 1;//sucesso
    }
    //trabalhando com n�veis n�o vazios
    while(aux->baixo!=NULL){
        while(aux->prox!=NULL&&aux->prox->info<N){
            aux=aux->prox;
        }
        if(nivel<=nro_niveis){
            SkipList* no = malloc(sizeof(SkipList));
            if(no==NULL){//se falhou a aloca��o libera todas as aloca��es feitas para esse N e retorna -1
                if(possui_no==1){
                    SkipList* auxremocao1=primeira_alocacao;
                    while(auxremocao1!=NULL){
                        SkipList* auxremocao2 = auxremocao1;
                        auxremocao1=auxremocao2->baixo;
                        free(auxremocao2);
                    }
                }
                return -1;//erro na inser��o
            }
            no->info=N;
            no->prox=aux->prox;//no->prox aponta para onde aux->prox apontava
            aux->prox=no;//aux->prox aponta para no
            if(possui_no==0){//se for a primeira aloca��o o endere�o � salvo caso seja necess�rio apagar o n�
                possui_no=1;
                primeira_alocacao=no;
                aux_alocacao=no;
                }
            if(possui_no==1){//n�o � a primeira aloca��o
                aux_alocacao->baixo = no;//liga os elementos do mesmo n�mero
                aux_alocacao=no;
            }
        }
        aux=aux->baixo;//vai para o pr�ximo n�vel
        nivel--;
    }
    //�ltimo n�vel
    SkipList* no = malloc(sizeof(SkipList));
        if(no==NULL){//se falhou a aloca��o libera todas as aloca��es feitas para esse N e retorna -1
            if(possui_no==1){
                SkipList* auxremocao1=primeira_alocacao;
                while(auxremocao1!=NULL){
                    SkipList* auxremocao2 = auxremocao1;
                    auxremocao1=auxremocao2->baixo;
                    free(auxremocao2);
                    }
            }
            return -1;//erro na inser��o
        }
        no->info=N;
        while(aux->prox!=NULL&&aux->prox->info<N){
            aux=aux->prox;
        }
            no->prox=aux->prox;//no aponta para onde o no aux aponta
            aux->prox=no;//aux aponta para no
            if(possui_no==1){
                aux_alocacao->baixo = no;
                aux_alocacao=no;
            }
            no->baixo=NULL;
            return 1;//sucesso
}

int removeSkipList(SkipList* sl, int N){
    if(sl==NULL){//lista n�o existe
        return -1;
    }
    if(vaziaSkipList(sl)==1){//lista vazia
        return 0;
    }
    SkipList* aux=sl;
    SkipList* aux2;
    int seguranca = 0;//seguran�a para elementos repetidos
    while(aux->baixo!=NULL){
        while(seguranca==0&&aux->prox!=NULL&&aux->prox->info<=N){
            if(aux->prox->info==N){
                aux2=aux->prox;
                aux->prox=aux2->prox;
                free(aux2);
                seguranca=1;
            }else{
                aux=aux->prox;
            }
        }
        aux=aux->baixo;//vai para o pr�ximo n�vel
        seguranca=0;
    }
    //�ltimo n�vel
    while(aux->prox!=NULL&&aux->prox->info<=N){
            if(aux->prox->info==N){
                aux2=aux->prox;
                aux->prox=aux2->prox;
                free(aux2);
                return 1;
            }
            aux=aux->prox;
        }
    return 0;//elemento n�o encontrado
}
