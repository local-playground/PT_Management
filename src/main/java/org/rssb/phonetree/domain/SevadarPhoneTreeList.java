package org.rssb.phonetree.domain;

import java.util.List;

public class SevadarPhoneTreeList {
    private SevadarPersonalInformation teamLeadPersonalInformation;
    private SevadarPersonalInformation backupTeamLeadPersonalInformation;
    private SevadarPersonalInformation sevadarPersonalInformation;
    private int totalFamiliesToCall;
    private String teamLeadDetails;
    private String backupTeamLeadDetails;
    private String sevadarDetails;
    private List<CalledFamilyDetails> calledFamilyDetailsList;

    public SevadarPersonalInformation getTeamLeadPersonalInformation() {
        return teamLeadPersonalInformation;
    }

    public void setTeamLeadPersonalInformation(SevadarPersonalInformation teamLeadPersonalInformation) {
        this.teamLeadPersonalInformation = teamLeadPersonalInformation;
    }

    public SevadarPersonalInformation getSevadarPersonalInformation() {
        return sevadarPersonalInformation;
    }

    public void setSevadarPersonalInformation(SevadarPersonalInformation sevadarPersonalInformation) {
        this.sevadarPersonalInformation = sevadarPersonalInformation;
    }

    public SevadarPersonalInformation getBackupTeamLeadPersonalInformation() {
        return backupTeamLeadPersonalInformation;
    }

    public void setBackupTeamLeadPersonalInformation(SevadarPersonalInformation backupTeamLeadPersonalInformation) {
        this.backupTeamLeadPersonalInformation = backupTeamLeadPersonalInformation;
    }

    public int getTotalFamiliesToCall() {
        return totalFamiliesToCall;
    }

    public void setTotalFamiliesToCall(int totalFamiliesToCall) {
        this.totalFamiliesToCall = totalFamiliesToCall;
    }

    public String getTeamLeadDetails() {
        return teamLeadDetails;
    }

    public void setTeamLeadDetails(String teamLeadDetails) {
        this.teamLeadDetails = teamLeadDetails;
    }

    public String getBackupTeamLeadDetails() {
        return backupTeamLeadDetails;
    }

    public void setBackupTeamLeadDetails(String backupTeamLeadDetails) {
        this.backupTeamLeadDetails = backupTeamLeadDetails;
    }

    public String getSevadarDetails() {
        return sevadarDetails;
    }

    public void setSevadarDetails(String sevadarDetails) {
        this.sevadarDetails = sevadarDetails;
    }

    public List<CalledFamilyDetails> getCalledFamilyDetailsList() {
        return calledFamilyDetailsList;
    }

    public void setCalledFamilyDetailsList(List<CalledFamilyDetails> calledFamilyDetailsList) {
        this.calledFamilyDetailsList = calledFamilyDetailsList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SevadarPhoneTreeList{");
        sb.append("totalFamiliesToCall=").append(totalFamiliesToCall);
        sb.append(", teamLeadDetails='").append(teamLeadDetails).append('\'');
        sb.append(", backupTeamLeadDetails='").append(backupTeamLeadDetails).append('\'');
        sb.append(", sevadarDetails='").append(sevadarDetails).append('\'');
        sb.append(", calledFamilyDetailsList=").append(calledFamilyDetailsList);
        sb.append('}');
        return sb.toString();
    }

}
