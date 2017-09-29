package org.rssb.phonetree.controller.sevadarslist;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.FamilyService;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class SevadarsListController extends AbstractController {

    @Autowired
    private SevadarService sevadarService;

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private FamilyService familyService;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXComboBox<TeamLead> teamLeadsComboBox;

    @FXML
    private JFXComboBox<Sevadar> sevadarsComboBox;

    @FXML
    private TableView<CalledFamilyDetails> sevadarsListTableView;

    private ObservableList<TeamLead> teamLeadObservableList;
    private ObservableList<Sevadar> sevadarObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        teamLeadObservableList = FXCollections.observableArrayList(teamLeadList);
        teamLeadsComboBox.getItems().addAll(teamLeadObservableList);
        sevadarsComboBox.setDisable(true);

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

        sevadarsComboBox.setConverter(new StringConverter<Sevadar>() {
            @Override
            public String toString(Sevadar sevadar) {
                return sevadar.getSevadarName();
            }

            @Override
            public Sevadar fromString(String sevadarName) {
                return sevadarObservableList
                        .stream()
                        .filter(sevadar -> sevadar.getSevadarName().equalsIgnoreCase(sevadarName))
                        .findFirst().get();

            }
        });
    }

    @FXML
    void changeSevadarsComboList(ActionEvent event) {
        sevadarsComboBox.setDisable(false);
        TeamLead teamLead = teamLeadsComboBox.getSelectionModel().getSelectedItem();
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadName(teamLead.getTeamLeadName());
        sevadarObservableList = FXCollections.observableArrayList(sevadarList);
        sevadarsComboBox.getItems().clear();
        sevadarsComboBox.getItems().addAll(sevadarObservableList);
    }

    @FXML
    void loadSevadarsPhoneList(ActionEvent event) {
        TeamLead teamLead = teamLeadsComboBox.getSelectionModel().getSelectedItem();
        Sevadar sevadar = sevadarsComboBox.getSelectionModel().getSelectedItem();
        SevadarPhoneTreeList sevadarPhoneTreeList =
                familyService.getSevadarPhoneTreeListByTeamLeadAndSevadarName(teamLead.getTeamLeadName(),
                        sevadar.getSevadarName());

        ObservableList<CalledFamilyDetails> calledFamilyDetailsObservableList =
                FXCollections.observableArrayList(sevadarPhoneTreeList.getCalledFamilyDetailsList());

        sevadarsListTableView.setItems(calledFamilyDetailsObservableList);
    }
}
