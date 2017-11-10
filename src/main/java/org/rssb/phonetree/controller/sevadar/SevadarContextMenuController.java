package org.rssb.phonetree.controller.sevadar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.rssb.phonetree.common.Constants;
import org.rssb.phonetree.common.ContextHolder;
import org.rssb.phonetree.controller.AbstractController;
import org.rssb.phonetree.controller.EmailTemplate;
import org.rssb.phonetree.entity.Sevadar;
import org.rssb.phonetree.services.SevadarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
@Lazy
public class SevadarContextMenuController extends AbstractController {

    private List<EmailTemplate<Sevadar>> emailTemplatesList = new ArrayList<>();

    @Autowired
    private SevadarService sevadarService;

    @FXML
    private VBox sevadarsEmailTemplateHolder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initializing...");
    }

    @Override
    public void postProcess() {
        List<Sevadar> sevadarList = (List<Sevadar>) contextHolder.get(Constants.REQUEST_OBJ);
        for (Sevadar Sevadar : sevadarList) {
            sevadarsEmailTemplateHolder.getChildren().add(getHBox(Sevadar));
        }
    }

    private HBox getHBox(Sevadar sevadar) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/email-template.fxml"));
        HBox parent;
        try {
            parent = fxmlLoader.load();
            EmailTemplate emailTemplate = fxmlLoader.getController();
            ContextHolder ctxHolder = createContextHolder(
                    new String[]{Constants.REQUEST_OBJ},
                    new Object[]{sevadar}, null);

            emailTemplate.setContextHolder(ctxHolder);
            emailTemplate.postProcess();
            emailTemplatesList.add(emailTemplate);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return parent;
    }

    @FXML
    void addEmailId(ActionEvent event) {
        for(EmailTemplate emailTemplate : emailTemplatesList){
            Sevadar sevadar = (Sevadar) emailTemplate.getRequest();
            sevadarService.save(sevadar);
        }
        closeScreen(event, contextHolder);
    }

    @FXML
    void cancel(ActionEvent event) {
        closeScreen(event, contextHolder);
    }

}
