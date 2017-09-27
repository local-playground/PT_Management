package org.rssb.phonetree.domain;

public class SevadarPersonalInformation {
    private String name;
    private String cellPhone;
    private String homePhone;
    private String emailId;

    public SevadarPersonalInformation(String name, String cellPhone, String homePhone, String emailId) {
        this.name = name;
        this.cellPhone = cellPhone;
        this.homePhone = homePhone;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getStringyfyInformation(String type){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(type).append(":").append(" ").append(name).append("\n");
        stringBuilder.append("Cell:").append(" "+cellPhone).append("\n");
        stringBuilder.append("Home:").append(" "+homePhone).append("\n");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("name='").append(name).append('\'');
        sb.append(", cellPhone='").append(cellPhone).append('\'');
        sb.append(", homePhone='").append(homePhone).append('\'');
        sb.append(", emailId='").append(emailId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
