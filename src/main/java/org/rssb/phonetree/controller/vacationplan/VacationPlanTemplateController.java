package org.rssb.phonetree.controller.vacationplan;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.VacationDate;
import org.rssb.phonetree.entity.SevadarVacation;
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
public class VacationPlanTemplateController extends AbstractController {
    private List<VacationDatesController> vacationDatesControllerList = new ArrayList<>();

    @FXML
    private Label firstNameLabel;

    @FXML
    private VBox vacationDatesTemplateHolder;

    @FXML
    private FontAwesomeIconView addMoreVacationDatePicker;

    @FXML
    private FontAwesomeIconView deleteEmptyVacationDatePicker;

    @Override
    public void postProcess() {
        SevadarVacation sevadarVacation = (SevadarVacation) contextHolder.get(Constants.REQUEST_OBJ);
        String sevadarName = (String) contextHolder.get("SEVADAR_NAME");
        if(CommonUtil.isNotEmptyOrNull(sevadarName)){
            firstNameLabel.setText(sevadarName);
        }

        if (sevadarVacation!=null) {
            List<VacationDate> vacationDateList = sevadarVacation.getVacationPlan();
            for (int index = 0; index < vacationDateList.size(); index++) {
                VacationDate vacationDate = vacationDateList.get(index);
                LocalDate startDate = vacationDate.getFromDate();
                LocalDate endDate = vacationDate.getToDate();
                int size = vacationDatesTemplateHolder.getChildren().size();
                if (size <= index) {
                    addMoreVacationDatePicker(null);
                }

                VacationDatesController vacationDatesController = vacationDatesControllerList.size() > index ?
                        vacationDatesControllerList.get(index):null;

                if(vacationDatesController!=null){
                    vacationDatesController.setFromDate(startDate);
                    vacationDatesController.setEndDate(endDate);
                }

            }
        }


    }

    @Override
    public boolean validate() {
        for(VacationDatesController controller:vacationDatesControllerList){
            System.out.println("validating dates controller now...");
            if(!controller.validate()){
                return false;
            }
        }
        System.out.println("good --- validating dates controller now...");
        return true;
    }

    public SevadarVacation getRequest() {
        SevadarVacation sevadarVacation = (SevadarVacation) contextHolder.get(Constants.REQUEST_OBJ);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < vacationDatesTemplateHolder.getChildren().size(); index++) {
            VacationDatesController vacationDatesController = vacationDatesControllerList.size() > index ?
                    vacationDatesControllerList.get(index) : null;

            /*if (vacationDatesController != null &&
                    CommonUtil.isNotEmptyOrNull(textFieldController.getText())) {
                sb.append(textFieldController.getText()).append(",");
            }*/
        }
        /*if (type instanceof TeamLead) {
            ((TeamLead) type).setEmailId(CommonUtil.extractDataButLastCharacter(sb));
        } else if (type instanceof Sevadar) {
            ((Sevadar) type).setEmailId(CommonUtil.extractDataButLastCharacter(sb));
        }*/

        return sevadarVacation;
    }


    @FXML
    void addMoreVacationDatePicker(MouseEvent event) {
        vacationDatesTemplateHolder.getChildren().add(loadFxml("/fxml/vacation-plan/vacation-date-picker.fxml",null,vacationDatesControllerList));
    }

    @FXML
    void deleteEmptyVacationDatePicker(MouseEvent event) {
        List<Node> nodeList = vacationDatesTemplateHolder.getChildren();
        int size = nodeList.size();
        for (int index = size - 1; index > 0; index--) {
            VacationDatesController vacationDatesController = vacationDatesControllerList.get(index);
            if (vacationDatesController.isEmpty()) {
                vacationDatesTemplateHolder.getChildren().remove(index);
                vacationDatesControllerList.remove(index);
                return;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addMoreVacationDatePicker(null);
    }
}
