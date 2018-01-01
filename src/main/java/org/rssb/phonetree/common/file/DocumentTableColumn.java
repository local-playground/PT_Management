package org.rssb.phonetree.common.file;

import java.math.BigInteger;

public enum DocumentTableColumn {
    SEQ_NO("Seq No") {
        @Override
        public String getColumnName() {
            return "Seq No";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(890); //0.62 inches
        }

        @Override
        public int getColumnOrder() {
            return 1;
        }

        @Override
        public boolean isSelected() {
            return true;
        }
    },
    FAMILY_INFORMATION("Family Information") {
        @Override
        public String getColumnName() {
            return "Family Information";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(2880); //2 inches
        }

        @Override
        public int getColumnOrder() {
            return 2;
        }

        @Override
        public boolean isSelected() {
            return true;
        }
    },
    TIME_OF_CALL("Time of Call") {
        @Override
        public String getColumnName() {
            return "Time of Call";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440); //1 inche
        }

        @Override
        public int getColumnOrder() {
            return 3;
        }

        @Override
        public boolean isSelected() {
            return true;
        }
    },
    TIME_OF_VM("Time of VM") {
        @Override
        public String getColumnName() {
            return "Time of VM";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440); //1 inche
        }

        @Override
        public int getColumnOrder() {
            return 4;
        }

        @Override
        public boolean isSelected() {
            return true;
        }
    },
    BUS_RIDE("Bus Ride") {
        @Override
        public String getColumnName() {
            return "Need Bus Ride";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440);
        }

        @Override
        public int getColumnOrder() {
            return 5;
        }

        @Override
        public boolean isSelected() {
            return false;
        }

    },
    NO_OF_PASSENGERS("No of passengers") {
        @Override
        public String getColumnName() {
            return "No. of passengers";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440);
        }

        @Override
        public int getColumnOrder() {
            return 6;
        }

        @Override
        public boolean isSelected() {
            return false;
        }
    },
    ZIP_CODE("Zip Code") {
        @Override
        public String getColumnName() {
            return "Zip Code";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440);
        }

        @Override
        public int getColumnOrder() {
            return 7;
        }

        @Override
        public boolean isSelected() {
            return false;
        }
    },
    NO_OF_ADULTS("No of adults") {
        @Override
        public String getColumnName() {
            return "No. of adults";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440);
        }

        @Override
        public int getColumnOrder() {
            return 8;
        }

        @Override
        public boolean isSelected() {
            return false;
        }
    },
    NO_OF_CHILDREN("No of children") {
        @Override
        public String getColumnName() {
            return "No. of children";
        }

        @Override
        public BigInteger getColumnWidth() {
            return BigInteger.valueOf(1440);
        }

        @Override
        public int getColumnOrder() {
            return 9;
        }

        @Override
        public boolean isSelected() {
            return false;
        }
    },
    COMMENTS("Comments") {
        @Override
        public String getColumnName() {
            return "Comments";
        }

        @Override
        public BigInteger getColumnWidth() {
            return null;
        }

        @Override
        public int getColumnOrder() {
            return 10;
        }

        @Override
        public boolean isSelected() {
            return true;
        }
    }
    ;

    private String type;

    DocumentTableColumn(String type) {
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public static DocumentTableColumn fromString(String text){
        for(DocumentTableColumn documentTableColumn:values()){
            if(documentTableColumn.type.equalsIgnoreCase(text)){
                return documentTableColumn;
            }
        }

        return null;
    }

    public abstract String getColumnName();

    public abstract BigInteger getColumnWidth();

    public abstract int getColumnOrder();

    public abstract boolean isSelected();
}
