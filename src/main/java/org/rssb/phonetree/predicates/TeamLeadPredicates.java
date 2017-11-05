package org.rssb.phonetree.predicates;


import javafx.scene.layout.StackPane;
import org.rssb.phonetree.entity.TeamLead;

import java.util.function.Function;
import java.util.function.Predicate;

public interface TeamLeadPredicates extends CommonPredicates{
    Predicate<String> isFormatFirstNameColumn = s -> s.equalsIgnoreCase("firstName");
    Predicate<String> isFormatLastNameColumn = s -> s.equalsIgnoreCase("lastName");
    Predicate<String> isFormatCellPhoneColumn = s -> s.equalsIgnoreCase("CellPhone");
    Predicate<String> isFormatHomePhoneColumn = s -> s.equalsIgnoreCase("HomePhone");

    Function<TeamLead, String> cellPhoneValueFunction = teamLead -> teamLead.getMember().getCellPhone();
    Function<TeamLead, String> homePhoneValueFunction = teamLead -> teamLead.getMember().getHomePhone();
    Function<TeamLead, String> firstNameValueFunction = teamLead -> teamLead.getMember().getFirstName();
    Function<TeamLead, String> lastNameValueFunction =  teamLead -> teamLead.getMember().getLastName();

    Function<TeamLead, StackPane> cellPhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(cellPhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<TeamLead, StackPane> homePhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(homePhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<TeamLead, StackPane> firstNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(firstNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<TeamLead, StackPane> lastNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(lastNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);
}
