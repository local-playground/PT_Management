package org.rssb.phonetree.custom.controls;


import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import org.rssb.phonetree.entity.Sevadar;

public class SevadarComboBoxFormatter extends StringConverter<Sevadar> {

    private ObservableList<Sevadar> sevadarObservableList;

    public SevadarComboBoxFormatter(ObservableList<Sevadar> sevadarObservableList) {
        this.sevadarObservableList = sevadarObservableList;
    }

    @Override
    public String toString(Sevadar sevadar) {
        return sevadar.getSevadarName();
    }

    @Override
    public Sevadar fromString(String sevadarName) {
        return sevadarObservableList
                .stream()
                .filter(sevadar -> sevadar.getSevadarName().equalsIgnoreCase(sevadarName))
                .findFirst().get();

    }
}
