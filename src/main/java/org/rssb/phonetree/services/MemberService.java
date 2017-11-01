package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.emums.YesNo;

import java.util.Optional;

public interface MemberService {
    void putSevadarBackToCallingList(YesNo yesNo, int familyId);
    Optional<Member> findMember(int memberId);
    Response deleteMember(int memberId);
    void updateMemberFamilyId(int familyId,int memberId);
}
