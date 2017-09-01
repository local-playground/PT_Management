package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.entity.Family;

import java.util.List;

public interface FamilyService {
    int getTotalFamiliesBySevadarId(int sevadarId);
    void updateSevadarAndTeamLeadId(int newSevadarId,int newTeamLeadId,int whereSevadarId);
    void updateSevadarAndTeamLeadIdForFamilyId(int newSevadarId, int newTeamLeadId, int whereFamilyId);
    Response deleteFamily(int familyId);
    Response deleteFamilyMember(int memberId);
    Response moveMemberAsSeparateFamily(int memberId);
    Response saveToDatabase(Family family);
    Response moveMemberUnderOtherFamily(int memberId,int otherFamilyId);
    List<Family> getFamiliesByTeamLeadAndSevadarName(String teamLeadName,String sevadarName);
}
