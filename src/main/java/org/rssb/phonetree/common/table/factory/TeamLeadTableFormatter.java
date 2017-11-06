package org.rssb.phonetree.common.table.factory;


import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.predicates.TeamLeadPredicates;

public class TeamLeadTableFormatter extends TableFormatter<TeamLead, TeamLead> {
    public TeamLeadTableFormatter() {
        put(TeamLeadPredicates.isFormatCellPhoneColumn,
                TeamLeadPredicates.cellPhoneLabelComposerFunction);
        put(TeamLeadPredicates.isFormatHomePhoneColumn,
                TeamLeadPredicates.homePhoneLabelComposerFunction);
        put(TeamLeadPredicates.isFormatFirstNameColumn,
                TeamLeadPredicates.firstNameLabelComposerFunction);
        put(TeamLeadPredicates.isFormatLastNameColumn,
                TeamLeadPredicates.lastNameLabelComposerFunction);
    }
}
