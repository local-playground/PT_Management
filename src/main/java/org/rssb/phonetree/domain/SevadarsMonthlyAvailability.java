package org.rssb.phonetree.domain;


public class SevadarsMonthlyAvailability {
    private String sevadarName;
    private String monthName;
    private String availableDates;
    private String outDates;
    private long totalDaysOut;

    public long getTotalDaysOut() {
        return totalDaysOut;
    }

    public void setTotalDaysOut(long totalDaysOut) {
        this.totalDaysOut = totalDaysOut;
    }

    public String getSevadarName() {
        return sevadarName;
    }

    public void setSevadarName(String sevadarName) {
        this.sevadarName = sevadarName;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(String availableDates) {
        this.availableDates = availableDates;
    }

    public String getOutDates() {
        return outDates;
    }

    public void setOutDates(String outDates) {
        this.outDates = outDates;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SevadarsMonthlyAvailability{");
        sb.append("sevadarName='").append(sevadarName).append('\'');
        sb.append(", monthName='").append(monthName).append('\'');
        sb.append(", availableDates='").append(availableDates).append('\'');
        sb.append(", outDates='").append(outDates).append('\'');
        sb.append(", totalDaysOut=").append(totalDaysOut);
        sb.append('}');
        return sb.toString();
    }
}
