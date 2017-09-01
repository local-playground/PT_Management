package org.rssb.phonetree.management;

import javafx.application.Application;
import javafx.stage.Stage;
import org.rssb.phonetree.ui.view.FxmlView;
import org.rssb.phonetree.ui.view.StageManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.rssb"})
@EntityScan(basePackages= {"org.rssb.phonetree.entity"})
public class PhoneTreeManagementApplication extends Application {
    
    private ConfigurableApplicationContext applicationContext;
    private StageManager stageManager;

    @Override
    public void init(){
        System.out.println("PhoneTree Init is called ");
        applicationContext=bootstrapSpringApplicationContext();
        System.out.println("Successfully done init of Spring APP ");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stageManager = applicationContext.getBean(StageManager.class, stage);
        stageManager.switchScene(FxmlView.MAIN);
    }
        
    private ConfigurableApplicationContext bootstrapSpringApplicationContext(){
        System.out.println("Bootstraping spring app ");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(PhoneTreeManagementApplication.class);
        String arg[] = getParameters().getRaw().stream().toArray(String[]::new);
        return builder.run(arg);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
