package org.rssb.phonetree.controller.snvreport;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.PhoneTreeActivationSummary;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Lazy
public class SNVReportStatisticsGraphController extends AbstractController {

    @FXML
    private PieChart snvReportStatisticsPieChart;

    @FXML
    private PieChart averageTimePieChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void postProcess() {
        PhoneTreeActivationSummary phoneTreeActivationSummary =
                (PhoneTreeActivationSummary) contextHolder.get(Constants.REQUEST_OBJ);

        snvReportStatisticsPieChart.getData().clear();
        PieChart.Data totalSangat = new PieChart.Data("Total Sangat", phoneTreeActivationSummary.getTotalSangat());
        PieChart.Data totalFamiliesCalled = new PieChart.Data("Families", phoneTreeActivationSummary.getTotalFamiliesCalled());
        PieChart.Data totalVMLeft = new PieChart.Data("Voice Msg", phoneTreeActivationSummary.getTotalVMLeft());
        PieChart.Data totalNonReachedFamilies = new PieChart.Data("Non Reached", phoneTreeActivationSummary.getToalNotReachableFamilies());

        snvReportStatisticsPieChart.getData().addAll(totalSangat, totalFamiliesCalled, totalVMLeft, totalNonReachedFamilies);

        snvReportStatisticsPieChart.getData().forEach(data -> data.nameProperty()
                .bind(Bindings.concat(data.getName() + " , " + (int) data.getPieValue())));

        averageTimePieChart.getData().clear();
        PieChart.Data minTimeTaken = new PieChart.Data("Minimum Time\n(minutes)",
                phoneTreeActivationSummary.getMaximumTimeTaken());
        PieChart.Data maxTimeTaken = new PieChart.Data("Maximum Time\n" +
                "(minutes)", phoneTreeActivationSummary.getMaximumTimeTaken());
        PieChart.Data averageTimeTaken = new PieChart.Data("Average Time\n" +
                "(minutes)", phoneTreeActivationSummary.getAverageTimeTaken());
        averageTimePieChart.getData().addAll(minTimeTaken, maxTimeTaken, averageTimeTaken);

        averageTimePieChart.getData().forEach(data -> data.nameProperty()
                .bind(Bindings.concat(data.getName() + " , " + (int) data.getPieValue())));

    }
}
