package org.rssb.phonetree.common.table.factory;

import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.predicates.CalledFamilyDetailsPredicates;

public class SevadarsListTableFormatter<S,T> extends TableFormatter<CalledFamilyDetails,CalledFamilyDetails>{
    public SevadarsListTableFormatter() {
        put(CalledFamilyDetailsPredicates.isFormatCellPhoneColumn,
                CalledFamilyDetailsPredicates.cellPhoneLabelComposerFunction);
        put(CalledFamilyDetailsPredicates.isFormatHomePhoneColumn,
                CalledFamilyDetailsPredicates.homePhoneLabelComposerFunction);
        put(CalledFamilyDetailsPredicates.isFormatWorkPhoneColumn,
                CalledFamilyDetailsPredicates.workPhoneLabelComposerFunction);
        put(CalledFamilyDetailsPredicates.isFormatSequenceNumberColumn,
                CalledFamilyDetailsPredicates.familySeqNumberLabelComposerFunction);
        put(CalledFamilyDetailsPredicates.isFormatZipCodeColumn,
                CalledFamilyDetailsPredicates.zipCodeLabelComposerFunction);

        put(CalledFamilyDetailsPredicates.isFormatFirstNameColumn,
                CalledFamilyDetailsPredicates.firstNameLabelComposerFunction);
        put(CalledFamilyDetailsPredicates.isFormatLastNameColumn,
                CalledFamilyDetailsPredicates.lastNameLabelComposerFunction);
        put(CalledFamilyDetailsPredicates.isFormatPhoneStatusColumn,
                CalledFamilyDetailsPredicates.phoneStatusLabelComposerFunction);
        put(CalledFamilyDetailsPredicates.isFormatMemberIdColumn,
                CalledFamilyDetailsPredicates.memberIdLabelComposerFunction);
        put(CalledFamilyDetailsPredicates.isFormatFamilyIdColumn,
                CalledFamilyDetailsPredicates.familyIdLabelComposerFunction);

    }

}
