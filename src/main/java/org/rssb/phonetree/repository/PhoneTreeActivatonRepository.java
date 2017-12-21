package org.rssb.phonetree.repository;

import org.rssb.phonetree.domain.PhoneTreeActivationSummary;
import org.rssb.phonetree.entity.PhoneTreeActivation;
import org.rssb.phonetree.entity.PhoneTreeActivationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface PhoneTreeActivatonRepository extends JpaRepository<PhoneTreeActivation,Integer> {
    PhoneTreeActivation findByPhoneTreeActivationDate(String activationDate);

    @Query("SELECT phd FROM PhoneTreeActivation pha " +
            " JOIN pha.phoneTreeActivationDetailList  phd " +
            " WHERE pha.phoneTreeActivationDate = :activationDate " +
            " AND phd.sevadar.sevadarsId = :sevadarId ")
    PhoneTreeActivationDetail getActivationDetailsByDateAndSevadarId(@Param(value = "activationDate") String activationDate,
                                                                     @Param(value = "sevadarId") int sevadarId);


    @Query("SELECT phd FROM PhoneTreeActivation pha " +
            " JOIN pha.phoneTreeActivationDetailList  phd " +
            " JOIN FETCH phd.sevadar " +
            " WHERE pha.phoneTreeActivationDate = :activationDate " +
            " AND phd.teamLead.teamLeadId = :teamLeadId ")
    List<PhoneTreeActivationDetail> getActivationDetailsByDateAndTeamLeadId(@Param(value = "activationDate") String activationDate,
                                                                            @Param(value = "teamLeadId") int teamLeadId);


    PhoneTreeActivationSummary getPhoneTreeActivationSummary(@Param(value = "activationDate") String activationDate);

}
