
package org.rssb.phonetree.entity;

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
                query = "Update Member m set m.isOnCallingList=?1 where m.family.familyId=?2")
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
    private String homePhone;
    @Column(name = "leaveHomeVM")
    private Integer leaveHomeVM;
    @Column(name = "WorkPhone")
    private String workPhone;
    @Column(name = "leaveWorkVM")
    private Integer leaveWorkVM;
    @Column(name = "CellPhone")
    private String cellPhone;
    @Column(name = "leaveCellVM")
    private Integer leaveCellVM;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "IsOnCallingList")
    private Integer isOnCallingList;
    @Column(name = "PreferredPhone")
    private Integer preferredPhone;
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


    /*@Column(name = "FamilyId",insertable = false,updatable = false)
    private int familyId;*/

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

    public Integer getLeaveHomeVM() {
        return leaveHomeVM;
    }

    public void setLeaveHomeVM(Integer leaveHomeVM) {
        this.leaveHomeVM = leaveHomeVM;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public Integer getLeaveWorkVM() {
        return leaveWorkVM;
    }

    public void setLeaveWorkVM(Integer leaveWorkVM) {
        this.leaveWorkVM = leaveWorkVM;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Integer getLeaveCellVM() {
        return leaveCellVM;
    }

    public void setLeaveCellVM(Integer leaveCellVM) {
        this.leaveCellVM = leaveCellVM;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getIsOnCallingList() {
        return isOnCallingList;
    }

    public void setIsOnCallingList(Integer isOnCallingList) {
        this.isOnCallingList = isOnCallingList;
    }

    public Integer getPreferredPhone() {
        return preferredPhone;
    }

    public void setPreferredPhone(Integer preferredPhone) {
        this.preferredPhone = preferredPhone;
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
        sb.append(", leaveHomeVM=").append(leaveHomeVM);
        sb.append(", workPhone='").append(workPhone).append('\'');
        sb.append(", leaveWorkVM=").append(leaveWorkVM);
        sb.append(", cellPhone='").append(cellPhone).append('\'');
        sb.append(", leaveCellVM=").append(leaveCellVM);
        sb.append(", priority=").append(priority);
        sb.append(", isOnCallingList=").append(isOnCallingList);
        sb.append(", homeNoVM=").append(homeNoVM);
        sb.append(", cellNoVM=").append(cellNoVM);
        sb.append(", workNoVM=").append(workNoVM);
        sb.append(", preferredPhoneType=").append(preferredPhoneType);
        sb.append(", preferredPhone=").append(preferredPhone);

       /* sb.append(", FamilyId='").append(familyId).append('\'');*/
        sb.append(", emailId='").append(emailId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
