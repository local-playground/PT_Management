package org.rssb.phonetree.common.table.factory;

import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.predicates.MembersTablePredicates;

public class MemberTableFormatter extends TableFormatter<Member,Member> {
    public MemberTableFormatter() {
        put(MembersTablePredicates.isFormatCellPhoneColumn,
                MembersTablePredicates.cellPhoneLabelComposerFunction);
        put(MembersTablePredicates.isFormatHomePhoneColumn,
                MembersTablePredicates.homePhoneLabelComposerFunction);
        put(MembersTablePredicates.isFormatWorkPhoneColumn,
                MembersTablePredicates.workPhoneLabelComposerFunction);
        put(MembersTablePredicates.isFormatPriorityColumn,
                MembersTablePredicates.priorityLabelComposerFunction);
        put(MembersTablePredicates.isFormatOnCallingListColumn,
                MembersTablePredicates.onCallingListLabelComposerFunction);

        put(MembersTablePredicates.isFormatFirstNameColumn,
                MembersTablePredicates.firstNameLabelComposerFunction);
        put(MembersTablePredicates.isFormatLastNameColumn,
                MembersTablePredicates.lastNameLabelComposerFunction);
        put(MembersTablePredicates.isFormatPreferredPhoneColumn,
                MembersTablePredicates.preferredPhoneLabelComposerFunction);
        put(MembersTablePredicates.isFormatMemberIdColumn,
                MembersTablePredicates.memberIdLabelComposerFunction);
        put(MembersTablePredicates.isFormatFamilyIdColumn,
                MembersTablePredicates.familyIdLabelComposerFunction);
    }

}
