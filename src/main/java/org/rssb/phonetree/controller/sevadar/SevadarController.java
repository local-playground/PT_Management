package org.rssb.phonetree.controller.sevadar;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.common.SevaType;
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
import java.util.List;
import java.util.ResourceBundle;

@Component("Sevadar")
@SuppressWarnings({"unused","unchecked"})
@Lazy
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
        ObservableList<Sevadar> sevadarObservableList = FXCollections.observableArrayList(sevadarList);
        sevadarsTableView.setItems(sevadarObservableList);
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
        CommonUtil.handleResponse(this,response, contextHolder, contextHolder1 -> {
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

        CommonUtil.handleResponse(this,response, contextHolder, contextHolder1 -> {
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
        CommonUtil.handleResponse(this,response, null, null);
        refresh();
        setOpacity(Constants.FULL_OPACITY, contextHolder);
    }

    @Override
    public Parent getRootPanel() {
        return teamManagementController.getRootPanel();
    }
}
