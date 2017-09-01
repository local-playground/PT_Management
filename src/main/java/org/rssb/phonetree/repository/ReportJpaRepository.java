package org.rssb.phonetree.repository;

import org.rssb.phonetree.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportJpaRepository extends JpaRepository<Report,Integer>{
}
