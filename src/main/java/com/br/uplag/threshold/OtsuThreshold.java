package com.br.uplag.threshold;

import java.util.ArrayList;
import java.util.List;

public class OtsuThreshold {
    private final List<Double> candidatesThresholds;

    public OtsuThreshold() {
        this.candidatesThresholds = new ArrayList<>();
    }

    public void storeThreshold(Double withinClass, Double betweenClass) {
        if (betweenClass > 0) {
            Double threshold = withinClass + betweenClass;
            candidatesThresholds.add(threshold);
        } else
            candidatesThresholds.add(0.0);
    }

    public Double findMaxThresholdValue() {
        double position = 0.0;
        double maxThreshold = 0.0;
        for (int i = 0; i < candidatesThresholds.size(); i++) {
            if (candidatesThresholds.get(i) > maxThreshold) {
                maxThreshold = candidatesThresholds.get(i);
                position =  i;
            }
        }

        return position * 10;
    }
}
