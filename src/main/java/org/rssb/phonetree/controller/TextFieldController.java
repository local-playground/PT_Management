package org.rssb.phonetree.controller;

import javafx.fxml.FXML;
import org.rssb.phonetree.common.CommonUtil;
import org.rssb.phonetree.custom.controls.DecoratedTextField;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class TextFieldController extends AbstractController{
    @FXML
    private DecoratedTextField decoratedTextField;

    public void setText(String text){
        decoratedTextField.setText(text);
    }

    public String getText(){
        return CommonUtil.isEmptyOrNull(decoratedTextField.getText()) ? "" :decoratedTextField.getText();
    }

    public boolean isTextFieldEmpty(){
        return CommonUtil.isEmptyOrNull(decoratedTextField.getText());
    }
}
