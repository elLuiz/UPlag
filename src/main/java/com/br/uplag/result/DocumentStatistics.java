package com.br.uplag.result;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class DocumentStatistics {
    private Integer totalNumberOfTokens;
    private String[] nGrams;
    private Double containment;

    public void setTotalNumberOfTokens(String nGrams) {
        this.totalNumberOfTokens = nGrams.split(" ").length;
    }

    public void calculateContainment(DocumentStatistics documentStatisticsB) {
        Set<String> originalSet = new HashSet<>(Arrays.asList(getNGrams()));
        Set<String> documentANgrams = new HashSet<>(Arrays.asList(getNGrams()));
        Set<String> documentBNgrams = new HashSet<>(Arrays.asList(documentStatisticsB.getNGrams()));
        documentANgrams.retainAll(documentBNgrams);
        this.containment = ((double) documentANgrams.size() / originalSet.size());
    }

    @Override
    public String toString() {
        return totalNumberOfTokens + " tokens";
    }
}