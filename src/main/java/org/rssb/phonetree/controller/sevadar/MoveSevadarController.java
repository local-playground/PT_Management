package org.rssb.phonetree.controller.sevadar;

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
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.status.SevadarActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
@SuppressWarnings("unused")
public class MoveSevadarController extends AbstractController{

    @Autowired
    private SevadarService sevadarService;

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private SevadarController sevadarController;

    @FXML
    private StackPane moveSevadarsRootPane;

    @FXML
    private JFXComboBox<TeamLead> moveSevadarUnderTeamLeadComboBox;

    @FXML
    private JFXButton moveSevadarButton;

    @FXML
    private Label movingSevadarName;

    @FXML
    private Label closeButton;
    private ObservableList<TeamLead> moveSevadarUnderTeamLeadList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        moveSevadarUnderTeamLeadList = FXCollections.observableArrayList(teamLeadList);
        moveSevadarUnderTeamLeadComboBox.getItems().addAll(moveSevadarUnderTeamLeadList);
        movingSevadarName.setText(((Sevadar)sevadarController.getSelected()).getSevadarName());
        moveSevadarUnderTeamLeadComboBox.setConverter(new StringConverter<TeamLead>() {
            @Override
            public String toString(TeamLead teamLead) {
                return teamLead.getTeamLeadName();
            }

            @Override
            public TeamLead fromString(String teamLeadName) {
                return moveSevadarUnderTeamLeadList
                        .stream()
                        .filter(teamLead ->teamLead.getTeamLeadName().equalsIgnoreCase(teamLeadName))
                        .findFirst()
                        .get();

            }
        });
    }


    @FXML
    void closeButton(MouseEvent event) {
        closeScreen(event,this.contextHolder);
    }

    @FXML
    void moveSevadar(ActionEvent event) {
        TeamLead moveUnderTeamLead = moveSevadarUnderTeamLeadComboBox.getSelectionModel().getSelectedItem();
        Sevadar sevadarToBeMoved = sevadarController.getSelected();
        movingSevadarName.setText(sevadarToBeMoved.getSevadarName());

        if(sevadarToBeMoved.getTeamLead().getTeamLeadId() == moveUnderTeamLead.getTeamLeadId()){
            CommonUtil.showNoActionNeededJFXDialog(this::getRootPanel,
                    new Object[]{sevadarToBeMoved.getSevadarName(),moveUnderTeamLead.getTeamLeadName()},
                    SevadarActionResponse.SEVADAR_CANNOT_MOVE_UNDER_SAME_TEAM_LEAD);
            return;
        }

        this.contextHolder.set(
                new String[]{"MOVE_SEVADAR","MOVE_UNDER_TEAM_LEAD"},
                new Object[]{sevadarToBeMoved,moveUnderTeamLead});
        closeScreen(event,contextHolder);
        this.delegator.delegate(this.contextHolder);
        this.contextHolder = null;
        this.delegator = null;
    }

    @Override
    public Parent getRootPanel(){
        return moveSevadarsRootPane;
    }
}
