package org.rssb.phonetree.entity;

import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.entity.converters.BusRideConverter;
import org.rssb.phonetree.entity.converters.CallStatusConverter;
import org.rssb.phonetree.entity.converters.YesNoConverter;
import org.rssb.phonetree.entity.emums.BusRide;
import org.rssb.phonetree.entity.emums.CallStatus;
import org.rssb.phonetree.entity.emums.YesNo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Family")
@NamedQueries({
        @NamedQuery(name = "Family.findFamilyByTown",
                query = Constants.SEARCH_CRITERIA_BASIC_QUERY +
                        "WHERE lower(f.town) LIKE lower(:town) "),
        @NamedQuery(name = "Family.findFamilyByZipCode",
                query = Constants.SEARCH_CRITERIA_BASIC_QUERY +
                        "WHERE lower(f.zipCode) LIKE lower(:zipCode) "),
        @NamedQuery(name = "Family.findFamilyByFirstName",
                query = Constants.SEARCH_CRITERIA_BASIC_QUERY +
                        "WHERE lower(m.firstName) LIKE lower(:firstName)"),
        @NamedQuery(name = "Family.findFamilyByLastName",
                query = Constants.SEARCH_CRITERIA_BASIC_QUERY +
                        "WHERE lower(m.lastName) LIKE lower(:lastName)"),
        @NamedQuery(name = "Family.findFamilyBySevadarName",
                query = Constants.SEARCH_CRITERIA_BASIC_QUERY +
                        "WHERE lower(f.sevadar.sevadarName) LIKE lower(:sevadarName)"),
        @NamedQuery(name = "Family.findFamilyByTeamLeadName",
                query = Constants.SEARCH_CRITERIA_BASIC_QUERY +
                        "WHERE lower(f.teamLead.teamLeadName) LIKE lower(:teamLeadName)"),
        @NamedQuery(name = "Family.findFamilyByPhoneNumber",
                query = Constants.SEARCH_CRITERIA_BASIC_QUERY +
                        "WHERE m.cellPhone LIKE :phoneNumber " +
                        "OR m.homePhone LIKE :phoneNumber " +
                        "OR m.workPhone LIKE :phoneNumber"),
        @NamedQuery(name = "Family.getSevadarsCallingFamilyCountByTeamLeadId",
                query = "SELECT NEW org.rssb.phonetree.domain.FamilyCount(s.sevadarName,count(f)) FROM " +
                        " Family f " +
                        " JOIN f.sevadar s " +
                        " WHERE f.teamLead.teamLeadId = :teamLeadId " +
                        " GROUP BY s.sevadarName")
})
public class Family implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "FamilyId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int familyId;
    @Column(name = "ContactType")
    private String contactType;
    @Column(name = "ZipCode")
    private String zipCode;
    @Column(name = "NoOfAdults")
    private Integer noOfAdults;
    @Column(name = "NoOfChildren")
    private Integer noOfChildren;
    @Column(name = "IsActive")
    private Integer isActive;
    @Column(name = "TOWN")
    private String town;
    @Column(name = "HasSNVGuidelines")
    private Integer hasSNVGuidelines;
    @Column(name = "CanCallBetween")
    private Integer canCallBetween;
    @Column(name = "callSpecificTime")
    private String callSpecificTime;
    @Column(name = "NeedBusRide")
    private Integer needBusRide;
    @Column(name = "NoOfPassengers")
    private Integer noOfPassengers;
    @Column(name = "Comments")
    private String comments;
    @Column(name = "InternalNote")
    private String internalNote;
    @Column(name = "isContactOK")
    private Integer isContactOK;
    @Column(name = "BusRide")
    @Convert(converter = BusRideConverter.class)
    private BusRide busRide;
    @Column(name = "ContactStatus")
    @Convert(converter = CallStatusConverter.class)
    private CallStatus callStatus;
    @Column(name = "Active")
    @Convert(converter = YesNoConverter.class)
    private YesNo active;
    @Column(name = "CanCallAnytime")
    @Convert(converter = YesNoConverter.class)
    private YesNo canCallAnytime;
    @Column(name = "SNVGuidelines")
    @Convert(converter = YesNoConverter.class)
    private YesNo SNVGuidelines;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TeamLeadId", referencedColumnName = "TeamLeadId")
    private TeamLead teamLead;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SevadarId", referencedColumnName = "SevadarsId")
    private Sevadar sevadar;

    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Member> membersList;

    public Family() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(Integer noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public Integer getNoOfChildren() {
        return noOfChildren;
    }

    public void setNoOfChildren(Integer noOfChildren) {
        this.noOfChildren = noOfChildren;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Integer getHasSNVGuidelines() {
        return hasSNVGuidelines;
    }

    public void setHasSNVGuidelines(Integer hasSNVGuidelines) {
        this.hasSNVGuidelines = hasSNVGuidelines;
    }

    public Integer getCanCallBetween() {
        return canCallBetween;
    }

    public void setCanCallBetween(Integer canCallBetween) {
        this.canCallBetween = canCallBetween;
    }

    public String getCallSpecificTime() {
        return callSpecificTime;
    }

    public void setCallSpecificTime(String callSpecificTime) {
        this.callSpecificTime = callSpecificTime;
    }

    public Integer getNeedBusRide() {
        return needBusRide;
    }

    public void setNeedBusRide(Integer needBusRide) {
        this.needBusRide = needBusRide;
    }

    public Integer getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(Integer noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getInternalNote() {
        return internalNote;
    }

    public void setInternalNote(String internalNote) {
        this.internalNote = internalNote;
    }

    public Integer getIsContactOK() {
        return isContactOK;
    }

    public void setIsContactOK(Integer isContactOK) {
        this.isContactOK = isContactOK;
    }

    public BusRide getBusRide() {
        return busRide;
    }

    public void setBusRide(BusRide busRide) {
        this.busRide = busRide;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }

    public YesNo getActive() {
        return active;
    }

    public void setActive(YesNo active) {
        this.active = active;
    }

    public YesNo getSNVGuidelines() {
        return SNVGuidelines;
    }

    public void setSNVGuidelines(YesNo SNVGuidelines) {
        this.SNVGuidelines = SNVGuidelines;
    }

    public YesNo getCanCallAnytime() {
        return canCallAnytime;
    }

    public void setCanCallAnytime(YesNo canCallAnytime) {
        this.canCallAnytime = canCallAnytime;
    }

    public TeamLead getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(TeamLead teamLead) {
        this.teamLead = teamLead;
    }

    public Sevadar getSevadar() {
        return sevadar;
    }

    public void setSevadar(Sevadar sevadar) {
        this.sevadar = sevadar;
    }

    public List<Member> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<Member> membersCollection) {
        this.membersList = membersCollection;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Family{");
        sb.append("familyId=").append(familyId);
        sb.append(", contactType='").append(contactType).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append(", noOfAdults=").append(noOfAdults);
        sb.append(", noOfChildren=").append(noOfChildren);
        sb.append(", isActive=").append(isActive);
        sb.append(", town='").append(town).append('\'');
        sb.append(", hasSNVGuidelines=").append(hasSNVGuidelines);
        sb.append(", canCallBetween=").append(canCallBetween);
        sb.append(", callSpecificTime='").append(callSpecificTime).append('\'');
        sb.append(", needBusRide=").append(needBusRide);
        sb.append(", noOfPassengers=").append(noOfPassengers);
        sb.append(", comments='").append(comments).append('\'');
        sb.append(", internalNote='").append(internalNote).append('\'');
        sb.append(", isContactOK=").append(isContactOK);
        sb.append(", BusRide=").append(busRide);
        sb.append(", SNVGuidelines=").append(SNVGuidelines);
        sb.append(", CanCallAnytime=").append(canCallAnytime);
        sb.append(", Active=").append(active);
        sb.append(", CallStatus=").append(callStatus);
        sb.append(", membersCollection=").append(membersList);
        sb.append('}');
        return sb.toString();
    }
}
