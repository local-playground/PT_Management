package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.domain.SevadarPhoneTreeList;
import org.rssb.phonetree.entity.Family;

import java.util.List;

public interface FamilyService {
    int getTotalFamiliesBySevadarId(int sevadarId);
    long getTotalFamiliesByTeamLeadAndSevadar(String teamLeadName,String sevadarName);
    void updateSevadarAndTeamLeadId(int newSevadarId,int newTeamLeadId,int whereSevadarId);
    void updateSevadarAndTeamLeadIdForFamilyId(int newSevadarId, int newTeamLeadId, int whereFamilyId);
    Response deleteFamily(int familyId);
    Response deleteFamilyMember(int memberId);
    Response moveMemberAsSeparateFamily(int memberId);
    Response saveToDatabase(Family family);
    Response moveMemberUnderOtherFamily(int memberId,int otherFamilyId);
    List<CalledFamilyDetails> getFamiliesByTeamLeadAndSevadarName(String teamLeadName, String sevadarName);
    SevadarPhoneTreeList getSevadarPhoneTreeListByTeamLeadAndSevadarName(String teamLeadName, String sevadarName);

}
