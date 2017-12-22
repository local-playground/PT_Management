package org.rssb.phonetree.controller.snvreport;

import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.custom.controls.DecoratedTextField;
import org.rssb.phonetree.entity.PhoneTreeActivationDetail;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

@Component
@Lazy
@Scope("prototype")
public class PerSevadarSNVReportTemplateController extends AbstractController {
    @FXML
    private Label firstNameLabel;

    @FXML
    private DecoratedTextField totalFamiliesTextField;

    @FXML
    private JFXTimePicker endTimeTextField;

    @FXML
    private DecoratedTextField totalVMTextField;

    @FXML
    private DecoratedTextField totalNonReachedTextField;

    @FXML
    private DecoratedTextField timeTakenTextField;


    @Override
    public void postProcess() {
        Sevadar sevadar = (Sevadar) contextHolder.get("SEVADAR");
        firstNameLabel.setText(sevadar.getSevadarName());
        PhoneTreeActivationDetail phoneTreeActivationDetail = (PhoneTreeActivationDetail) contextHolder.get(Constants.REQUEST_OBJ);

        if (phoneTreeActivationDetail != null) {
            int totalFamiliesToCall = phoneTreeActivationDetail.getTotalFamiliesCalled();
            if (totalFamiliesToCall == 0) {
                totalFamiliesTextField.setText("");
            } else {
                totalFamiliesTextField.setText(CommonUtil.convertIntToString(totalFamiliesToCall, ""));
            }

            if (CommonUtil.isEmptyOrNull(phoneTreeActivationDetail.getPhoneTreeActivationFinishedTime())) {
                endTimeTextField.setValue(null);
            } else {
                endTimeTextField.setValue(LocalTime.parse(phoneTreeActivationDetail.getPhoneTreeActivationFinishedTime()));
            }

            totalVMTextField.setText(
                    CommonUtil.convertIntToString(phoneTreeActivationDetail.getTotalVMLeft(), ""));

            totalNonReachedTextField.setText(CommonUtil.convertIntToString(phoneTreeActivationDetail.getTotalNotReachableFamilies(), ""));

            timeTakenTextField.setText(CommonUtil.convertIntToString(phoneTreeActivationDetail.getTotalTimeTaken(), ""));
        }
    }


    public PhoneTreeActivationDetail getRequest() {
        Sevadar sevadar = (Sevadar) contextHolder.get("SEVADAR");
        TeamLead teamLead = sevadar.getTeamLead();

        PhoneTreeActivationDetail phoneTreeActivationDetail =
                (PhoneTreeActivationDetail) contextHolder.get(Constants.REQUEST_OBJ);
        if(phoneTreeActivationDetail==null){
            phoneTreeActivationDetail = new PhoneTreeActivationDetail();
            phoneTreeActivationDetail.setSevadar(sevadar);
            phoneTreeActivationDetail.setTeamLead(teamLead);
        }

        phoneTreeActivationDetail.setTotalFamiliesCalled(
                CommonUtil.convertStringToInt(totalFamiliesTextField.getText(), 0));
        if (endTimeTextField.getValue() != null) {
            phoneTreeActivationDetail.setPhoneTreeActivationFinishedTime(
                    endTimeTextField.getValue().format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            phoneTreeActivationDetail.setPhoneTreeActivationFinishedTime("");
        }
        phoneTreeActivationDetail.setTotalVMLeft(
                CommonUtil.convertStringToInt(totalVMTextField.getText(), 0));
        phoneTreeActivationDetail.setTotalNotReachableFamilies(
                CommonUtil.convertStringToInt(totalNonReachedTextField.getText(), 0));
        phoneTreeActivationDetail.setTotalTimeTaken(
                CommonUtil.convertStringToInt(timeTakenTextField.getText(), 0));
        return phoneTreeActivationDetail;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        endTimeTextField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!CommonUtil.isValidTime(newValue)) {
                timeTakenTextField.setText("");
                CommonUtil.showPopOver("Please provide valid activation finished time.", endTimeTextField);
                return;
            }

            LocalTime activationTime = (LocalTime) contextHolder.get("ACTIVATION_TIME");
            if (activationTime != null) {
                if (activationTime.isAfter(newValue)) {
                    timeTakenTextField.setText("");
                    CommonUtil.showPopOver("Phone Tree activation end time should be after start time.", endTimeTextField);
                    return;
                }

                long minutes = ChronoUnit.MINUTES.between(activationTime, newValue);
                timeTakenTextField.setText(String.valueOf(minutes));
            }
        });
    }

}
