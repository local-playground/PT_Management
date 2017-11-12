package org.rssb.phonetree.services.impl;

import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.common.SevaType;
import org.rssb.phonetree.entity.BackupSevadar;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.services.BackupSevadarService;
import org.rssb.phonetree.services.MemberService;
import org.rssb.phonetree.services.SevadarService;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.services.UtilityService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.BackupSevadarActionResponse;
import org.rssb.phonetree.status.CommonActionResponse;
import org.rssb.phonetree.status.SevadarActionResponse;
import org.rssb.phonetree.status.TeamLeadActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtilityServiceImpl implements UtilityService {
    @Autowired
    private MemberService memberService;

    @Autowired
    private SevadarService sevadarService;

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private BackupSevadarService backupSevadarService;

    @Override
    public Response isMemberAvailableForSeva(int memberId, SevaType sevaType) {
        /*
            Check if Member is already assigned seva as Team Lead
        */
        Optional<TeamLead> teamLead = teamLeadService.findTeamLeadByMemberId(memberId);
        if (teamLead.isPresent()) {
            return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_ALREADY_EXISTS,
                    new Object[]{teamLead.get().getTeamLeadName()}, ActionAlertType.ERROR);
        }
        /*
            Check if Member is already assigned seva as Sevadar
        */
        Optional<Sevadar> sevadar = sevadarService.findSevadarByMemberId(memberId);
        if (sevadar.isPresent()) {
            return CommonUtil.createResponse(SevadarActionResponse.SEVADAR_ALREADY_EXISTS,
                    new Object[]{sevadar.get().getSevadarName()}, ActionAlertType.ERROR);
        }
        /*
            Check if member has family member doing seva as Team Lead
         */
        Optional<Member> member = memberService.findMember(memberId);
        int familyId = member.get().getFamily().getFamilyId();
        teamLead = teamLeadService.findTeamLeadByFamilyId(familyId);
        if (teamLead.isPresent() && sevaType == SevaType.ADD_TEAM_LEAD) {
            return CommonUtil.createResponse(TeamLeadActionResponse.FAMILY_MEMBER_ALREADY_ASSIGNED_TEAM_LEAD_SEVA,
                    new Object[]{CommonUtil.getFullName(member.get()), teamLead.get().getTeamLeadName()},
                    ActionAlertType.ERROR);
        }
        /*
            Check if member has family member doing seva as Sevadar
         */
        if (teamLead.isPresent() && sevaType == SevaType.ADD_SEVADAR) {
            return CommonUtil.createResponse(TeamLeadActionResponse.FAMILY_MEMBER_EXISTS_AS_TEAM_LEAD,
                    new Object[]{CommonUtil.getFullName(member.get()), teamLead.get().getTeamLeadName()},
                    ActionAlertType.CONFIRMATION);
        }

        /*
            Check if member has family member doing seva as Sevadar
         */

        sevadar = sevadarService.findSevadarByFamilyId(familyId);
        if (sevadar.isPresent() && sevaType == SevaType.ADD_TEAM_LEAD) {
            return CommonUtil.createResponse(SevadarActionResponse.FAMILY_MEMBER_EXISTS_AS_SEVADAR,
                    new Object[]{CommonUtil.getFullName(member.get()), sevadar.get().getSevadarName()},
                    ActionAlertType.CONFIRMATION);
        }

        sevadar = sevadarService.findSevadarByFamilyId(familyId);
        if (sevadar.isPresent() && sevaType == SevaType.ADD_SEVADAR) {
            return CommonUtil.createResponse(SevadarActionResponse.FAMILY_MEMBER_ALREADY_ASSIGNED_SEVADAR_SEVA,
                    new Object[]{CommonUtil.getFullName(member.get()), sevadar.get().getSevadarName()},
                    ActionAlertType.ERROR);
        }

        if(sevaType == SevaType.ADD_BACKUP_SEVADAR){
            Optional<BackupSevadar> backupSevadarOptional = backupSevadarService.findBackupSevadarByMemberId(memberId);
            if(backupSevadarOptional.isPresent()){
                return CommonUtil.createResponse(BackupSevadarActionResponse.BACKUP_SEVADAR_ALREADY_EXISTS,
                        new Object[]{CommonUtil.getFullName(backupSevadarOptional.get().getMember())},
                        ActionAlertType.ERROR);
            }
        }
        return CommonUtil.createResponse(CommonActionResponse.SUCCESS_MESSAGE,null,ActionAlertType.NONE);
    }

    @Override
    public boolean hasAnyMemberInFamilyAssignedAnySeva(int familyId) {
        Optional<TeamLead> teamLead = teamLeadService.findTeamLeadByFamilyId(familyId);
        if (teamLead.isPresent()){
            return true;
        }

        Optional<Sevadar> sevadar = sevadarService.findSevadarByFamilyId(familyId);
        if(sevadar.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public boolean hasMemberAssignedAnySeva(int memberId) {
        Optional<TeamLead> teamLead = teamLeadService.findTeamLeadByMemberId(memberId);
        if(teamLead.isPresent()){
            return true;
        }

        Optional<Sevadar> sevadar = sevadarService.findSevadarByMemberId(memberId);
        if(sevadar.isPresent()){
            return true;
        }

        return false;
    }
}
