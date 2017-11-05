package org.rssb.phonetree.predicates;


import javafx.scene.layout.StackPane;
import org.rssb.phonetree.entity.Sevadar;

import java.util.function.Function;
import java.util.function.Predicate;

public interface SevadarPredicates  extends CommonPredicates{
    Predicate<String> isFormatFirstNameColumn = s -> s.equalsIgnoreCase("firstName");
    Predicate<String> isFormatLastNameColumn = s -> s.equalsIgnoreCase("lastName");
    Predicate<String> isFormatCellPhoneColumn = s -> s.equalsIgnoreCase("CellPhone");
    Predicate<String> isFormatHomePhoneColumn = s -> s.equalsIgnoreCase("HomePhone");

    Function<Sevadar, String> cellPhoneValueFunction = sevadar -> sevadar.getMember().getCellPhone();
    Function<Sevadar, String> homePhoneValueFunction = sevadar -> sevadar.getMember().getHomePhone();
    Function<Sevadar, String> firstNameValueFunction = sevadar -> sevadar.getMember().getFirstName();
    Function<Sevadar, String> lastNameValueFunction =  sevadar -> sevadar.getMember().getLastName();

    Function<Sevadar, StackPane> cellPhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(cellPhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<Sevadar, StackPane> homePhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(homePhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<Sevadar, StackPane> firstNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(firstNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<Sevadar, StackPane> lastNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(lastNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);
}
