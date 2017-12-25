package org.rssb.phonetree.services;

import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.domain.PhoneTreeActivationSummary;
import org.rssb.phonetree.domain.PhoneTreeActivationTeamLeadSummary;
import org.rssb.phonetree.entity.PhoneTreeActivation;
import org.rssb.phonetree.entity.PhoneTreeActivationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "org.rssb.phonetree")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class PhoneTreeActivationServiceTest extends ApplicationSetup {

    @Autowired
    private PhoneTreeActivationService phoneTreeActivationService;


    @Test
    public void findByPhoneTreeActivationDate(){
        Optional<PhoneTreeActivation> phoneTreeActivation =
                phoneTreeActivationService.getActivationDetailsByDate("2017-12-21");
        if(phoneTreeActivation.isPresent()){
            System.out.println(phoneTreeActivation.get().getPhoneTreeActivationDetailList());
        }
    }

    @Test
    public void getActivationDetailsByDateAndSevadarId(){
        Optional<PhoneTreeActivationDetail> phoneTreeActivationDetailOptional =
                phoneTreeActivationService.getActivationDetailsByDateAndSevadarId("2017-12-21", 1);

        if (phoneTreeActivationDetailOptional.isPresent()) {
            System.out.println(phoneTreeActivationDetailOptional.get());
        }
    }

    @Test
    public void getActivationDetailsByDateAndTeamLeadId(){
        Optional<List<PhoneTreeActivationDetail>> phoneTreeActivationDetailOptional =
                phoneTreeActivationService.getActivationDetailsByDateAndTeamLeadId("2017-12-21", 3);

        if (phoneTreeActivationDetailOptional.isPresent()) {
            System.out.println(phoneTreeActivationDetailOptional.get());
        }
    }

    @Test
    public void getActivationSummaryByDateAndTeamLeadId(){
        List<PhoneTreeActivationTeamLeadSummary> list =
                phoneTreeActivationService.getTeamLeadsActivationSummaryByDate("2017-12-21");

        System.out.println(list);

    }


    @Test
    public void getActivationSummary(){
        PhoneTreeActivationSummary phoneTreeActivationSummary =
                phoneTreeActivationService.getActivationSummary("2017-12-21");
        System.out.println(phoneTreeActivationSummary);
    }
}
