package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.entity.Sevadar;

import java.util.List;
import java.util.Optional;

public interface SevadarService {
    List<Sevadar> findAllSevadars();
    Optional<Sevadar> findSevadarByFamilyId(int familyId);
    Optional<Sevadar> findSevadarByMemberId(int memberId);
    Optional<Sevadar> findSevadarBySevadarId(int sevadarId);
    Optional<Sevadar> findSevadarBySevadarName(String sevadarName);
    Response addSevadar(int memberId,int teamLeadId);
    Response deleteSevadar(int sevadarId);
    Response moveSevadarUnderOtherTeamLead(int sevadarId,int teamLeadId);
    Response replaceSevadar(int sevadarId,int memberId);
    Response swapSevadar(int sevadarIdToBeSwapped, int sevadarIdSwappedWith);
    List<FamilyCount> getSevadarsCallingFamilyCountByTeamLeadId(int teamLeadId);
    String getSevadarStrigyfyInformation(String sevadarName);
}
