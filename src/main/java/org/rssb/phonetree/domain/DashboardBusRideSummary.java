package org.rssb.phonetree.domain;

import org.rssb.phonetree.entity.emums.BusRide;

public class DashboardBusRideSummary {

    private BusRide busRide;
    private long count;

    public DashboardBusRideSummary(BusRide busRide, long count) {
        this.busRide = busRide;
        this.count = count;
    }

    public BusRide getBusRide() {
        return busRide;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DashboardBusRideSummary{");
        sb.append("busRide=").append(busRide);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
