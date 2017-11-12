package org.rssb.phonetree.common.table.factory;

import org.rssb.phonetree.entity.BackupSevadar;
import org.rssb.phonetree.predicates.BackupSevadarPredicates;

public class BackupSevadarTableFormatter extends TableFormatter<BackupSevadar,BackupSevadar>{
    public BackupSevadarTableFormatter() {
        put(BackupSevadarPredicates.isFormatCellPhoneColumn,
                BackupSevadarPredicates.cellPhoneLabelComposerFunction);
        /*put(BackupSevadarPredicates.isFormatAssignedToColumn,
                BackupSevadarPredicates.assignedToLabelComposerFunction);*/
        put(BackupSevadarPredicates.isFormatFirstNameColumn,
                BackupSevadarPredicates.firstNameLabelComposerFunction);
        put(BackupSevadarPredicates.isFormatLastNameColumn,
                BackupSevadarPredicates.lastNameLabelComposerFunction);
    }
}
