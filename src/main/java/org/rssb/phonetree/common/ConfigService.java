package org.rssb.phonetree.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    @Value("${org.rssb.phonetree.seceratery-name}")
    private String secerateryName;

    @Value("${org.rssb.phonetree.admin-names}")
    private String adminNames;

    public String getSecerateryName() {
        return secerateryName;
    }

    public String getAdminNames() {
        return adminNames;
    }

}
