package org.rssb.phonetree.entity;

import org.rssb.phonetree.domain.VacationDate;
import org.rssb.phonetree.entity.converters.VacationDatesConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "SevadarsVacation")
public class SevadarVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SevadarsVacationId")
    private int sevadarsVacationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sevadarId",referencedColumnName = "sevadarsId")
    private Sevadar sevadar;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teamLeadId",referencedColumnName = "teamLeadId")
    private TeamLead teamLead;

    @Column(name = "vacationPlan")
    @Convert(converter = VacationDatesConverter.class)
    private List<VacationDate> vacationPlan;

    public int getSevadarsVacationId() {
        return sevadarsVacationId;
    }

    public void setSevadarsVacationId(int sevadarsVacationId) {
        this.sevadarsVacationId = sevadarsVacationId;
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

    public List<VacationDate> getVacationPlan() {
        return vacationPlan;
    }

    public void setVacationPlan(List<VacationDate> vacationPlan) {
        this.vacationPlan = vacationPlan;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SevadarVacation{");
        sb.append("sevadarsVacationId=").append(sevadarsVacationId);
        sb.append(", vacationPlan='").append(vacationPlan).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
