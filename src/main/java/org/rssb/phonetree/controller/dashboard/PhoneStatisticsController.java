package org.rssb.phonetree.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardPhoneStatusSummary;
import org.rssb.phonetree.entity.emums.CallStatus;
import org.rssb.phonetree.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
public class PhoneStatisticsController extends AbstractController {
    @FXML
    private Label okCount;

    @FXML
    private Label disconnectCount;

    @FXML
    private Label movedCount;

    @FXML
    private Label notPickedCount;

    @FXML
    private Label wrongNumberCount;

    @FXML
    private Label removeRequestCount;

    @Autowired
    private DashboardService dashboardService;

    @Override
    public void postProcess() {
        List<DashboardPhoneStatusSummary> dashboardPhoneStatusSummaryList = dashboardService.getPhoneStatusSummary();
        dashboardPhoneStatusSummaryList.forEach(dashboardPhoneStatusSummary -> {
            if(dashboardPhoneStatusSummary.getCallStatus()== CallStatus.OK){
                okCount.setText(String.valueOf(dashboardPhoneStatusSummary.getCount()));
            }
            if(dashboardPhoneStatusSummary.getCallStatus()== CallStatus.DISCONNECTED){
                disconnectCount.setText(String.valueOf(dashboardPhoneStatusSummary.getCount()));
            }
            if(dashboardPhoneStatusSummary.getCallStatus()== CallStatus.MOVED){
                movedCount.setText(String.valueOf(dashboardPhoneStatusSummary.getCount()));
            }
            if(dashboardPhoneStatusSummary.getCallStatus()== CallStatus.REMOVE_REQUEST){
                removeRequestCount.setText(String.valueOf(dashboardPhoneStatusSummary.getCount()));
            }
            if(dashboardPhoneStatusSummary.getCallStatus()== CallStatus.WRONG_NUMBER){
                wrongNumberCount.setText(String.valueOf(dashboardPhoneStatusSummary.getCount()));
            }
            if(dashboardPhoneStatusSummary.getCallStatus()== CallStatus.NOT_PICKED){
                notPickedCount.setText(String.valueOf(dashboardPhoneStatusSummary.getCount()));
            }
        });
    }

}
