package org.rssb.phonetree.services;

import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.common.SevaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = "org.rssb.phonetree")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UtilityServiceTest extends ApplicationSetup{
    @Autowired
    private UtilityService utilityService;

    @Test
    public void isMemberEligibleForSevadar(){
        Response response = utilityService.isMemberAvailableForSeva(164, SevaType.ADD_SEVADAR);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void isMemberEligibleForTeamLead(){
        Response response = utilityService.isMemberAvailableForSeva(115, SevaType.ADD_TEAM_LEAD);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void isMemberEligibleForSevadarWhenFamilyMemberAlreadyTeamLead(){
        Response response = utilityService.isMemberAvailableForSeva(211, SevaType.ADD_SEVADAR);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void isMemberEligibleForTeamLeadWhenFamilyMemberAlreadySevadar(){
        Response response = utilityService.isMemberAvailableForSeva(51, SevaType.ADD_TEAM_LEAD);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }


}
