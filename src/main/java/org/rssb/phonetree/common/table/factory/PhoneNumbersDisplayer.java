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
    @Override
    public TableCell<S, T> call(TableColumn<S, T> p) {
        return new TableCell<S, T>() {
            @Override
            public void updateItem(T item, boolean empty) {
                System.out.println("being called PhoneNumbersDisplayer");
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                System.out.println("Item not empty");
                Member member = (Member) p.getTableView().getItems().get(getIndex());
                System.out.println("member.cellPhone = "+member.getCellPhone());
                String phoneNumbers = member.getCellPhone();
                VBox vBox = new VBox();
                vBox.setSpacing(5);

                List<String> phoneNumbersList = Arrays.asList(phoneNumbers.split(","));
                Label label = new Label();
                //label.setWrapText(true);
                StringBuilder sb = new StringBuilder();
                for(String phone:phoneNumbersList){
                    sb.append(phone).append("\n");
                }
                label.setText(sb.toString());
                //vBox.getChildren().add(label);
                //StackPane pane = new StackPane();
                //pane.getChildren().addAll(label);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(label);
            }
        };
    }
}
