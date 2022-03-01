package com.br.uplag.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtil {
    private CollectionUtil() {}

    public static List<Double> convertDoubleCollectionToArraylist(Collection<Double> collection) {
        return new ArrayList<>(collection);
    }
}