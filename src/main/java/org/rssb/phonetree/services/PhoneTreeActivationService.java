package org.rssb.phonetree.services;


import org.rssb.phonetree.domain.PhoneTreeActivationSummary;
import org.rssb.phonetree.domain.PhoneTreeActivationTeamLeadSummary;
import org.rssb.phonetree.entity.PhoneTreeActivation;
import org.rssb.phonetree.entity.PhoneTreeActivationDetail;

import java.util.List;
import java.util.Optional;

public interface PhoneTreeActivationService {
    Optional<PhoneTreeActivation> getActivationDetailsByDate(String activationDate);

    Optional<PhoneTreeActivationDetail> getActivationDetailsByDateAndSevadarId(String activationDate, int sevadarId);

    Optional<List<PhoneTreeActivationDetail>> getActivationDetailsByDateAndTeamLeadId(String activationDate, int teamLeadId);

    List<PhoneTreeActivationTeamLeadSummary> getActivationSummaryByDateAndTeamLeadId(String activationDate);

    PhoneTreeActivationSummary getActivationSummary(String activationDate);
}
