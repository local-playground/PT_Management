package org.rssb.phonetree.controller.vacationplan;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.time.LocalDate;
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
        LocalDate startMonth = LocalDate.of(2017, 12, 01);
        LocalDate endMonth = LocalDate.of(2018, 02, 01);

        for (Sevadar sevadar : sevadarList) {
            Optional<SevadarVacation> sevadarVacationOptional = sevadarVacationService.getSevadarVacationBySevadarId(sevadar.getSevadarsId());
            SevadarVacation sevadarVacation = null;
            List<VacationDate> vacationDateList = new ArrayList<>();
            if (sevadarVacationOptional.isPresent()) {
                sevadarVacation = sevadarVacationOptional.get();
                vacationDateList = sevadarVacation.getVacationDateList();
            }

            List<SevadarsMonthlyAvailability> sevadarsMonthlyAvailabilityList =
                    VacationPlanHelper.getSevadarAvailabilityDetails(sevadar.getSevadarName(),
                            vacationDateList, startMonth, endMonth);


            ContextHolder ctxHolder = createContextHolder(
                    new String[]{Constants.REQUEST_OBJ, "SEVADAR"},
                    new Object[]{sevadarsMonthlyAvailabilityList, sevadar},
                    null);

            sevadarsAvailabliltyHolder.getChildren().add(loadFxml("/fxml/vacation-plan/sevadar-vacation-plan-template.fxml", ctxHolder, null));
        }

    }
}
