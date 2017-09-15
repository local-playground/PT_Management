package org.rssb.phonetree.entity.builder;

import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.TeamLead;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TeamLeadEntityBuilder implements EntityBuilder {
    private List<TeamLead> teamLeadList = new ArrayList<>();
    @Override
    public void build(List<Map<String, String>> data) {
        data.stream().forEach(mapData -> {
            int familyId = CommonUtil.convertStringToInt(mapData.get("FamilyId"),-1);
            int memberId = CommonUtil.convertStringToInt(mapData.get("MemberId"), -1);
            int teamLeadId = CommonUtil.convertStringToInt(mapData.get("TeamLeadId"),-1);
            String teamLeadName = mapData.get("TeamLeadName");

            TeamLead teamLead = new TeamLead();
            teamLead.setTeamLeadId(teamLeadId);

            Family family = new Family();
            family.setFamilyId(familyId);

            Member member = new Member();
            member.setMemberId(memberId);

            teamLead.setFamily(family);
            teamLead.setMember(member);
            teamLead.setTeamLeadName(teamLeadName);

            teamLeadList.add(teamLead);
        });
    }

    @Override
    public String getFileHeaders() {
        return Constants.TEAM_LEAD_IMPORT_CSV_HEADERS;
    }

    @Override
    public String getCsvFileName() {
        return Constants.TEAMLEAD__TABLE_CSV_FILE_NAME;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> List<T> getData() {
        return (List<T>) teamLeadList;
    }
}
