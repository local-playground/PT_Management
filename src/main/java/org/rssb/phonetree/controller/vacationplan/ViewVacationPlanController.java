package org.rssb.phonetree.controller.vacationplan;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.SevadarsMonthlyAvailability;
import org.rssb.phonetree.domain.VacationDate;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.SevadarVacation;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.helper.VacationPlanHelper;
import org.rssb.phonetree.services.SevadarVacationService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@Lazy
public class ViewVacationPlanController extends AbstractController {
    @FXML
    private StackPane rootPane;

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private SevadarVacationService sevadarVacationService;

    @FXML
    private JFXComboBox<TeamLead> teamLeadsComboBox;

    @FXML
    private VBox sevadarsAvailabliltyHolder;

    @FXML
    private FlowPane sevadarsMonthlyAvailabilityHolder;

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
    void showSevadarsVacationTime(ActionEvent event) {
        TeamLead teamLead = teamLeadsComboBox.getSelectionModel().getSelectedItem();
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadName(teamLead.getTeamLeadName());
        sevadarsAvailabliltyHolder.getChildren().clear();
        int startMonth=12;
        int endMonth=12;

        for (int index = startMonth; index <=endMonth ; index++) {
            Month month = Month.of(index);
            List<SevadarsMonthlyAvailability> sevadarsMonthlyAvailabilityList = new ArrayList<>();
            for (Sevadar sevadar : sevadarList) {
                Optional<SevadarVacation> sevadarVacationOptional = sevadarVacationService.getSevadarVacationBySevadarId(sevadar.getSevadarsId());
                SevadarVacation sevadarVacation = null;
                List<VacationDate> vacationDateList = new ArrayList<>();
                if (sevadarVacationOptional.isPresent()) {
                    sevadarVacation = sevadarVacationOptional.get();
                    vacationDateList = sevadarVacation.getVacationDateList();
                }

                SevadarsMonthlyAvailability sevadarsMonthlyAvailability =
                        VacationPlanHelper.getSevadarAvailabilityDetails(sevadar.getSevadarName(),
                                vacationDateList,month);

                sevadarsMonthlyAvailabilityList.add(sevadarsMonthlyAvailability);

                ContextHolder ctxHolder = createContextHolder(
                        new String[]{"SEVADAR", Constants.REQUEST_OBJ},
                        new Object[]{sevadar, sevadarVacation},
                        null);
           /* teamVacationTimeHolder.getChildren().add(
                    loadFxml("/fxml/vacation-plan/vacation-dates-template.fxml", ctxHolder,
                            perSevadarVacationPlanTemplateControllerList)
            );*/

            }
        }

    }
}
