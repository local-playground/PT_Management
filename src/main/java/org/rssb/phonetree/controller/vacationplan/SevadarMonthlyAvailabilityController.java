package org.rssb.phonetree.controller.vacationplan;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.SevadarsMonthlyAvailability;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Lazy
@Scope("prototype")
public class SevadarMonthlyAvailabilityController extends AbstractController {
    @FXML
    private Label monthNameLabel;

    @FXML
    private VBox availableDatesHolder;

    @FXML
    private VBox outDatesHolder;

    @FXML
    private VBox daysOutDatesHolder;


    @Override
    public void postProcess() {
        SevadarsMonthlyAvailability sevadarsMonthlyAvailability = (SevadarsMonthlyAvailability) contextHolder.get(Constants.REQUEST_OBJ);
        monthNameLabel.setText(sevadarsMonthlyAvailability.getMonthName());
        availableDatesHolder.getChildren().add(getLabel(sevadarsMonthlyAvailability.getAvailableDates()));
        outDatesHolder.getChildren().add(getLabel(sevadarsMonthlyAvailability.getOutDates()));
        daysOutDatesHolder.getChildren().add(getLabel(String.valueOf(sevadarsMonthlyAvailability.getTotalDaysOut())));
    }

    private Label getLabel(String data){
        List<String> datesList = Arrays.asList(data.split(","));
        Label label = new Label(data);
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("\n", datesList));
        System.out.println("Dates to show "+sb.toString());
        label.setText(sb.toString());
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }
}
