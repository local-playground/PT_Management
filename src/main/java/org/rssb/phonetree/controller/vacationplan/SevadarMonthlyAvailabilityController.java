package org.rssb.phonetree.controller.vacationplan;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.SevadarsMonthlyAvailability;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Lazy
@Scope("prototype")
public class SevadarMonthlyAvailabilityController extends AbstractController {
    @FXML
    private TableView<SevadarsMonthlyAvailability> monthlyTableView;

    /*@FXML
    private TableColumn<SevadarsMonthlyAvailability, String> sevadarNameTableColumn;

    @FXML
    private TableColumn<SevadarsMonthlyAvailability, String> monthNameTableColumn;

    @FXML
    private TableColumn<SevadarsMonthlyAvailability, String> availableTableColumn;

    @FXML
    private TableColumn<SevadarsMonthlyAvailability, String> outTableColumn;

    @FXML
    private TableColumn<SevadarsMonthlyAvailability, String> daysOutTableColumn;*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*ObservableList<SevadarsMonthlyAvailability> monthlyAvailabilityObservableList = FXCollections.observableList(sevadarsMonthlyAvailabilityList);
        monthlyTableView.setItems(monthlyAvailabilityObservableList);*/
    }
}
