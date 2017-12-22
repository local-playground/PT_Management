package org.rssb.phonetree.controller.snvreport;


import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.custom.controls.LocalDateFormatter;
import org.rssb.phonetree.custom.controls.TeamLeadComboBoxFormatter;
import org.rssb.phonetree.entity.PhoneTreeActivation;
import org.rssb.phonetree.entity.PhoneTreeActivationDetail;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.PhoneTreeActivationService;
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
public class AddSNVReportController extends AbstractController {


    private List<PerSevadarSNVReportTemplateController> perSevadarSNVReportTemplateControllerList = new ArrayList<>();

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private PhoneTreeActivationService phoneTreeActivationService;

    @FXML
    private JFXComboBox<TeamLead> teamLeadsComboBox;

    @FXML
    private VBox teamSNVDetailsHolder;

    @FXML
    private JFXDatePicker activationDatePicker;

    @FXML
    private JFXTimePicker activationTime;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activationDatePicker.setConverter(new LocalDateFormatter());
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        ObservableList<TeamLead> teamLeadObservableList = FXCollections.observableArrayList(teamLeadList);
        teamLeadsComboBox.getItems().addAll(teamLeadObservableList);
        teamLeadsComboBox.setConverter(new TeamLeadComboBoxFormatter(teamLeadObservableList));

        activationDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (CommonUtil.isValidDate(newValue)) {
                activationTime.setDisable(false);
            } else {
                activationTime.setDisable(true);
                teamLeadsComboBox.setDisable(true);
                CommonUtil.showPopOver("Please provide valid phone tree activation date.", activationDatePicker);
            }
        });

        activationTime.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (CommonUtil.isValidTime(newValue)) {
                teamLeadsComboBox.setDisable(false);
            } else {
                teamLeadsComboBox.setDisable(true);
                CommonUtil.showPopOver("Please provide valid phone tree activation time", activationTime);
            }
        });


    }

    @FXML
    void saveSnvDetails(ActionEvent event) {
        Optional<PhoneTreeActivation> phoneTreeActivationOptional =
                phoneTreeActivationService.getActivationDetailsByDate(activationDatePicker.getValue().toString());

        PhoneTreeActivation phoneTreeActivation = null;
        if(!phoneTreeActivationOptional.isPresent()){
            phoneTreeActivation = new PhoneTreeActivation();
            phoneTreeActivation.setPhoneTreeActivationDate(activationDatePicker.getValue().toString());
            phoneTreeActivation.setPhoneTreeActivationTime(activationTime.getValue().toString());
            phoneTreeActivationService.save(phoneTreeActivation);
        }


        List<PhoneTreeActivationDetail> phoneTreeActivationDetailList = new ArrayList<>();
        for(PerSevadarSNVReportTemplateController perSevadarSNVReportTemplateController:
                perSevadarSNVReportTemplateControllerList){
            PhoneTreeActivationDetail phoneTreeActivationDetail = perSevadarSNVReportTemplateController.getRequest();
            phoneTreeActivationDetailList.add(phoneTreeActivationDetail);
        }
        phoneTreeActivationService.savePhoneTreeActivationDetails(activationDatePicker.getValue().toString(),
                phoneTreeActivationDetailList);
    }

    @FXML
    void showSevadarsSnvDetails(ActionEvent event) {
        TeamLead teamLead = teamLeadsComboBox.getSelectionModel().getSelectedItem();
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadName(teamLead.getTeamLeadName());
        teamSNVDetailsHolder.getChildren().clear();
        perSevadarSNVReportTemplateControllerList.clear();
        for (Sevadar sevadar : sevadarList) {
            Optional<PhoneTreeActivationDetail> phoneTreeActivationDetailOptional =
                    phoneTreeActivationService.getActivationDetailsByDateAndSevadarId(activationDatePicker.getValue().toString(), sevadar.getSevadarsId());

            PhoneTreeActivationDetail phoneTreeActivationDetail = null;
            if (phoneTreeActivationDetailOptional.isPresent()) {
                phoneTreeActivationDetail = phoneTreeActivationDetailOptional.get();
            }
            ContextHolder ctxHolder = createContextHolder(
                    new String[]{"SEVADAR", Constants.REQUEST_OBJ,"ACTIVATION_TIME"},
                    new Object[]{sevadar, phoneTreeActivationDetail,activationTime.getValue()},
                    null);
            teamSNVDetailsHolder.getChildren().add(
                    loadFxml("/fxml/snv-report/sevadar-snv-report-template.fxml", ctxHolder,
                            perSevadarSNVReportTemplateControllerList)
            );

        }
    }
}
