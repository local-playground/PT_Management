package org.rssb.phonetree.services.impl;


import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.entity.BackupSevadar;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.repository.BackupSevadarJpaRepository;
import org.rssb.phonetree.services.BackupSevadarService;
import org.rssb.phonetree.services.MemberService;
import org.rssb.phonetree.status.ActionAlertType;
import org.rssb.phonetree.status.BackupSevadarActionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BackupSevadarServiceImpl implements BackupSevadarService {
    @Autowired
    private BackupSevadarJpaRepository backupSevadarJpaRepository;

    @Autowired
    private MemberService memberService;


    @Override
    public List<BackupSevadar> findAllBackupSevadars() {
        return backupSevadarJpaRepository.findAll();
    }

    @Override
    public Optional<BackupSevadar> findBackupSevadarByMemberId(int memberId) {
        return Optional.ofNullable(backupSevadarJpaRepository.findByMemberMemberId(memberId));
    }

    @Override
    public Optional<BackupSevadar> findBackupSevadarByFamilyId(int familyId) {
        return Optional.ofNullable(backupSevadarJpaRepository.findByFamilyFamilyId(familyId));
    }

    @Override
    public Response addAsBackupSevadar(int memberId) {
        BackupSevadar backupSevadar = backupSevadarJpaRepository.findByMemberMemberId(memberId);
        if (backupSevadar != null) {
            return CommonUtil.createResponse(BackupSevadarActionResponse.BACKUP_SEVADAR_ALREADY_EXISTS,
                    new Object[]{backupSevadar.getSevadarName()},
                    ActionAlertType.ERROR);
        }
        Member member = memberService.findMember(memberId).get();
        backupSevadar = new BackupSevadar();
        backupSevadar.setSevadarName(CommonUtil.getFullName(member));
        backupSevadar.setFamily(member.getFamily());
        backupSevadar.setMember(member);
        backupSevadarJpaRepository.save(backupSevadar);

        return CommonUtil.createResponse(BackupSevadarActionResponse.BACKUP_SEVADAR_SUCCESSFULLY_ADDED,
                new Object[]{CommonUtil.getFullName(member)},
                ActionAlertType.INFORMATION);
    }

    @Override
    public Response removeBackupSevadar(int memberId) {
        BackupSevadar backupSevadar = backupSevadarJpaRepository.findByMemberMemberId(memberId);
        if (backupSevadar != null) {
            backupSevadarJpaRepository.delete(backupSevadar);
            return CommonUtil.createResponse(BackupSevadarActionResponse.BACKUP_SEVADAR_SUCCESSFULLY_DELETED,
                    new Object[]{backupSevadar.getSevadarName()},
                    ActionAlertType.INFORMATION);
        }
        return CommonUtil.createResponse(BackupSevadarActionResponse.BACKUP_SEVADAR_NOT_FOUND,
                new Object[]{},
                ActionAlertType.INFORMATION);
    }
}
