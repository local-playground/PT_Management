package org.rssb.phonetree.predicates;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import org.rssb.phonetree.domain.CalledFamilyDetails;
import org.rssb.phonetree.entity.emums.CallStatus;

import java.util.function.Function;
import java.util.function.Predicate;

public interface CalledFamilyDetailsPredicates extends CommonPredicates{
     Predicate<String> isFormatCellPhoneColumn = s -> s.equalsIgnoreCase("CellPhone");
     Predicate<String> isFormatHomePhoneColumn = s -> s.equalsIgnoreCase("HomePhone");
     Predicate<String> isFormatWorkPhoneColumn = s -> s.equalsIgnoreCase("WorkPhone");
     Predicate<String> isFormatSequenceNumberColumn = s -> s.equalsIgnoreCase("familySeqNumber");
     Predicate<String> isFormatFirstNameColumn = s -> s.equalsIgnoreCase("firstName");
     Predicate<String> isFormatLastNameColumn = s -> s.equalsIgnoreCase("lastName");
     Predicate<String> isFormatZipCodeColumn = s -> s.equalsIgnoreCase("zipCode");
     Predicate<String> isFormatMemberIdColumn = s -> s.equalsIgnoreCase("memberId");
     Predicate<String> isFormatFamilyIdColumn = s -> s.equalsIgnoreCase("familyId");
     Predicate<String> isFormatPhoneStatusColumn = s -> s.equalsIgnoreCase("callStatus");


     Function<CalledFamilyDetails, String> cellPhoneValueFunction = CalledFamilyDetails::getCellPhone;
     Function<CalledFamilyDetails, String> homePhoneValueFunction = CalledFamilyDetails::getHomePhone;
     Function<CalledFamilyDetails, String> workPhoneValueFunction = CalledFamilyDetails::getWorkPhone;
     Function<CalledFamilyDetails, String> firstNameValueFunction = CalledFamilyDetails::getFirstName;
     Function<CalledFamilyDetails, String> lastNameValueFunction = CalledFamilyDetails::getLastName;
     Function<CalledFamilyDetails, String> zipCodeValueFunction = cfd -> cfd.getZipCode();
     Function<CalledFamilyDetails, String> memberIdValueFunction = cfd -> String.valueOf(cfd.getMemberId());
     Function<CalledFamilyDetails, String> familyIdValueFunction = cfd -> String.valueOf(cfd.getFamilyId());
     Function<CalledFamilyDetails, String> familySequenceNumberValueFunction = cfd -> String.valueOf(cfd.getFamilySeqNumber());
     Function<CalledFamilyDetails, CallStatus> phoneStatusValueFunction = cfd -> cfd.getCallStatus();


    Function<CallStatus, StackPane> phoneStatusLabelFormatFunction = (CallStatus callStatus) -> {
        StackPane pane = new StackPane();
        FontAwesomeIconView phoneStatusIcon =new FontAwesomeIconView();
        phoneStatusIcon.setSize("32");
        if(callStatus == CallStatus.OK){
            phoneStatusIcon.setIcon(FontAwesomeIcon.CHECK_CIRCLE);
            phoneStatusIcon.getStyleClass().add("ok");
        }
        if(callStatus== CallStatus.WRONG_NUMBER){
            phoneStatusIcon.setIcon(FontAwesomeIcon.TIMES_CIRCLE);
            phoneStatusIcon.getStyleClass().add("wrong-number");
        }
        if(callStatus== CallStatus.DISCONNECTED){
            phoneStatusIcon.setIcon(FontAwesomeIcon.TIMES_CIRCLE);
            phoneStatusIcon.getStyleClass().add("disconnected");
        }
        if(callStatus== CallStatus.REMOVE_REQUEST){
            phoneStatusIcon.setIcon(FontAwesomeIcon.CUT);
            phoneStatusIcon.getStyleClass().add("remove-me");
        }
        if(callStatus== CallStatus.NOT_PICKED){
            phoneStatusIcon.setIcon(FontAwesomeIcon.TTY);
            phoneStatusIcon.getStyleClass().add("not-picked");
        }
        if(callStatus== CallStatus.MOVED){
            phoneStatusIcon.setIcon(FontAwesomeIcon.TRUCK);
            phoneStatusIcon.getStyleClass().add("moved");
        }
        Tooltip.install(phoneStatusIcon,new Tooltip(callStatus.toString()));
        pane.getChildren().addAll(phoneStatusIcon);
        return pane;
    };


    Function<CalledFamilyDetails, StackPane> cellPhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(cellPhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<CalledFamilyDetails, StackPane> homePhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(homePhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<CalledFamilyDetails, StackPane> workPhoneLabelComposerFunction =
            backgroundLabelFunction
                    .compose(workPhoneValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<CalledFamilyDetails, StackPane> firstNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(firstNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<CalledFamilyDetails, StackPane> lastNameLabelComposerFunction =
            backgroundLabelFunction
                    .compose(lastNameValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);
    Function<CalledFamilyDetails, StackPane> memberIdLabelComposerFunction =
            backgroundLabelFunction
                    .compose(memberIdValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<CalledFamilyDetails, StackPane> familyIdLabelComposerFunction =
            backgroundLabelFunction
                    .compose(familyIdValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<CalledFamilyDetails, StackPane> zipCodeLabelComposerFunction =
            backgroundLabelFunction
                    .compose(zipCodeValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(wrapInStackPane);

    Function<CalledFamilyDetails, StackPane> familySeqNumberLabelComposerFunction =
            backgroundLabelFunction
                    .compose(familySequenceNumberValueFunction)
                    .andThen(transparentBackgroundLabelFunction)
                    .andThen(seqNumberLabelFormatFunction);

    Function<CalledFamilyDetails, StackPane> phoneStatusLabelComposerFunction =
                     phoneStatusValueFunction
                    .andThen(phoneStatusLabelFormatFunction);


}
