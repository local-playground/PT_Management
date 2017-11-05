package org.rssb.phonetree.common.table.factory;


import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.predicates.SevadarPredicates;
import org.rssb.phonetree.predicates.TeamLeadPredicates;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class SevadarTableFormatter  <S, T>
        implements Callback<TableColumn<S, T>, TableCell<S, T>>{

    private String formatColumn;

    public String getFormatColumn() {
        return formatColumn;
    }

    public void setFormatColumn(String formatColumn) {
        if(CommonUtil.isNotEmptyOrNull(formatColumn)) {
            this.formatColumn = formatColumn;
            return;
        }
        this.formatColumn="";
    }

    Map<Predicate<String>, Function<Sevadar, ? extends Node>> predicateFunctionMap =
            new HashMap<>();

    public SevadarTableFormatter() {
        predicateFunctionMap.put(SevadarPredicates.isFormatCellPhoneColumn,
                SevadarPredicates.cellPhoneLabelComposerFunction);
        predicateFunctionMap.put(SevadarPredicates.isFormatHomePhoneColumn,
                SevadarPredicates.homePhoneLabelComposerFunction);
        predicateFunctionMap.put(TeamLeadPredicates.isFormatFirstNameColumn,
                SevadarPredicates.firstNameLabelComposerFunction);
        predicateFunctionMap.put(TeamLeadPredicates.isFormatLastNameColumn,
                SevadarPredicates.lastNameLabelComposerFunction);

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
                Sevadar sevadar = (Sevadar) p.getTableView().getItems().get(getIndex());

                for(Map.Entry<Predicate<String>,Function<Sevadar,? extends Node>> entry:predicateFunctionMap.entrySet()){
                    Predicate<String> predicate = entry.getKey();
                    if(predicate.test(getFormatColumn())){
                        Node node = entry.getValue().apply(sevadar);
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
