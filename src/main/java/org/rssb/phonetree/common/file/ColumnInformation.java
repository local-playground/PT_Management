package org.rssb.phonetree.common.file;

import java.math.BigInteger;

public interface ColumnInformation {
    String getColumnName();
    BigInteger getColumnWidth();
    int getColumnOrder();
    boolean isSelected();
}
