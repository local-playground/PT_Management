package org.rssb.phonetree.repository;


import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.emums.YesNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface MemberJpaRepository extends JpaRepository<Member,Integer> {

    /*@Query(value = "UPDATE Members SET isOnCallingList=:onCallingList WHERE FamilyId = :familyId",nativeQuery = true)
    void putSevadarBackToCallingList(@Param("onCallingList") int onCallingList,
                                    @Param("familyId") int familyId);*/
    @Modifying
    void putSevadarBackToCallingList(YesNo yesNo, int familyId);

    @Query("SELECT max (m.memberId) from Member m")
    int getMaxMemberId();

    @Query("UPDATE Member SET family.familyId = :familyId " +
            "WHERE memberId = :memberId")
    @Modifying
    void updateMemberFamilyId(@Param(value = "familyId") int familyId,
                                    @Param(value = "memberId") int memberId);

}
