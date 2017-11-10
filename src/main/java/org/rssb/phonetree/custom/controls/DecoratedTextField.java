package org.rssb.phonetree.custom.controls;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomTextField;
import org.rssb.phonetree.common.CommonUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecoratedTextField extends CustomTextField {
    private Pattern phoneNumberPattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{4})");
    private Pattern tollPhoneNumberPattern = Pattern.compile("(\\d{1})(\\d{3})(\\d{3})(\\d{4})");
    private Pattern emailPattern = Pattern.compile("^[-+\\w]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-zA-Z]{2,})$");
    private String leftGlyphIconName;
    private String leftGlyphIconSize;
    private String rightGlyphIconName;
    private String rightGlyphIconSize;
    private String acceptedCharactersRegex="[0-9a-zA-Z]";
    private String maxLength;
    private String minLength;
    private int MAX_ALLOWANCE_AFTER_FORMAT = 0;
    private boolean isRequired;
    private String errorMessage;
    private PopOver popOver = new PopOver();
    private boolean isPhoneNumber;
    private boolean isEmail;
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
                showPopOver();
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

    public boolean isEmail() {
        return isEmail;
    }

    public void setEmail(boolean email) {
        isEmail = email;
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
        if (minLength <= 0 && isRequired()) {
            minLength = 1;//default
        }
        if (CommonUtil.isEmptyOrNull(this.getText()) && isRequired()) {
            return false;
        }
        if (CommonUtil.isEmptyOrNull(this.getText()) && !isRequired()) {
            return true;
        }
        int currentTextLength = this.getText().trim().length();
        if (currentTextLength >= minLength) {
            return true;
        }

        return false;
    }

    private void showPopOver() {
        if (!validate()) {
            Label label = new Label(prepareErrorMessage());
            VBox box = new VBox();
            box.setPadding(new Insets(10));
            box.getChildren().add(label);
            popOver.setContentNode(box);
            popOver.setAnimated(true);
            popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
            popOver.show(this);
        }else{
            hidePopover();
        }
    }

    public void showPopOver(String errorMessage){
        Label label = new Label(errorMessage);
        VBox box = new VBox();
        box.setPadding(new Insets(10));
        box.getChildren().add(label);
        popOver.setContentNode(box);
        popOver.setAnimated(true);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_CENTER);
        popOver.hide(Duration.seconds(10));
        popOver.show(this);
    }

    public boolean validate() {
        if(isEmail() && CommonUtil.isNotEmptyOrNull(this.getText())){
            Matcher matcher = emailPattern.matcher(this.getText());
            return matcher.matches();
        }

        if (isMinLengthMet()) {
            /*hidePopover();*/
            return true;
        }

        return false;
    }

    private String prepareMinimumLength() {
        int minLength = (isValid(getMinLength()) ? Integer.parseInt(getMinLength()) : -1);
        return (minLength <= 0 ? "" : "Minimum " + minLength + " characters are required");
    }

    private String prepareErrorMessage() {
        String msg = CommonUtil.isEmptyOrNull(errorMessage) ? "" : errorMessage;

        StringBuilder sb = new StringBuilder();
        if (CommonUtil.isNotEmptyOrNull(msg)) {
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
        if (CommonUtil.isNotEmptyOrNull(rightGlyphIconSize) && isValid(rightGlyphIconSize)) {
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
        if (CommonUtil.isNotEmptyOrNull(rightGlyphIconName)) {
            FontAwesomeIcon icon = FontAwesomeIcon.valueOf(rightGlyphIconName);
            rightGlyphIconView.setIcon(icon);
            Label label = new Label();
            label.setGraphic(rightGlyphIconView);
            label.getStyleClass().add("icon-background-label");
            if (getPreferredValue(getRightGlyphIconLabelHeight()) != 0) {
                label.setPrefHeight(getPreferredValue(getRightGlyphIconLabelHeight()));
                label.setPrefWidth(getPreferredValue(getRightGlyphIconLabelWidth()));
            }
            this.setRight(label);
        }
    }

    private double getPreferredValue(String value) {
        if (CommonUtil.isEmptyOrNull(value)) {
            return 0;
        }

        if (isValid(value)) {
            return Double.parseDouble(value);
        }

        return 0;
    }


    public String getLeftGlyphIconName() {
        return leftGlyphIconName;
    }

    public void setLeftGlyphIconName(String leftGlyphIconName) {
        this.leftGlyphIconName = leftGlyphIconName;
        if (CommonUtil.isNotEmptyOrNull(leftGlyphIconName)) {
            FontAwesomeIcon icon = FontAwesomeIcon.valueOf(leftGlyphIconName);
            leftGlyphIconView.setIcon(icon);
            Label label = new Label();
            label.setGraphic(leftGlyphIconView);
            if (getPreferredValue(getLeftGlyphIconLabelHeight()) != 0) {
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
        if (CommonUtil.isNotEmptyOrNull(leftGlyphIconSize) && isValid(leftGlyphIconSize)) {
            leftGlyphIconView.setSize(leftGlyphIconSize);
        }
    }

    public String getAcceptedCharactersRegex() {
        return acceptedCharactersRegex;
    }

    public void setAcceptedCharactersRegex(String acceptedCharactersRegex) {
        this.acceptedCharactersRegex = acceptedCharactersRegex;
        if(CommonUtil.isEmptyOrNull(this.acceptedCharactersRegex)){
            this.acceptedCharactersRegex="[0-9a-zA-Z\\s]";
        }
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
        if(CommonUtil.isEmptyOrNull(minLength)){
            this.minLength="0";
        }
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
        if(CommonUtil.isEmptyOrNull(maxLength)){
            this.maxLength="999";
        }
        if (isValid(maxLength)) {
            this.maxLengthAsInt = Integer.parseInt(this.maxLength);
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
        showPopOver();
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
        showPopOver();
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
        if (CommonUtil.isNotEmptyOrNull(acceptedCharactersRegex)) {
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

        if (isPhoneNumber()) {
            if (maxLengthAsInt <= 0) {
                setMinLength("10");
                setMaxLength("10");
            }

            if (maxLengthAsInt == 10) {
                MAX_ALLOWANCE_AFTER_FORMAT = 4;
            } else if (maxLengthAsInt == 11) {
                MAX_ALLOWANCE_AFTER_FORMAT = 5;
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

}
