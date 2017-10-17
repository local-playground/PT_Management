package org.rssb.phonetree.custom.controls;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;

//@Component
public class CustomLabelAndTextField extends HBox implements Initializable{
   /* @Autowired
    private SpringFXMLLoader springFXMLLoader;
*/
    @FXML
    private CustomTextField customTextField;

    @FXML
    private Label label;

    private String labelText;
    private String labelGlyphIconName;
    private String labelGlyphIconSize;

    private String text;
    private String promptText;
    private String textLeftGlyphIconName;
    private String textRightGlyphIconName;
    private String acceptedTextFormat;
    private String textLimit;

    public CustomLabelAndTextField() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/custom/controls/hbox-label-textfield.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        label.setText(labelText);
    }

    public String getLabelGlyphIconName() {
        return labelGlyphIconName;
    }

    public void setLabelGlyphIconName(String labelGlyphIconName) {
        this.labelGlyphIconName = labelGlyphIconName;
    }

    public String getLabelGlyphIconSize() {
        return labelGlyphIconSize;
    }

    public void setLabelGlyphIconSize(String labelGlyphIconSize) {
        this.labelGlyphIconSize = labelGlyphIconSize;
    }

    public String getText() {
        return customTextField.getText();
    }

    public void setText(String text) {
        customTextField.setText(text);
    }

    public String getPromptText() {
        return  customTextField.getPromptText();
    }

    public void setPromptText(String promptText) {
        customTextField.setPromptText(promptText);
    }

    public String getTextLeftGlyphIconName() {
        return textLeftGlyphIconName;
    }

    public void setTextLeftGlyphIconName(String textLeftGlyphIconName) {
        this.textLeftGlyphIconName = textLeftGlyphIconName;
    }

    public String getTextRightGlyphIconName() {
        return textRightGlyphIconName;
    }

    public void setTextRightGlyphIconName(String textRightGlyphIconName) {
        this.textRightGlyphIconName = textRightGlyphIconName;
    }

    public String getAcceptedTextFormat() {
        return acceptedTextFormat;
    }

    public void setAcceptedTextFormat(String acceptedTextFormat) {
        this.acceptedTextFormat = acceptedTextFormat;
    }

    public String getTextLimit() {
        return textLimit;
    }

    public void setTextLimit(String textLimit) {
        this.textLimit = textLimit;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Initialize called");
    }
}
