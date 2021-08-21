/*
Grupo HXX
Integrantes:
Higor Raphael Faria e Sousa - 11811BCC014
*/
#include <stdlib.h>
#include <stdio.h>
#include "GrupoHXX.h"

SkipList* criaSkipList(){//cria uma coluna de 20 níveis que apontam com o prox para NULL
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
        aux->baixo = no;//liga o que se tem da coluna até então ao novo no
        aux = no;
    }
    aux->baixo= NULL;//ultimo no aponta com baixo para NULL
    return p;
}

int vaziaSkipList(SkipList* sl){
    if(sl==NULL){
        return -1;//lista não existe
    }
    int boolean = 1;//seta a lista como vazia
    SkipList* aux = sl;
    for(int i=0;i<20;i++){
        if(aux->prox!=NULL){
            boolean=0;//lista não é vazia
            return boolean;
        }
        aux=aux->baixo;
    }
    return boolean;
}

void liberaSkipList(SkipList** sl){
    if((*sl)==NULL){//lista não existe, nada é feito
    }else{
    SkipList* aux = (*sl);
    if(vaziaSkipList(*sl)==0){//lista não está vazia, libera os nós dos níveis que não pertencem à coluna principal da skiplist
        while(aux!=NULL){ //percorre cada nível
            SkipList* aux2=aux->prox;
            while(aux2!=NULL){ //libera cada elemento do nível
                SkipList* aux3=aux2;
                aux2 = aux3->prox;
                free (aux3);
            }
            aux->prox=NULL;
            aux=aux->baixo;
        }
        aux=(*sl);
    }
    //lista está vazia
    while(aux!=NULL){//libera a coluna dos níveis
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
            SkipList* aux = sl;//primeiro nível
            SkipList* aux2;
            for(int i=0;i<20;i++){//percorre a coluna dos níveis
                aux2=aux->prox;
                if(aux2==NULL){
                    printf("NIVEL VAZIO");
                }else{
                    while(aux2!=NULL){//percorre o nível printando elemento por elemento
                    printf("%d ",aux2->info);
                    aux2=aux2->prox;
                    }
                }
                printf("\n");//pula linha
                aux=aux->baixo;//vai para o próximo nível
            }
        }
    }
}

int tamanhoSkipList(SkipList* sl){
    if(sl==NULL)
        return -1;//lista não existe
    if(vaziaSkipList(sl)==1)
        return 0;//lista vazia
    SkipList* aux = sl;
    while(aux->baixo!=NULL)//vai até o último nível da skiplist
        aux=aux->baixo;
    aux=aux->prox;
    int cont = 0;
    while(aux!=NULL){//percorre o último nível incrementando o contador
        cont++;
        aux=aux->prox;
    }
    return cont;//retorna o tamanho
}

int buscaSkipList(SkipList* sl,int N){
    if(sl==NULL){//lista não existe
        return -1;
    }
    if(vaziaSkipList(sl)==1){//lista vazia
        return 0;
    }
    SkipList* aux=sl;
    while(aux->baixo!=NULL&&(aux->prox==NULL||aux->prox->info>N)){//pula os níveis vazios e os começados em valores maiores que N
        aux=aux->baixo;
        }
    aux=aux->prox;
    while(aux->baixo!=NULL){//percorre os níveis um a um até chegar no último
        while(aux->prox!=NULL&&aux->prox->info<=N){//percorre cada nível exceto o último
            aux=aux->prox;//percorre a lista até chegar em seu últimmo elemento ou no último elemento <=N
        }
        if(aux->info==N)//testa elemento
            return 1;//retorna 1 se aux->info for igual a N
        aux=aux->baixo;//pula pro nível inferior
    }
    while(aux->prox!=NULL&&aux->prox->info<=N)//testa o último nível
        aux=aux->prox;//próximo elemento
    if(aux->info==N)//testa elemento (que é >=N ou o último elemento da lista)
        return 1;//retorna 1 se aux->info==N
    return 0;//retorna 0 pois N não pertence à lista
}

