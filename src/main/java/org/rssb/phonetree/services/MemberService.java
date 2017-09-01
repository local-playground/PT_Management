package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.entity.Member;

import java.util.Optional;

public interface MemberService {
    void putSevadarBackToCallingList(int onCallingList,int familyId);
    Optional<Member> findMember(int memberId);
    Response deleteMember(int memberId);

}
