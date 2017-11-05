package org.rssb.phonetree.common.table.factory;


import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.predicates.TeamLeadPredicates;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class TeamLeadTableFormatter<S, T>
        implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private String formatColumn;

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

    Map<Predicate<String>, Function<TeamLead, ? extends Node>> predicateFunctionMap =
            new HashMap<>();

    public TeamLeadTableFormatter() {
        predicateFunctionMap.put(TeamLeadPredicates.isFormatCellPhoneColumn,
                TeamLeadPredicates.cellPhoneLabelComposerFunction);
        predicateFunctionMap.put(TeamLeadPredicates.isFormatHomePhoneColumn,
                TeamLeadPredicates.homePhoneLabelComposerFunction);
        predicateFunctionMap.put(TeamLeadPredicates.isFormatFirstNameColumn,
                TeamLeadPredicates.firstNameLabelComposerFunction);
        predicateFunctionMap.put(TeamLeadPredicates.isFormatLastNameColumn,
                TeamLeadPredicates.lastNameLabelComposerFunction);

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
                TeamLead teamLead = (TeamLead) p.getTableView().getItems().get(getIndex());

                for(Map.Entry<Predicate<String>,Function<TeamLead,? extends Node>> entry:predicateFunctionMap.entrySet()){
                    Predicate<String> predicate = entry.getKey();
                    if(predicate.test(getFormatColumn())){
                        Node node = entry.getValue().apply(teamLead);
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
