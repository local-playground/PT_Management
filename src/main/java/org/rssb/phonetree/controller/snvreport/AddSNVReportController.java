package org.rssb.phonetree.controller.snvreport;


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
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class AddSNVReportController extends AbstractController {

    private List<PerSevadarSNVReportTemplateController> perSevadarSNVReportTemplateControllerList = new ArrayList<>();

    @Autowired
    private TeamLeadService teamLeadService;

    @FXML
    private JFXComboBox<TeamLead> teamLeadsComboBox;

    @FXML
    private VBox teamSNVDetailsHolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        ObservableList<TeamLead> teamLeadObservableList = FXCollections.observableArrayList(teamLeadList);
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
        /*for(PerSevadarSNVReportTemplateController perSevadarSNVReportTemplateController : perSevadarSNVReportTemplateControllerList){
            if(!perSevadarSNVReportTemplateController.validate()){
                return;
            }
        }

        for(PerSevadarSNVReportTemplateController perSevadarSNVReportTemplateController : perSevadarSNVReportTemplateControllerList){
            SevadarVacation sevadarVacation = perSevadarSNVReportTemplateController.getRequest();
            if(sevadarVacation!=null) {
                sevadarVacationService.save(sevadarVacation);
            }
        }*/

        //perSevadarSNVReportTemplateControllerList.forEach(PerSevadarSNVReportTemplateController::refresh);

    }

    @FXML
    void showSevadarsVacationTime(ActionEvent event) {
        TeamLead teamLead = teamLeadsComboBox.getSelectionModel().getSelectedItem();
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadName(teamLead.getTeamLeadName());
        teamSNVDetailsHolder.getChildren().clear();
        perSevadarSNVReportTemplateControllerList.clear();
        for (Sevadar sevadar : sevadarList) {
            /*Optional<SevadarVacation> sevadarVacationOptional = sevadarVacationService.getSevadarVacationBySevadarId(sevadar.getSevadarsId());
            SevadarVacation sevadarVacation = null;
            if(sevadarVacationOptional.isPresent()){
                sevadarVacation=sevadarVacationOptional.get();
            }*/
            ContextHolder ctxHolder = createContextHolder(
                    new String[]{"SEVADAR", Constants.REQUEST_OBJ},
                    new Object[]{sevadar,null},
                    null);
            teamSNVDetailsHolder.getChildren().add(
                    loadFxml("/fxml/snv-report/sevadar-snv-report-template.fxml", ctxHolder,
                            perSevadarSNVReportTemplateControllerList)
            );

        }
    }
}
