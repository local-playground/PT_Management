package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.repository.FamilyJpaRepository;
import org.rssb.phonetree.repository.NamedQueryExecutor;
import org.rssb.phonetree.services.FamilyService;
import org.rssb.phonetree.services.MemberService;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.services.UtilityService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.FamilyActionResponse;
import org.rssb.phonetree.status.MemberActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyJpaRepository familyJpaRepository;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private NamedQueryExecutor namedQueryExecutor;

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private SevadarService sevadarService;

    @Override
    public Optional<Family> findByFamilyId(int familyId) {
        return Optional.of(familyJpaRepository.findOne(familyId));
    }

    @Override
    public int getTotalFamiliesBySevadarId(int sevadarId) {
        return familyJpaRepository.getTotalFamiliesBySevadarId(sevadarId);
    }

    @Override
    public long getTotalFamiliesByTeamLeadAndSevadar(String teamLeadName, String sevadarName) {
       return namedQueryExecutor.executeSingleResultQuery("Family.findCalledFamiliesCountByTeamLeadAndSevadar",
                new String[]{"teamLeadName","sevadarName"},
                new String[]{teamLeadName,sevadarName},
                Long.class);
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
    @Transactional
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
        Optional<Member> memberFromDatabase = memberService.findMember(memberId);
        if(!memberFromDatabase.isPresent()){
            return CommonUtil.createResponse(MemberActionResponse.MEMBER_DOES_NOT_EXISTS,
                    new Object[]{memberId}, ActionAlertType.ERROR);
        }
        Member member = memberFromDatabase.get();
        Family family = member.getFamily();
        family.setFamilyId(0);
        family.setMembersList(new ArrayList<>());
        Family newFamily = familyJpaRepository.save(family);
        //Lets change family id for the member id
        memberService.updateMemberFamilyId(newFamily.getFamilyId(),memberId);
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
        Optional<Member> memberFromDatabase = memberService.findMember(memberId);
        if(!memberFromDatabase.isPresent()){
            return CommonUtil.createResponse(MemberActionResponse.MEMBER_DOES_NOT_EXISTS,
                    new Object[]{memberId}, ActionAlertType.ERROR);
        }
        Member member = memberFromDatabase.get();
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
    public List<CalledFamilyDetails> getFamiliesByTeamLeadAndSevadarName(String teamLeadName, String sevadarName) {
        List<CalledFamilyDetails> calledFamilyDetailsList = namedQueryExecutor.executeNamedQuery("Family.findCalledFamiliesByTeamLeadAndSevadar",
                new String[]{"teamLeadName","sevadarName"},
                new String[]{teamLeadName,sevadarName},
                CalledFamilyDetails.class);
        int previousFamilyId=0;
        int familyCounter=0;
        for (CalledFamilyDetails calledFamilyDetail:calledFamilyDetailsList){
            if(calledFamilyDetail.getFamilyId() !=previousFamilyId){
                calledFamilyDetail.setFamilySeqNumner(familyCounter+1);
                previousFamilyId=calledFamilyDetail.getFamilyId();
                familyCounter++;
            }
        }
        return calledFamilyDetailsList;
    }

    /*private <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor){
        Map<Object,Boolean> found = new ConcurrentHashMap<>();
        return t -> found.putIfAbsent(keyExtractor.apply(t),Boolean.TRUE)==null;
    }*/

    @Override
    public SevadarPhoneTreeList getSevadarPhoneTreeListByTeamLeadAndSevadarName(String teamLeadName,
                                                                                String sevadarName) {
        String teamLeadDetails = teamLeadService.getTeamLeadStrigyfyInformation(teamLeadName);
        String backupTeamLeadDetails = teamLeadService.getBackupTeamLeadStringyfyInformation(teamLeadName);
        String sevadarDetails = sevadarService.getSevadarStrigyfyInformation(sevadarName);
        List<CalledFamilyDetails> calledFamilyDetailsList = getFamiliesByTeamLeadAndSevadarName(teamLeadName,sevadarName);

        /*long familiesCount = calledFamilyDetailsList.stream()
                .filter(distinctByKey(calledFamilyDetails -> calledFamilyDetails.getFamilyId()))
                .count();*/
        long familiesCount = getTotalFamiliesByTeamLeadAndSevadar(teamLeadName,sevadarName);
        SevadarPhoneTreeList sevadarPhoneTreeList = new SevadarPhoneTreeList();
        sevadarPhoneTreeList.setTotalFamiliesToCall((int) familiesCount);
        sevadarPhoneTreeList.setTeamLeadDetails(teamLeadDetails);
        sevadarPhoneTreeList.setBackupTeamLeadDetails(backupTeamLeadDetails);
        sevadarPhoneTreeList.setSevadarDetails(sevadarDetails);
        sevadarPhoneTreeList.setCalledFamilyDetailsList(calledFamilyDetailsList);

        return sevadarPhoneTreeList;
    }

}