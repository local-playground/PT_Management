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
    private DecoratedTextField cellPhoneTextField;

    @FXML
    private DecoratedTextField cellPhoneCommentTextField;

    public boolean isPhoneNumberEmpty(){
        return CommonUtil.isEmptyOrNull(cellPhoneTextField.getText());
    }

    public String getPhoneNumber(){
        return cellPhoneTextField.getText();
    }

    public String getPhoneComments(){
        return CommonUtil.isEmptyOrNull(cellPhoneCommentTextField.getText())?"":cellPhoneCommentTextField.getText();
    }

    public void setPhoneNumber(String text){
        cellPhoneTextField.setText(text);
    }

    public void setPhoneComments(String text){
        cellPhoneCommentTextField.setText(text);
    }

    public void showErrorMessage(String message){
        cellPhoneTextField.showPopOver(message);
    }
}
