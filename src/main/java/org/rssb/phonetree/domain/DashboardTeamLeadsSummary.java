package org.rssb.phonetree.domain;

import java.util.ArrayList;
import java.util.List;

public class DashboardTeamLeadsSummary {
    private String firstName;
    private String lastName;
    private int totalFamilies;
    private int totalSevadars;

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

    private List<FamilyCount> familyCountList = new ArrayList<>();

    public int getTotalFamilies() {
        return totalFamilies;
    }

    public void setTotalFamilies(int totalFamilies) {
        this.totalFamilies = totalFamilies;
    }

    public int getTotalSevadars() {
        return totalSevadars;
    }

    public void setTotalSevadars(int totalSevadars) {
        this.totalSevadars = totalSevadars;
    }

    public List<FamilyCount> getFamilyCountList() {
        return familyCountList;
    }

    public void setFamilyCountList(List<FamilyCount> familyCountList) {
        this.familyCountList = familyCountList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DashboardTeamLeadsSummary{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", totalFamilies=").append(totalFamilies);
        sb.append(", totalSevadars=").append(totalSevadars);
        sb.append(", familyCountList=").append(familyCountList);
        sb.append('}');
        return sb.toString();
    }
}
