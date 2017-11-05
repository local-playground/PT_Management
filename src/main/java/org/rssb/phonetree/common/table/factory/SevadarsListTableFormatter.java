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

public class SevadarsListTableFormatter <S, T>
        implements Callback<TableColumn<S, T>, TableCell<S, T>> {

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


    Map<Predicate<String>,Function<CalledFamilyDetails,? extends Node>> predicateFunctionMap =
            new HashMap<>();

    public SevadarsListTableFormatter() {
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatCellPhoneColumn,
                CalledFamilyDetailsPredicates.cellPhoneLabelComposerFunction);
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatHomePhoneColumn,
                CalledFamilyDetailsPredicates.homePhoneLabelComposerFunction);
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatWorkPhoneColumn,
                CalledFamilyDetailsPredicates.workPhoneLabelComposerFunction);
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatSequenceNumberColumn,
                CalledFamilyDetailsPredicates.familySeqNumberLabelComposerFunction);
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatZipCodeColumn,
                CalledFamilyDetailsPredicates.zipCodeLabelComposerFunction);

        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatFirstNameColumn,
                CalledFamilyDetailsPredicates.firstNameLabelComposerFunction);
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatLastNameColumn,
                CalledFamilyDetailsPredicates.lastNameLabelComposerFunction);
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatPhoneStatusColumn,
                CalledFamilyDetailsPredicates.phoneStatusLabelComposerFunction);
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatMemberIdColumn,
                CalledFamilyDetailsPredicates.memberIdLabelComposerFunction);
        predicateFunctionMap.put(CalledFamilyDetailsPredicates.isFormatFamilyIdColumn,
                CalledFamilyDetailsPredicates.familyIdLabelComposerFunction);

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
                CalledFamilyDetails calledFamilyDetails = (CalledFamilyDetails) p.getTableView().getItems().get(getIndex());

                if (CalledFamilyDetailsPredicates.isFormatSequenceNumberColumn.test(getFormatColumn()) &&
                        calledFamilyDetails.getFamilySeqNumber() == 0) {
                    setGraphic(null);
                    return;
                }
                for(Map.Entry<Predicate<String>,Function<CalledFamilyDetails,? extends Node>> entry:predicateFunctionMap.entrySet()){
                    Predicate<String> predicate = entry.getKey();
                    if(predicate.test(getFormatColumn())){
                        Node node = entry.getValue().apply(calledFamilyDetails);
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
