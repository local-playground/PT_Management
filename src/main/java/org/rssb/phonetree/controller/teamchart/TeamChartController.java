package org.rssb.phonetree.controller.teamchart;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.ConfigService;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Component
@Lazy
public class TeamChartController extends AbstractController {
    @Autowired
    private TeamLeadService teamLeadService;

    @FXML
    private VBox teamChartContainer;

    @FXML
    private HBox secretoryBox;

    @FXML
    private HBox adminsBox;

    @FXML
    private FlowPane teamLeadHolder;

    @Autowired
    private ConfigService configService;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addSecretory();
        addAdmins();

        Map<String, List<String>> teamLeadAndSevadarsMap = teamLeadService.getAllTeamLeadAndSevadarsMap(false);

        teamLeadAndSevadarsMap.forEach((teamLead, sevadarsList) -> {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.setSpacing(10);

            List<String> stringList = new ArrayList<>();
            stringList.add(teamLead);
            stringList.addAll(sevadarsList);

            createData(stringList, vBox);
            teamLeadHolder.getChildren().add(vBox);
        });
    }


    private void addSecretory() {
        createData(Arrays.asList(configService.getSecerateryName().split(",")), secretoryBox);
    }

    private void addAdmins() {
        createData(Arrays.asList(configService.getAdminNames().split(",")), adminsBox);
    }

    private void createData(List<String> namesList, Pane node) {
        for (String name : namesList) {
            String title = "";
            String firstName = "";
            String lastName = "";

            String[] split = name.split(":");
            if (split.length == 3) {
                firstName = split[0];
                lastName = split[1];
                title = split[2];
            }
            ContextHolder contextHolder = createContextHolder(new String[]{"FIRST_NAME", "LAST_NAME", "TITLE"},
                    new String[]{firstName, lastName, title},
                    null);
            Parent parent = loadFxml("/fxml/team-chart/name-placeholder.fxml", contextHolder, null);
            node.getChildren().add(parent);
        }

    }

}
