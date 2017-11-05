package org.rssb.phonetree.common.table.factory;

import javafx.scene.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class TableFormatter <S> {
    private String formatColumn;
    private Map<Predicate<String>, Function<S, ? extends Node>> predicateFunctionMap = new HashMap<>();

    public String getFormatColumn() {
        return formatColumn;
    }

    public void setFormatColumn(String formatColumn) {
        this.formatColumn = formatColumn;
    }

    protected <T> void put(Predicate<String> predicate, Function<S, ? extends Node> function) {
        predicateFunctionMap.put(predicate, function);
    }

    protected Node get(S object) {
        for (Map.Entry<Predicate<String>, Function<S, ? extends Node>> entry : predicateFunctionMap.entrySet()) {
            Predicate<String> predicate = entry.getKey();
            if (predicate.test(formatColumn)) {
                return entry.getValue().apply((S) object);
            }
        }

        return null;
    }
}


