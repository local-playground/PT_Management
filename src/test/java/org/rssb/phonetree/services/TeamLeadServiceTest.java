package org.rssb.phonetree.services;

import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.entity.TeamLead;
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
public class TeamLeadServiceTest extends ApplicationSetup{

    @Autowired
    private TeamLeadService teamLeadService;

    @Test
    public void findAllTeamLeads(){
        List<TeamLead> teamLeadList = teamLeadService.findAllTeamLeads();
        teamLeadList.stream().forEach(teamLead -> System.out.println(teamLead.getTeamLeadName()));
    }

    @Test
    public void findTeamLeadById(){
        Optional<TeamLead> teamLead = teamLeadService.findTeamLeadById(15);
        if(teamLead.isPresent()){
            System.out.println("Team Lead Name =" +teamLead.get().getTeamLeadName());
        }else{
            System.out.println("Team Lead not found");
        }
    }

    @Test
    public void replaceTeamLead(){
        Response response = teamLeadService.replaceTeamLead(10,1);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void deleteTeamLeadWhenSevadarsAssigned(){
        Response response = teamLeadService.deleteTeamLead(1);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void deleteTeamLeadWhenNoSevadarsAssigned(){
        Response response = teamLeadService.deleteTeamLead(7);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void addTeamLead(){
        Response response = teamLeadService.addTeamLead(1);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }
    @Test
    public void findTeamLeadByMemberId(){
        Optional<TeamLead> teamLead = teamLeadService.findTeamLeadByMemberId(1);
        if(teamLead.isPresent()){
            System.out.println("Team Lead Name =" +teamLead.get().getTeamLeadName());
        }else{
            System.out.println("Team Lead not found");
        }
    }
    @Test
    public void findTeamLeadByFamilyId(){
        Optional<TeamLead> teamLead = teamLeadService.findTeamLeadByFamilyId(1);
        if(teamLead.isPresent()){
            System.out.println("Team Lead Name =" +teamLead.get().getTeamLeadName());
        }else{
            System.out.println("Team Lead not found");
        }
    }
    @Test
    public void findSevadarsListByTeamLeadName(){
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadName("Naren Shah");
        sevadarList.stream().forEach(sevadar -> System.out.println(sevadar.getSevadarName()));
    }

    @Test
    public void findSevadarsListByTeamLeadId(){
        List<Sevadar> sevadarList = teamLeadService.findSevadarListByTeamLeadId(1);
        sevadarList.stream().forEach(sevadar -> System.out.println(sevadar.getSevadarName()));
    }

    @Test
    public void swapTeamLead(){
        Response response = teamLeadService.swapTeamLead(1,7);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void swapTeamLeadWhenBothTeamLeadIdAreSame(){
        Response response = teamLeadService.swapTeamLead(1,1);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void swapTeamLeadWhenBothTeamLeadAreNotFoundInDB(){
        Response response = teamLeadService.swapTeamLead(1,20);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }
}
