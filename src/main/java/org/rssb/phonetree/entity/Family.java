package org.rssb.phonetree.entity;

import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.domain.DashboardNameValueBasedSummary;
import org.rssb.phonetree.entity.converters.BusRideConverter;
import org.rssb.phonetree.entity.converters.CallStatusConverter;
import org.rssb.phonetree.entity.converters.YesNoConverter;
import org.rssb.phonetree.entity.emums.BusRide;
import org.rssb.phonetree.entity.emums.CallStatus;
import org.rssb.phonetree.entity.emums.YesNo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
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
                query = "SELECT  NEW org.rssb.phonetree.domain.FamilyCount(s.sevadarName,count(f)) FROM " +
                        " Family f " +
                        " JOIN f.sevadar s " +
                        //" JOIN f.membersList m " +
                        " WHERE f.teamLead.teamLeadId = :teamLeadId " +
                        //" AND m.onCallingList = org.rssb.phonetree.entity.emums.YesNo.YES" +
                        " AND f.familyId = (SELECT distinct (mf.family.familyId) FROM f.membersList mf" +
                        " WHERE mf.onCallingList = org.rssb.phonetree.entity.emums.YesNo.YES)" +
                        " GROUP BY s.sevadarName"),
        @NamedQuery(name = "Family.findCalledFamiliesByTeamLeadAndSevadar",
                query = "SELECT NEW org.rssb.phonetree.domain.CalledFamilyDetails(" +
                        "m.memberId,f.familyId,m.firstName,m.lastName," +
                        "m.cellPhone, m.homePhone,m.workPhone,f.zipCode,f.callStatus," +
                        "f.busRide,f.noOfPassengers,f.SNVGuidelines) " +
                        " FROM Member m " +
                        " JOIN m.family f " +
                        " WHERE f.teamLead.teamLeadName = :teamLeadName" +
                        " AND f.sevadar.sevadarName = :sevadarName" +
                        " AND m.onCallingList = org.rssb.phonetree.entity.emums.YesNo.YES" +
                        " ORDER BY f.familyId, m.priority"),
        @NamedQuery(name = "Family.findCalledFamiliesCountByTeamLeadAndSevadar",
                query = "SELECT distinct count(f) FROM Family f" +
                        " JOIN f.sevadar s" +
                        " JOIN f.teamLead t " +
                        " WHERE t.teamLeadName = :teamLeadName" +
                        " AND s.sevadarName = :sevadarName" +
                        " AND f.familyId = (SELECT distinct (mf.family.familyId) FROM f.membersList mf" +
                        " WHERE mf.onCallingList = org.rssb.phonetree.entity.emums.YesNo.YES)" +
                        " GROUP BY t.teamLeadName,s.sevadarName"),
        @NamedQuery(name = "Family.getDashboardPhoneStatusSummary",
                query = "SELECT NEW org.rssb.phonetree.domain.DashboardPhoneStatusSummary(f.callStatus,count(f)) FROM " +
                        " Family f " +
                        " GROUP BY f.callStatus"),

        @NamedQuery(name = "Family.getDashboardBusRideNeededSummary",
                query = "SELECT NEW org.rssb.phonetree.domain.DashboardBusRideSummary(f.busRide,count(f)) FROM " +
                        " Family f " +
                        " GROUP BY f.busRide")

})

