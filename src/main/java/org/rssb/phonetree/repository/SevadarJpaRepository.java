package org.rssb.phonetree.repository;


import org.rssb.phonetree.entity.Sevadar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface SevadarJpaRepository extends JpaRepository<Sevadar,Integer>{
    Sevadar findByMemberMemberId(int memberId);
    Sevadar findByFamilyFamilyId(int familyId);
    Sevadar findBySevadarName(String sevadarName);
    @Query("SELECT max(s.sevadarsId) from Sevadar s")
    int getMaxSevadarId();

    @Query("UPDATE Sevadar SET isBackupForTeamLead = 1 " +
            "WHERE sevadarsId = :sevadarId")
    @Modifying
    void updateSevadarAsTeamLeadBackup(@Param(value = "sevadarId") int sevadarId);

    @Query("UPDATE Sevadar SET isBackupForTeamLead = 0 " +
            "WHERE teamLead.teamLeadId = :teamLeadId")
    @Modifying
    void updateSevadarsForTeamLeadAsNoBackup(@Param(value = "teamLeadId") int teamLeadId);
}
