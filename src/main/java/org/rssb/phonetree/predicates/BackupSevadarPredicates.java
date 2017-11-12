package org.rssb.phonetree.predicates;

import javafx.scene.layout.StackPane;
import org.rssb.phonetree.entity.BackupSevadar;

import java.util.function.Function;
import java.util.function.Predicate;

public interface BackupSevadarPredicates extends CommonPredicates{
    Predicate<String> isFormatFirstNameColumn = s -> s.equalsIgnoreCase("firstName");
    Predicate<String> isFormatLastNameColumn = s -> s.equalsIgnoreCase("lastName");
    Predicate<String> isFormatCellPhoneColumn = s -> s.equalsIgnoreCase("cellPhone");
    Predicate<String> isFormatAssignedToColumn = s -> s.equalsIgnoreCase("assignedTo");

    Function<BackupSevadar, String> cellPhoneValueFunction = backupSevadar -> backupSevadar.getMember().getCellPhone();
    Function<BackupSevadar, String> assignedToValueFunction = backupSevadar -> backupSevadar.getAssignedToTeamLead();
    Function<BackupSevadar, String> firstNameValueFunction = backupSevadar -> backupSevadar.getMember().getFirstName();
    Function<BackupSevadar, String> lastNameValueFunction =  backupSevadar -> backupSevadar.getMember().getLastName();

    Function<BackupSevadar, StackPane> cellPhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(cellPhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<BackupSevadar, StackPane> assignedToLabelComposerFunction =
            backgroundLabelFunction
                    .compose(assignedToValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<BackupSevadar, StackPane> firstNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(firstNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<BackupSevadar, StackPane> lastNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(lastNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);
}
