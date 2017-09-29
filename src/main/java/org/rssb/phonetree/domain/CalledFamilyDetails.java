package org.rssb.phonetree.domain;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import org.rssb.phonetree.entity.emums.BusRide;
import org.rssb.phonetree.entity.emums.CallStatus;
import org.rssb.phonetree.entity.emums.YesNo;

public class CalledFamilyDetails  {
    private int familySeqNumber;
    private int familyId;
    private int memberId;
    private String firstName;
    private String lastName;
    private String cellPhone;
    private String homePhone;
    private String workPhone;
    private String zipCode;
    private CallStatus callStatus;
    private BusRide busRide;
    private int noOfPassengers;
    private YesNo SNVGuideLines;

    public CalledFamilyDetails(int memberId,int familyId, String firstName, String lastName,
                               String cellPhone, String homePhone, String workPhone,
                               String zipCode, CallStatus callStatus, BusRide busRide,
                               int noOfPassengers, YesNo SNVGuideLines) {
        this.memberId = memberId;
        this.familyId = familyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhone = cellPhone;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.zipCode = zipCode;
        this.callStatus = callStatus;
        this.busRide = busRide;
        this.noOfPassengers = noOfPassengers;
        this.SNVGuideLines = SNVGuideLines;
    }

    public int getFamilyId() {
        return familyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public BusRide getBusRide() {
        return busRide;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public YesNo getSNVGuideLines() {
        return SNVGuideLines;
    }

    public int getMemberId(){
        return memberId;
    }

    public int getFamilySeqNumber(){
        return familySeqNumber;
    }

    public void setFamilySeqNumner(int familySeqNumber){
        this.familySeqNumber = familySeqNumber;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CalledFamilyDetails{");
        sb.append("familySeqNumber=").append(familySeqNumber);
        sb.append(", familyId='").append(familyId).append('\'');
        sb.append(", memberId='").append(memberId).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", cellPhone='").append(cellPhone).append('\'');
        sb.append(", homePhone='").append(homePhone).append('\'');
        sb.append(", workPhone='").append(workPhone).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append(", callStatus=").append(callStatus);
        sb.append(", busRide=").append(busRide);
        sb.append(", noOfPassengers=").append(noOfPassengers);
        sb.append(", SNVGuideLines=").append(SNVGuideLines);
        sb.append('}');
        return sb.toString();
    }
}
