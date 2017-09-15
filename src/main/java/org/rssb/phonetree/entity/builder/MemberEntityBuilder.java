package org.rssb.phonetree.entity.builder;

import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;
import org.rssb.phonetree.entity.emums.PreferredPhoneType;
import org.rssb.phonetree.entity.emums.YesNo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Component
public class MemberEntityBuilder implements EntityBuilder {
    private List<Member> memberList = new ArrayList<>();

    /*
    "MemberId,FirstName,LastName,HomePhone,leaveHomeVM," +
            "WorkPhone,leaveWorkVM,CellPhone,leaveCellVM,PRIORITY,FamilyId," +
            "IsOnCallingList,PreferredPhone,EmailId";
     */
    @Override
    public void build(List<Map<String, String>> data) {
        data.stream().forEach(mapData -> {
            int memberId = CommonUtil.convertStringToInt(mapData.get("MemberId"), -1);
            String firstName = mapData.get("FirstName");
            String lastName = mapData.get("LastName");
            String homePhone = mapData.get("HomePhone");
            String cellPhone = mapData.get("CellPhone");
            String workPhone = mapData.get("WorkPhone");

            int leaveHomeVM = CommonUtil.convertStringToInt(mapData.get("leaveHomeVM"), -1);
            int leaveWorkVM = CommonUtil.convertStringToInt(mapData.get("leaveWorkVM"), -1);
            int leaveCellVM = CommonUtil.convertStringToInt(mapData.get("leaveCellVM"), -1);

            int priority = CommonUtil.convertStringToInt(mapData.get("PRIORITY"), -1);
            int familyId = CommonUtil.convertStringToInt(mapData.get("FamilyId"), -1);
            int isOnCallingList = CommonUtil.convertStringToInt(mapData.get("IsOnCallingList"), -1);
            int preferredPhone = CommonUtil.convertStringToInt(mapData.get("PreferredPhone"), -1);
            String emailId = mapData.get("EmailId");

            Member member = new Member();
            member.setMemberId(memberId);

            member.setFirstName(firstName);
            member.setLastName(lastName);

            member.setCellPhone(cellPhone);
            if(leaveCellVM == 1){
                member.setCellNoVM(YesNo.YES);
            }else{
                member.setCellNoVM(YesNo.NO);
            }
            member.setHomePhone(homePhone);
            if(leaveHomeVM == 1){
                member.setHomeNoVM(YesNo.YES);
            }else{
                member.setHomeNoVM(YesNo.NO);
            }
            member.setWorkPhone(workPhone);
            if(leaveWorkVM == 1){
                member.setWorkNoVM(YesNo.YES);
            }else{
                member.setWorkNoVM(YesNo.NO);
            }

            member.setPriority(priority);

            Family family = new Family();
            family.setFamilyId(familyId);
            member.setFamily(family);

            if(isOnCallingList == 1){
                member.setOnCallingList(YesNo.YES);
            }else{
                member.setOnCallingList(YesNo.NO);
            }

            if(preferredPhone == 1){
                member.setPreferredPhoneType(PreferredPhoneType.CELL);
            }else if(preferredPhone == 2){
                member.setPreferredPhoneType(PreferredPhoneType.HOME);
            }else{
                member.setPreferredPhoneType(PreferredPhoneType.WORK);
            }

            member.setEmailId(emailId);

            memberList.add(member);
            //System.out.println(memberList);
        });
    }

    @Override
    public String getFileHeaders() {
        return Constants.MEMBERS_DATA_IMPORT_CSV_HEADERS;
    }

    @Override
    public String getCsvFileName() {
        return Constants.MEMBERS_TABLE_CSV_FILE_NAME;
    }

    @Override
    public <T> List<T> getData() {
        return (List<T>) memberList;
    }
}
