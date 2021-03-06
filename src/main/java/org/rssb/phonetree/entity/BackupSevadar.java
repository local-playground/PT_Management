package org.rssb.phonetree.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "BackupSevadars")
public class BackupSevadar implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "BackupSevadarsId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int backupSevadarsId;
    @Column(name = "SevadarName")
    private String sevadarName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="FamilyId")
    private Family family;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberId")
    private Member member;

    @Transient
    private String assignedToTeamLead;

    public BackupSevadar() {
    }

    public int getBackupSevadarsId() {
        return backupSevadarsId;
    }

    public void setBackupSevadarsId(int backupSevadarsId) {
        this.backupSevadarsId = backupSevadarsId;
    }

    public String getSevadarName() {
        return sevadarName;
    }

    public void setSevadarName(String sevadarName) {
        this.sevadarName = sevadarName;
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

    public String getAssignedToTeamLead() {
        return assignedToTeamLead;
    }

    public void setAssignedToTeamLead(String teamLeadName) {
        this.assignedToTeamLead = teamLeadName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BackupSevadar{");
        sb.append("backupSevadarsId=").append(backupSevadarsId);
        sb.append(", sevadarName='").append(sevadarName).append('\'');
        sb.append(", teamLeadName='").append(assignedToTeamLead).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
