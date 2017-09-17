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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.rssb.phonetree.common.*;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.teamlead.TeamLeadController;
import org.rssb.phonetree.controller.teammanagement.TeamManagementController;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component("Sevadar")
@SuppressWarnings("unused")
public class SevadarController extends AbstractController {

    @Autowired
    private SevadarService sevadarService;

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private TeamLeadController teamLeadController;

    @Autowired
    private TeamManagementController teamManagementController;

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
        TeamLead teamLead = teamLeadController.getSelected();
        if(teamLead==null){
            Alert alert = CommonUtil.getAlert("Please select Team Lead to add Sevadar", ActionAlertType.ERROR);
            alert.showAndWait();
            return;
        }

        ContextHolder contextHolder = createContextHolder(
                new String[]{Constants.ACTION,Constants.REQUEST_OBJ},
                new Object[]{SevadarAction.ADD_SEVADAR,teamLead},
                teamManagementController.getTeamManagementRootPane());
        setOpacity(Constants.LOW_OPACITY,contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this, contextHolder, true);
    }

    @FXML
    void deleteSevadar(ActionEvent event) {
        Sevadar sevadar = sevadarsTableView.getSelectionModel().getSelectedItem();
        Alert alert;
        if (sevadar == null) {
            alert = CommonUtil.getAlert("Please select Sevadar you want to delete.", ActionAlertType.ERROR);
            alert.showAndWait();
            return;
        }

        alert = CommonUtil.getAlert("Are you sure you want to remove " +
                sevadar.getSevadarName() + " from the list?", ActionAlertType.CONFIRMATION);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                Response response = sevadarService.deleteSevadar(sevadar.getSevadarsId());
                Alert newAlert = CommonUtil.getAlert(response);
                newAlert.showAndWait();
                refresh();
            }
        });
    }

    @FXML
    void moveSevadar(ActionEvent event) {

    }

    @FXML
    void replaceSevadar(ActionEvent event) {
        Sevadar sevadar = sevadarsTableView.getSelectionModel().getSelectedItem();
        if (sevadar == null) {
            Alert alert = CommonUtil.getAlert("Please select Sevadar you want to replace.", ActionAlertType.ERROR);
            alert.showAndWait();
            return;
        }
        ContextHolder contextHolder = createContextHolder(
                new String[]{Constants.ACTION, Constants.REQUEST_OBJ,"TEAM_LEAD"},
                new Object[]{SevadarAction.REPLACE_SEVADAR, sevadar,teamLeadController.getSelected()},
                teamManagementController.getTeamManagementRootPane());
        setOpacity(Constants.LOW_OPACITY,contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this, contextHolder, true);
    }

    @FXML
    void swapSevadar(ActionEvent event) {
        ContextHolder contextHolder = createContextHolder(Constants.ACTION,
                SevadarAction.SWAP_SEVADAR,
                teamManagementController.getTeamManagementRootPane());
        setOpacity(Constants.LOW_OPACITY,contextHolder);
        stageManager.switchScene(FxmlView.SWAP_SEVADAR, this, contextHolder, true);
    }


    @Override
    public void refresh() {
        TeamLead teamLead = (TeamLead) contextHolder.get(Constants.REQUEST_OBJ);
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadId(teamLead.getTeamLeadId());
        ObservableList<Sevadar> sevadarObservableList = FXCollections.observableArrayList(sevadarList);
        sevadarsTableView.setItems(sevadarObservableList);
        refreshBarChart(teamLead);
    }

    private void refreshBarChart(TeamLead teamLead){
        List<FamilyCount> familyCountList = sevadarService.getSevadarsCallingFamilyCountByTeamLeadId(teamLead.getTeamLeadId());
        pieChart.getData().clear();
        familyCountList.stream().forEach(familyCount -> {
            PieChart.Data data = new PieChart.Data(familyCount.getName(),familyCount.getCount());
            pieChart.getData().add(data);
        });

        pieChart.getData().stream().forEach(data -> {
            data.nameProperty().bind(Bindings.concat(data.getName() + " , "+ (int)data.getPieValue()));
        });
    }

    @Override
    public void delegate(ContextHolder contextHolder) {
        SevadarAction action = (SevadarAction) contextHolder.get(Constants.ACTION);
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        Response response = null;
        Alert alert;
        switch (action) {
            case REPLACE_SEVADAR:
                Sevadar sevadar = (Sevadar) contextHolder.get(Constants.REQUEST_OBJ);
                response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_SEVADAR);
                if (response.getActionAlertType() == ActionAlertType.NONE) {
                    replaceSevadar(sevadar.getSevadarsId(), selectedResult.getMemberId());
                    return;
                }
                alert = CommonUtil.getAlert(response);
                alert.showAndWait().ifPresent(buttonType -> {
                    if (alert.getAlertType() != Alert.AlertType.CONFIRMATION) {
                        return;
                    }

                    if (buttonType == ButtonType.NO) {
                        return;
                    }

                    replaceSevadar(sevadar.getSevadarsId(), selectedResult.getMemberId());
                });
                break;
            case ADD_SEVADAR:
                TeamLead teamLead = (TeamLead)contextHolder.get(Constants.REQUEST_OBJ);
                response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_SEVADAR);
                if (response.getActionAlertType() == ActionAlertType.NONE) {
                    addSevadar(selectedResult.getMemberId(),teamLead.getTeamLeadId());
                    return;
                }
                alert = CommonUtil.getAlert(response);
                alert.showAndWait().ifPresent(buttonType -> {
                    if (alert.getAlertType() != Alert.AlertType.CONFIRMATION) {
                        return;
                    }

                    if (buttonType == ButtonType.NO) {
                        return;
                    }
                    addSevadar(selectedResult.getMemberId(),teamLead.getTeamLeadId());
                });
                break;
            case SWAP_SEVADAR:
                Sevadar swapSevadar = (Sevadar) contextHolder.get("SWAP_SEVADAR");
                Sevadar swapSevadarWith = (Sevadar) contextHolder.get("SWAP_SEVADAR_WITH");
                swapSevadar(swapSevadar.getSevadarsId(), swapSevadarWith.getSevadarsId());
                break;
        }

        setOpacity(Constants.FULL_OPACITY,contextHolder);

    }

    private void addSevadar(int memberId,int teamLeadId) {
        Response response = sevadarService.addSevadar(memberId,teamLeadId);
        Alert alert = CommonUtil.getAlert(response);
        alert.showAndWait();
        refresh();
    }

    private void replaceSevadar(int sevadarId, int memberId) {
        Response response = sevadarService.replaceSevadar(sevadarId, memberId);
        Alert alert = CommonUtil.getAlert(response);
        alert.showAndWait();
        refresh();
    }

    private void swapSevadar(int swapSevadarId, int swapSevadarWith) {
        Response response = sevadarService.swapSevadar(swapSevadarId, swapSevadarWith);
        Alert alert = CommonUtil.getAlert(response);
        alert.showAndWait();
        refresh();
    }

    private enum SevadarAction {
        ADD_SEVADAR,
        REPLACE_SEVADAR,
        DELETE_SEVADAR,
        SWAP_SEVADAR;
    }
}
