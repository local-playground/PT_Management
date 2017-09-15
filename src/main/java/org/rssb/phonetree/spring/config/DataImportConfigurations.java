package org.rssb.phonetree.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages="org.rssb.phonetree.repository")
@Configuration
@ComponentScan(basePackages = {"org.rssb"})
public class DataImportConfigurations {
}
