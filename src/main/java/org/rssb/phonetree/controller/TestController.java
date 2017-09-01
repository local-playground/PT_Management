package org.rssb.phonetree.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.rssb.phonetree.repository.TeamLeadJpaRepository;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TestController {
    @Autowired
    @Lazy
    private TeamLeadService teamLeadService;

    @Autowired @Lazy
    private SevadarService sevadarService;

    @Autowired
    @Lazy
    private TeamLeadJpaRepository familyJpaRepository;

    @FXML
    private Button button;

    @FXML
    private Label label;

    @FXML
    void clickMe(ActionEvent event) {
        System.out.println("you clicked me");
        count();
    }

    public void count() {
        //System.out.println(teamLeadService.deleteTeamLead(1).getMessage());
        /*TeamLead teamLead = teamLeadService.findTeamLeadByMemberId(529);
        System.out.println(teamLead);*/

        /*TeamLead teamLead = teamLeadService.findTeamLeadByFamilyId(69);
        System.out.println(teamLead);*/

        //teamLeadService.addTeamLead(822);


        /*Sevadar sevadar = sevadarService.findSevadarByFamilyId(102);
        System.out.println(sevadar);
        sevadar = sevadarService.findSevadarByMemberId(164);
        System.out.println(sevadar);
        sevadar=sevadarService.findSevadarBySevadarId(1);
        System.out.println(sevadar);*/

      /*  List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadName("Naren Shah");
        System.out.println(sevadarList);*/

    }


}
