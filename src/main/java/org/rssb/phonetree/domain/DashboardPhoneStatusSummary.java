package org.rssb.phonetree.domain;


import org.rssb.phonetree.entity.emums.CallStatus;

public class DashboardPhoneStatusSummary {
    private CallStatus callStatus;
    private long count;

    public DashboardPhoneStatusSummary(CallStatus callStatus, long count) {
        this.callStatus = callStatus;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DashboardPhoneStatusSummary{");
        sb.append("callStatus=").append(callStatus);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
