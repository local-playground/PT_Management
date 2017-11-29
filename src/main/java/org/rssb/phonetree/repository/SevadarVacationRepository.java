package org.rssb.phonetree.repository;


import org.rssb.phonetree.entity.SevadarVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface SevadarVacationRepository extends JpaRepository<SevadarVacation,Integer> {
    SevadarVacation findBySevadarSevadarsId(int sevadarId);
    SevadarVacation findByTeamLeadTeamLeadId(int teamLeadId);
}
