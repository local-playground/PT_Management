package org.rssb.phonetree.controller.vacationplan;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import org.rssb.phonetree.controller.AbstractController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
@Lazy
public class VacationDatesController extends AbstractController{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private JFXDatePicker vacationStartDate;

    @FXML
    private JFXDatePicker vacationEndDate;

    public boolean isEmpty(){
        LocalDate startDate = vacationStartDate.getValue();
        LocalDate endDate = vacationEndDate.getValue();

        return Objects.isNull(startDate) && Objects.isNull(endDate);
    }
}
