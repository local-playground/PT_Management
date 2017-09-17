package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.entity.emums.YesNo;
import org.rssb.phonetree.repository.NamedQueryExecutor;
import org.rssb.phonetree.repository.SevadarJpaRepository;
import org.rssb.phonetree.services.*;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.SevadarActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SevadarServiceImpl implements SevadarService {

    @Autowired
    private NamedQueryExecutor namedQueryExecutor;

    @Autowired
    private SevadarJpaRepository sevadarJpaRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TeamLeadService teamLeadService;

    @Autowired
    private BackupSevadarService backupSevadarService;

    @Autowired
    private FamilyService familyService;

    @Override
    public List<Sevadar> findAllSevadars() {
        return sevadarJpaRepository.findAll();
    }

    @Override
    public Optional<Sevadar> findSevadarByFamilyId(int familyId) {
        return Optional.ofNullable(sevadarJpaRepository.findByFamilyFamilyId(familyId));
    }

    @Override
    public Optional<Sevadar> findSevadarByMemberId(int memberId) {
        return Optional.ofNullable(sevadarJpaRepository.findByMemberMemberId(memberId));
    }

    @Override
    public Optional<Sevadar> findSevadarBySevadarId(int sevadarId) {
        return Optional.ofNullable(sevadarJpaRepository.findOne(sevadarId));
    }

    @Override
    public Optional<Sevadar> findSevadarBySevadarName(String sevadarName) {
        return Optional.ofNullable(sevadarJpaRepository.findBySevadarName(sevadarName));
    }

    @Override
    public Response addSevadar(int memberId,int teamLeadId) {
        Optional<Member> member = memberService.findMember(memberId);
        if(!member.isPresent()){
            return null; //To-Do
        }
        //Find team Lead for this Sevadar
        TeamLead teamLead = teamLeadService.findTeamLeadById(teamLeadId).get();
        int sevadarMaxId = sevadarJpaRepository.getMaxSevadarId();
        //Lets create Sevadar and Save
        Sevadar sevadar = new Sevadar();
        sevadar.setSevadarsId(sevadarMaxId+1);
        sevadar.setMember(member.get());
        sevadar.setFamily(member.get().getFamily());
        sevadar.setSevadarName(CommonUtil.getFullName(member.get()));
        sevadar.setTeamLead(teamLead);
        sevadar.setIsBackupForTeamLead(0);
        sevadarJpaRepository.save(sevadar);
        //Mark newly added Sevadar Family to NOT to get SNV calls.
        memberService.putSevadarBackToCallingList(YesNo.NO,member.get().getFamily().getFamilyId());
        //Remove from BackupSevadars table if already exists
        backupSevadarService.removeBackupSevadar(memberId);
        return CommonUtil.createResponse(SevadarActionResponse.SEVADAR_SUCCESSFULLY_ADDED,
                new Object[]{CommonUtil.getFullName(member.get()),teamLead.getTeamLeadName()},
                ActionAlertType.INFORMATION);
    }

    @Override
    public Response deleteSevadar(int sevadarId) {
        Optional<Sevadar> sevadar = findSevadarBySevadarId(sevadarId);
        if(!sevadar.isPresent()){
            return null;//To-Do
        }

        String sevadarName = sevadar.get().getSevadarName();
        int familiesCount = familyService.getTotalFamiliesBySevadarId(sevadarId);
        if(familiesCount!=0){
            return CommonUtil.createResponse(SevadarActionResponse.SEVADAR_HAS_SEVADARS_ASSIGNED,
                    new Object[]{sevadar.get().getSevadarName(),familiesCount},
                    ActionAlertType.ERROR);
        }

        //Put Sevadar back on calling list, so that he/she can get SNV info
        memberService.putSevadarBackToCallingList(YesNo.YES,sevadar.get().getFamily().getFamilyId());

        //DELETE team from Sevadars table
        sevadarJpaRepository.delete(sevadar.get());
        return CommonUtil.createResponse(SevadarActionResponse.SEVADAR_SUCCESSFULLY_DELETED,
                new Object[]{sevadar.get().getSevadarName()},
                ActionAlertType.INFORMATION);
    }

    @Override
    public Response moveSevadarUnderOtherTeamLead(int sevadarId, int teamLeadId) {
        Sevadar sevadar = findSevadarBySevadarId(sevadarId).get();
        TeamLead newTeamLead = teamLeadService.findTeamLeadById(teamLeadId).get();
        sevadar.setTeamLead(newTeamLead);
        sevadarJpaRepository.save(sevadar);

        //Lets change Team Lead in Family table as well.
        familyService.updateSevadarAndTeamLeadId(sevadarId,teamLeadId,sevadarId);
        return CommonUtil.createResponse(SevadarActionResponse.SEVADAR_SUCCESSFULLY_MOVED_UNDER_OTHER_TEAM_LEAD,
                new Object[]{sevadar.getSevadarName(),newTeamLead.getTeamLeadName()},
                ActionAlertType.INFORMATION);
    }

    @Override
    public Response replaceSevadar(int sevadarId, int withNewMemberId) {
        Sevadar oldSevadar = findSevadarBySevadarId(sevadarId).get();
        String oldSevadarName = oldSevadar.getSevadarName();
        int oldSevadarFamilyId = oldSevadar.getFamily().getFamilyId();

        Member member = memberService.findMember(withNewMemberId).get();
        int newSevadarMemberId = member.getMemberId();
        int newSevadarFamilyId = member.getFamily().getFamilyId();

        //STEP 1 - Replace Old Sevadar with New Sevadar
        oldSevadar.setFamily(member.getFamily());
        oldSevadar.setMember(member);
        oldSevadar.setSevadarName(CommonUtil.getFullName(member));
        sevadarJpaRepository.save(oldSevadar);

        //STEP 2 - Put new Sevadar's Family NOT on calling list
        memberService.putSevadarBackToCallingList(YesNo.NO,newSevadarFamilyId);
        //STEP 3 - Put old Sevadar's family back on calling list, so that he/she can get SNV info
        memberService.putSevadarBackToCallingList(YesNo.YES,oldSevadarFamilyId);
        //STEP 4 - If new Team was on Backup Sevadars List, then remove from there
        backupSevadarService.removeBackupSevadar(newSevadarMemberId);

        return CommonUtil.createResponse(SevadarActionResponse.SEVADAR_SUCCESSFULLY_REPLACED,
                new Object[]{oldSevadarName,CommonUtil.getFullName(member)},
                ActionAlertType.INFORMATION);
    }

    @Override
    //7 Manoj - 21 Kamal
    public Response swapSevadar(int sevadarIdToBeSwapped, int sevadarIdSwappedWith) {
        if(sevadarIdToBeSwapped == sevadarIdSwappedWith){
            //Please pick different Sevadars to swap.
            return null;//to do
        }
        //Manoj
        Sevadar toBeSwappedSevadar = findSevadarBySevadarId(sevadarIdToBeSwapped).get();
        //Kamal
        Sevadar swapWithSevadar = findSevadarBySevadarId(sevadarIdSwappedWith).get();

        //Naren Shah
        TeamLead toBeSwappedTeamLead = toBeSwappedSevadar.getTeamLead();
        //Mina Patel
        TeamLead swapWithTeamLead = swapWithSevadar.getTeamLead();

        if(toBeSwappedTeamLead.getTeamLeadId() == swapWithTeamLead.getTeamLeadId()){
            //Both Sevadars reports to same Team Lead, so can't be swapped.
            return null; //to do
        }

        //temporary hold values
        //Manoj information
        Member member = toBeSwappedSevadar.getMember();
        Family family = toBeSwappedSevadar.getFamily();
        String sevadarName = toBeSwappedSevadar.getSevadarName();

        //Kamal information
        Member memberS = swapWithSevadar.getMember();
        Family familyS = swapWithSevadar.getFamily();
        String sevadarNameS = swapWithSevadar.getSevadarName();

        //Replace Kamal's info with Manoj
        swapWithSevadar.setMember(member);
        swapWithSevadar.setFamily(family);
        swapWithSevadar.setSevadarName(sevadarName);

        //Replace Manoj's info with Kamal
        toBeSwappedSevadar.setMember(memberS);
        toBeSwappedSevadar.setFamily(familyS);
        toBeSwappedSevadar.setSevadarName(sevadarNameS);

        sevadarJpaRepository.save(toBeSwappedSevadar);
        sevadarJpaRepository.save(swapWithSevadar);

        //Lets carry over their assigned families to call back in their list
        //Fetch updated info back from DB
        //Manoj information
        //21
        Sevadar updatedSevadar = findSevadarBySevadarName(sevadarName).get();
        int sevadarId = updatedSevadar.getSevadarsId();
        int teamLeadId = updatedSevadar.getTeamLead().getTeamLeadId();
        //Kamal information
        //7
        Sevadar updatedSevadarS = findSevadarBySevadarName(sevadarNameS).get();
        int sevadarIdS = updatedSevadarS.getSevadarsId();
        int teamLeadIdS = updatedSevadarS.getTeamLead().getTeamLeadId();

        int tempSevadarId = 9999;
        int tempTeamLeadId = 9999;

        //Update Manoj's Calling families to temporary
        familyService.updateSevadarAndTeamLeadId(tempSevadarId,tempTeamLeadId,sevadarIdToBeSwapped);

        //Update Kamal's calling families to new sevadar and teamlead id
        familyService.updateSevadarAndTeamLeadId(sevadarIdS,teamLeadIdS,sevadarIdSwappedWith);

        //Update Manoj's calling families to new sevadar and teamlead id
        familyService.updateSevadarAndTeamLeadId(sevadarId,teamLeadId,tempSevadarId);

        return CommonUtil.createResponse(SevadarActionResponse.SEVADAR_HAS_BEEN_SWAPPED,
                new Object[]{sevadarName,sevadarNameS},
                ActionAlertType.INFORMATION);
    }

    @Override
    public List<FamilyCount> getSevadarsCallingFamilyCountByTeamLeadId(int teamLeadId) {
        return namedQueryExecutor.executeNamedQuery("Family.getSevadarsCallingFamilyCountByTeamLeadId","teamLeadId",teamLeadId,FamilyCount.class);
    }


}
