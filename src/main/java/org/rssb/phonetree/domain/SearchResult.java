package org.rssb.phonetree.domain;


public class SearchResult {
    private int memberId;
    private int familyId;
    private String firstName;
    private String lastName;
    private String sevadarName;
    private String teamLeadName;
    private String cellPhone;
    private String homePhone;
    private String workPhone;
    private String zipCode;
    private String town;
    private String status;

    public SearchResult(int memberId, int familyId, String firstName,
                        String lastName,String cellPhone,
                        String homePhone, String workPhone,
                        String zipCode, String town,
                        String sevadarName, String teamLeadName) {
        this.memberId = memberId;
        this.familyId = familyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cellPhone = cellPhone;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.zipCode = zipCode;
        this.town = town;
        this.sevadarName = sevadarName;
        this.teamLeadName = teamLeadName;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
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

    public String getSevadarName() {
        return sevadarName;
    }

    public void setSevadarName(String sevadarName) {
        this.sevadarName = sevadarName;
    }

    public String getTeamLeadName() {
        return teamLeadName;
    }

    public void setTeamLeadName(String teamLeadName) {
        this.teamLeadName = teamLeadName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchResult{");
        sb.append("memberId=").append(memberId);
        sb.append(", familyId=").append(familyId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", cellPhone='").append(cellPhone).append('\'');
        sb.append(", homePhone='").append(homePhone).append('\'');
        sb.append(", workPhone='").append(workPhone).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append(", town='").append(town).append('\'');
        sb.append(", sevadarName='").append(sevadarName).append('\'');
        sb.append(", teamLeadName='").append(teamLeadName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
