package org.rssb.phonetree.controller.sevadar;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.rssb.phonetree.common.*;
import org.rssb.phonetree.common.table.factory.TableRowContextMenuFactory;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.teamlead.TeamLeadController;
import org.rssb.phonetree.controller.teammanagement.TeamManagementController;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.status.SevadarActionResponse;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Component("Sevadar")
@SuppressWarnings({"unused", "unchecked"})
@Lazy
public class SevadarController extends AbstractController {

    private final SevadarService sevadarService;

    private final TeamLeadService teamLeadService;

    private final TeamLeadController teamLeadController;

    private final TeamManagementController teamManagementController;

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

    @Autowired
    public SevadarController(SevadarService sevadarService, TeamLeadService teamLeadService, TeamLeadController teamLeadController, TeamManagementController teamManagementController) {
        this.sevadarService = sevadarService;
        this.teamLeadService = teamLeadService;
        this.teamLeadController = teamLeadController;
        this.teamManagementController = teamManagementController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sevadarsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        firstNameTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMember().getFirstName()));

        lastNameTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMember().getLastName()));

        cellPhoneTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMember().getCellPhone()));

        homePhoneTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMember().getHomePhone()));

        Map<String, EventHandler<ActionEvent>> contextMenuActionMap = new HashMap<>();
        contextMenuActionMap.put("Add/Change Email Id", this::addSevadarEmailId);
        contextMenuActionMap.put("Make Team Lead's Backup", this::makeTeamLeadsBackUp);

        sevadarsTableView.setRowFactory(new TableRowContextMenuFactory<>(contextMenuActionMap));
    }

    private void makeTeamLeadsBackUp(ActionEvent actionEvent) {
        teamLeadController.makeTeamLeadsBackUp();
        /*TeamLead teamLead = teamLeadController.getSelected();
        List<Sevadar> sevadarList = teamLead.getSevadarsList();
        Sevadar existingBackupLead = null;
        for (Sevadar sevadar : sevadarList) {
            if(sevadar.getIsBackupForTeamLead()==1){
                existingBackupLead = sevadar;
                break;
            }
        }
        Sevadar sevadar = sevadarsTableView.getSelectionModel().getSelectedItem();
        if(existingBackupLead!=null) {
            if (sevadar.getSevadarsId() == existingBackupLead.getSevadarsId()) {
                CommonUtil.showNoActionNeededJFXDialog(this,
                        new Object[]{sevadar.getSevadarName(), teamLead.getTeamLeadName() + "'s"},
                        SevadarActionResponse.SEVADAR_IS_ALREADY_TEAM_LEAD_BACKUP);
                return;
            }
            CommonUtil.showConfirmationJFXDialog(this,
                    new Object[]{sevadar.getSevadarName(), existingBackupLead.getSevadarName()},
                    SevadarActionResponse.SEVADAR_CONFIRM_BEFORE_CHANGE_TEAM_LEAD_BACKUP,
                    null,
                    contextHolder1 -> {
                        Response response = sevadarService.makeTeamLeadsBackup(sevadar.getSevadarsId(),
                                teamLead.getTeamLeadName(),teamLead.getTeamLeadId());
                        refresh();
                        return response;
                    });
        }
*/
    }

    private void addSevadarEmailId(ActionEvent actionEvent) {
        List<Sevadar> sevadarList = sevadarsTableView.getSelectionModel().getSelectedItems();
        ContextHolder contextHolder = createContextHolder(
                new String[]{Constants.REQUEST_OBJ},
                new Object[]{sevadarList},
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SEVADAR_ADD_EMAIL, null, contextHolder, true);
    }

    @FXML
    void addSevadar(ActionEvent event) {
        TeamLead teamLead = teamLeadController.getSelected();
        if (teamLead == null) {
            CommonUtil.showNoActionNeededJFXDialog(this, null, SevadarActionResponse.SEVADAR_SELECT_TEAM_LEAD_BEFORE_ACTION);
            return;
        }

        ContextHolder contextHolder = createContextHolder(
                new String[]{Constants.REQUEST_OBJ},
                new Object[]{teamLead},
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this::addSevadar, contextHolder, true);
    }

    @FXML
    void deleteSevadar(ActionEvent event) {
        Sevadar sevadar = sevadarsTableView.getSelectionModel().getSelectedItem();
        if (sevadar == null) {
            CommonUtil.showNoActionNeededJFXDialog(this, null, SevadarActionResponse.SEVADAR_SELECT_BEFORE_ACTION);
            return;
        }

        CommonUtil.showConfirmationJFXDialog(this,
                new Object[]{sevadar.getSevadarName()},
                SevadarActionResponse.SEVADAR_CONFIRM_BEFORE_REMOVE,
                null,
                contextHolder1 -> {
                    Response response = sevadarService.deleteSevadar(sevadar.getSevadarsId());
                    refresh();
                    return response;
                });
    }

    @FXML
    void moveSevadar(ActionEvent event) {
        Sevadar sevadar = sevadarsTableView.getSelectionModel().getSelectedItem();
        if (sevadar == null) {
            CommonUtil.showNoActionNeededJFXDialog(this, null, SevadarActionResponse.SEVADAR_SELECT_BEFORE_ACTION);
            return;
        }
        ContextHolder contextHolder = createContextHolder("",
                null,
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.MOVE_SEVADAR, this::moveSevadar, contextHolder, true);

    }

    @FXML
    void replaceSevadar(ActionEvent event) {
        Sevadar sevadar = sevadarsTableView.getSelectionModel().getSelectedItem();
        if (sevadar == null) {
            CommonUtil.showNoActionNeededJFXDialog(this, null, SevadarActionResponse.SEVADAR_SELECT_BEFORE_ACTION);
            return;
        }
        ContextHolder contextHolder = createContextHolder(
                new String[]{"", Constants.REQUEST_OBJ, "TEAM_LEAD"},
                new Object[]{null, sevadar, teamLeadController.getSelected()},
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this::replaceSevadar, contextHolder, true);
    }

    @FXML
    void swapSevadar(ActionEvent event) {
        ContextHolder contextHolder = createContextHolder("",
                null,
                getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SWAP_SEVADAR, this::swapSevadar, contextHolder, true);
    }


    @Override
    public void refresh() {
        TeamLead teamLead = (TeamLead) contextHolder.get(Constants.REQUEST_OBJ);
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadId(teamLead.getTeamLeadId());
        sevadarsTableView.getItems().clear();
        ObservableList<Sevadar> sevadarObservableList = FXCollections.observableArrayList(sevadarList);
        sevadarsTableView.setItems(sevadarObservableList);
        sevadarsTableView.refresh();
        refreshBarChart(teamLead);
    }

    private void refreshBarChart(TeamLead teamLead) {
        List<FamilyCount> familyCountList = sevadarService.getSevadarsCallingFamilyCountByTeamLeadId(teamLead.getTeamLeadId());
        pieChart.getData().clear();
        familyCountList.forEach(familyCount -> {
            PieChart.Data data = new PieChart.Data(familyCount.getName(), familyCount.getCount());
            pieChart.getData().add(data);
        });

        pieChart.getData().forEach(data -> data.nameProperty()
                .bind(Bindings.concat(data.getName() + " , " + (int) data.getPieValue())));
    }

    private void addSevadar(ContextHolder contextHolder) {
        TeamLead teamLead = (TeamLead) contextHolder.get(Constants.REQUEST_OBJ);
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        Response response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_SEVADAR);
        CommonUtil.handleResponse(this, response, contextHolder, contextHolder1 -> {
            Response newResponse = sevadarService.addSevadar(selectedResult.getMemberId(), teamLead.getTeamLeadId());
            refresh();
            return newResponse;
        });
        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    private void replaceSevadar(ContextHolder contextHolder) {
        Sevadar sevadar = (Sevadar) contextHolder.get(Constants.REQUEST_OBJ);
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        Response response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_SEVADAR);

        CommonUtil.handleResponse(this, response, contextHolder, contextHolder1 -> {
            Response newResponse = sevadarService.replaceSevadar(sevadar.getSevadarsId(), selectedResult.getMemberId());
            refresh();
            return newResponse;
        });
        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    private void swapSevadar(ContextHolder contextHolder) {
        Sevadar swapSevadar = (Sevadar) contextHolder.get("SWAP_SEVADAR");
        Sevadar swapSevadarWith = (Sevadar) contextHolder.get("SWAP_SEVADAR_WITH");
        Response response = sevadarService.swapSevadar(swapSevadar.getSevadarsId(), swapSevadarWith.getSevadarsId());
        CommonUtil.handleResponse(this, response, null, null);
        refresh();
        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    private void moveSevadar(ContextHolder contextHolder) {
        Sevadar sevadarToBeMoved = (Sevadar) contextHolder.get("MOVE_SEVADAR");
        TeamLead teamLead = (TeamLead) contextHolder.get("MOVE_UNDER_TEAM_LEAD");
        Response response = sevadarService.moveSevadarUnderOtherTeamLead(sevadarToBeMoved.getSevadarsId(), teamLead.getTeamLeadId());
        CommonUtil.handleResponse(this, response, null, null);
        refresh();
        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    @Override
    public Parent getRootPanel() {
        return teamManagementController.getRootPanel();
    }

    @Override
    public <T> T getSelected() {
        return (T) sevadarsTableView.getSelectionModel().getSelectedItem();
    }
}
