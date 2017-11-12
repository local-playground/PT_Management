package org.rssb.phonetree.controller.backup.sevadar;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.common.SevaType;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.teamlead.TeamLeadController;
import org.rssb.phonetree.controller.teammanagement.TeamManagementController;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.entity.BackupSevadar;
import org.rssb.phonetree.services.BackupSevadarService;
import org.rssb.phonetree.status.SevadarActionResponse;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class BackupSevadarController extends AbstractController {

    @Autowired
    private TeamLeadController teamLeadController;

    @Autowired
    private TeamManagementController teamManagementController;

    @Autowired
    private BackupSevadarService backupSevadarService;

    @FXML
    private JFXButton addBackupSevadarButton;

    @FXML
    private JFXButton removeBackupSevadarButton;

    @FXML
    private JFXButton assignToTeamLeadButton;

    @FXML
    private TableView<BackupSevadar> backupSevadarsTableView;

    @FXML
    private TableColumn<BackupSevadar, String> firstNameTableColumn;

    @FXML
    private TableColumn<BackupSevadar, String> lastNameTableColumn;

    @FXML
    private TableColumn<BackupSevadar, String> cellPhoneTableColumn;


    @FXML
    void addBackupSevadar(ActionEvent event) {
        ContextHolder contextHolder = createContextHolder("", "", getRootPanel());
        setOpacity(Constants.LOW_OPACITY, contextHolder);
        stageManager.switchScene(FxmlView.SEARCH, this::addBackupSevadar, contextHolder, true);
    }

    public void addBackupSevadar(ContextHolder contextHolder) {
        SearchResult selectedResult = (SearchResult) contextHolder.get(Constants.RESPONSE_OBJ);
        Response response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_BACKUP_SEVADAR);
        CommonUtil.handleResponse(this, response, contextHolder, contextHolder1 -> {
            Response newResponse = backupSevadarService.addAsBackupSevadar(selectedResult.getMemberId());
            refresh();
            return newResponse;
        });
        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    @FXML
    void assignToTeamLead(ActionEvent event) {

    }

    @FXML
    void removeBackupSevadar(ActionEvent event) {
        BackupSevadar backupSevadar = backupSevadarsTableView.getSelectionModel().getSelectedItem();
        if (backupSevadar == null) {
            CommonUtil.showNoActionNeededJFXDialog(this, null, SevadarActionResponse.SEVADAR_SELECT_TEAM_LEAD_BEFORE_ACTION);
            return;
        }

        CommonUtil.showConfirmationJFXDialog(this,
                new Object[]{backupSevadar.getSevadarName()},
                SevadarActionResponse.SEVADAR_CONFIRM_BEFORE_REMOVE,
                null,
                contextHolder1 -> {
                    Response response = backupSevadarService.removeBackupSevadar(backupSevadar.getMember().getMemberId());
                    refresh();
                    return response;
                });
    }

    @Override
    public void refresh() {
        List<BackupSevadar> backupSevadarList = backupSevadarService.findAllBackupSevadars();
        ObservableList<BackupSevadar> backupSevadarObservableList = FXCollections.observableArrayList(backupSevadarList);
        backupSevadarsTableView.setItems(backupSevadarObservableList);
    }

    @Override
    public Parent getRootPanel() {
        return teamManagementController.getRootPanel();
    }

    @Override
    public <T> T getSelected() {
        return (T) backupSevadarsTableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMember().getFirstName()));

        lastNameTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMember().getLastName()));

        cellPhoneTableColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMember().getCellPhone()));
        refresh();
    }
}
