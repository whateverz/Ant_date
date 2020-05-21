package lib.frame.utils;

public enum DateStyle {

    MM_DD("MM-dd"),
    YYYY_MM("yyyy-MM"),
    YYYY_MM_DD("yyyy-MM-dd"),
    MM_DD_HH_MM("MM-dd HH:mm"),
    MM_DD_HH_MM_SS("MM-dd HH:mm:ss"),
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

    MM_DD_EN("MM/dd"),
    YYYY_MM_EN("yyyy/MM"),
    YYYY_MM_DD_EN("yyyy/MM/dd"),
    MM_DD_HH_MM_EN("MM/dd HH:mm"),
    MM_DD_HH_MM_SS_EN("MM/dd HH:mm:ss"),
    YYYY_MM_DD_HH_MM_EN("yyyy/MM/dd HH:mm"),
    YYYY_MM_DD_HH_MM_SS_EN("yyyy/MM/dd HH:mm:ss"),

    MM_DD_CN("MM月dd日"),
    YYYY_MM_CN("yyyy年MM月"),
    YYYY_MM_DD_CN("yyyy年MM月dd日"),
    MM_DD_HH_MM_CN("MM月dd日 HH:mm"),
    MM_DD_HH_MM_SS_CN("MM月dd日 HH:mm:ss"),
    YYYY_MM_DD_HH_MM_CN("yyyy年MM月dd日 HH:mm"),
    YYYY_MM_DD_HH_MM_SS_CN("yyyy年MM月dd日 HH:mm:ss"),

    HH_MM("HH:mm"),
    HH_MM_SS("HH:mm:ss");


    private String value;

    DateStyle(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public enum Week {

        Mon("星期一", "Monday", "Mon.", 1),
        Tues("星期二", "Tuesday", "Tues.", 2),
        Wed("星期三", "Wednesday", "Wed.", 3),
        Thur("星期四", "Thursday", "Thur.", 4),
        Fri("星期五", "Friday", "Fri.", 5),
        Sat("星期六", "Saturday", "Sat.", 6),
        Sun("星期日", "Sunday", "Sun.", 7),;

        String name_cn;
        String name_en;
        String name_enShort;
        int number;

        Week(String name_cn, String name_en, String name_enShort, int number) {
            this.name_cn = name_cn;
            this.name_en = name_en;
            this.name_enShort = name_enShort;
            this.number = number;
        }

        public String getChineseName() {
            return name_cn;
        }

        public String getName() {
            return name_en;
        }

        public String getShortName() {
            return name_enShort;
        }

        public int getNumber() {
            return number;
        }
    }
}