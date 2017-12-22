package org.rssb.phonetree.custom.controls;

import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import org.rssb.phonetree.entity.TeamLead;

public class TeamLeadComboBoxFormatter extends StringConverter<TeamLead>{
    private ObservableList<TeamLead> teamLeadObservableList;

    public TeamLeadComboBoxFormatter(ObservableList<TeamLead> teamLeadObservableList){
        this.teamLeadObservableList = teamLeadObservableList;
    }
    @Override
    public String toString(TeamLead teamLead) {
        return teamLead.getTeamLeadName();
    }

    @Override
    public TeamLead fromString(String teamLeadName) {
        return teamLeadObservableList
                .stream()
                .filter(teamLead -> teamLead.getTeamLeadName().equalsIgnoreCase(teamLeadName))
                .findFirst().get();

    }
}
