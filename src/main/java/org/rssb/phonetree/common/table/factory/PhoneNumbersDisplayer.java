package org.rssb.phonetree.common.table.factory;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.rssb.phonetree.entity.Member;

import java.util.Arrays;
import java.util.List;

public class PhoneNumbersDisplayer<S, T>
        implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private String phoneType;

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
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

                String phoneNumbers= member.getCellPhone();//default
                if(phoneType.equalsIgnoreCase("CELL")) {
                    phoneNumbers = member.getCellPhone();
                }else if(phoneType.equalsIgnoreCase("HOME")){
                    phoneNumbers = member.getHomePhone();
                }else if(phoneType.equalsIgnoreCase("WORK")){
                    phoneNumbers = member.getWorkPhone();
                }

                VBox vBox = new VBox();
                vBox.setSpacing(5);

                List<String> phoneNumbersList = Arrays.asList(phoneNumbers.split(","));
                Label label = new Label();
                StringBuilder sb = new StringBuilder();
                for(String phone:phoneNumbersList){
                    sb.append(phone).append("\n");
                }
                label.setText(sb.toString());
                label.setStyle("-fx-background-color: transparent");
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(label);
            }
        };
    }
}
