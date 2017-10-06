package org.rssb.phonetree.common.table.factory;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.entity.emums.CallStatus;

public class PhoneStatusColumnCell <S, T>
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
                StackPane pane = new StackPane();
                FontAwesomeIconView phoneStatusIcon =new FontAwesomeIconView();
                phoneStatusIcon.setSize("32");
                if(calledFamilyDetails.getCallStatus()== CallStatus.OK){
                    phoneStatusIcon.setIcon(FontAwesomeIcon.CHECK_CIRCLE);
                    phoneStatusIcon.getStyleClass().add("ok");
                }
                if(calledFamilyDetails.getCallStatus()== CallStatus.WRONG_NUMBER){
                    phoneStatusIcon.setIcon(FontAwesomeIcon.TIMES_CIRCLE);
                    phoneStatusIcon.getStyleClass().add("wrong-number");
                }
                if(calledFamilyDetails.getCallStatus()== CallStatus.DISCONNECTED){
                    phoneStatusIcon.setIcon(FontAwesomeIcon.TIMES_CIRCLE);
                    phoneStatusIcon.getStyleClass().add("disconnected");
                }
                if(calledFamilyDetails.getCallStatus()== CallStatus.REMOVE_REQUEST){
                    phoneStatusIcon.setIcon(FontAwesomeIcon.CUT);
                    phoneStatusIcon.getStyleClass().add("remove-me");
                }
                if(calledFamilyDetails.getCallStatus()== CallStatus.NOT_PICKED){
                    phoneStatusIcon.setIcon(FontAwesomeIcon.TTY);
                    phoneStatusIcon.getStyleClass().add("not-picked");
                }
                if(calledFamilyDetails.getCallStatus()== CallStatus.MOVED){
                    phoneStatusIcon.setIcon(FontAwesomeIcon.TRUCK);
                    phoneStatusIcon.getStyleClass().add("moved");
                }
                pane.getChildren().addAll(phoneStatusIcon);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(pane);
            }
        };
    }
}
