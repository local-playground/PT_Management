package org.rssb.phonetree.services;

import org.junit.Test;
import org.rssb.phonetree.ApplicationSetup;
import org.rssb.phonetree.common.Response;
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
public class BackupSevadarServiceTest extends ApplicationSetup{

    @Autowired
    private BackupSevadarService backupSevadarService;

    @Test
    public void addBackupSevadar(){
        Response response = backupSevadarService.addAsBackupSevadar(513);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }

    @Test
    public void removeBackupSevadar(){
        Response response = backupSevadarService.removeBackupSevadar(513);
        System.out.println("Response = "+ (response!=null?response.getMessage():"Null Response"));
    }
}
