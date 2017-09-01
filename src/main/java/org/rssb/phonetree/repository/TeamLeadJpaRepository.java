package org.rssb.phonetree.repository;

import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface TeamLeadJpaRepository extends JpaRepository<TeamLead,Integer>{

    @Query("SELECT SIZE(t.sevadarsList) FROM TeamLead t WHERE t.teamLeadId = :id")
    int getSevadarsCount(@Param("id") int id);

    @Modifying
    @Transactional(readOnly = false)
    @Query("DELETE FROM TeamLead t WHERE t.teamLeadId = :id")
    int deleteTeamLead(@Param("id") int id);

    TeamLead findByMemberMemberId(int memberId);

    TeamLead findByFamilyFamilyId(int familyid);

    @Query("SELECT t.sevadarsList from TeamLead t where t.teamLeadName = :teamLeadName")
    List<Sevadar> getSevadarsListByTeamLeadName(@Param(value = "teamLeadName") String teamLeadName);

    @Query("SELECT t.sevadarsList from TeamLead t where t.teamLeadId = :teamLeadId")
    List<Sevadar> getSevadarsListByTeamLeadId(@Param(value = "teamLeadId") int teamLeadId);

    @Query("SELECT max(t.teamLeadId) from TeamLead t")
    Integer getMaxTeamLeadId();

}
