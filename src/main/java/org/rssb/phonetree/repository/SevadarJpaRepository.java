package org.rssb.phonetree.repository;


import org.rssb.phonetree.entity.Sevadar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
