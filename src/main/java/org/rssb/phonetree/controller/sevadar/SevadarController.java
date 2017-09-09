package org.rssb.phonetree.controller.sevadar;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.SevadarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component("Sevadar")
public class SevadarController extends AbstractController {

    @Autowired
    private SevadarService sevadarService;

    @FXML
    private JFXButton addSevadarButton;

    @FXML
    private JFXButton deleteSevadarButton;

    @FXML
    private JFXButton replaceSevadarButton;

    @FXML
    private JFXButton swapSevadarButton;

    @FXML
    private JFXButton moveSevadarButton;

    @FXML
    private TableView<Sevadar> sevadarsTableView;

    @FXML
    private TableColumn<Sevadar, String> firstNameTableColumn;

    @FXML
    private TableColumn<Sevadar, String> lastNameTableColumn;

    @FXML
    private TableColumn<Sevadar, String> cellPhoneTableColumn;

    @FXML
    private TableColumn<Sevadar, String> homePhoneTableColumn;

    /*@FXML
    private BarChart<String,Integer> barChart;*/

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sevadar, String>,
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sevadar, String> param) {
                return new SimpleStringProperty(param.getValue().getMember().getFirstName());
            }
        });

        lastNameTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sevadar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sevadar, String> param) {
                return new SimpleStringProperty(param.getValue().getMember().getLastName());
            }
        });

        cellPhoneTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sevadar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sevadar, String> param) {
                return new SimpleStringProperty(param.getValue().getMember().getCellPhone());
            }
        });

        homePhoneTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sevadar, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sevadar, String> param) {
                return new SimpleStringProperty(param.getValue().getMember().getHomePhone());
            }
        });
    }

    @FXML
    void addSevadar(ActionEvent event) {

    }

    @FXML
    void deleteSevadar(ActionEvent event) {

    }

    @FXML
    void moveSevadar(ActionEvent event) {

    }

    @FXML
    void replaceSevadar(ActionEvent event) {

    }

    @FXML
    void swapSevadar(ActionEvent event) {

    }


    @Override
    public void refresh() {
        TeamLead teamLead = (TeamLead) contextHolder.getRequest();
        System.out.println("Sevadar controller found selected item = "+teamLead.getTeamLeadName());
        List<Sevadar> sevadarList = teamLead.getSevadarsList();
        System.out.println("total number of sevadars = "+sevadarList.size());
        ObservableList<Sevadar> sevadarObservableList = FXCollections.observableArrayList(sevadarList);
        if(sevadarsTableView == null){
            System.out.println("table view is null");
        }
        sevadarsTableView.setItems(sevadarObservableList);

        refreshBarChart(teamLead);
    }

    private void refreshBarChart(TeamLead teamLead){
        List<FamilyCount> familyCountList = sevadarService.getSevadarsCallingFamilyCountByTeamLeadId(teamLead.getTeamLeadId());
        /*XYChart.Series<String,Integer> dataSeries = new XYChart.Series<>();
        familyCountList.stream().forEach(familyCount -> {
            XYChart.Data<String,Integer> data = new XYChart.Data(familyCount.getName(),familyCount.getCount());
            dataSeries.getData().add(data);
        });
        barChart.getData().clear();
        barChart.getData().add(dataSeries);*/
        pieChart.getData().clear();
        familyCountList.stream().forEach(familyCount -> {
            PieChart.Data data = new PieChart.Data(familyCount.getName(),familyCount.getCount());
            pieChart.getData().add(data);
        });

        pieChart.getData().stream().forEach(data -> {
            data.nameProperty().bind(Bindings.concat(data.getName() + " , "+ (int)data.getPieValue()));
        });
    }
}
