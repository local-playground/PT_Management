package org.rssb.phonetree.domain;

public class DashboardNameValueBasedSummary {
    private String name;
    private int count;

    public DashboardNameValueBasedSummary(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DashboardNameValueBasedSummary{");
        sb.append("name='").append(name).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
