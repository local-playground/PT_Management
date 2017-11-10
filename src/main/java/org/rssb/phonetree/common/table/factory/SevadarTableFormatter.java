package org.rssb.phonetree.common.table.factory;


import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.predicates.SevadarPredicates;

public class SevadarTableFormatter extends TableFormatter<Sevadar,Sevadar>{
    public SevadarTableFormatter() {
        put(SevadarPredicates.isFormatCellPhoneColumn,
                SevadarPredicates.cellPhoneLabelComposerFunction);
        put(SevadarPredicates.isFormatHomePhoneColumn,
                SevadarPredicates.homePhoneLabelComposerFunction);
        put(SevadarPredicates.isFormatFirstNameColumn,
                SevadarPredicates.firstNameLabelComposerFunction);
        put(SevadarPredicates.isFormatLastNameColumn,
                SevadarPredicates.lastNameLabelComposerFunction);
        put(SevadarPredicates.isFormatBackupTeamLead,
                SevadarPredicates.backupTeamLeadLabelComposerFunction);

    }
}
