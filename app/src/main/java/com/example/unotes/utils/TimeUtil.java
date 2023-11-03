package com.example.unotes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    protected String getCreateTimeAsString(String parttern) {
        String s = new SimpleDateFormat(parttern).toString();
        return s.format(String.valueOf(new Date()));
    }

    public String getCreateTimeAsString() {
        return getCreateTimeAsString("HH:mm");
    }

    public String getCreateDateAsString() {
        return getCreateTimeAsString("YYYY-MM-dd");
    }
    //格式化时间
    public static String getTimes(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        return format.format(date);
    }

}
