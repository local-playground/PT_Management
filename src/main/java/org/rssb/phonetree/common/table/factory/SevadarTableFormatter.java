package org.rssb.phonetree.common.table.factory;


import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.predicates.SevadarPredicates;
import org.rssb.phonetree.predicates.TeamLeadPredicates;

public class SevadarTableFormatter extends TableFormatter<Sevadar,Sevadar>{
    public SevadarTableFormatter() {
        put(SevadarPredicates.isFormatCellPhoneColumn,
                SevadarPredicates.cellPhoneLabelComposerFunction);
        put(SevadarPredicates.isFormatHomePhoneColumn,
                SevadarPredicates.homePhoneLabelComposerFunction);
        put(TeamLeadPredicates.isFormatFirstNameColumn,
                SevadarPredicates.firstNameLabelComposerFunction);
        put(TeamLeadPredicates.isFormatLastNameColumn,
                SevadarPredicates.lastNameLabelComposerFunction);

    }
}
