package org.rssb.phonetree.common.table.factory;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.predicates.CalledFamilyDetailsPredicates;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class TableFormatter <S,T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private String formatColumn;
    private Map<Predicate<String>, Function<S, ? extends Node>> predicateFunctionMap = new HashMap<>();

    public String getFormatColumn() {
        return formatColumn;
    }

    public void setFormatColumn(String formatColumn) {
        if (CommonUtil.isNotEmptyOrNull(formatColumn)) {
            this.formatColumn = formatColumn;
            return;
        }
        this.formatColumn = "";
    }

    protected <T> void put(Predicate<String> predicate, Function<S, ? extends Node> function) {
        predicateFunctionMap.put(predicate, function);
    }

    protected Node get(S object) {
        for (Map.Entry<Predicate<String>, Function<S, ? extends Node>> entry : predicateFunctionMap.entrySet()) {
            Predicate<String> predicate = entry.getKey();
            if (predicate.test(getFormatColumn())) {
                return entry.getValue().apply((S) object);
            }
        }

        return null;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> p) {
        return new TableCell<S, T>() {
            @Override
            public void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                S object = p.getTableView().getItems().get(getIndex());

                if(object instanceof CalledFamilyDetails){
                    if (CalledFamilyDetailsPredicates.isFormatSequenceNumberColumn.test(getFormatColumn()) &&
                            ((CalledFamilyDetails)object).getFamilySeqNumber() == 0) {
                        System.out.println("found seq is = 0, ignoring");
                        setGraphic(null);
                        return;
                    }
                }
                for(Map.Entry<Predicate<String>,Function<S,? extends Node>> entry:predicateFunctionMap.entrySet()){
                    Predicate<String> predicate = entry.getKey();
                    if(predicate.test(formatColumn)){
                        Node node = entry.getValue().apply(object);
                        if(node!=null){
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            setGraphic(node);
                            return;
                        }
                    }
                }
            }
        };
    }
}


