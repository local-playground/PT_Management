package org.rssb.phonetree.controller.snvreport;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.domain.PhoneTreeActivationSummary;
import org.rssb.phonetree.domain.PhoneTreeActivationTeamLeadSummary;
import org.rssb.phonetree.services.PhoneTreeActivationService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class ViewSNVReportController extends AbstractController {
    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private PhoneTreeActivationService phoneTreeActivationService;

    @FXML
    private JFXComboBox<?> activationDateComboBox;

    @FXML
    private HBox snvReportStatisticsHolder;

    @FXML
    private FlowPane teamLeadSnvDataHolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PhoneTreeActivationSummary phoneTreeActivationSummary =
                phoneTreeActivationService.getActivationSummary("2017-12-22");
        ContextHolder ctxHolder = createContextHolder(
                new String[]{Constants.REQUEST_OBJ,},
                new Object[]{phoneTreeActivationSummary},
                null);

        snvReportStatisticsHolder.getChildren()
                .add(loadFxml("/fxml/snv-report/snv-report-statistics-graph-template.fxml", ctxHolder, null));

        List<PhoneTreeActivationTeamLeadSummary> phoneTreeActivationTeamLeadSummaries =
                phoneTreeActivationService.getTeamLeadsActivationSummaryByDate("2017-12-22");

        for(PhoneTreeActivationTeamLeadSummary phoneTreeActivationTeamLeadSummary:
                phoneTreeActivationTeamLeadSummaries){
            ctxHolder = createContextHolder(
                    new String[]{Constants.REQUEST_OBJ,},
                    new Object[]{phoneTreeActivationTeamLeadSummary},
                    null);

            teamLeadSnvDataHolder.getChildren().add(loadFxml("/fxml/snv-report/teamlead-snv-report-statistics.fxml", ctxHolder, null));
        }
    }
}
