package org.rssb.phonetree.repository;

import org.rssb.phonetree.entity.PhoneTreeActivationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PhoneTreeActivationDetailRepository extends JpaRepository<PhoneTreeActivationDetail, Integer> {
    PhoneTreeActivationDetail findBySevadarSevadarsId(int sevadarId);
}
