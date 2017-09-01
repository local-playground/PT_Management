package org.rssb.phonetree.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TeamLeads")
public class TeamLead {

    @Id
    @Column(name = "TeamLeadId")
    private int teamLeadId;

    @Column(name = "TeamLeadName")
    private String teamLeadName;

    @OneToMany(mappedBy = "teamLead", fetch = FetchType.LAZY)
    private List<Sevadar> sevadarsList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FamilyId")
    private Family family;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberId")
    private Member member;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamLead{");
        sb.append("teamLeadId=").append(teamLeadId);
        sb.append(", teamLeadName='").append(teamLeadName).append('\'');
        //sb.append(", sevadarsList=").append(sevadarsList);
        sb.append('}');
        return sb.toString();
    }
}