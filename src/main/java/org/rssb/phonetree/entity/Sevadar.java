
package org.rssb.phonetree.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Sevadars")
@NamedQueries({
        @NamedQuery(name = "Sevadar.personalInformation",
                query="SELECT NEW org.rssb.phonetree.domain.SevadarPersonalInformation(" +
                        "s.sevadarName,m.cellPhone,m.homePhone,m.emailId) FROM Sevadar s " +
                        " JOIN s.member m " +
                        " WHERE s.sevadarName = :sevadarName")

})

public class Sevadar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SevadarsId")
    private int sevadarsId;
    @Column(name = "SevadarName")
    private String sevadarName;
    @Column(name = "isBackupForTeamLead")
    private Integer isBackupForTeamLead;

    @JoinColumn(name = "TeamLeadId")
    @ManyToOne(fetch = FetchType.LAZY)
    private TeamLead teamLead;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FamilyId")
    private Family family;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberId")
    private Member member;

    public Sevadar() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getSevadarsId() {
        return sevadarsId;
    }

    public void setSevadarsId(int sevadarsId) {
        this.sevadarsId = sevadarsId;
    }

    public String getSevadarName() {
        return sevadarName;
    }

    public void setSevadarName(String sevadarName) {
        this.sevadarName = sevadarName;
    }

    public Integer getIsBackupForTeamLead() {
        return isBackupForTeamLead;
    }

    public void setIsBackupForTeamLead(Integer isBackupForTeamLead) {
        this.isBackupForTeamLead = isBackupForTeamLead;
    }

    public TeamLead getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(TeamLead teamLead) {
        this.teamLead = teamLead;
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
        final StringBuilder sb = new StringBuilder("Sevadar{");
        sb.append("sevadarsId=").append(sevadarsId);
        sb.append(", sevadarName='").append(sevadarName).append('\'');
        sb.append(", isBackupForTeamLead=").append(isBackupForTeamLead);
        sb.append('}');
        return sb.toString();
    }
}
