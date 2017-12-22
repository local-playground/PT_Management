package org.rssb.phonetree.services.impl;

import org.rssb.phonetree.domain.PhoneTreeActivationSevadarSummary;
import org.rssb.phonetree.domain.PhoneTreeActivationSummary;
import org.rssb.phonetree.domain.PhoneTreeActivationTeamLeadSummary;
import org.rssb.phonetree.entity.PhoneTreeActivation;
import org.rssb.phonetree.entity.PhoneTreeActivationDetail;
import org.rssb.phonetree.entity.TeamLead;
import org.rssb.phonetree.repository.PhoneTreeActivationDetailRepository;
import org.rssb.phonetree.repository.PhoneTreeActivatonRepository;
import org.rssb.phonetree.services.PhoneTreeActivationService;
import org.rssb.phonetree.services.TeamLeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneTreeActivationServiceImpl implements PhoneTreeActivationService {

    @Autowired
    private PhoneTreeActivatonRepository phoneTreeActivatonRepository;

    @Autowired
    private PhoneTreeActivationDetailRepository phoneTreeActivationDetailRepository;

    @Autowired
    private TeamLeadService teamLeadService;

    @Override
    public Optional<PhoneTreeActivation> getActivationDetailsByDate(String activationDate) {
        return Optional.ofNullable(phoneTreeActivatonRepository.findByPhoneTreeActivationDate(activationDate));
    }

    @Override
    public Optional<PhoneTreeActivationDetail> getActivationDetailsByDateAndSevadarId(String activationDate, int sevadarId) {
        return Optional.ofNullable(phoneTreeActivatonRepository.getActivationDetailsByDateAndSevadarId(activationDate, sevadarId));
    }

    @Override
    public Optional<List<PhoneTreeActivationDetail>> getActivationDetailsByDateAndTeamLeadId(String activationDate, int teamLeadId) {
        return Optional.ofNullable(phoneTreeActivatonRepository.getActivationDetailsByDateAndTeamLeadId(activationDate, teamLeadId));
    }

    @Override
    public List<PhoneTreeActivationTeamLeadSummary> getActivationSummaryByDateAndTeamLeadId(String activationDate) {
        List<PhoneTreeActivationTeamLeadSummary> phoneTreeActivationTeamLeadSummaries = new ArrayList<>();
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        for (TeamLead teamLead : teamLeadList) {
            Optional<List<PhoneTreeActivationDetail>> phoneTreeActivationDetailListOptional = getActivationDetailsByDateAndTeamLeadId(activationDate, teamLead.getTeamLeadId());
            if (phoneTreeActivationDetailListOptional.isPresent()) {
                PhoneTreeActivationTeamLeadSummary phoneTreeActivationTeamLeadSummary = new PhoneTreeActivationTeamLeadSummary();
                phoneTreeActivationTeamLeadSummary.setTeamLeadId(teamLead.getTeamLeadId());
                phoneTreeActivationTeamLeadSummary.setFirstName(teamLead.getMember().getFirstName());
                phoneTreeActivationTeamLeadSummary.setLastName(teamLead.getMember().getLastName());
                phoneTreeActivationTeamLeadSummaries.add(phoneTreeActivationTeamLeadSummary);

                List<PhoneTreeActivationDetail> phoneTreeActivationDetailList = phoneTreeActivationDetailListOptional.get();

                phoneTreeActivationTeamLeadSummary.setTotalFamilies(phoneTreeActivationDetailList.stream()
                        .mapToInt(PhoneTreeActivationDetail::getTotalFamiliesCalled).sum());

                phoneTreeActivationTeamLeadSummary.setTotalNotReachedFamilies(phoneTreeActivationDetailList.stream()
                        .mapToInt(PhoneTreeActivationDetail::getTotalNotReachableFamilies).sum());

                phoneTreeActivationTeamLeadSummary.setTotalVMLeft(phoneTreeActivationDetailList.stream()
                .mapToInt(PhoneTreeActivationDetail::getTotalVMLeft).sum());

                List<PhoneTreeActivationSevadarSummary> phoneTreeActivationSevadarSummaries = new ArrayList<>();
                for (PhoneTreeActivationDetail phoneTreeActivationDetail : phoneTreeActivationDetailList) {
                    String firstName = phoneTreeActivationDetail.getSevadar().getMember().getFirstName();
                    String lastName = phoneTreeActivationDetail.getSevadar().getMember().getLastName();
                    int totalFamiliesCalled = phoneTreeActivationDetail.getTotalFamiliesCalled();
                    int totalVMLeft = phoneTreeActivationDetail.getTotalVMLeft();
                    int totalNotReachedFamilies = phoneTreeActivationDetail.getTotalNotReachableFamilies();
                    int totalTimeTaken = phoneTreeActivationDetail.getTotalTimeTaken();
                    String activationFinishedTime = phoneTreeActivationDetail.getPhoneTreeActivationFinishedTime();


                    PhoneTreeActivationSevadarSummary phoneTreeActivationSevadarSummary = new PhoneTreeActivationSevadarSummary();
                    phoneTreeActivationSevadarSummary.setFirstName(firstName);
                    phoneTreeActivationSevadarSummary.setLastName(lastName);
                    phoneTreeActivationSevadarSummary.setTotalFamilies(totalFamiliesCalled);
                    phoneTreeActivationSevadarSummary.setTotalVMLeft(totalVMLeft);
                    phoneTreeActivationSevadarSummary.setTotalNotReachedFamilies(totalNotReachedFamilies);
                    phoneTreeActivationSevadarSummary.setTotalTimeTaken(totalTimeTaken);
                    phoneTreeActivationSevadarSummary.setActivationFinishedTime(activationFinishedTime);

                    phoneTreeActivationSevadarSummaries.add(phoneTreeActivationSevadarSummary);
                }
                phoneTreeActivationTeamLeadSummary.setPhoneTreeActivationSevadarSummaryList(phoneTreeActivationSevadarSummaries);
            }
        }

        return phoneTreeActivationTeamLeadSummaries;
    }

    @Override
    public PhoneTreeActivationSummary getActivationSummary(String activationDate) {
        return phoneTreeActivatonRepository.getPhoneTreeActivationSummary(activationDate);
    }

    @Override
    public PhoneTreeActivation save(PhoneTreeActivation phoneTreeActivation) {
        return phoneTreeActivatonRepository.saveAndFlush(phoneTreeActivation);
    }

    @Override
    public void savePhoneTreeActivationDetails(String activationDate,
                                               List<PhoneTreeActivationDetail> phoneTreeActivationDetailList) {
        Optional<PhoneTreeActivation> phoneTreeActivationOptional = getActivationDetailsByDate(activationDate);
        PhoneTreeActivation phoneTreeActivation = null;
        if(phoneTreeActivationOptional.isPresent()){
            phoneTreeActivation = phoneTreeActivationOptional.get();
        }

        for(PhoneTreeActivationDetail phoneTreeActivationDetail:phoneTreeActivationDetailList){
            phoneTreeActivationDetail.setPhoneTreeActivation(phoneTreeActivation);
            //phoneTreeActivation.addPhoneTreeActivationDetail(phoneTreeActivationDetail);
            phoneTreeActivationDetailRepository.saveAndFlush(phoneTreeActivationDetail);
        }

    }

}
