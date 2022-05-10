package com.br.uplag.threshold.otsu;

import com.br.uplag.util.DoubleUtil;

import java.util.ArrayList;
import java.util.List;

public class OtsuThreshold {
    private final List<Double> candidatesThresholds;

    public OtsuThreshold() {
        this.candidatesThresholds = new ArrayList<>();
    }

    public OtsuThreshold(List<Double> candidatesThresholds) {
        this.candidatesThresholds = candidatesThresholds;
    }

    public void storeBetweenClassVariance(Double betweenClass) {
        candidatesThresholds.add(betweenClass);
    }

    public Double findMaxThresholdValue() {
        int cumulativePositionIndex = 0;
        int totalOfDuplicates = 0;
        double position = 0.0;
        double maxThreshold = 0.0;

        for (int i = 0; i < candidatesThresholds.size(); i++) {
            if (candidatesThresholds.get(i) >= maxThreshold) {
                if (candidatesThresholds.get(i) == maxThreshold) {
                    totalOfDuplicates++;
                    position = (position * cumulativePositionIndex + i) / totalOfDuplicates;
                    cumulativePositionIndex++;
                } else {
                    position =  i;
                    cumulativePositionIndex = 1;
                    totalOfDuplicates = 1;
                }
                maxThreshold = candidatesThresholds.get(i);
            }
        }

        return DoubleUtil.prettifyDouble(position * 10, 2);
    }
}