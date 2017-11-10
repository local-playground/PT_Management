package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.domain.SevadarPersonalInformation;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.entity.emums.YesNo;
import org.rssb.phonetree.repository.NamedQueryExecutor;
import org.rssb.phonetree.repository.TeamLeadJpaRepository;
import org.rssb.phonetree.services.BackupSevadarService;
import org.rssb.phonetree.services.MemberService;
import org.rssb.phonetree.services.TeamLeadService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.TeamLeadActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TeamLeadServiceImpl implements TeamLeadService {

    @Autowired
    private NamedQueryExecutor namedQueryExecutor;

    @Autowired
    private TeamLeadJpaRepository teamLeadJpaRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BackupSevadarService backupSevadarService;


    @Override
    public List<TeamLead> findAllTeamLeads() {
        return teamLeadJpaRepository.findAll();
        //return namedQueryExecutor.executeNamedQuery("TeamLead.findAllTeamLeads",null,null,TeamLead.class);
    }

    @Override
    public Optional<TeamLead> findTeamLeadById(int teamLeadId) {
        return Optional.ofNullable(teamLeadJpaRepository.findOne(teamLeadId));
    }

    @Override
    public Optional<TeamLead> findTeamLeadByTeamLeadName(String teamLeadName) {
        return Optional.ofNullable(teamLeadJpaRepository.findByTeamLeadName(teamLeadName));
    }

    @Override
    public Response replaceTeamLead(int teamLeadId, int withNewMemberId) {
        TeamLead oldTeamLead = findTeamLeadById(teamLeadId).get();
        String oldTeamLeadName = oldTeamLead.getTeamLeadName();
        int oldTeamLeadFamilyId = oldTeamLead.getFamily().getFamilyId();

        Member member = memberService.findMember(withNewMemberId).get();
        int newTeamLeadMemberId = member.getMemberId();
        int newTeamLeadFamilyId = member.getFamily().getFamilyId();

        //STEP 1 - Replace Old Team Lead with New Team Lead
        oldTeamLead.setMember(member);
        oldTeamLead.setFamily(member.getFamily());
        oldTeamLead.setTeamLeadName(CommonUtil.getFullName(member));
        teamLeadJpaRepository.save(oldTeamLead);
        //STEP 2 - Put new TeamLead Family NOT on calling list
        memberService.putSevadarBackToCallingList(YesNo.NO, newTeamLeadFamilyId);
        //STEP 3 - Put old TeamLead back on calling list, so that he/she can get SNV info
        memberService.putSevadarBackToCallingList(YesNo.YES, oldTeamLeadFamilyId);
        //STEP 4 - If new Team was on Backup Sevadars List, then remove from there
        backupSevadarService.removeBackupSevadar(newTeamLeadMemberId);

        return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_SUCCESSFULLY_REPLACED,
                new Object[]{oldTeamLeadName, CommonUtil.getFullName(member)},
                ActionAlertType.INFORMATION);
    }

    @Override
    public Response deleteTeamLead(int teamLeadId) {
        Optional<TeamLead> teamLead = findTeamLeadById(teamLeadId);
        if (!teamLead.isPresent()) {
            return null; // TO-DO
        }
        String teamLeadName = teamLead.get().getTeamLeadName();
        int sevadarsCount = teamLeadJpaRepository.getSevadarsCount(teamLead.get().getTeamLeadId());
        if (sevadarsCount != 0) {
            return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_HAS_SEVADARS_ASSIGNED,
                    new Object[]{teamLeadName, sevadarsCount},
                    ActionAlertType.ERROR);
        }

        //Reached here means its safe to delete team lead
        int familyId = teamLead.get().getFamily().getFamilyId();
        memberService.putSevadarBackToCallingList(YesNo.YES, familyId);
        System.out.println(teamLeadName + ": members have been put back on calling list for family id " + familyId);
        int rowsUpdated = teamLeadJpaRepository.deleteTeamLead(teamLead.get().getTeamLeadId());
        return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_SUCCESSFULLY_DELETED,
                new Object[]{teamLeadName},
                ActionAlertType.INFORMATION);
    }

    @Override
    public Response swapTeamLead(int teamLeadIdToBeSwapped, int teamLeadIdSwappedWith) {
        if (teamLeadIdToBeSwapped == teamLeadIdSwappedWith) {
            return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_TO_BE_SWAPPED_HAS_TO_BE_DIFFERENT,
                    new Object[]{},
                    ActionAlertType.ERROR);
        }

        Optional<TeamLead> teamLeadToBeSwapped = findTeamLeadById(teamLeadIdToBeSwapped);
        Optional<TeamLead> teamLeadSwapWith = findTeamLeadById(teamLeadIdSwappedWith);

        if (!teamLeadToBeSwapped.isPresent()) {
            return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_DOES_NOT_EXISTS,
                    new Object[]{teamLeadIdToBeSwapped},
                    ActionAlertType.ERROR);
        }

        if (!teamLeadSwapWith.isPresent()) {
            return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_DOES_NOT_EXISTS,
                    new Object[]{teamLeadIdSwappedWith},
                    ActionAlertType.ERROR);
        }

        TeamLead toBeSwapped = teamLeadToBeSwapped.get();
        TeamLead swapWith = teamLeadSwapWith.get();

        //temporary hold values
        Member member = toBeSwapped.getMember();
        Family family = toBeSwapped.getFamily();
        String teamLeadName = toBeSwapped.getTeamLeadName();

        Member memberS = swapWith.getMember();
        Family familyS = swapWith.getFamily();
        String teamLeadNameS = swapWith.getTeamLeadName();

        swapWith.setMember(member);
        swapWith.setFamily(family);
        swapWith.setTeamLeadName(teamLeadName);

        toBeSwapped.setMember(memberS);
        toBeSwapped.setFamily(familyS);
        toBeSwapped.setTeamLeadName(teamLeadNameS);

        teamLeadJpaRepository.save(toBeSwapped);
        teamLeadJpaRepository.save(swapWith);
        return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_HAS_BEEN_SWAPPED,
                new Object[]{teamLeadName, teamLeadNameS},
                ActionAlertType.INFORMATION);
    }

    @Override
    public Response addTeamLead(int memberId) {
        Optional<Member> member = memberService.findMember(memberId);
        if (!member.isPresent()) {
            return null; //To-Do
        }
        int maxTeamLeadId = teamLeadJpaRepository.getMaxTeamLeadId();
        Family family = member.get().getFamily();
        TeamLead teamLead = new TeamLead();
        teamLead.setTeamLeadId(maxTeamLeadId + 1);
        teamLead.setTeamLeadName(member.get().getFirstName() + " " + member.get().getLastName());
        teamLead.setFamily(family);
        teamLead.setMember(member.get());

        teamLeadJpaRepository.save(teamLead);
        System.out.println("Saved successfully");

        int familyId = family.getFamilyId();
        memberService.putSevadarBackToCallingList(YesNo.NO, familyId);
        return CommonUtil.createResponse(TeamLeadActionResponse.TEAM_LEAD_SUCCESSFULLY_ADDED,
                new Object[]{CommonUtil.getFullName(member.get())},
                ActionAlertType.INFORMATION);
    }

    @Override
    public Optional<TeamLead> findTeamLeadByMemberId(int memberId) {
        return Optional.ofNullable(teamLeadJpaRepository.findByMemberMemberId(memberId));
    }

    @Override
    public Optional<TeamLead> findTeamLeadByFamilyId(int familyId) {
        return Optional.ofNullable(teamLeadJpaRepository.findByFamilyFamilyId(familyId));
    }

    @Override
    public List<Sevadar> findSevadarListByTeamLeadName(String teamLeadName) {
        List<Sevadar> sevadarList = teamLeadJpaRepository.getSevadarsListByTeamLeadName(teamLeadName);
        return sortByBackupTeamLead(sevadarList);
    }

    @Override
    public List<Sevadar> findSevadarListByTeamLeadId(int teamLeadId) {
        List<Sevadar> sevadarList = teamLeadJpaRepository.getSevadarsListByTeamLeadId(teamLeadId);
        return sortByBackupTeamLead(sevadarList);
    }

    private List<Sevadar> sortByBackupTeamLead(List<Sevadar> sevadarList) {
        Predicate<Sevadar> isBackupTeamLead = sevadar -> sevadar.getIsBackupForTeamLead() == 1;
        List<Sevadar> sortedList = new ArrayList<>();
        List<Sevadar> backupTeamLeadsList = sevadarList
                .stream()
                .filter(isBackupTeamLead)
                .collect(Collectors.toList());
        sortedList.addAll(backupTeamLeadsList);
        List<Sevadar> nonBackupTeamLeadsList = sevadarList
                .stream()
                .filter(isBackupTeamLead.negate())
                .collect(Collectors.toList());
        sortedList.addAll(nonBackupTeamLeadsList);
        return sortedList;
    }

    @Override
    public String getTeamLeadStrigyfyInformation(String teamLeadName) {
        SevadarPersonalInformation sevadarPersonalInformation =
                namedQueryExecutor.executeSingleResultQuery("TeamLead.personalInformation",
                        "teamLeadName", teamLeadName, SevadarPersonalInformation.class);

        return sevadarPersonalInformation.getStringyfyInformation("Team Lead");
    }

    @Override
    public String getBackupTeamLeadStringyfyInformation(String teamLeadName) {
        SevadarPersonalInformation sevadarPersonalInformation =
                namedQueryExecutor.executeSingleResultQuery("TeamLead.BackupTeamLeadPersonalInformation",
                        "teamLeadName", teamLeadName, SevadarPersonalInformation.class);

        return sevadarPersonalInformation.getStringyfyInformation("Backup Team Lead");
    }

    @Override
    public void save(TeamLead teamLead) {
        teamLeadJpaRepository.saveAndFlush(teamLead);
    }
}


