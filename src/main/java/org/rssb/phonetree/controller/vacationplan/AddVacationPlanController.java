package org.rssb.phonetree.controller.vacationplan;


import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.SevadarVacation;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.SevadarVacationService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@Lazy
public class AddVacationPlanController extends AbstractController {

    private List<PerSevadarVacationPlanTemplateController> perSevadarVacationPlanTemplateControllerList = new ArrayList<>();

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private SevadarVacationService sevadarVacationService;

    @FXML
    private JFXComboBox<TeamLead> teamLeadsComboBox;

   /* @FXML
    private JFXButton saveButton;*/

    @FXML
    private VBox teamVacationTimeHolder;

    private ObservableList<TeamLead> teamLeadObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        teamLeadObservableList = FXCollections.observableArrayList(teamLeadList);
        teamLeadsComboBox.getItems().addAll(teamLeadObservableList);
        teamLeadsComboBox.setConverter(new StringConverter<TeamLead>() {
            @Override
            public String toString(TeamLead teamLead) {
                return teamLead.getTeamLeadName();
            }

            @Override
            public TeamLead fromString(String teamLeadName) {
                return teamLeadObservableList
                        .stream()
                        .filter(teamLead -> teamLead.getTeamLeadName().equalsIgnoreCase(teamLeadName))
                        .findFirst().get();

            }
        });
    }

    @FXML
    void saveData(ActionEvent event) {
        for(PerSevadarVacationPlanTemplateController perSevadarVacationPlanTemplateController : perSevadarVacationPlanTemplateControllerList){
            if(!perSevadarVacationPlanTemplateController.validate()){
                return;
            }
        }

        for(PerSevadarVacationPlanTemplateController perSevadarVacationPlanTemplateController : perSevadarVacationPlanTemplateControllerList){
            SevadarVacation sevadarVacation = perSevadarVacationPlanTemplateController.getRequest();
            if(sevadarVacation!=null) {
                sevadarVacationService.save(sevadarVacation);
            }
        }

        perSevadarVacationPlanTemplateControllerList.forEach(PerSevadarVacationPlanTemplateController::refresh);

    }

    @FXML
    void showSevadarsVacationTime(ActionEvent event) {
        TeamLead teamLead = teamLeadsComboBox.getSelectionModel().getSelectedItem();
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadName(teamLead.getTeamLeadName());
        teamVacationTimeHolder.getChildren().clear();
        perSevadarVacationPlanTemplateControllerList.clear();
        for (Sevadar sevadar : sevadarList) {
            Optional<SevadarVacation> sevadarVacationOptional = sevadarVacationService.getSevadarVacationBySevadarId(sevadar.getSevadarsId());
            SevadarVacation sevadarVacation = null;
            if(sevadarVacationOptional.isPresent()){
                sevadarVacation=sevadarVacationOptional.get();
            }
            ContextHolder ctxHolder = createContextHolder(
                    new String[]{"SEVADAR", Constants.REQUEST_OBJ},
                    new Object[]{sevadar,sevadarVacation},
                    null);
            teamVacationTimeHolder.getChildren().add(
                    loadFxml("/fxml/vacation-plan/vacation-dates-template.fxml", ctxHolder,
                            perSevadarVacationPlanTemplateControllerList)
            );

        }
    }
}
