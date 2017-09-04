package org.rssb.phonetree.common;

public enum SearchCriteria {
    FIRST_NAME("First Name"),
    LAST_NAME("Last Name"),
    PHONE_NUMBER("Phone Number"),
    TEAM_LEAD("Team Lead"),
    SEVADAR("Sevadar"),
    ZIP_CODE("Zip Code"),
    TOWN("Town"),
    PHONE_STATUS("Phone Status");

    private String type;
    SearchCriteria(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
