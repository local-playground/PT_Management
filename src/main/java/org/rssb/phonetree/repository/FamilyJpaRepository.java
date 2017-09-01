package org.rssb.phonetree.repository;


import org.rssb.phonetree.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface FamilyJpaRepository extends JpaRepository<Family,Integer> {

    @Query("SELECT count(f.familyId) from Family f Where f.sevadar.sevadarsId = :sevadarId")
    int getTotalFamiliesBySevadarId(@Param(value = "sevadarId") int sevadarId);

    @Query("UPDATE Family SET sevadar.sevadarsId= :newSevadarId, teamLead.teamLeadId = :newTeamLeadId " +
            "WHERE sevadar.sevadarsId = :whereSevadarId")
    @Modifying
    void updateSevadarAndTeamLeadId(@Param(value = "newSevadarId") int newSevadarId,
                                    @Param(value = "newTeamLeadId") int newTeamLeadId,
                                    @Param(value = "whereSevadarId") int whereSevadarId);

    @Query("SELECT max(f.familyId) from Family f")
    int getMaxFamilyId();

    @Query("UPDATE Family SET sevadar.sevadarsId= :newSevadarId, teamLead.teamLeadId = :newTeamLeadId " +
            "WHERE familyId = :whereFamilyId")
    @Modifying
    void updateSevadarAndTeamLeadIdForFamilyId(@Param(value = "newSevadarId") int newSevadarId,
                                               @Param(value = "newTeamLeadId") int newTeamLeadId,
                                               @Param(value = "whereFamilyId") int whereFamilyId);

    List<Family> findByTeamLeadTeamLeadNameAndSevadarSevadarName(String teamLeadName,String sevadarName);
}
