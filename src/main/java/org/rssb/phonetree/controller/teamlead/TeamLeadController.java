package org.rssb.phonetree.controller.teamlead;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.rssb.phonetree.common.*;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.sevadar.SevadarController;
import org.rssb.phonetree.controller.teammanagement.TeamManagementController;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
@SuppressWarnings("unused")
public class TeamLeadController extends AbstractController {

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private SevadarController sevadarController;

    @Autowired
    private TeamManagementController teamManagementController;

    @FXML
    private JFXButton addTeamLeadButton;

    @FXML
    private JFXButton removeTeamLeadButton;

    @FXML
    private JFXButton replaceTeamLeadButton;

    @FXML
    private JFXButton swapTeamLeadButton;

    @FXML
    private TableView<TeamLead> teamLeadTableView;

    @FXML
    private TableColumn<TeamLead, String> firstNameTableColumn;

    @FXML
    private TableColumn<TeamLead, String> lastNameTableColumn;

    @FXML
    private TableColumn<TeamLead, String> cellPhoneTableColumn;

    @FXML
    private TableColumn<TeamLead, String> homePhoneTableColumn;

    @FXML
    void addTeamLead(ActionEvent event) {
        ContextHolder contextHolder = createContextHolder(Constants.ACTION,
                TeamLeadAction.ADD_TEAM_LEAD,
                teamManagementController.getTeamManagementRootPane());
        setOpacity(Constants.LOW_OPACITY,contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this, contextHolder, true);
    }

    @FXML
    void removeTeamLead(ActionEvent event) {
        TeamLead teamLead = teamLeadTableView.getSelectionModel().getSelectedItem();
        Alert alert;
        if (teamLead == null) {
            alert = CommonUtil.getAlert("Please select Team Lead you want to delete.", ActionAlertType.ERROR);
            alert.showAndWait();
            return;
        }

        alert = CommonUtil.getAlert("Are you sure you want to remove " +
                teamLead.getTeamLeadName() + " from the list?", ActionAlertType.CONFIRMATION);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                Response response = teamLeadService.deleteTeamLead(teamLead.getTeamLeadId());
                Alert newAlert = CommonUtil.getAlert(response);
                newAlert.showAndWait();
                refresh();
            }
        });

    }

    @FXML
    void replaceTeamLead(ActionEvent event) {
        TeamLead teamLead = teamLeadTableView.getSelectionModel().getSelectedItem();
        if (teamLead == null) {
            Alert alert = CommonUtil.getAlert("Please select Team Lead you want to replace.", ActionAlertType.ERROR);
            alert.showAndWait();
            return;
        }

        ContextHolder contextHolder = createContextHolder(
                new String[]{Constants.ACTION, Constants.REQUEST_OBJ},
                new Object[]{TeamLeadAction.REPLACE_TEAM_LEAD, teamLead},
                teamManagementController.getTeamManagementRootPane());
        setOpacity(Constants.LOW_OPACITY,contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this, contextHolder, true);
    }

    @FXML
    void swapTeamLead(ActionEvent event) {
        ContextHolder contextHolder = createContextHolder(Constants.ACTION,
                TeamLeadAction.SWAP_TEAM_LEAD,
                teamManagementController.getTeamManagementRootPane());
        setOpacity(Constants.LOW_OPACITY,contextHolder);
        stageManager.switchScene(FxmlView.SWAP_TEAM_LEAD, this, contextHolder, true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamLeadTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TeamLead>() {
            @Override
            public void changed(ObservableValue<? extends TeamLead> observable, TeamLead oldValue, TeamLead newValue) {
                if (oldValue == null && newValue == null)
                    return;

                if (oldValue != null && (oldValue.getTeamLeadId() == newValue.getTeamLeadId())) {
                    return;
                }

                triggerChangeEvent();
            }
        });
        firstNameTableColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getMember().getFirstName()));

        lastNameTableColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getMember().getLastName()));

        cellPhoneTableColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getMember().getCellPhone()));

        homePhoneTableColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getMember().getHomePhone()));
        refresh();
    }

    @Override
    public void postProcess() {
        teamLeadTableView.getSelectionModel().select(0);
    }

    private void triggerChangeEvent() {
        TeamLead teamLead = teamLeadTableView.getSelectionModel().getSelectedItem();
        ContextHolder contextHolder = createContextHolder(Constants.REQUEST_OBJ, teamLead,null);
        sevadarController.setContextHolder(contextHolder);
        sevadarController.refresh();
    }

    @Override
    public void refresh() {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        ObservableList<TeamLead> teamLeadsTableList = FXCollections.observableArrayList(teamLeadList);
        teamLeadTableView.setItems(teamLeadsTableList);
    }

    @Override
    public <T> T getSelected(){
        return (T) teamLeadTableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void delegate(ContextHolder contextHolder) {
        TeamLeadAction action = (TeamLeadAction) contextHolder.get(Constants.ACTION);
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        Response response = null;
        Alert alert;
        switch (action) {
            case REPLACE_TEAM_LEAD:
                TeamLead teamLead = (TeamLead) contextHolder.get(Constants.REQUEST_OBJ);
                response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_TEAM_LEAD);
                if (response.getActionAlertType() == ActionAlertType.NONE) {
                    replaceTeamLead(teamLead.getTeamLeadId(), selectedResult.getMemberId());
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

                    replaceTeamLead(teamLead.getTeamLeadId(), selectedResult.getMemberId());
                });
                break;
            case ADD_TEAM_LEAD:
                response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_TEAM_LEAD);
                if (response.getActionAlertType() == ActionAlertType.NONE) {
                    addTeamLead(selectedResult.getMemberId());
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

                    addTeamLead(selectedResult.getMemberId());
                });
                break;
            case SWAP_TEAM_LEAD:
                TeamLead swapTeamLead = (TeamLead) contextHolder.get("SWAP_TEAM_LEAD");
                TeamLead swapTeamLeadWith = (TeamLead) contextHolder.get("SWAP_TEAM_LEAD_WITH");
                swapTeamLead(swapTeamLead.getTeamLeadId(), swapTeamLeadWith.getTeamLeadId());
                break;
        }

        setOpacity(Constants.FULL_OPACITY,contextHolder);

    }

    private void addTeamLead(int memberId) {
        Response response = teamLeadService.addTeamLead(memberId);
        Alert alert = CommonUtil.getAlert(response);
        alert.showAndWait();
        refresh();
    }

    private void replaceTeamLead(int teamLeadId, int memberId) {
        Response response = teamLeadService.replaceTeamLead(teamLeadId, memberId);
        Alert alert = CommonUtil.getAlert(response);
        alert.showAndWait();
        refresh();
    }

    private void swapTeamLead(int swapTeamLeadId, int swapTeamLeadIdWith) {
        Response response = teamLeadService.swapTeamLead(swapTeamLeadId, swapTeamLeadIdWith);
        Alert alert = CommonUtil.getAlert(response);
        alert.showAndWait();
        refresh();
    }



    private enum TeamLeadAction {
        ADD_TEAM_LEAD,
        REPLACE_TEAM_LEAD,
        SWAP_TEAM_LEAD;
    }
}
