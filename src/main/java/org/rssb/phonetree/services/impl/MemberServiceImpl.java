package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.emums.YesNo;
import org.rssb.phonetree.repository.MemberJpaRepository;
import org.rssb.phonetree.services.MemberService;
import org.rssb.phonetree.services.UtilityService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.MemberActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private UtilityService utilityService;

    @Override
    @Transactional(readOnly = false)
    public void putSevadarBackToCallingList(YesNo yesNo, int familyId) {
        memberJpaRepository.putSevadarBackToCallingList(yesNo,familyId);
    }

    @Override
    public Optional<Member> findMember(int memberId) {
        return Optional.ofNullable(memberJpaRepository.findOne(memberId));
    }

    @Override
    @Transactional(readOnly = false)
    public Response deleteMember(int memberId) {
        Optional<Member> member = findMember(memberId);
        if(!member.isPresent()){
            return CommonUtil.createResponse(MemberActionResponse.MEMBER_DOES_NOT_EXISTS,
                    new Object[]{memberId}, ActionAlertType.ERROR);
        }

        if(utilityService.hasMemberAssignedAnySeva(memberId)){
            return CommonUtil.createResponse(MemberActionResponse.MEMBER_HAS_ASSIGNED_SEVA,
                    new Object[]{CommonUtil.getFullName(member.get())}, ActionAlertType.ERROR);
        }

        memberJpaRepository.delete(member.get());

        return CommonUtil.createResponse(MemberActionResponse.MEMBER_SUCCESSFULLY_DELETED,
                new Object[]{CommonUtil.getFullName(member.get())}, ActionAlertType.INFORMATION);
    }
}
