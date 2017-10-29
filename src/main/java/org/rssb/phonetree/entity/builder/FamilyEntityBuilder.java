package org.rssb.phonetree.entity.builder;

import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.FamilyBuilder;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.entity.emums.BusRide;
import org.rssb.phonetree.entity.emums.CallStatus;
import org.rssb.phonetree.entity.emums.YesNo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FamilyEntityBuilder implements EntityBuilder{
    private List<Family> familyList = new ArrayList<>();
/*
"FamilyId,TeamLeadId,SevadarId,ContactType,ZipCode," +
            "NoOfAdults,NoOfChildren,IsActive,TOWN,HasSNVGuidelines,CanCallBetween," +
            "callSpecificTime,NeedBusRide,NoOfPassengers,Comments,InternalNote,isContactOK"
 */
    @Override
    public void build(List<Map<String, String>> data) {
        data.stream().forEach(mapData -> {
           /* if(familyList.size()==1){
                return;
            }*/

            //System.out.println("Working on data \n"+mapData);
            int familyId = CommonUtil.convertStringToInt(mapData.get("FamilyId"),-1);
            //System.out.println("Family Id = "+familyId);
            int teamLeadId = CommonUtil.convertStringToInt(mapData.get("TeamLeadId"),-1);
            int sevadarId = CommonUtil.convertStringToInt(mapData.get("SevadarId"),-1);
            String zipCode = mapData.get("ZipCode");
            int noOfAdults= CommonUtil.convertStringToInt(mapData.get("NoOfAdults"),-1);
            int noOfChildren= CommonUtil.convertStringToInt(mapData.get("NoOfChildren"),-1);
            int active= CommonUtil.convertStringToInt(mapData.get("IsActive"),-1);
            String town= mapData.get("TOWN");
            int hasSNVGuidelines= CommonUtil.convertStringToInt(mapData.get("HasSNVGuidelines"),-1);
            int canCallBetween= CommonUtil.convertStringToInt(mapData.get("CanCallBetween"),-1);
            String callSpecificTime= mapData.get("callSpecificTime");
            int needBusRide= CommonUtil.convertStringToInt(mapData.get("NeedBusRide"),-1);
            int noOfPassengers= CommonUtil.convertStringToInt(mapData.get("NoOfPassengers"),-1);
            String comments= mapData.get("Comments");
            String internalNote= mapData.get("InternalNote");
            int isContactOK= CommonUtil.convertStringToInt(mapData.get("isContactOK"),-1);

            Family family = new FamilyBuilder().build();
            family.setFamilyId(familyId);
            family.setCsvFileFamilyId(familyId);

            TeamLead teamLead = new TeamLead();
            teamLead.setTeamLeadId(teamLeadId);
            family.setTeamLead(teamLead);

            Sevadar sevadar = new Sevadar();
            sevadar.setSevadarsId(sevadarId);
            family.setSevadar(sevadar);

            family.setZipCode(zipCode);
            family.setNoOfAdults(noOfAdults);
            family.setNoOfChildren(noOfChildren);
            if(active == 1){
                family.setActive(YesNo.YES);
            }else{
                family.setActive(YesNo.NO);
            }

            family.setTown(town);

            if(hasSNVGuidelines == 1){
                family.setSNVGuidelines(YesNo.YES);
            }else{
                family.setSNVGuidelines(YesNo.NO);
            }

            if(canCallBetween == 1){
                family.setCanCallAnytime(YesNo.YES);
            }else{
                family.setCanCallAnytime(YesNo.NO);
            }

            family.setCallSpecificTime(callSpecificTime);

            if(needBusRide == 1){
                family.setBusRide(BusRide.YES);
            }else if(needBusRide == 2){
                family.setBusRide(BusRide.MAYBE);
            }else{
                family.setBusRide(BusRide.NO);
            }

            family.setNoOfPassengers(noOfPassengers);

            family.setComments(comments);
            family.setInternalNote(internalNote);

            if(isContactOK ==1){
                family.setCallStatus(CallStatus.OK);
            }else if(isContactOK == 2){
                family.setCallStatus(CallStatus.NOT_PICKED);
            }else if(isContactOK == 3){
                family.setCallStatus(CallStatus.MOVED);
            }else if(isContactOK == 4){
                family.setCallStatus(CallStatus.REMOVE_REQUEST);
            }else if(isContactOK == 5){
                family.setCallStatus(CallStatus.DISCONNECTED);
            }else{
                family.setCallStatus(CallStatus.WRONG_NUMBER);
            }

            familyList.add(family);
        });
    }

    @Override
    public String getFileHeaders() {
        return Constants.FAMILY_DATA_IMPORT_CSV_HEADERS;
    }

    @Override
    public String getCsvFileName() {
        return Constants.FAMILY_TABLE_CSV_FILE_NAME;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public <T> List<T> getData() {
        return (List<T>) familyList;
    }
}
