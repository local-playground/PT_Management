package org.rssb.phonetree.predicates;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.rssb.phonetree.entity.Family;
import org.rssb.phonetree.entity.Member;

import java.util.function.Function;
import java.util.function.Predicate;

public interface MembersTablePredicates extends CommonPredicates{
     Predicate<String> isFormatCellPhoneColumn = s -> s.equalsIgnoreCase("CellPhone");
     Predicate<String> isFormatHomePhoneColumn = s -> s.equalsIgnoreCase("HomePhone");
     Predicate<String> isFormatWorkPhoneColumn = s -> s.equalsIgnoreCase("WorkPhone");
     Predicate<String> isFormatPriorityColumn = s -> s.equalsIgnoreCase("Priority");
     Predicate<String> isFormatOnCallingListColumn = s -> s.equalsIgnoreCase("OnCallingList");
     Predicate<String> isFormatFirstNameColumn = s -> s.equalsIgnoreCase("firstName");
     Predicate<String> isFormatLastNameColumn = s -> s.equalsIgnoreCase("lastName");
     Predicate<String> isFormatPreferredPhoneColumn = s -> s.equalsIgnoreCase("preferredPhoneType");
     Predicate<String> isFormatMemberIdColumn = s -> s.equalsIgnoreCase("memberId");
     Predicate<String> isFormatFamilyIdColumn = s -> s.equalsIgnoreCase("familyId");

     Function<Member, String> cellPhoneValueFunction = Member::getCellPhone;
     Function<Member, String> homePhoneValueFunction = Member::getHomePhone;
     Function<Member, String> workPhoneValueFunction = Member::getWorkPhone;
     Function<Member, String> priorityValueFunction = m -> String.valueOf(m.getPriority());
     Function<Member, String> onCallingListValueFunction = m -> m.getOnCallingList().toString();
     Function<Member, String> firstNameValueFunction = Member::getFirstName;
     Function<Member, String> lastNameValueFunction = Member::getLastName;
     Function<Member, String> preferredPhoneValueFunction = m -> String.valueOf(m.getPreferredPhoneType());
     Function<Member, String> memberIdValueFunction = m -> String.valueOf(m.getMemberId());
     Function<Member, String> familyIdValueFunction = m -> {
        Family family = m.getFamily();
        if (family != null) {
            return String.valueOf(m.getFamily().getFamilyId());
        }

        return "";
    };

     Function<Label, StackPane> onCallingListLabelFormatFunction = label -> {
        StackPane pane = new StackPane();
        FontAwesomeIconView phoneStatusIcon = new FontAwesomeIconView();
        phoneStatusIcon.setSize("32");
        if (label.getText().equalsIgnoreCase("YES")) {
            phoneStatusIcon.getStyleClass().add("ok");
            phoneStatusIcon.setIcon(FontAwesomeIcon.CHECK_CIRCLE);
        } else {
            phoneStatusIcon.getStyleClass().add("wrong-number");
            phoneStatusIcon.setIcon(FontAwesomeIcon.TIMES_CIRCLE);
        }
        pane.getChildren().addAll(phoneStatusIcon);
        return pane;
    };

     Function<Member, StackPane> cellPhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(cellPhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

     Function<Member, StackPane> homePhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(homePhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

     Function<Member, StackPane> workPhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(workPhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);


     Function<Member, StackPane> priorityLabelComposerFunction =
            backgroundLabelFunction
                    .compose(priorityValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(seqNumberLabelFormatFunction);

     Function<Member, StackPane> onCallingListLabelComposerFunction =
            backgroundLabelFunction
                    .compose(onCallingListValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(onCallingListLabelFormatFunction);

     Function<Member, StackPane> firstNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(firstNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

     Function<Member, StackPane> lastNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(lastNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

     Function<Member, StackPane> preferredPhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(preferredPhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

     Function<Member, StackPane> memberIdLabelComposerFunction =
            backgroundLabelFunction
                    .compose(memberIdValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

     Function<Member, StackPane> familyIdLabelComposerFunction =
            backgroundLabelFunction
                    .compose(familyIdValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

}
