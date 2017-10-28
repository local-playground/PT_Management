package org.rssb.phonetree.controller.familymanagement;


import javafx.fxml.FXML;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.custom.controls.DecoratedTextField;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class PhoneNumberController {
    @FXML
    private DecoratedTextField phoneNumberTextField;

    @FXML
    private DecoratedTextField phoneNumberCommentTextField;

    boolean isPhoneNumberEmpty() {
        return CommonUtil.isEmptyOrNull(phoneNumberTextField.getText());
    }

    String getPhoneNumber() {
        return phoneNumberTextField.getText();
    }

    String getPhoneComments() {
        return CommonUtil.isEmptyOrNull(phoneNumberCommentTextField.getText()) ? "" : phoneNumberCommentTextField.getText();
    }

    void setPhoneNumber(String text) {
        phoneNumberTextField.setText(text);
    }

    void setPhoneComments(String text) {
        phoneNumberCommentTextField.setText(text);
    }

    void showErrorMessage(String message) {
        phoneNumberTextField.showPopOver(message);
    }

    DecoratedTextField getPhoneNumberTextField() {
        return phoneNumberTextField;
    }
}
