package org.rssb.phonetree.spring.config;

import javafx.stage.Stage;
import org.rssb.phonetree.common.ExceptionWriter;
import org.rssb.phonetree.ui.view.StageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ResourceBundle;

@Configuration
@EnableJpaRepositories(basePackages="org.rssb.phonetree.repository")
public class PhoneTreeConfigurations {

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    public PhoneTreeConfigurations() {
        System.out.println("PhoneTreeConfigurations constructor called ");
    }

    @Bean
    @Scope("prototype")
    public ExceptionWriter exceptionWriter() {
        System.out.println("PhoneTreeConfigurations exceptionWriter ");
        return new ExceptionWriter(new StringWriter());
    }

    @Bean
    public ResourceBundle resourceBundle() {
        System.out.println("PhoneTreeConfigurations resourceBundle ");
        return ResourceBundle.getBundle("messages");
    }

    /*@Bean
    public TestController controller(){
        return new TestController();
    }*/

    @Bean
    @Lazy(value = true) //Stage only created after Spring context bootstap
    public StageManager stageManager(Stage stage) throws IOException {
        System.out.println("PhoneTreeConfigurations stageManager ");
        return new StageManager(springFXMLLoader, stage);
    }

}
