package org.rssb.phonetree.controller.teamlead;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.common.SevaType;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.SearchResult;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.ui.view.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class TeamLeadController extends AbstractController implements Initializable {

    @Autowired
    private TeamLeadService teamLeadService;

    @FXML
    private JFXButton addTeamLeadButton;

    @FXML
    private JFXButton removeTeamLeadButton;

    @FXML
    private JFXButton swapTeamLeadButton;

    @FXML
    private JFXButton replaceTeamLeadButton;

    @FXML
    private TableView<TeamLead> teamLeadTableView;

    @FXML
    private TableColumn<TeamLead, String> teamLeadNameTextField;

    @FXML
    void addTeamLead(ActionEvent event) {
        ContextHolder<TeamLeadAction,SearchResult> contextHolder = createContextHolder(TeamLeadAction.ADD_TEAM_LEAD,null);
        stageManager.switchScene(FxmlView.SEARCH,this,contextHolder,true);
    }

    @FXML
    void removeTeamLead(ActionEvent event) {

    }

    @FXML
    void replaceTeamLead(ActionEvent event) {

    }

    @FXML
    void swapTeamLead(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        teamLeadTableView.setOnMousePressed(event -> {
            if(event.isPrimaryButtonDown()){
                System.out.println(teamLeadTableView.getSelectionModel().getSelectedItem());
            }
        });
        teamLeadNameTextField.setCellValueFactory(new PropertyValueFactory<TeamLead,String>("teamLeadName"));
        refresh();
    }

    @Override
    public void refresh() {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        ObservableList<TeamLead> teamLeadsTableList = FXCollections.observableArrayList(teamLeadList);
        teamLeadTableView.setItems(teamLeadsTableList);
    }

    @Override
    public void delegate(ContextHolder contextHolder) {
        TeamLeadAction action = (TeamLeadAction) contextHolder.getRequest();
        SearchResult selectedResult = (SearchResult) contextHolder.getResponse();

        switch (action){
            case REPLACE_TEAM_LEAD:
                break;
            case ADD_TEAM_LEAD:
                Response response = utilityService.isMemberAvailableForSeva(selectedResult.getMemberId(), SevaType.ADD_TEAM_LEAD);
                if(response.getActionAlertType()!= ActionAlertType.NONE){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Testing");
                    alert.setContentText(response.getMessage());
                    Optional<ButtonType> result = alert.showAndWait();

                }
                break;
            case SWAP_TEAM_LEAD:
                break;
            case REMOVE_TEAM_LEAD:
                break;
        }

    }

    private enum TeamLeadAction{
        ADD_TEAM_LEAD,
        REPLACE_TEAM_LEAD,
        SWAP_TEAM_LEAD,
        REMOVE_TEAM_LEAD;
    }
}
