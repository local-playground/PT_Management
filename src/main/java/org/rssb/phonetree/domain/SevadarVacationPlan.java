package org.rssb.phonetree.domain;


import java.util.List;

public class SevadarVacationPlan {
    private String sevadarName;
    private List<SevadarsMonthlyAvailability> sevadarsMonthlyAvailabilities;

    public String getSevadarName() {
        return sevadarName;
    }

    public void setSevadarName(String sevadarName) {
        this.sevadarName = sevadarName;
    }

    public List<SevadarsMonthlyAvailability> getSevadarsMonthlyAvailabilities() {
        return sevadarsMonthlyAvailabilities;
    }

    public void setSevadarsMonthlyAvailabilities(List<SevadarsMonthlyAvailability> sevadarsMonthlyAvailabilities) {
        this.sevadarsMonthlyAvailabilities = sevadarsMonthlyAvailabilities;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SevadarVacationPlan{");
        sb.append("sevadarName='").append(sevadarName).append('\'');
        sb.append(", sevadarsMonthlyAvailabilities=").append(sevadarsMonthlyAvailabilities);
        sb.append('}');
        return sb.toString();
    }
}
