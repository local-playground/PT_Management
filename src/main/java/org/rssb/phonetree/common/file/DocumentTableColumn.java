package org.rssb.phonetree.common.file;

import java.math.BigInteger;

public enum DocumentTableColumn {
    SEQ_NO {
        @Override
        public String getColumnName() {
            return "Seq No";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(890); //0.62 inches
        }
    },
    FAMILY_INFORMATION {
        @Override
        public String getColumnName() {
            return "Family Information";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(2880); //2 inches
        }
    },
    TIME_OF_CALL {
        @Override
        public String getColumnName() {
            return "Time of Call";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440); //1 inche
        }
    },
    TIME_OF_VM {
        @Override
        public String getColumnName() {
            return "Time of VM";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440); //1 inche
        }
    },
    COMMENTS {
        @Override
        public String getColumnName() {
            return "Comments";
        }

        @Override
        public BigInteger getColumnWidth() {
            return null;
        }
    };

    public abstract String getColumnName();
    public abstract BigInteger getColumnWidth();
}
