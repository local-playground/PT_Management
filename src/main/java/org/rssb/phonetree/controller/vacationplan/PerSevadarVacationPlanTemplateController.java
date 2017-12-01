package org.rssb.phonetree.controller.vacationplan;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.VacationDate;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.SevadarVacation;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.helper.VacationPlanHelper;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
@Scope("prototype")
public class PerSevadarVacationPlanTemplateController extends AbstractController {
    private List<PerSevadarVacationDatesController> perSevadarVacationDatesControllerList = new ArrayList<>();

    @FXML
    private Label firstNameLabel;

    @FXML
    private VBox perSevadarVacationDatesTemplateHolder;

   /* @FXML
    private FontAwesomeIconView addMoreVacationDatePicker;

    @FXML
    private FontAwesomeIconView deleteEmptyVacationDatePicker;
*/
    @Override
    public void postProcess() {
        SevadarVacation sevadarVacation = (SevadarVacation) contextHolder.get(Constants.REQUEST_OBJ);
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
        }
    }

    @Override
    public boolean validate() {
        for(PerSevadarVacationDatesController controller: perSevadarVacationDatesControllerList){
            if(!controller.validate()){
                return false;
            }
        }
        return true;
    }

    public SevadarVacation getRequest() {
        SevadarVacation sevadarVacation = (SevadarVacation) contextHolder.get(Constants.REQUEST_OBJ);
        List<VacationDate> vacationDateList = new ArrayList<>();
        for (int index = 0; index < perSevadarVacationDatesTemplateHolder.getChildren().size(); index++) {
            PerSevadarVacationDatesController perSevadarVacationDatesController =
                    perSevadarVacationDatesControllerList.size() > index ?
                    perSevadarVacationDatesControllerList.get(index) : null;

            if (perSevadarVacationDatesController != null &&
                    !perSevadarVacationDatesController.isEmpty()) {
                vacationDateList.add(perSevadarVacationDatesController.getVacationDate());
            }
        }

        if (sevadarVacation == null && vacationDateList.isEmpty()) {
            return null;
        }

        Object type = contextHolder.get("SEVADAR");
        if(sevadarVacation==null){
            sevadarVacation  = new SevadarVacation();
        }
        if (type instanceof TeamLead) {
            sevadarVacation.setSevadar(null);
            sevadarVacation.setTeamLead((TeamLead) type);
        } else if (type instanceof Sevadar) {
            sevadarVacation.setSevadar((Sevadar)type);
            sevadarVacation.setTeamLead(null);
        }
        sevadarVacation.setVacationPlan(VacationPlanHelper.convertToDatabaseColumn(vacationDateList));
        return sevadarVacation;
    }


    @FXML
    void addMoreVacationDatePicker(MouseEvent event) {
        perSevadarVacationDatesTemplateHolder.getChildren().add(loadFxml("/fxml/vacation-plan/vacation-date-picker.fxml",null, perSevadarVacationDatesControllerList));
    }

    @FXML
    void deleteEmptyVacationDatePicker(MouseEvent event) {
        List<Node> nodeList = perSevadarVacationDatesTemplateHolder.getChildren();
        int size = nodeList.size();
        for (int index = size - 1; index > 0; index--) {
            PerSevadarVacationDatesController perSevadarVacationDatesController = perSevadarVacationDatesControllerList.get(index);
            if (perSevadarVacationDatesController.isEmpty()) {
                perSevadarVacationDatesTemplateHolder.getChildren().remove(index);
                perSevadarVacationDatesControllerList.remove(index);
                return;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMoreVacationDatePicker(null);
    }

    @Override
    public void refresh() {
        perSevadarVacationDatesControllerList.forEach(PerSevadarVacationDatesController::clear);
    }
}
