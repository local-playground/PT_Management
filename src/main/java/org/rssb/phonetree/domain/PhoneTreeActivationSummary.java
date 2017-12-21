package org.rssb.phonetree.domain;

public class PhoneTreeActivationSummary {
    private String activationDate;
    private int totalSangat;
    private long totalFamiliesCalled;
    private long totalVMLeft;
    private long toalNotReachableFamilies;

    private int minimumTimeTaken;
    private int maximumTimeTaken;
    private double averageTimeTaken;

    public PhoneTreeActivationSummary(String activationDate, int totalSangat,
                                        long totalFamiliesCalled, long totalVMLeft,
                                        long toalNotReachableFamilies, int minimumTimeTaken,
                                        int maximumTimeTaken, double averageTimeTaken) {
        this.activationDate = activationDate;
        this.totalSangat = totalSangat;
        this.totalFamiliesCalled = totalFamiliesCalled;
        this.totalVMLeft = totalVMLeft;
        this.toalNotReachableFamilies = toalNotReachableFamilies;
        this.minimumTimeTaken = minimumTimeTaken;
        this.maximumTimeTaken = maximumTimeTaken;
        this.averageTimeTaken = averageTimeTaken;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public int getTotalSangat() {
        return totalSangat;
    }

    public long getTotalFamiliesCalled() {
        return totalFamiliesCalled;
    }

    public long getTotalVMLeft() {
        return totalVMLeft;
    }

    public long getToalNotReachableFamilies() {
        return toalNotReachableFamilies;
    }

    public int getMinimumTimeTaken() {
        return minimumTimeTaken;
    }

    public int getMaximumTimeTaken() {
        return maximumTimeTaken;
    }

    public double getAverageTimeTaken() {
        return averageTimeTaken;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhoneTreeActivationSummary{");
        sb.append("activationDate='").append(activationDate).append('\'');
        sb.append(", totalSangat=").append(totalSangat);
        sb.append(", totalFamiliesCalled=").append(totalFamiliesCalled);
        sb.append(", totalVMLeft=").append(totalVMLeft);
        sb.append(", toalNotReachableFamilies=").append(toalNotReachableFamilies);
        sb.append(", minimumTimeTaken=").append(minimumTimeTaken);
        sb.append(", maximumTimeTaken=").append(maximumTimeTaken);
        sb.append(", averageTimeTaken=").append(averageTimeTaken);
        sb.append('}');
        return sb.toString();
    }
}
