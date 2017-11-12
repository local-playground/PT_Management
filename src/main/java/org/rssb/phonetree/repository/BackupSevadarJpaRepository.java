package org.rssb.phonetree.repository;

import org.rssb.phonetree.entity.BackupSevadar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupSevadarJpaRepository extends JpaRepository<BackupSevadar,Integer>{
    BackupSevadar findByFamilyFamilyId(int familyId);
    @Query("SELECT max (b.backupSevadarsId) from BackupSevadar b")
    Integer getMaxBackupSevadarId();

    BackupSevadar findByMemberMemberId(int memberId);
}
