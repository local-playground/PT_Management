package org.rssb.phonetree.controller.vacationplan;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.controlsfx.control.PopOver;
import org.rssb.phonetree.controller.AbstractController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
@Lazy
public class VacationDatesController extends AbstractController {
    private PopOver popOver = new PopOver();
    private static final String pattern = "yyyy-MM-dd";

    @FXML
    private JFXDatePicker vacationStartDate;

    @FXML
    private JFXDatePicker vacationEndDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vacationStartDate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        vacationEndDate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    public boolean isEmpty() {
        LocalDate startDate = vacationStartDate.getValue();
        LocalDate endDate = vacationEndDate.getValue();

        return Objects.isNull(startDate) && Objects.isNull(endDate);
    }

    public void setFromDate(LocalDate fromDate) {
        this.vacationStartDate.setValue(fromDate);
    }

    public void setEndDate(LocalDate endDate) {
        this.vacationEndDate.setValue(endDate);
    }


    @Override
    public boolean validate() {
        if (isEmpty()) {
            return true;
        }

        if(!Objects.isNull(vacationStartDate.getValue()) && !Objects.isNull(vacationEndDate.getValue())){
            System.out.println("validating both not null values");
            if(vacationStartDate.getValue().isAfter(vacationEndDate.getValue())){
                showPopover(vacationStartDate,"Vacation Start Date should less than End Date.");
                return false;
            }
        }

        return true;
    }

    private void showPopover(JFXDatePicker datePicker,String message) {
        Label label = new Label(message);
        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.getChildren().add(label);
        popOver.setContentNode(box);
        popOver.setAnimated(true);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        popOver.show(datePicker);
    }
}
