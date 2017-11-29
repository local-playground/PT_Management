package org.rssb.phonetree.services;

import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.common.Response;
import org.rssb.phonetree.domain.FamilyCount;
import org.rssb.phonetree.entity.Sevadar;
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
public class SevadarServiceTest extends ApplicationSetup{

    @Autowired
    private SevadarService sevadarService;

    @Test
    public void findAllSevadars(){
        List<Sevadar> sevadarList = sevadarService.findAllSevadars();
        sevadarList.stream().forEach(sevadar -> System.out.println(sevadar.getSevadarName()));
    }

    @Test
    public void findSevadarByMemberId(){
        Optional<Sevadar> sevadar = sevadarService.findSevadarByMemberId(1);
        if(sevadar.isPresent()){
            System.out.println("Sevadar Name =" +sevadar.get().getSevadarName());
        }else{
            System.out.println("Sevadar not found");
        }
    }

    @Test
    public void findSevadarByFamilyId(){
        Optional<Sevadar> sevadar = sevadarService.findSevadarByFamilyId(1);
        if(sevadar.isPresent()){
            System.out.println("Sevadar Name =" +sevadar.get().getSevadarName());
        }else{
            System.out.println("Sevadar not found");
        }
    }

    @Test
    public void findSevadarBySevadarId(){
        Optional<Sevadar> sevadar = sevadarService.findSevadarBySevadarId(3);
        if(sevadar.isPresent()){
            System.out.println("Sevadar Name =" +sevadar.get().getSevadarName());
        }else{
            System.out.println("Sevadar not found");
        }
    }

    @Test
    public void findSevadarBySevadarName(){
        Optional<Sevadar> sevadar = sevadarService.findSevadarBySevadarName("");
        if(sevadar.isPresent()){
            System.out.println("Sevadar Name =" +sevadar.get().getSevadarName());
        }else{
            System.out.println("Sevadar not found");
        }
    }

    @Test
    public void addSevadar(){
        Response response = sevadarService.addSevadar(1,1);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void deleteSevadar(){
        Response response = sevadarService.deleteSevadar(1);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void replaceSevadar(){
        Response response = sevadarService.replaceSevadar(1,1);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void swapSevadar(){
        Response response = sevadarService.swapSevadar(21,7);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void moveSevadarUnderOtherTeamLead(){
        Response response = sevadarService.moveSevadarUnderOtherTeamLead(1,3);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void getFamilyCountByTeamLeadId(){
        List<FamilyCount> familyCountList = sevadarService.getSevadarsCallingFamilyCountByTeamLeadId(5);
        familyCountList.stream().forEach(familyCount -> System.out.println(familyCount));
    }

    @Test
    public void getSevadarStrigyfyInformation(){
        String information = sevadarService.getSevadarStrigyfyInformation("Kamal Singh");
        System.out.println("Got info\n"+information);
    }

}
