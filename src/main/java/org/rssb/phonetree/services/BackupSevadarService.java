package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.entity.BackupSevadar;

import java.util.List;
import java.util.Optional;

public interface BackupSevadarService {
    List<BackupSevadar> findAllBackupSevadars();
    Optional<BackupSevadar> findBackupSevadarByMemberId(int memberId);
    Optional<BackupSevadar> findBackupSevadarByFamilyId(int familyId);
    Response addAsBackupSevadar(int memberId);
    Response removeBackupSevadar(int memberId);
}

