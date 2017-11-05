package org.rssb.phonetree.common.table.factory;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.predicates.MembersTablePredicates;

public class MemberTableFormatter<S, T> extends TableFormatter<Member>
        implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private String formatColumn;

    //Map<Predicate<String>,Function<Member,? extends Node>> predicateFunctionMap =new HashMap<>();

    public MemberTableFormatter() {
        put(MembersTablePredicates.isFormatCellPhoneColumn, MembersTablePredicates.cellPhoneLabelComposerFunction);

        /*predicateFunctionMap.put(MembersTablePredicates.isFormatCellPhoneColumn,
                MembersTablePredicates.cellPhoneLabelComposerFunction);
        predicateFunctionMap.put(MembersTablePredicates.isFormatHomePhoneColumn,
                MembersTablePredicates.homePhoneLabelComposerFunction);
        predicateFunctionMap.put(MembersTablePredicates.isFormatWorkPhoneColumn,
                MembersTablePredicates.workPhoneLabelComposerFunction);
        predicateFunctionMap.put(MembersTablePredicates.isFormatPriorityColumn,
                MembersTablePredicates.priorityLabelComposerFunction);
        predicateFunctionMap.put(MembersTablePredicates.isFormatOnCallingListColumn,
                MembersTablePredicates.onCallingListLabelComposerFunction);

        predicateFunctionMap.put(MembersTablePredicates.isFormatFirstNameColumn,
                MembersTablePredicates.firstNameLabelComposerFunction);
        predicateFunctionMap.put(MembersTablePredicates.isFormatLastNameColumn,
                MembersTablePredicates.lastNameLabelComposerFunction);
        predicateFunctionMap.put(MembersTablePredicates.isFormatPreferredPhoneColumn,
                MembersTablePredicates.preferredPhoneLabelComposerFunction);
        predicateFunctionMap.put(MembersTablePredicates.isFormatMemberIdColumn,
                MembersTablePredicates.memberIdLabelComposerFunction);
        predicateFunctionMap.put(MembersTablePredicates.isFormatFamilyIdColumn,
                MembersTablePredicates.familyIdLabelComposerFunction);*/

    }

    public String getFormatColumn() {
        return formatColumn;
    }

    public void setFormatColumn(String formatColumn) {
        this.formatColumn = formatColumn;
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
                Member member = (Member) p.getTableView().getItems().get(getIndex());
                Node node = get(member);
                if(node!=null){
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(node);
                    return;
                }
                /*for(Map.Entry<Predicate<String>,Function<Member,? extends Node>> entry:predicateFunctionMap.entrySet()){
                    Predicate<String> predicate = entry.getKey();
                    if(predicate.test(formatColumn)){
                        Node node = entry.getValue().apply(member);
                        if(node!=null){
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            setGraphic(node);
                            return;
                        }
                    }
                }*/
            }
        };
    }


}
