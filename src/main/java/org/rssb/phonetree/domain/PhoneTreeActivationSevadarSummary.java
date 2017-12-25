package org.rssb.phonetree.domain;

public class PhoneTreeActivationSevadarSummary {
    private int sevadarId;
    private String firstName;
    private String lastName;
    private int totalFamilies;
    private int totalVMLeft;
    private int totalNotReachedFamilies;
    private int totalTimeTaken;
    private String activationFinishedTime;
    private String fullName;

    public String getFullName() {
        return firstName +" "+ lastName;
    }

    public int getSevadarId() {
        return sevadarId;
    }

    public void setSevadarId(int sevadarId) {
        this.sevadarId = sevadarId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTotalFamilies() {
        return totalFamilies;
    }

    public void setTotalFamilies(int totalFamilies) {
        this.totalFamilies = totalFamilies;
    }

    public int getTotalVMLeft() {
        return totalVMLeft;
    }

    public void setTotalVMLeft(int totalVMLeft) {
        this.totalVMLeft = totalVMLeft;
    }

    public int getTotalNotReachedFamilies() {
        return totalNotReachedFamilies;
    }

    public void setTotalNotReachedFamilies(int totalNotReachedFamilies) {
        this.totalNotReachedFamilies = totalNotReachedFamilies;
    }

    public int getTotalTimeTaken() {
        return totalTimeTaken;
    }

    public void setTotalTimeTaken(int totalTimeTaken) {
        this.totalTimeTaken = totalTimeTaken;
    }

    public String getActivationFinishedTime() {
        return activationFinishedTime;
    }

    public void setActivationFinishedTime(String activationFinishedTime) {
        this.activationFinishedTime = activationFinishedTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhoneTreeActivationSevadarSummary{");
        sb.append("sevadarId=").append(sevadarId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", totalFamilies=").append(totalFamilies);
        sb.append(", totalVMLeft=").append(totalVMLeft);
        sb.append(", totalNotReachedFamilies=").append(totalNotReachedFamilies);
        sb.append(", totalTimeTaken='").append(totalTimeTaken).append('\'');
        sb.append(", activationFinishedTime='").append(activationFinishedTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
