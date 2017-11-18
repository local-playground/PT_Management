package org.rssb.phonetree.repository;


import org.rssb.phonetree.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface FamilyJpaRepository extends JpaRepository<Family,Integer> {

    @Query("SELECT count(f.familyId) from Family f Where f.sevadar.sevadarsId = :sevadarId")
    int getTotalFamiliesBySevadarId(@Param(value = "sevadarId") int sevadarId);

    @Query("SELECT count(f.familyId) from Family f Where f.teamLead.teamLeadId = :teamLeadId")
    int getTotalFamiliesByTeamLeadId(@Param(value = "teamLeadId") int teamLeadId);


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


    /*@Query(value = "SELECT Count(*) AS totalSangatCount FROM Members\n" +
            "UNION\n" +
            "SELECT  Count(*) AS totalFamiliesCount FROM Family \n" +
            "UNION \n" +
            "SELECT Count(*) As onCallingListFamiliesCount from (SELECT DISTINCT familyId FROM Members WHERE onCallingList = 'Yes')\n" +
            "UNION \n" +
            "SELECT Count(*) AS sevadarsFamilies FROM (SELECT DISTINCT familyId FROM Members WHERE onCallingList = 'No')",
            nativeQuery = true)*/

    @Query(value = "SELECT  Count(*) AS totalFamiliesCount FROM Family",nativeQuery = true)
    int getTotalFamiliesCount();


}
