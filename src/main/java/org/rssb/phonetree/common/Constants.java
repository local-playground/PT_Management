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

    String FAMILY_DATA_IMPORT_CSV_HEADERS="FamilyId,TeamLeadId,SevadarId,ContactType,ZipCode," +
            "NoOfAdults,NoOfChildren,IsActive,TOWN,HasSNVGuidelines,CanCallBetween," +
            "callSpecificTime,NeedBusRide,NoOfPassengers,Comments,InternalNote,isContactOK";
    String MEMBERS_DATA_IMPORT_CSV_HEADERS="MemberId,FirstName,LastName,HomePhone,leaveHomeVM," +
            "WorkPhone,leaveWorkVM,CellPhone,leaveCellVM,PRIORITY,FamilyId," +
            "IsOnCallingList,PreferredPhone,EmailId";
    String TEAM_LEAD_IMPORT_CSV_HEADERS="TeamLeadId,MemberId,TeamLeadName,FamilyId";
    String SEVADARS_IMPORT_CSV_HEADERS="SevadarsId,teamLeadId,MemberId,SevadarName,FamilyId,isBackupForTeamLead";
    String BACKUP_SEVADARS_IMPORT_CSV_HEADERS="BackupSevadarsId,MemberId,SevadarName,FamilyId";
    String CSV_DATA_FILES_BASE_DIR="C:\\Rajesh\\Personal\\Satsang\\Export\\";
    String FAMILY_TABLE_CSV_FILE_NAME=CSV_DATA_FILES_BASE_DIR+"Family.csv";
    String MEMBERS_TABLE_CSV_FILE_NAME=CSV_DATA_FILES_BASE_DIR+"Members.csv";
    String TEAMLEAD__TABLE_CSV_FILE_NAME=CSV_DATA_FILES_BASE_DIR+"TeamLeads.csv";
    String SEVADARS_TABLE_CSV_FILE_NAME=CSV_DATA_FILES_BASE_DIR+"Sevadars.csv";
    String BACKUP_SEVADARS_TABLE_CSV_FILE_NAME=CSV_DATA_FILES_BASE_DIR+"BackupSevadars.csv";
    String SQLITE_DB_FILE_NAME="C:\\Rajesh\\Personal\\Satsang\\Test.db";
    String REQUEST_OBJ="REQUEST_OBJ";
    String RESPONSE_OBJ="RESPONSE_OBJ";
    String ACTION="ACTION";
    String PARENT_PANE = "PARENT_PANE";
    double LOW_OPACITY=0.1;
    double FULL_OPACITY=1.0;
    String WORD_DOCUMENT_HEADING1_STYLE = "Heading1";
    String WORD_DOCUMENT_TABLE_STYLE_ID="GridTable4-Accent6";
}
