package org.rssb.phonetree.services;

import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;

import java.util.List;
import java.util.Optional;

public interface TeamLeadService {
   List<TeamLead> findAllTeamLeads();
   Optional<TeamLead> findTeamLeadById(int teamLeadId);
   Response replaceTeamLead(int teamLeadId, int withNewMemberId);
   Response deleteTeamLead(int teamLeadId);
   Response swapTeamLead(int teamLeadIdToBeSwapped, int teamLeadIdSwappedWith);
   Response addTeamLead(int memberId);
   Optional<TeamLead> findTeamLeadByMemberId(int memberId);
   Optional<TeamLead> findTeamLeadByFamilyId(int familyId);
   List<Sevadar> findSevadarListByTeamLeadName(String teamLeadName);
   List<Sevadar> findSevadarListByTeamLeadId(int teamLeadId);

}
