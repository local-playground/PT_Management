package org.rssb.phonetree.domain;


import java.time.LocalDate;

public class VacationDate {

    private LocalDate fromDate;
    private LocalDate toDate;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(fromDate).append(":");
        sb.append(toDate);
        return sb.toString();
    }
}
