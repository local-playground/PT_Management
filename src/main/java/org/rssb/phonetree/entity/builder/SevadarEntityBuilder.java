package org.rssb.phonetree.entity.builder;

import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class SevadarEntityBuilder implements EntityBuilder {
    private List<Sevadar> sevadarList = new ArrayList<>();

    @Override
    public void build(List<Map<String, String>> data) {
        data.stream().forEach(mapData -> {
            int familyId = CommonUtil.convertStringToInt(mapData.get("FamilyId"),-1);
            int memberId = CommonUtil.convertStringToInt(mapData.get("MemberId"), -1);
            int teamLeadId = CommonUtil.convertStringToInt(mapData.get("teamLeadId"), -1);
            int sevadarsId = CommonUtil.convertStringToInt(mapData.get("SevadarsId"),-1);

            int isBackupForTeamLead = CommonUtil.convertStringToInt(mapData.get("isBackupForTeamLead"),0);
            String sevadarName = mapData.get("SevadarName");

            TeamLead teamLead = new TeamLead();
            teamLead.setTeamLeadId(teamLeadId);

            Family family = new Family();
            family.setFamilyId(familyId);

            Member member = new Member();
            member.setMemberId(memberId);

            Sevadar sevadar = new Sevadar();
            sevadar.setSevadarsId(sevadarsId);
            sevadar.setSevadarName(sevadarName);
            sevadar.setFamily(family);
            sevadar.setMember(member);
            sevadar.setIsBackupForTeamLead(isBackupForTeamLead);
            sevadar.setTeamLead(teamLead);

            sevadarList.add(sevadar);
        });
    }

    @Override
    public String getFileHeaders() {
        return Constants.SEVADARS_IMPORT_CSV_HEADERS;
    }

    @Override
    public String getCsvFileName() {
        return Constants.SEVADARS_TABLE_CSV_FILE_NAME;
    }

    @Override
    public <T> List<T> getData() {
        return (List<T>) sevadarList;
    }
}