@NamedNativeQueries({
        @NamedNativeQuery(name = "Family.nativeQuery.zipCodeCollectionSummary",
                query = "select \"Collected\" as name, count(*) as count from Family " +
                        " where zipCode <> \"\"" +
                        " UNION " +
                        " select \"Missing\" as name, count(*) as count from Family " +
                        " where zipCode = \"\"",
                resultSetMapping = "zipCodeCollectionSummaryMapping"),

        @NamedNativeQuery(name = "Family.nativeQuery.adultsAttendSNVSummary",
                query = "Select \"Adults\" as name , Sum(NoOfAdults) as total from Family " +
                        "UNION " +
                        "Select \"Children\" as name , Sum(NoOfChildren) as total from Family",
                resultSetMapping = "adultsAttendSNVSummaryMapping"),

        @NamedNativeQuery(name = "Family.nativeQuery.busRideNeededSummary",
                query = "SELECT \"Yes\" as name , count(*) as count from Family where BusRide = 'Yes'" +
                        "UNION\n" +
                        "SELECT \"No\" as name , count(*) as count from Family where BusRide = 'No'" +
                        "UNION " +
                        "SELECT \"MayBe\" as name , count(*) as count from Family where BusRide = 'May Be'",
                resultSetMapping = "busRideNeededSummaryMapping")
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "zipCodeCollectionSummaryMapping",
                classes = @ConstructorResult(
                        targetClass = DashboardNameValueBasedSummary.class,
                        columns = {
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "count", type = Integer.class)
                        }
                )
        ),
        @SqlResultSetMapping(
                name = "adultsAttendSNVSummaryMapping",
                classes = @ConstructorResult(
                        targetClass = DashboardNameValueBasedSummary.class,
                        columns = {
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "total", type = Integer.class)
                        }
                )
        ),
        @SqlResultSetMapping(
                name = "busRideNeededSummaryMapping",
                classes = @ConstructorResult(
                        targetClass = DashboardNameValueBasedSummary.class,
                        columns = {
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "count", type = Integer.class)
                        }
                )
        )
})

public class Family implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "FamilyId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int familyId;
    @Column(name = "ZipCode")
    private String zipCode;
    @Column(name = "NoOfAdults")
    private Integer noOfAdults;
    @Column(name = "NoOfChildren")
    private Integer noOfChildren;
    @Column(name = "TOWN")
    private String town;
    @Column(name = "callSpecificTime")
    private String callSpecificTime;
    @Column(name = "NoOfPassengers")
    private Integer noOfPassengers;
    @Column(name = "Comments")
    private String comments;
    @Column(name = "InternalNote")
    private String internalNote;
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
    private List<Member> membersList = new ArrayList<>();

    @Column(name = "CsvFileFamilyId")
    private int csvFileFamilyId;

    public Family() {
    }

    public Family(String zipCode, Integer noOfAdults, Integer noOfChildren,
                  String town, String callSpecificTime, Integer noOfPassengers,
                  String comments, String internalNote, BusRide busRide,
                  CallStatus callStatus, YesNo active, YesNo canCallAnytime,
                  YesNo SNVGuidelines, TeamLead teamLead, Sevadar sevadar,
                  List<Member> membersList, int csvFileFamilyId) {
        this.zipCode = zipCode;
        this.noOfAdults = noOfAdults;
        this.noOfChildren = noOfChildren;
        this.town = town;
        this.callSpecificTime = callSpecificTime;
        this.noOfPassengers = noOfPassengers;
        this.comments = comments;
        this.internalNote = internalNote;
        this.busRide = busRide;
        this.callStatus = callStatus;
        this.active = active;
        this.canCallAnytime = canCallAnytime;
        this.SNVGuidelines = SNVGuidelines;
        this.teamLead = teamLead;
        this.sevadar = sevadar;
        this.membersList = membersList;
        this.csvFileFamilyId = csvFileFamilyId;
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCallSpecificTime() {
        return callSpecificTime;
    }

    public void setCallSpecificTime(String callSpecificTime) {
        this.callSpecificTime = callSpecificTime;
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

    public int getCsvFileFamilyId() {
        return csvFileFamilyId;
    }

    public void setCsvFileFamilyId(int csvFileFamilyId) {
        this.csvFileFamilyId = csvFileFamilyId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Family{");
        sb.append("familyId=").append(familyId);
        sb.append(", CsvFileFamilyId='").append(csvFileFamilyId).append('\'');
        sb.append(", zipCode='").append(zipCode).append('\'');
        sb.append(", noOfAdults=").append(noOfAdults);
        sb.append(", noOfChildren=").append(noOfChildren);
        sb.append(", town='").append(town).append('\'');
        sb.append(", callSpecificTime='").append(callSpecificTime).append('\'');
        sb.append(", noOfPassengers=").append(noOfPassengers);
        sb.append(", comments='").append(comments).append('\'');
        sb.append(", internalNote='").append(internalNote).append('\'');
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
