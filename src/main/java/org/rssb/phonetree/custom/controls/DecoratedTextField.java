package org.rssb.phonetree.custom.controls;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecoratedTextField extends CustomTextField {
    private Pattern phoneNumberPattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{4})");
    private Pattern tollPhoneNumberPattern = Pattern.compile("(\\d{1})(\\d{3})(\\d{3})(\\d{4})");
    private String leftGlyphIconName;
    private String leftGlyphIconSize;
    private String rightGlyphIconName;
    private String rightGlyphIconSize;
    private String acceptedCharactersRegex;
    private String maxLength;
    private String minLength;
    private int MAX_ALLOWANCE_AFTER_FORMAT = 0;
    private boolean isRequired;
    private String errorMessage;
    private PopOver popOver = new PopOver();
    private boolean isPhoneNumber;
    private String leftGlyphIconLabelHeight;
    private String rightGlyphIconLabelHeight;
    private String leftGlyphIconLabelWidth;
    private String rightGlyphIconLabelWidth;

    private int maxLengthAsInt = 0;

    private FontAwesomeIconView leftGlyphIconView = new FontAwesomeIconView();
    private FontAwesomeIconView rightGlyphIconView = new FontAwesomeIconView();

    public DecoratedTextField() {
        getStyleClass().add("decorated-text-field");
        //getStylesheets().add(DecoratedTextField.class.getResource("/css/styles.css").toExternalForm());
        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                validate();
            }
        });
    }

    public String getLeftGlyphIconLabelHeight() {
        return leftGlyphIconLabelHeight;
    }

    public void setLeftGlyphIconLabelHeight(String leftGlyphIconLabelHeight) {
        this.leftGlyphIconLabelHeight = leftGlyphIconLabelHeight;
    }

    public String getRightGlyphIconLabelHeight() {
        return rightGlyphIconLabelHeight;
    }

    public void setRightGlyphIconLabelHeight(String rightGlyphIconLabelHeight) {
        this.rightGlyphIconLabelHeight = rightGlyphIconLabelHeight;
    }

    public String getLeftGlyphIconLabelWidth() {
        return leftGlyphIconLabelWidth;
    }

    public void setLeftGlyphIconLabelWidth(String leftGlyphIconLabelWidth) {
        this.leftGlyphIconLabelWidth = leftGlyphIconLabelWidth;
    }

    public String getRightGlyphIconLabelWidth() {
        return rightGlyphIconLabelWidth;
    }

    public void setRightGlyphIconLabelWidth(String rightGlyphIconLabelWidth) {
        this.rightGlyphIconLabelWidth = rightGlyphIconLabelWidth;
    }

    private void hidePopover() {
        if (popOver.isShowing()) {
            popOver.hide();
        }
    }

    private boolean isMinLengthMet() {
        int minLength = (isValid(getMinLength()) ? Integer.parseInt(getMinLength()) : -1);

        if (minLength<=0 && isRequired()) {
            minLength=1;//default
        }else if(minLength<=0){
            return true;
        }

        if(isNull(this.getText())){
            return false;
        }

        int currentTextLength = this.getText().trim().length();

        if(currentTextLength>=minLength){
            return true;
        }

        return false;
    }

    private void showPopOver() {
        if (isRequired() || !isMinLengthMet()) {
            Label label = new Label(prepareErrorMessage());
            VBox box = new VBox();
            box.setPadding(new Insets(10));
            box.getChildren().add(label);
            popOver.setContentNode(box);
            popOver.show(this);
        }
    }

    public void validate(){
        if(isRequired() && isMinLengthMet()){
            hidePopover();
            return;
        }

        //not required but if entered, enter minlength
        if(isMinLengthMet()){
            hidePopover();
            return;
        }


        showPopOver();
    }

    private String prepareMinimumLength(){
        int minLength = (isValid(getMinLength()) ? Integer.parseInt(getMinLength()) : -1);
        return (minLength<=0?"":"Minimum "+minLength+" characters are required");
    }

    private String prepareErrorMessage(){
        String msg = isNull(errorMessage)?"":errorMessage;

        StringBuilder sb = new StringBuilder();
        if(!isNull(msg)) {
            sb.append(errorMessage).append("\n");
        }
        sb.append(prepareMinimumLength());

        return sb.toString();
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRightGlyphIconSize() {
        return rightGlyphIconSize;
    }

    public void setRightGlyphIconSize(String rightGlyphIconSize) {
        this.rightGlyphIconSize = rightGlyphIconSize;
        if (!isNull(rightGlyphIconSize) && isValid(rightGlyphIconSize)) {
            rightGlyphIconView.setSize(rightGlyphIconSize);
        }
    }

    private boolean isValid(String size) {
        try {
            Integer.parseInt(size);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


    public String getRightGlyphIconName() {
        return rightGlyphIconName;
    }

    public void setRightGlyphIconName(String rightGlyphIconName) {
        this.rightGlyphIconName = rightGlyphIconName;
        if (!isNull(rightGlyphIconName)) {
            FontAwesomeIcon icon = FontAwesomeIcon.valueOf(rightGlyphIconName);
            rightGlyphIconView.setIcon(icon);
            Label label = new Label();
            label.setGraphic(rightGlyphIconView);
            label.getStyleClass().add("icon-background-label");
            if(getPreferredValue(getRightGlyphIconLabelHeight())!=0) {
                label.setPrefHeight(getPreferredValue(getRightGlyphIconLabelHeight()));
                label.setPrefWidth(getPreferredValue(getRightGlyphIconLabelWidth()));
            }
            this.setRight(label);
        }
    }

    private double getPreferredValue(String value){
        if(isNull(value)){
            return 0;
        }

        if(isValid(value)){
            return Double.parseDouble(value);
        }

        return 0;
    }


    public String getLeftGlyphIconName() {
        return leftGlyphIconName;
    }

    public void setLeftGlyphIconName(String leftGlyphIconName) {
        this.leftGlyphIconName = leftGlyphIconName;
        if (!isNull(leftGlyphIconName)) {
            FontAwesomeIcon icon = FontAwesomeIcon.valueOf(leftGlyphIconName);
            leftGlyphIconView.setIcon(icon);
            Label label = new Label();
            label.setGraphic(leftGlyphIconView);
            if(getPreferredValue(getLeftGlyphIconLabelHeight())!=0) {
                label.setPrefHeight(getPreferredValue(getLeftGlyphIconLabelHeight()));
                label.setPrefWidth(getPreferredValue(getLeftGlyphIconLabelWidth()));
            }
            label.getStyleClass().add("icon-background-label");
            this.setLeft(label);
        }
    }


    public String getLeftGlyphIconSize() {
        return leftGlyphIconSize;
    }

    public void setLeftGlyphIconSize(String leftGlyphIconSize) {
        this.leftGlyphIconSize = leftGlyphIconSize;
        if (!isNull(leftGlyphIconSize) && isValid(leftGlyphIconSize)) {
            leftGlyphIconView.setSize(leftGlyphIconSize);
        }
    }

    public String getAcceptedCharactersRegex() {
        return acceptedCharactersRegex;
    }

    public void setAcceptedCharactersRegex(String acceptedCharactersRegex) {
        this.acceptedCharactersRegex = acceptedCharactersRegex;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
        if (!isNull(maxLength) && isValid(maxLength)) {
            this.maxLengthAsInt = Integer.parseInt(maxLength);
        }
    }

    @Override
    public void replaceText(int start, int end, String text) {
        boolean isCharacterIgnoredByLength = controlCharactersLimit(start, end, text);
        boolean isCharacterIgnoredByFormat = controlAcceptedFormat(start, end, text);

        if (isCharacterIgnoredByLength || isCharacterIgnoredByFormat) {
            super.replaceText(start, end, "");
            return;
        }

        if (isPhoneNumber()) {
            int position = removeFormattedCharacters();
            if (position != 0) {
                start = position;
                end = position;
            }
        }
        super.replaceText(start, end, text);
        validate();

        applyPhonePattern();
    }


    private int removeFormattedCharacters() {
        if (this.getText().indexOf('(') != -1) {
            this.setText(this.getText().replaceAll("[(\\s)-]", ""));
            this.requestFocus();
            this.end();
            return this.getText().length();
        }

        return 0;
    }

    private void applyPhonePattern() {
        if (isPhoneNumber()) {
            Matcher matcher = tollPhoneNumberPattern.matcher(this.getText());
            if (matcher.matches()) {
                this.setText(matcher.group(1) + " " + "(" + matcher.group(2) + ")-" + matcher.group(3) + "-" + matcher.group(4));
                this.requestFocus();
                this.end();
            }
        }
        if (isPhoneNumber()) {
            Matcher matcher = phoneNumberPattern.matcher(this.getText());
            if (matcher.matches()) {
                this.setText("(" + matcher.group(1) + ")-" + matcher.group(2) + "-" + matcher.group(3));
                this.requestFocus();
                this.end();
            }
        }
    }


    @Override
    public void deleteText(int start, int end) {
        super.deleteText(start, end);
        int caretPostion = this.getCaretPosition();
        if (isPhoneNumber()) {
            removeFormattedCharacters();
            this.positionCaret(caretPostion);
            applyPhonePattern();
        }
    }

    public boolean isPhoneNumber() {
        return isPhoneNumber;
    }

    public void setPhoneNumber(boolean phoneNumber) {
        isPhoneNumber = phoneNumber;
    }


    private boolean controlAcceptedFormat(int start, int end, String text) {
        if (!isNull(acceptedCharactersRegex)) {
            if (text.matches(acceptedCharactersRegex)) {
                return false;
            }
        }

        return true;
    }

    private boolean controlCharactersLimit(int start, int end, String text) {
        if (maxLengthAsInt <= 0) {
            // Default behavior, in case of no max length
            return false;
        }
        // Get the text in the textfield, before the user enters something
        String currentText = this.getText() == null ? "" : this.getText();
        // Compute the text that should normally be in the textfield now
        String finalText = currentText.substring(0, start) + text + currentText.substring(end);

        if(isPhoneNumber()){
            if(maxLengthAsInt<=0){
                setMinLength("10");
                setMaxLength("10");
            }

            if(maxLengthAsInt==10){
                MAX_ALLOWANCE_AFTER_FORMAT=4;
            }else if(maxLengthAsInt==11){
                MAX_ALLOWANCE_AFTER_FORMAT=5;
            }
        }
        // If the max length is not excedeed
        int numberOfexceedingCharacters = finalText.length() - (maxLengthAsInt + MAX_ALLOWANCE_AFTER_FORMAT);
        if (numberOfexceedingCharacters <= 0) {
            // Normal behavior
            return false;
        }

        return true;
    }

    private boolean isNull(String str) {
        if (str == null || str.trim().length() == 0)
            return true;

        return false;
    }

}
