package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {

    public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    public static Date convertStrToDate(String str) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        format1.setLenient(false);
        format2.setLenient(false);
        Date ret;
        try {
            if (str.contains("/")) {
                ret = format1.parse(str);
            } else {
                ret = format2.parse(str);
            }
            return ret;
        } catch (ParseException ex) {
            return null;
        }
    }
}
