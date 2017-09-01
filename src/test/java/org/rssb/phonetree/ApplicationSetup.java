package org.rssb.phonetree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rssb.phonetree.management.PhoneTreeManagementApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PhoneTreeManagementApplication.class)
public class ApplicationSetup {

    @Test
    public void contextLoads() {
    }
}
