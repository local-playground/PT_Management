package org.rssb.phonetree.controller.teamlead;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.common.SevaType;
import org.rssb.phonetree.common.table.factory.TableRowContextMenuFactory;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.sevadar.SevadarController;
import org.rssb.phonetree.controller.teammanagement.TeamManagementController;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.status.SevadarActionResponse;
import org.rssb.phonetree.status.TeamLeadActionResponse;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Component
@Lazy
@SuppressWarnings({"unused", "unchecked"})
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
        ContextHolder contextHolder = createContextHolder("",
                null,
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this::addTeamLead, contextHolder, true);
    }

    @FXML
    void removeTeamLead(ActionEvent event) {
        TeamLead teamLead = teamLeadTableView.getSelectionModel().getSelectedItem();
        if (teamLead == null) {
            CommonUtil.showNoActionNeededJFXDialog(this, null, TeamLeadActionResponse.TEAM_LEAD_SELECT_BEFORE_ACTION);
            return;
        }

        CommonUtil.showConfirmationJFXDialog(this,
                new Object[]{teamLead.getTeamLeadName()},
                TeamLeadActionResponse.TEAM_LEAD_CONFIRM_BEFORE_REMOVE,
                null,
                contextHolder1 -> {
                    Response response = teamLeadService.deleteTeamLead(teamLead.getTeamLeadId());
                    refresh();
                    return response;
                });
    }

    @FXML
    void replaceTeamLead(ActionEvent event) {
        TeamLead teamLead = teamLeadTableView.getSelectionModel().getSelectedItem();
        if (teamLead == null) {
            CommonUtil.showNoActionNeededJFXDialog(this, null, TeamLeadActionResponse.TEAM_LEAD_SELECT_BEFORE_ACTION);
            return;
        }

        ContextHolder contextHolder = createContextHolder(
                new String[]{Constants.REQUEST_OBJ},
                new Object[]{teamLead},
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this::replaceTeamLead, contextHolder, true);
    }

    @FXML
    void swapTeamLead(ActionEvent event) {
        ContextHolder contextHolder = createContextHolder("", null,
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SWAP_TEAM_LEAD, this::swapTeamLead, contextHolder, true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamLeadTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        teamLeadTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TeamLead>() {
            @Override
            public void changed(ObservableValue<? extends TeamLead> observable, TeamLead oldValue, TeamLead newValue) {
                if (oldValue == null && newValue == null)
                    return;

                if (oldValue != null && newValue != null && (oldValue.getTeamLeadId() == newValue.getTeamLeadId())) {
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

        Map<String, EventHandler<ActionEvent>> contextMenuActionMap = new HashMap<>();
        contextMenuActionMap.put("Add/Change Email Id",this::addTeamLeadEmailId);

        teamLeadTableView.setRowFactory(new TableRowContextMenuFactory<>(contextMenuActionMap));
        refresh();
    }

    private void addTeamLeadEmailId(ActionEvent event){
        List<TeamLead> teamLeadList = teamLeadTableView.getSelectionModel().getSelectedItems();
        ContextHolder contextHolder = createContextHolder(
                new String[]{Constants.REQUEST_OBJ},
                new Object[]{teamLeadList},
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.TEAM_LEAD_ADD_EMAIL, null, contextHolder, true);
    }

    public void makeTeamLeadsBackUp(){
        TeamLead teamLead = teamLeadTableView.getSelectionModel().getSelectedItem();
        List<Sevadar> sevadarList = teamLead.getSevadarsList();
        Sevadar existingBackupLead = null;
        for (Sevadar sevadar : sevadarList) {
            if(sevadar.getIsBackupForTeamLead()==1){
                existingBackupLead = sevadar;
                break;
            }
        }
        Sevadar sevadar = sevadarController.getSelected();
        if(existingBackupLead!=null) {
            if (sevadar.getSevadarsId() == existingBackupLead.getSevadarsId()) {
                CommonUtil.showNoActionNeededJFXDialog(this,
                        new Object[]{sevadar.getSevadarName(), teamLead.getTeamLeadName() + "'s"},
                        SevadarActionResponse.SEVADAR_IS_ALREADY_TEAM_LEAD_BACKUP);
                return;
            }
            /*CommonUtil.showConfirmationJFXDialog(this,
                    new Object[]{sevadar.getSevadarName(), existingBackupLead.getSevadarName()},
                    SevadarActionResponse.SEVADAR_CONFIRM_BEFORE_CHANGE_TEAM_LEAD_BACKUP,
                    null,
                    contextHolder1 -> {
                        Response response = sevadarService.makeTeamLeadsBackup(sevadar.getSevadarsId(),
                                teamLead.getTeamLeadName(),teamLead.getTeamLeadId());
                        refresh();
                        return response;
                    });*/
        }

    }

    @Override
    public void postProcess() {
        teamLeadTableView.getSelectionModel().select(0);
    }

    private void triggerChangeEvent() {
        List<TeamLead> teamLeadsList = teamLeadTableView.getSelectionModel().getSelectedItems();
        System.out.println("triggered event = "+ teamLeadsList.size());
        if(teamLeadsList.size()==1) {
            ContextHolder contextHolder = createContextHolder(Constants.REQUEST_OBJ, teamLeadsList.get(0), null);
            sevadarController.setContextHolder(contextHolder);
            sevadarController.refresh();
        }
    }

    @Override
    public void refresh() {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        ObservableList<TeamLead> teamLeadsTableList = FXCollections.observableArrayList(teamLeadList);
        teamLeadTableView.setItems(teamLeadsTableList);
        teamLeadTableView.getSelectionModel().select(0);
    }

    @Override
    public <T> T getSelected() {
        return (T) teamLeadTableView.getSelectionModel().getSelectedItem();
    }


    private void addTeamLead(ContextHolder contextHolder) {
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        Response response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_TEAM_LEAD);
        CommonUtil.handleResponse(this, response, contextHolder,
                contextHolder1 -> {
                    Response newResponse = teamLeadService.addTeamLead(selectedResult.getMemberId());
                    refresh();
                    return newResponse;
                });

        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    private void replaceTeamLead(ContextHolder contextHolder) {
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        TeamLead teamLead = (TeamLead) contextHolder.get(Constants.REQUEST_OBJ);
        Response response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_TEAM_LEAD);

        CommonUtil.handleResponse(this, response, contextHolder,
                contextHolder1 -> {
                    Response newResponse = teamLeadService.replaceTeamLead(teamLead.getTeamLeadId(), selectedResult.getMemberId());
                    refresh();
                    return newResponse;
                });

        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    private void swapTeamLead(ContextHolder contextHolder) {
        TeamLead swapTeamLead = (TeamLead) contextHolder.get("SWAP_TEAM_LEAD");
        TeamLead swapTeamLeadWith = (TeamLead) contextHolder.get("SWAP_TEAM_LEAD_WITH");
        Response response = teamLeadService.swapTeamLead(swapTeamLead.getTeamLeadId(), swapTeamLeadWith.getTeamLeadId());
        CommonUtil.handleResponse(this, response, null, null);
        refresh();
        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    @Override
    public Parent getRootPanel() {
        return teamManagementController.getRootPanel();
    }

}
