package org.rssb.phonetree.common.table.factory;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.rssb.phonetree.domain.CalledFamilyDetails;

public class SequenceNumberColumnCell<S, T>
        implements Callback<TableColumn<S, T>, TableCell<S, T>> {

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
                if (calledFamilyDetails.getFamilySeqNumber() == 0) {
                    setGraphic(null);
                    return;
                }

                StackPane pane = new StackPane();
                Label label = new Label();
                label.setText(String.valueOf(item));
                pane.getChildren().add(label);
                label.getStyleClass().add("sequence-number");
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(pane);

            }
        };
    }
}