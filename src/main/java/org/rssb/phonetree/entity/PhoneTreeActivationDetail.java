package org.rssb.phonetree.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PhoneTreeActivationDetail")
public class PhoneTreeActivationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhoneTreeActivationDetailId")
    private int phoneTreeActivationDetailId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sevadarId",referencedColumnName = "sevadarsId")
    private Sevadar sevadar;

    @JoinColumn(name = "PhoneTreeActivationId")
    @ManyToOne(fetch = FetchType.LAZY)
    private PhoneTreeActivation phoneTreeActivation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamLeadId",referencedColumnName = "teamLeadId")
    private TeamLead teamLead;

    @Column(name = "PhoneTreeActivationFinishedTime")
    private String phoneTreeActivationFinishedTime;

    @Column(name = "totalFamiliesCalled")
    private int totalFamiliesCalled;

    @Column(name = "totalVMLeft")
    private int totalVMLeft;

    @Column(name = "totalNotReachableFamilies")
    private int totalNotReachableFamilies;

    @Column(name = "totalTimeTaken")
    private int totalTimeTaken;


    public PhoneTreeActivation getPhoneTreeActivation() {
        return phoneTreeActivation;
    }

    public void setPhoneTreeActivation(PhoneTreeActivation phoneTreeActivation) {
        this.phoneTreeActivation = phoneTreeActivation;
    }

    public int getPhoneTreeActivationDetailId() {
        return phoneTreeActivationDetailId;
    }

    public void setPhoneTreeActivationDetailId(int phoneTreeActivationDetailId) {
        this.phoneTreeActivationDetailId = phoneTreeActivationDetailId;
    }

    public Sevadar getSevadar() {
        return sevadar;
    }

    public void setSevadar(Sevadar sevadar) {
        this.sevadar = sevadar;
    }

    public TeamLead getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(TeamLead teamLead) {
        this.teamLead = teamLead;
    }

    public String getPhoneTreeActivationFinishedTime() {
        return phoneTreeActivationFinishedTime;
    }

    public void setPhoneTreeActivationFinishedTime(String phoneTreeActivationFinishedTime) {
        this.phoneTreeActivationFinishedTime = phoneTreeActivationFinishedTime;
    }

    public int getTotalFamiliesCalled() {
        return totalFamiliesCalled;
    }

    public void setTotalFamiliesCalled(int totalFamiliesCalled) {
        this.totalFamiliesCalled = totalFamiliesCalled;
    }

    public int getTotalVMLeft() {
        return totalVMLeft;
    }

    public void setTotalVMLeft(int totalVMLeft) {
        this.totalVMLeft = totalVMLeft;
    }

    public int getTotalNotReachableFamilies() {
        return totalNotReachableFamilies;
    }

    public void setTotalNotReachableFamilies(int totalNotReachableFamilies) {
        this.totalNotReachableFamilies = totalNotReachableFamilies;
    }

    public int getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(int totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhoneTreeActivationDetail{");
        sb.append("phoneTreeActivationDetailId=").append(phoneTreeActivationDetailId);
        /*sb.append(", sevadar=").append(sevadar);
        sb.append(", teamLead=").append(teamLead);*/
        //sb.append(", phoneTreeActivation='").append(phoneTreeActivation).append('\'');
        sb.append(", phoneTreeActivationFinishedTime='").append(phoneTreeActivationFinishedTime).append('\'');
        sb.append(", totalFamiliesCalled=").append(totalFamiliesCalled);
        sb.append(", totalVMLeft=").append(totalVMLeft);
        sb.append(", totalNotReachableFamilies=").append(totalNotReachableFamilies);
        sb.append(", totalTimeTaken=").append(totalTimeTaken);
        sb.append('}');
        return sb.toString();
    }
}
