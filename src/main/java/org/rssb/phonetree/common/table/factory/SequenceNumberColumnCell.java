package org.rssb.phonetree.common.table.factory;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
                FontAwesomeIconView thumbsUpIcon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
                thumbsUpIcon.setSize("48");
                thumbsUpIcon.getStyleClass().add("seq-number-glyph-icon");

                Text text = new Text();
                text.setText(String.valueOf(item));
                text.getStyleClass().add("seq-number-glyph-icon-text");
                pane.getChildren().addAll(thumbsUpIcon,text);
                //label.getStyleClass().add("sequence-number");
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(pane);
            }
        };
    }
}
