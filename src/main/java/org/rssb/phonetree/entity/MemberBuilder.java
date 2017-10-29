package org.rssb.phonetree.entity;

import org.rssb.phonetree.entity.emums.PreferredPhoneType;
import org.rssb.phonetree.entity.emums.YesNo;

public class MemberBuilder {
    private String firstName;
    private String lastName;
    private String homePhone;
    private String homePhoneComments;
    private String workPhone;
    private String workPhoneComments;
    private String cellPhone;
    private String cellPhoneComments;
    private Integer priority;
    private String emailId;
    private YesNo homeNoVM;
    private YesNo cellNoVM;
    private YesNo workNoVM;
    private PreferredPhoneType preferredPhoneType;
    private YesNo onCallingList;
    private int csvFileFamilyId;
    private int csvFileMemberId;
    private Family family;

    public MemberBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public MemberBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public MemberBuilder setHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public MemberBuilder setHomePhoneComments(String homePhoneComments) {
        this.homePhoneComments = homePhoneComments;
        return this;
    }

    public MemberBuilder setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public MemberBuilder setWorkPhoneComments(String workPhoneComments) {
        this.workPhoneComments = workPhoneComments;
        return this;
    }

    public MemberBuilder setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
        return this;
    }

    public MemberBuilder setCellPhoneComments(String cellPhoneComments) {
        this.cellPhoneComments = cellPhoneComments;
        return this;
    }

    public MemberBuilder setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public MemberBuilder setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public MemberBuilder setHomeNoVM(YesNo homeNoVM) {
        this.homeNoVM = homeNoVM;
        return this;
    }

    public MemberBuilder setCellNoVM(YesNo cellNoVM) {
        this.cellNoVM = cellNoVM;
        return this;
    }

    public MemberBuilder setWorkNoVM(YesNo workNoVM) {
        this.workNoVM = workNoVM;
        return this;
    }

    public MemberBuilder setPreferredPhoneType(PreferredPhoneType preferredPhoneType) {
        this.preferredPhoneType = preferredPhoneType;
        return this;
    }

    public MemberBuilder setOnCallingList(YesNo onCallingList) {
        this.onCallingList = onCallingList;
        return this;
    }

    public MemberBuilder setCsvFileFamilyId(int csvFileFamilyId) {
        this.csvFileFamilyId = csvFileFamilyId;
        return this;
    }

    public MemberBuilder setCsvFileMemberId(int csvFileMemberId) {
        this.csvFileMemberId = csvFileMemberId;
        return this;
    }

    public MemberBuilder setFamily(Family family) {
        this.family = family;
        return this;
    }

    public Member build() {
        return new Member(firstName, lastName,
                homePhone, homePhoneComments,
                workPhone, workPhoneComments,
                cellPhone, cellPhoneComments,
                priority, emailId, homeNoVM,
                cellNoVM, workNoVM, preferredPhoneType,
                onCallingList, csvFileFamilyId,
                csvFileMemberId, family);
    }
}