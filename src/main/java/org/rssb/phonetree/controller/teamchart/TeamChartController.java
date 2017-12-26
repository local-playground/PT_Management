package org.rssb.phonetree.controller.teamchart;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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

    @Value("${org.rssb.phonetree.seceratery-name}")
    private String secerateryName;

    @Value("${org.rssb.phonetree.admin-names}")
    private String adminNames;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("name = " + secerateryName);
        addSecretory();
        addAdmins();


        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();

        for (TeamLead teamLead : teamLeadList) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.setSpacing(10);

            List<String> stringList = new ArrayList<>();
            stringList.add(teamLead.getMember().getFirstName() + ":" +
                    teamLead.getMember().getLastName() + ":" +
                    "Team Lead");
            List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadId(teamLead.getTeamLeadId());
            stringList.addAll(sevadarList
                    .stream()
                    .map(sevadar -> {
                        String title = "Sevadar";
                        if (sevadar.getIsBackupForTeamLead() == 1) {
                            title = "Team Lead Backup";
                        }
                        return sevadar.getMember().getFirstName() + ":" +
                                sevadar.getMember().getLastName() + ":" +
                                title;
                    })
                    .collect(Collectors.toList())
            );
           /* stringList.addAll(teamLead.getSevadarsList().stream().map(sevadar -> sevadar.getMember().getFirstName() + ":" +
                    sevadar.getMember().getLastName() + ":" +
                    "Sevadar").collect(Collectors.toList()));*/
            createData(stringList, vBox);
            teamLeadHolder.getChildren().add(vBox);
        }
    }


    private void addSecretory() {
        createData(Arrays.asList(secerateryName.split(",")), secretoryBox);
    }

    private void addAdmins() {
        createData(Arrays.asList(adminNames.split(",")), adminsBox);
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
