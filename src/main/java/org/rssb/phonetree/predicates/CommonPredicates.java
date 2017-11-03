package org.rssb.phonetree.predicates;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public interface CommonPredicates {
    Function<Label, StackPane> wrapInStackPane = label -> {
        StackPane pane = new StackPane();
        pane.getChildren().add(label);
        return pane;
    };

    Function<Label, Label> transparentBackgroundLabelFunction = label -> {
        label.setStyle("-fx-background-color: transparent");
        return label;
    };

    Function<String, Label> backgroundLabelFunction = value -> {
        List<String> phoneNumbersList = Arrays.asList(value.split(","));
        Label label = new Label();
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("\n", phoneNumbersList));
        label.setText(sb.toString());
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    };

    Function<Label,StackPane> seqNumberLabelFormatFunction = label ->{
        StackPane pane = new StackPane();
        FontAwesomeIconView phoneStatusIcon = new FontAwesomeIconView(FontAwesomeIcon.CIRCLE);
        phoneStatusIcon.setSize("40");
        String value = label.getText();
        phoneStatusIcon.getStyleClass().add("seq-number-glyph-icon");

        Text text = new Text();
        text.setText(value);
        text.getStyleClass().add("seq-number-glyph-icon-text");
        pane.getChildren().addAll(phoneStatusIcon,text);
        return pane;
    };
}
