package org.rssb.phonetree.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TeamLeads")
@NamedQueries({
        @NamedQuery(name = "TeamLead.findAllTeamLeads",
                query = "SELECT distinct t from TeamLead t" +
                        " JOIN FETCH t.sevadarsList s" +
                        " JOIN FETCH t.member " +
                        " JOIN FETCH s.member " ),
        @NamedQuery(name = "TeamLead.personalInformation",
                query= "SELECT NEW org.rssb.phonetree.domain.SevadarPersonalInformation(" +
                        "t.teamLeadName,m.cellPhone,m.homePhone,m.emailId) FROM TeamLead t " +
                        " JOIN t.member m " +
                        " WHERE t.teamLeadName = :teamLeadName"),
        @NamedQuery(name ="TeamLead.BackupTeamLeadPersonalInformation",
                query= "SELECT NEW org.rssb.phonetree.domain.SevadarPersonalInformation(" +
                        "s.sevadarName,s.member.cellPhone,s.member.homePhone,s.member.emailId) FROM TeamLead t " +
                        " JOIN t.member m " +
                        " JOIN t.sevadarsList s " +
                        " WHERE t.teamLeadId = s.teamLead.teamLeadId " +
                        " AND t.teamLeadName = :teamLeadName" +
                        " AND s.isBackupForTeamLead = 1")
})

public class TeamLead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TeamLeadId")
    private int teamLeadId;

    @Column(name = "TeamLeadName")
    private String teamLeadName;

    @OneToMany(mappedBy = "teamLead", fetch = FetchType.LAZY)
    private List<Sevadar> sevadarsList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FamilyId")
    private Family family;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberId")
    private Member member;

    @Column(name = "EmailId")
    private String emailId;

    public int getTeamLeadId() {
        return teamLeadId;
    }

    public void setTeamLeadId(int teamLeadId) {
        this.teamLeadId = teamLeadId;
    }

    public String getTeamLeadName() {
        return teamLeadName;
    }

    public void setTeamLeadName(String teamLeadName) {
        this.teamLeadName = teamLeadName;
    }

    public List<Sevadar> getSevadarsList() {
        return sevadarsList;
    }

    public void setSevadarsList(List<Sevadar> sevadarsList) {
        this.sevadarsList = sevadarsList;
    }


    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    @Override

    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamLead{");
        sb.append("teamLeadId=").append(teamLeadId);
        sb.append(", teamLeadName='").append(teamLeadName).append('\'');
        sb.append("emailId=").append(emailId);
        //sb.append(", sevadarsList=").append(sevadarsList);
        sb.append('}');
        return sb.toString();
    }
}