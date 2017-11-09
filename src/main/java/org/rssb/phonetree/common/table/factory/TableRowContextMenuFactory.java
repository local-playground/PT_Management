package org.rssb.phonetree.common.table.factory;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.Map;

public class TableRowContextMenuFactory<T> implements Callback<TableView<T>, TableRow<T>> {
    private Map<String, EventHandler<ActionEvent>> contexteMenusAndActionMap;

    public TableRowContextMenuFactory(Map<String, EventHandler<ActionEvent>> contexteMenusAndActionMap) {
        this.contexteMenusAndActionMap = contexteMenusAndActionMap;
    }

    @Override
    public TableRow<T> call(TableView<T> param) {
        TableRow<T> row = new TableRow<>();
        ContextMenu contextMenu = new ContextMenu();
        for (Map.Entry<String, EventHandler<ActionEvent>> entry : contexteMenusAndActionMap.entrySet()) {
            MenuItem contextMenuItem = new MenuItem(entry.getKey());
            contextMenuItem.setOnAction(entry.getValue());
            contextMenu.getItems().add(contextMenuItem);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
        }
        return row;
    }
}