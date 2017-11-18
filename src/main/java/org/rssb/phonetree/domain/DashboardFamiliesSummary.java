
package org.rssb.phonetree.domain;

public class DashboardFamiliesSummary {
    private int totalSangatCount;
    private int totalFamiliesCount;
    private int onCallingListFamiliesCount;
    private int notOnCallingListFamiliesCount;

    public int getTotalSangatCount() {
        return totalSangatCount;
    }

    public void setTotalSangatCount(int totalSangatCount) {
        this.totalSangatCount = totalSangatCount;
    }

    public int getTotalFamiliesCount() {
        return totalFamiliesCount;
    }

    public void setTotalFamiliesCount(int totalFamiliesCount) {
        this.totalFamiliesCount = totalFamiliesCount;
    }

    public int getOnCallingListFamiliesCount() {
        return onCallingListFamiliesCount;
    }

    public void setOnCallingListFamiliesCount(int onCallingListFamiliesCount) {
        this.onCallingListFamiliesCount = onCallingListFamiliesCount;
    }

    public int getNotOnCallingListFamiliesCount() {
        return notOnCallingListFamiliesCount;
    }

    public void setNotOnCallingListFamiliesCount(int notOnCallingListFamiliesCount) {
        this.notOnCallingListFamiliesCount = notOnCallingListFamiliesCount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DashboardFamiliesSummary{");
        sb.append("totalSangatCount=").append(totalSangatCount);
        sb.append(", totalFamiliesCount=").append(totalFamiliesCount);
        sb.append(", onCallingListFamiliesCount=").append(onCallingListFamiliesCount);
        sb.append(", notOnCallingListFamiliesCount=").append(notOnCallingListFamiliesCount);
        sb.append('}');
        return sb.toString();
    }
}
