package org.rssb.phonetree.domain;

public class FamilyCount {
    private String name;
    private long count;

    public FamilyCount(String name, long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FamilyCount{");
        sb.append("name='").append(name).append('\'');
        sb.append(", count='").append(count).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
