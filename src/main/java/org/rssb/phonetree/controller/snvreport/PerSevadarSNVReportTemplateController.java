package org.rssb.phonetree.controller.snvreport;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.entity.Sevadar;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Lazy
@Scope("prototype")
public class PerSevadarSNVReportTemplateController extends AbstractController {
    @FXML
    private Label firstNameLabel;

    @Override
    public void postProcess() {
        Sevadar sevadar = (Sevadar) contextHolder.get("SEVADAR");
        firstNameLabel.setText(sevadar.getSevadarName());

       /* SevadarVacation sevadarVacation = (SevadarVacation) contextHolder.get(Constants.REQUEST_OBJ);
        Object object = contextHolder.get("SEVADAR");
        if(object instanceof Sevadar){
            firstNameLabel.setText(((Sevadar)object).getSevadarName());
        }

        if(object instanceof TeamLead){
            firstNameLabel.setText(((TeamLead)object).getTeamLeadName());
        }

        if (sevadarVacation!=null) {
            List<VacationDate> vacationDateList = sevadarVacation.getVacationDateList();
            for (int index = 0; index < vacationDateList.size(); index++) {
                VacationDate vacationDate = vacationDateList.get(index);
                LocalDate startDate = vacationDate.getFromDate();
                LocalDate endDate = vacationDate.getToDate();
                int size = perSevadarVacationDatesTemplateHolder.getChildren().size();
                if (size <= index) {
                    addMoreVacationDatePicker(null);
                }

                PerSevadarVacationDatesController perSevadarVacationDatesController = perSevadarVacationDatesControllerList.size() > index ?
                        perSevadarVacationDatesControllerList.get(index):null;

                if(perSevadarVacationDatesController !=null){
                    perSevadarVacationDatesController.setFromDate(startDate);
                    perSevadarVacationDatesController.setEndDate(endDate);
                }

            }
        }*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
    }

}
