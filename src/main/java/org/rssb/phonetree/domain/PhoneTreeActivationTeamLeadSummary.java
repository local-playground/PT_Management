package org.rssb.phonetree.domain;


import java.util.ArrayList;
import java.util.List;

public class PhoneTreeActivationTeamLeadSummary {
    private int teamLeadId;
    private String firstName;
    private String lastName;
    private int totalFamilies;
    private int totalVMLeft;
    private int totalNotReachedFamilies;
    private List<PhoneTreeActivationSevadarSummary> phoneTreeActivationSevadarSummaryList = new ArrayList<>();


    public int getTeamLeadId() {
        return teamLeadId;
    }

    public void setTeamLeadId(int teamLeadId) {
        this.teamLeadId = teamLeadId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTotalFamilies() {
        return totalFamilies;
    }

    public void setTotalFamilies(int totalFamilies) {
        this.totalFamilies = totalFamilies;
    }

    public int getTotalVMLeft() {
        return totalVMLeft;
    }

    public void setTotalVMLeft(int totalVMLeft) {
        this.totalVMLeft = totalVMLeft;
    }

    public int getTotalNotReachedFamilies() {
        return totalNotReachedFamilies;
    }

    public void setTotalNotReachedFamilies(int totalNotReachedFamilies) {
        this.totalNotReachedFamilies = totalNotReachedFamilies;
    }

    public List<PhoneTreeActivationSevadarSummary> getPhoneTreeActivationSevadarSummaryList() {
        return phoneTreeActivationSevadarSummaryList;
    }

    public void setPhoneTreeActivationSevadarSummaryList(List<PhoneTreeActivationSevadarSummary> phoneTreeActivationSevadarSummaryList) {
        this.phoneTreeActivationSevadarSummaryList = phoneTreeActivationSevadarSummaryList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhoneTreeActivationTeamLeadSummary{");
        sb.append("teamLeadId=").append(teamLeadId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", totalFamilies=").append(totalFamilies);
        sb.append(", totalVMLeft=").append(totalVMLeft);
        sb.append(", totalNotReachedFamilies=").append(totalNotReachedFamilies);
        sb.append(", phoneTreeActivationSevadarSummaryList=").append(phoneTreeActivationSevadarSummaryList);
        sb.append('}');
        return sb.toString();
    }
}
