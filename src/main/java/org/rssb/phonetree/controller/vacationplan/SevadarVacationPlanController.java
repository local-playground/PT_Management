package org.rssb.phonetree.controller.vacationplan;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.SevadarsMonthlyAvailability;
import org.rssb.phonetree.entity.Sevadar;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
@Scope("prototype")
public class SevadarVacationPlanController extends AbstractController{

    @FXML
    private Label sevadarNameLabel;

    @FXML
    private FlowPane sevadarsMonthlyAvailabilityHolder;

    @Override
    public void postProcess() {
        Sevadar sevadar = (Sevadar) contextHolder.get("SEVADAR");
        sevadarNameLabel.setText(sevadar.getSevadarName());
        List<SevadarsMonthlyAvailability> sevadarsMonthlyAvailabilityList = (List<SevadarsMonthlyAvailability>) contextHolder.get(Constants.REQUEST_OBJ);
        for(SevadarsMonthlyAvailability sevadarsMonthlyAvailability: sevadarsMonthlyAvailabilityList) {
            ContextHolder ctxHolder = createContextHolder(
                    new String[]{Constants.REQUEST_OBJ},
                    new Object[]{sevadarsMonthlyAvailability},
                    null);
            sevadarsMonthlyAvailabilityHolder.getChildren().add(
                    loadFxml("/fxml/vacation-plan/monthly-availability-template.fxml", ctxHolder, null));
        }
    }
}