int insereSkipList(SkipList* sl, int N){
    if(sl==NULL){//lista não existe retorna -1
        return -1;
    }
    int nro_niveis = 1+(rand()%20);//define o número de níveis e o nível máximo em que esse número estará presente na skiplist
    SkipList* aux = sl;
    SkipList* primeira_alocacao;//guarda o endereço do primeiro no do numero alocado
    SkipList* aux_alocacao;//usado para acompanhar os nos que serao alocados para ligalos
    int possui_no = 0;//informa se o no já tem no alocado em nivel anterior, se 0 então não tá alocado
    int nivel = 20;//começa com o nível mais alto
    //trabalho com os níveis vazios
    while(aux->prox==NULL&&aux->baixo!=NULL){
        if(nivel<=nro_niveis){//cria no se ele deve ser criado
            SkipList* no = malloc(sizeof(SkipList));
            if(no==NULL){//se falhou a alocação libera todas as alocações feitas para esse N e retorna -1
                if(possui_no==1){
                    SkipList* auxremocao1=primeira_alocacao;
                    while(auxremocao1!=NULL){
                        SkipList* auxremocao2=auxremocao1;
                        auxremocao1=auxremocao2->baixo;
                        free(auxremocao2);
                    }
                }
                return -1;//houve erro na inserção
            }
            no->info=N;
            no->prox=NULL;//não há outro elemento
            aux->prox=no;//"ponteiro" do nível aponta para no
            if(possui_no==0){//se for a primeira alocação o endereço é salvo caso seja necessário apagar o nó
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
    //último nível
    if(nivel==1){
        SkipList* no = malloc(sizeof(SkipList));
        if(no==NULL){//se falhou a alocação libera todas as alocações feitas para esse N e retorna -1
            if(possui_no==1){
                SkipList* auxremocao1=primeira_alocacao;
                while(auxremocao1!=NULL){
                    SkipList* auxremocao2 = auxremocao1;
                    auxremocao1=auxremocao2->baixo;
                    free(auxremocao2);
                }
            }
            return -1;//houve erro na inserção
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
    //trabalhando com níveis não vazios
    while(aux->baixo!=NULL){
        while(aux->prox!=NULL&&aux->prox->info<N){
            aux=aux->prox;
        }
        if(nivel<=nro_niveis){
            SkipList* no = malloc(sizeof(SkipList));
            if(no==NULL){//se falhou a alocação libera todas as alocações feitas para esse N e retorna -1
                if(possui_no==1){
                    SkipList* auxremocao1=primeira_alocacao;
                    while(auxremocao1!=NULL){
                        SkipList* auxremocao2 = auxremocao1;
                        auxremocao1=auxremocao2->baixo;
                        free(auxremocao2);
                    }
                }
                return -1;//erro na inserção
            }
            no->info=N;
            no->prox=aux->prox;//no->prox aponta para onde aux->prox apontava
            aux->prox=no;//aux->prox aponta para no
            if(possui_no==0){//se for a primeira alocação o endereço é salvo caso seja necessário apagar o nó
                possui_no=1;
                primeira_alocacao=no;
                aux_alocacao=no;
                }
            if(possui_no==1){//não é a primeira alocação
                aux_alocacao->baixo = no;//liga os elementos do mesmo número
                aux_alocacao=no;
            }
        }
        aux=aux->baixo;//vai para o próximo nível
        nivel--;
    }
    //último nível
    SkipList* no = malloc(sizeof(SkipList));
        if(no==NULL){//se falhou a alocação libera todas as alocações feitas para esse N e retorna -1
            if(possui_no==1){
                SkipList* auxremocao1=primeira_alocacao;
                while(auxremocao1!=NULL){
                    SkipList* auxremocao2 = auxremocao1;
                    auxremocao1=auxremocao2->baixo;
                    free(auxremocao2);
                    }
            }
            return -1;//erro na inserção
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
    if(sl==NULL){//lista não existe
        return -1;
    }
    if(vaziaSkipList(sl)==1){//lista vazia
        return 0;
    }
    SkipList* aux=sl;
    SkipList* aux2;
    int seguranca = 0;//segurança para elementos repetidos
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
        aux=aux->baixo;//vai para o próximo nível
        seguranca=0;
    }
    //Último nível
    while(aux->prox!=NULL&&aux->prox->info<=N){
            if(aux->prox->info==N){
                aux2=aux->prox;
                aux->prox=aux2->prox;
                free(aux2);
                return 1;
            }
            aux=aux->prox;
        }
    return 0;//elemento não encontrado
}
