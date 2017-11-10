package org.rssb.phonetree.predicates;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import org.rssb.phonetree.entity.Sevadar;

import java.util.function.Function;
import java.util.function.Predicate;

public interface SevadarPredicates  extends CommonPredicates{
    Predicate<String> isFormatFirstNameColumn = s -> s.equalsIgnoreCase("firstName");
    Predicate<String> isFormatLastNameColumn = s -> s.equalsIgnoreCase("lastName");
    Predicate<String> isFormatCellPhoneColumn = s -> s.equalsIgnoreCase("CellPhone");
    Predicate<String> isFormatHomePhoneColumn = s -> s.equalsIgnoreCase("HomePhone");
    Predicate<String> isFormatBackupTeamLead = s -> s.equalsIgnoreCase("BackupTeamLead");

    Function<Sevadar, String> cellPhoneValueFunction = sevadar -> sevadar.getMember().getCellPhone();
    Function<Sevadar, String> homePhoneValueFunction = sevadar -> sevadar.getMember().getHomePhone();
    Function<Sevadar, String> firstNameValueFunction = sevadar -> sevadar.getMember().getFirstName();
    Function<Sevadar, String> lastNameValueFunction =  sevadar -> sevadar.getMember().getLastName();
    Function<Sevadar, Integer> backupTeamLeadValueFunction =  sevadar -> sevadar.getIsBackupForTeamLead();

    Function<Integer, StackPane> backupTeamLeadLabelFormatFunction = (Integer value) -> {
        StackPane pane = new StackPane();
        FontAwesomeIconView backupTeamLeadIcon =new FontAwesomeIconView();
        backupTeamLeadIcon.setSize("32");
        String toolTip = null;
        if(value == 1){
            backupTeamLeadIcon.setIcon(FontAwesomeIcon.CHECK_CIRCLE);
            backupTeamLeadIcon.getStyleClass().add("ok");
            toolTip="Yes";
        }
        else{
            backupTeamLeadIcon.setIcon(FontAwesomeIcon.TIMES_CIRCLE);
            backupTeamLeadIcon.getStyleClass().add("wrong-number");
            toolTip="No";
        }
        Tooltip.install(backupTeamLeadIcon,new Tooltip(toolTip));
        pane.getChildren().addAll(backupTeamLeadIcon);
        return pane;
    };

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

    Function<Sevadar, StackPane> backupTeamLeadLabelComposerFunction =
            backupTeamLeadValueFunction
                    .andThen(backupTeamLeadLabelFormatFunction);

}
