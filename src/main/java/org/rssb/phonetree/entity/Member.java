
package org.rssb.phonetree.entity;

import org.rssb.phonetree.entity.converters.PhoneNumberConverter;
import org.rssb.phonetree.entity.converters.PreferredPhoneTypeConverter;
import org.rssb.phonetree.entity.converters.YesNoConverter;
import org.rssb.phonetree.entity.emums.PreferredPhoneType;
import org.rssb.phonetree.entity.emums.YesNo;

import javax.persistence.*;
import java.io.Serializable;


/*
public SearchResult(int memberId, int familyId, String firstName,
                        String lastName, String sevadarName, String teamLeadName,
                        String cellPhone, String homePhone, String workPhone,
                        String zipCode, String town)
 */
@Entity
@Table(name = "Members")
@NamedQueries({
        @NamedQuery(name = "Member.putSevadarBackToCallingList",
                query = "Update Member m set m.onCallingList=?1 where m.family.familyId=?2")
})

public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MemberId")
    private int memberId;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "HomePhone")
    @Convert(converter = PhoneNumberConverter.class)
    private String homePhone;
    @Column(name = "WorkPhone")
    //@Convert(converter = PhoneNumberConverter.class)
    private String workPhone;
    @Column(name = "CellPhone")
    @Convert(converter = PhoneNumberConverter.class)
    private String cellPhone;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "EmailId")
    private String emailId;
    @Column(name = "homeNoVM")
    @Convert(converter = YesNoConverter.class)
    private YesNo homeNoVM;
    @Column(name = "cellNoVM")
    @Convert(converter = YesNoConverter.class)
    private YesNo cellNoVM;
    @Column(name = "workNoVM")
    @Convert(converter = YesNoConverter.class)
    private YesNo workNoVM;
    @Column(name="PreferredPhoneType")
    @Convert(converter = PreferredPhoneTypeConverter.class)
    private PreferredPhoneType preferredPhoneType;
    @Column(name = "OnCallingList")
    @Convert(converter = YesNoConverter.class)
    private YesNo onCallingList;

    public YesNo getOnCallingList() {
        return onCallingList;
    }

    public void setOnCallingList(YesNo onCallingList) {
        this.onCallingList = onCallingList;
    }

    @JoinColumn(name = "FamilyId", referencedColumnName = "FamilyId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Family family;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public YesNo getHomeNoVM() {
        return homeNoVM;
    }

    public void setHomeNoVM(YesNo homeNoVM) {
        this.homeNoVM = homeNoVM;
    }

    public YesNo getCellNoVM() {
        return cellNoVM;
    }

    public void setCellNoVM(YesNo cellNoVM) {
        this.cellNoVM = cellNoVM;
    }

    public YesNo getWorkNoVM() {
        return workNoVM;
    }

    public void setWorkNoVM(YesNo workNoVM) {
        this.workNoVM = workNoVM;
    }

    public PreferredPhoneType getPreferredPhoneType() {
        return preferredPhoneType;
    }

    public void setPreferredPhoneType(PreferredPhoneType preferredPhoneType) {
        this.preferredPhoneType = preferredPhoneType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Member{");
        sb.append("memberId=").append(memberId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", homePhone='").append(homePhone).append('\'');
        sb.append(", workPhone='").append(workPhone).append('\'');
        sb.append(", cellPhone='").append(cellPhone).append('\'');
        sb.append(", priority=").append(priority);
        sb.append(", homeNoVM=").append(homeNoVM);
        sb.append(", cellNoVM=").append(cellNoVM);
        sb.append(", workNoVM=").append(workNoVM);
        sb.append(", preferredPhoneType=").append(preferredPhoneType);
        sb.append(", onCallingList=").append(onCallingList);
        sb.append(", emailId='").append(emailId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
