package org.rssb.phonetree.domain;

import java.util.List;

public class PhoneTreeActivationReport {
    private PhoneTreeActivationSummary phoneTreeActivationSummary;
    private List<PhoneTreeActivationTeamLeadSummary> phoneTreeActivationTeamLeadSummaryList;

    public PhoneTreeActivationSummary getPhoneTreeActivationSummary() {
        return phoneTreeActivationSummary;
    }

    public void setPhoneTreeActivationSummary(PhoneTreeActivationSummary phoneTreeActivationSummary) {
        this.phoneTreeActivationSummary = phoneTreeActivationSummary;
    }

    public List<PhoneTreeActivationTeamLeadSummary> getPhoneTreeActivationTeamLeadSummaryList() {
        return phoneTreeActivationTeamLeadSummaryList;
    }

    public void setPhoneTreeActivationTeamLeadSummaryList(List<PhoneTreeActivationTeamLeadSummary> phoneTreeActivationTeamLeadSummaryList) {
        this.phoneTreeActivationTeamLeadSummaryList = phoneTreeActivationTeamLeadSummaryList;
    }
}

