package org.rssb.phonetree.common;


public interface Constants {
    int ON_CALLING_LIST=1;
    String SUCCESS = "success";
    String SEARCH_CRITERIA_BASIC_QUERY="SELECT NEW org.rssb.phonetree.domain.SearchResult(" +
            "m.memberId,f.familyId,m.firstName,m.lastName," +
            "m.cellPhone, m.homePhone,m.workPhone,f.zipCode,f.town," +
            "f.sevadar.sevadarName,f.teamLead.teamLeadName) " +
            "FROM Member m " +
            "JOIN m.family f ";

}
