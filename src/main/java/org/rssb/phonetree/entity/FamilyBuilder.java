package org.rssb.phonetree.entity;

import org.rssb.phonetree.entity.emums.BusRide;
import org.rssb.phonetree.entity.emums.CallStatus;
import org.rssb.phonetree.entity.emums.YesNo;

import java.util.List;

public class FamilyBuilder {
    private String zipCode;
    private Integer noOfAdults;
    private Integer noOfChildren;
    private String town;
    private String callSpecificTime;
    private Integer noOfPassengers;
    private String comments;
    private String internalNote;
    private BusRide busRide;
    private CallStatus callStatus;
    private YesNo active;
    private YesNo canCallAnytime;
    private YesNo snvGuidelines;
    private TeamLead teamLead;
    private Sevadar sevadar;
    private List<Member> membersList;
    private int csvFileFamilyId;

    public FamilyBuilder setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public FamilyBuilder setNoOfAdults(Integer noOfAdults) {
        this.noOfAdults = noOfAdults;
        return this;
    }

    public FamilyBuilder setNoOfChildren(Integer noOfChildren) {
        this.noOfChildren = noOfChildren;
        return this;
    }

    public FamilyBuilder setTown(String town) {
        this.town = town;
        return this;
    }

    public FamilyBuilder setCallSpecificTime(String callSpecificTime) {
        this.callSpecificTime = callSpecificTime;
        return this;
    }

    public FamilyBuilder setNoOfPassengers(Integer noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
        return this;
    }

    public FamilyBuilder setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public FamilyBuilder setInternalNote(String internalNote) {
        this.internalNote = internalNote;
        return this;
    }

    public FamilyBuilder setBusRide(BusRide busRide) {
        this.busRide = busRide;
        return this;
    }

    public FamilyBuilder setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
        return this;
    }

    public FamilyBuilder setActive(YesNo active) {
        this.active = active;
        return this;
    }

    public FamilyBuilder setCanCallAnytime(YesNo canCallAnytime) {
        this.canCallAnytime = canCallAnytime;
        return this;
    }

    public FamilyBuilder setSNVGuidelines(YesNo snvGuidelines) {
        this.snvGuidelines = snvGuidelines;
        return this;
    }

    public FamilyBuilder setTeamLead(TeamLead teamLead) {
        this.teamLead = teamLead;
        return this;
    }

    public FamilyBuilder setSevadar(Sevadar sevadar) {
        this.sevadar = sevadar;
        return this;
    }

    public FamilyBuilder setMembersList(List<Member> membersList) {
        this.membersList = membersList;
        return this;
    }

    public FamilyBuilder setCsvFileFamilyId(int csvFileFamilyId) {
        this.csvFileFamilyId = csvFileFamilyId;
        return this;
    }

    public Family build() {
        return new Family(zipCode, noOfAdults, noOfChildren,
                town, callSpecificTime, noOfPassengers, comments,
                internalNote, busRide, callStatus, active, canCallAnytime,
                snvGuidelines, teamLead, sevadar, membersList, csvFileFamilyId);
    }
}