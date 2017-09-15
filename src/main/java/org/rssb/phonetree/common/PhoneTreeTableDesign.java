package org.rssb.phonetree.common;

public interface PhoneTreeTableDesign {
    String FAMILY_TABLE_CREATE_DDL="CREATE TABLE IF NOT EXISTS Family (\n" +
            "    FamilyId         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    TeamLeadId       INTEGER REFERENCES TeamLeads (TeamLeadId),\n" +
            "    SevadarId        INTEGER REFERENCES Sevadars (SevadarsId),\n" +
            "    ZipCode          VARCHAR,\n" +
            "    NoOfAdults       INTEGER,\n" +
            "    NoOfChildren     INTEGER,\n" +
            "    TOWN             VARCHAR,\n" +
            "    CanCallAnytime   VARCHAR,\n" +
            "    CallSpecificTime VARCHAR,\n" +
            "    BusRide          VARCHAR,\n" +
            "    NoOfPassengers   INTEGER,\n" +
            "    ContactStatus    VARCHAR,\n" +
            "    Active           VARCHAR,\n" +
            "    SNVGuidelines    VARCHAR,\n" +
            "    Comments         VARCHAR,\n" +
            "    InternalNote     VARCHAR\n" +
            ");";

    String MEMBERS_TABLE_CREATE_DDL="CREATE TABLE IF NOT EXISTS Members (\n" +
            "    MemberId           INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    FamilyId           INTEGER REFERENCES Family (FamilyId) ON DELETE CASCADE,\n" +
            "    FirstName          TEXT,\n" +
            "    LastName           TEXT,\n" +
            "    HomePhone          TEXT,\n" +
            "    homeNoVM           VARCHAR,\n" +
            "    WorkPhone          TEXT,\n" +
            "    workNoVM           VARCHAR,\n" +
            "    CellPhone          TEXT,\n" +
            "    cellNoVM           VARCHAR,\n" +
            "    PRIORITY           INTEGER,\n" +
            "    OnCallingList      VARCHAR,\n" +
            "    EmailId            TEXT,\n" +
            "    PreferredPhoneType VARCHAR\n" +
            ");\n";

    String TEAM_LEADS_TABLE_CREATE_DDL="CREATE TABLE IF NOT EXISTS TeamLeads (\n" +
            "    TeamLeadId   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    MemberId     INTEGER REFERENCES Members (MemberId),\n" +
            "    TeamLeadName TEXT,\n" +
            "    FamilyId     INTEGER REFERENCES Family (FamilyId) \n" +
            ");";

    String SEVADARS_TABLE_CREATE_DDL="CREATE TABLE IF NOT EXISTS Sevadars (\n" +
            "    SevadarsId          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    TeamLeadId          INTEGER REFERENCES TeamLeads (TeamLeadId),\n" +
            "    MemberId            INTEGER REFERENCES Members (MemberId),\n" +
            "    SevadarName         TEXT,\n" +
            "    FamilyId            INTEGER REFERENCES Family (FamilyId),\n" +
            "    isBackupForTeamLead INTEGER\n" +
            ");";
    String BACKUP_SEVADARS_TABLE_CREATE_DDL="CREATE TABLE IF NOT EXISTS BackupSevadars (\n" +
            "    BackupSevadarsId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    MemberId         INTEGER,\n" +
            "    SevadarName      TEXT,\n" +
            "    FamilyId         INTEGER\n" +
            ");";

    String[] TABLE_CREATE_DDL_STATEMENTS={FAMILY_TABLE_CREATE_DDL,MEMBERS_TABLE_CREATE_DDL,
            TEAM_LEADS_TABLE_CREATE_DDL,SEVADARS_TABLE_CREATE_DDL,BACKUP_SEVADARS_TABLE_CREATE_DDL};
}
