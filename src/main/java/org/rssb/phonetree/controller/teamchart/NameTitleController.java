package org.rssb.phonetree.controller.teamchart;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.controller.AbstractController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class NameTitleController extends AbstractController{
    @FXML
    private AnchorPane container;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private FontAwesomeIconView fontIConView;

    @Override
    public void postProcess() {
        String firstName = (String) contextHolder.get("FIRST_NAME");
        String lastName = (String) contextHolder.get("LAST_NAME");
        String title = (String) contextHolder.get("TITLE");
        if (CommonUtil.isNotEmptyOrNull(firstName)) {
            firstNameLabel.setText(firstName);
        }

        if(CommonUtil.isNotEmptyOrNull(lastName)){
            lastNameLabel.setText(lastName);
        }

        if (CommonUtil.isNotEmptyOrNull(title)) {
            titleLabel.setText(title);
            if(title.equalsIgnoreCase("Secretory")){
                container.setStyle("-fx-background-color: #0d47a1");
            }
            if(title.equalsIgnoreCase("Team Lead Monitor")){
                container.setStyle("-fx-background-color: #311b92");
                fontIConView.setIcon(FontAwesomeIcon.GROUP);
            }
            if(title.equalsIgnoreCase("Phone Tree Admin")){
                container.setStyle("-fx-background-color: #880e4f");
            }
            if(title.equalsIgnoreCase("Team Lead")){
                container.setStyle("-fx-background-color: #43a047");
                fontIConView.setIcon(FontAwesomeIcon.GROUP);

            }
            if(title.equalsIgnoreCase("Sevadar")){
                container.setStyle("-fx-background-color: #fb8c00");
            }
            if(title.equalsIgnoreCase("Team Lead Backup")){
                container.setStyle("-fx-background-color: #1b5e20");
            }
        }
    }
}
