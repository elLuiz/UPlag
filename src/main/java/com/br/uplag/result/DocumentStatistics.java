package com.br.uplag.result;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class DocumentStatistics {
    private int totalNumberOfTokens;
    private String[] nGrams;
    private Double containment;

    public DocumentStatistics() {
    }

    public DocumentStatistics(int totalNumberOfTokens, String[] nGrams) {
        this.totalNumberOfTokens = totalNumberOfTokens;
        this.nGrams = nGrams;
    }

    public void setTotalNumberOfTokens(String nGrams) {
        this.totalNumberOfTokens = nGrams.split(" ").length;
    }

    public void calculateContainment(DocumentStatistics secondDocumentStatistics) {
        Set<String> documentANgrams = getNGramsSet(this.getNGrams());
        Set<String> documentBNgrams = getNGramsSet(secondDocumentStatistics.getNGrams());
        intersectCommonNgrams(documentANgrams, documentBNgrams);
        this.containment = Math.min((double) documentANgrams.size() / getNgramsSize(), 1.0);
    }

    private Set<String> getNGramsSet(String[] ngrams) {
        Set<String> ngramsSet = new HashSet<>();
        for (String ngram : ngrams) {
            ngramsSet.add(ngram);
        }

        return ngramsSet;
    }

    private void intersectCommonNgrams(Set<String> documentANgrams, Set<String> documentBNgrams) {
        documentANgrams.retainAll(documentBNgrams);
    }

    private int getNgramsSize() {
        return this.getNGrams().length;
    }

    @Override
    public String toString() {
        return totalNumberOfTokens + " tokens";
    }
}