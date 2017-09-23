package org.rssb.phonetree.controller.teamlead;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.status.TeamLeadActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class SwapTeamLeadController extends AbstractController{
    @FXML
    private StackPane swapTeamLeadRootPane;

    @FXML
    private JFXComboBox<TeamLead> swapTeamLeadComboBox;

    @FXML
    private JFXComboBox<TeamLead> swapTeamLeadWithComboBox;

    @FXML
    private JFXButton swapTeamLeadsButton;

    @FXML
    private Label closeButton;



    @Autowired
    private TeamLeadService teamLeadService;

    private ObservableList<TeamLead> swapTeamLeadsList;
    private ObservableList<TeamLead> swapTeamLeadsWithList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        swapTeamLeadsList = FXCollections.observableArrayList(teamLeadList);
        swapTeamLeadsWithList = FXCollections.observableArrayList(teamLeadList);
        swapTeamLeadComboBox.getItems().addAll(swapTeamLeadsList);
        swapTeamLeadWithComboBox.getItems().addAll(swapTeamLeadsWithList);

        swapTeamLeadComboBox.setConverter(new StringConverter<TeamLead>() {
            @Override
            public String toString(TeamLead teamLead) {
                return teamLead.getTeamLeadName();
            }

            @Override
            public TeamLead fromString(String teamLeadName) {
                return swapTeamLeadsList
                        .stream()
                        .filter(teamLead ->teamLead.getTeamLeadName().equalsIgnoreCase(teamLeadName))
                        .findFirst().get();

            }
        });

        swapTeamLeadWithComboBox.setConverter(new StringConverter<TeamLead>() {
            @Override
            public String toString(TeamLead teamLead) {
                return teamLead.getTeamLeadName();
            }

            @Override
            public TeamLead fromString(String teamLeadName) {
                return swapTeamLeadsList
                        .stream()
                        .filter(teamLead ->teamLead.getTeamLeadName().equalsIgnoreCase(teamLeadName))
                        .findFirst().get();

            }
        });
    }

    @FXML
    void swapTeamLeads(ActionEvent event) {
        TeamLead swapTeamLead = swapTeamLeadComboBox.getSelectionModel().getSelectedItem();
        TeamLead swapTeamLeadWith = swapTeamLeadWithComboBox.getSelectionModel().getSelectedItem();
        if(swapTeamLead.getTeamLeadId() == swapTeamLeadWith.getTeamLeadId()){
            CommonUtil.showNoActionNeededJFXDialog(this::getRootPanel,
                    null,
                    TeamLeadActionResponse.TEAM_LEAD_SELECT_DIFFERENT_TEAMLEADS_TO_SWAP);
            return;
        }

        this.contextHolder.set(
                new String[]{"SWAP_TEAM_LEAD","SWAP_TEAM_LEAD_WITH"},
                new Object[]{swapTeamLead,swapTeamLeadWith});
        closeScreen(event,contextHolder);
        this.delegator.delegate(this.contextHolder);
        this.contextHolder = null;
        this.delegator = null;
    }

    @FXML
    void closeButton(MouseEvent event) {
        closeScreen(event,this.contextHolder);
    }

    @Override
    public Parent getRootPanel(){
        return swapTeamLeadRootPane;
    }

}
