package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.repository.FamilyJpaRepository;
import org.rssb.phonetree.services.FamilyService;
import org.rssb.phonetree.services.MemberService;
import org.rssb.phonetree.services.UtilityService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.FamilyActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyJpaRepository familyJpaRepository;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private MemberService memberService;

    @Override
    public int getTotalFamiliesBySevadarId(int sevadarId) {
        return familyJpaRepository.getTotalFamiliesBySevadarId(sevadarId);
    }

    @Override
    public void updateSevadarAndTeamLeadId(int newSevadarId, int newTeamLeadId, int whereSevadarId) {
        familyJpaRepository.updateSevadarAndTeamLeadId(newSevadarId, newTeamLeadId, whereSevadarId);
    }

    @Override
    public void updateSevadarAndTeamLeadIdForFamilyId(int newSevadarId, int newTeamLeadId, int whereFamilyId) {
        familyJpaRepository.updateSevadarAndTeamLeadIdForFamilyId(newSevadarId, newTeamLeadId, whereFamilyId);
    }

    @Override
    @Transactional(readOnly = false)
    public Response deleteFamily(int familyId) {
        Family family = familyJpaRepository.findOne(familyId);
        if (family == null) {
            return CommonUtil.createResponse(FamilyActionResponse.FAMILY_DOES_NOT_EXISTS,
                    new Object[]{familyId}, ActionAlertType.ERROR);
        }

        if (utilityService.hasAnyMemberInFamilyAssignedAnySeva(familyId)) {
            return CommonUtil.createResponse(FamilyActionResponse.FAMILY_MEMBER_HAS_ASSIGNED_SEVA,
                    null, ActionAlertType.ERROR);
        }

        familyJpaRepository.delete(family);

        return CommonUtil.createResponse(FamilyActionResponse.FAMILY_SUCCESSFULLY_DELETED,
                new Object[]{}, ActionAlertType.INFORMATION);
    }

    @Override
    public Response deleteFamilyMember(int memberId) {
        return memberService.deleteMember(memberId);
    }

    @Override
    public Response moveMemberAsSeparateFamily(int memberId) {
        Member member = memberService.findMember(memberId).get();

        Family family = member.getFamily();
        int nextFamilyId = familyJpaRepository.getMaxFamilyId() + 1;
        family.setFamilyId(nextFamilyId);

        List<Member> newMembersList = family.getMembersList()
                .stream()
                .filter(localMember -> localMember.getMemberId() == memberId)
                .collect(Collectors.toCollection(ArrayList<Member>::new));

        family.setMembersList(newMembersList);
        saveToDatabase(family);

        return CommonUtil.createResponse(FamilyActionResponse.FAMILY_MEMBER_SUCCESSFULLY_MOVED_AS_SEPARATE_FAMILY,
                new Object[]{CommonUtil.getFullName(member)}, ActionAlertType.INFORMATION);
    }

    @Override
    public Response saveToDatabase(Family family) {
        Family savedFamily = familyJpaRepository.saveAndFlush(family);
        if (savedFamily != null) {
            return CommonUtil.createResponse(FamilyActionResponse.FAMILY_SAVED_SUCCESSFULLY,
                    new Object[]{}, ActionAlertType.INFORMATION);
        }

        //to do
        return CommonUtil.createResponse(FamilyActionResponse.FAMILY_SAVED_ERROR,
                new Object[]{}, ActionAlertType.WARNING);
    }

    @Override
    public Response moveMemberUnderOtherFamily(int memberId, int otherFamilyId) {
        Member member = memberService.findMember(memberId).get();
        int oldFamilyIdForMember = member.getFamily().getFamilyId();
        Family newFamily = familyJpaRepository.findOne(otherFamilyId);
        if (newFamily == null) {
            return CommonUtil.createResponse(FamilyActionResponse.FAMILY_DOES_NOT_EXISTS,
                    new Object[]{otherFamilyId}, ActionAlertType.ERROR);
        }
        //set member a new family
        member.setFamily(newFamily);
        List<Member> memberList = newFamily.getMembersList();
        memberList.add(member);
        newFamily.setMembersList(memberList);
        saveToDatabase(newFamily);
        //check if old family doesn't have any member associated with it,
        Family oldFamily = familyJpaRepository.findOne(oldFamilyIdForMember);
        if (oldFamily.getMembersList().size() == 0) {
            Response response = deleteFamily(oldFamily.getFamilyId());
            System.out.println(response.getMessage());
        }

        return CommonUtil.createResponse(FamilyActionResponse.FAMILY_MEMBER_SUCCESSFULLY_MOVED_UNDER_OTHER_FAMILY,
                new Object[]{CommonUtil.getFullName(member),newFamily.getFamilyId()}, ActionAlertType.INFORMATION);
    }

    @Override
    public List<Family> getFamiliesByTeamLeadAndSevadarName(String teamLeadName, String sevadarName) {
        return familyJpaRepository.findByTeamLeadTeamLeadNameAndSevadarSevadarName(teamLeadName,sevadarName);
    }
}