package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;

public interface BackupSevadarService {
    Response addAsBackupSevadar(int memberId);
    Response removeBackupSevadar(int memberId);
}

