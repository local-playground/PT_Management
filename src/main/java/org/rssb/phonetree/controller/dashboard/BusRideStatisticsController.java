package org.rssb.phonetree.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.DashboardBusRideSummary;
import org.rssb.phonetree.entity.emums.BusRide;
import org.rssb.phonetree.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Lazy
public class BusRideStatisticsController extends AbstractController {
    @FXML
    private Label yesCount;

    @FXML
    private Label noCount;

    @FXML
    private Label mayBeCount;

    @Autowired
    private DashboardService dashboardService;

    @Override
    public void postProcess() {
        List<DashboardBusRideSummary> busRideSummaryList = dashboardService.getBusRideNeededSummary();
        busRideSummaryList.forEach(busRideSummary -> {
            if(busRideSummary.getBusRide()== BusRide.YES){
                yesCount.setText(String.valueOf(busRideSummary.getCount()));
            }
            if(busRideSummary.getBusRide()== BusRide.NO){
                noCount.setText(String.valueOf(busRideSummary.getCount()));
            }
            if(busRideSummary.getBusRide()== BusRide.MAYBE){
                mayBeCount.setText(String.valueOf(busRideSummary.getCount()));
            }
        });
    }

}
