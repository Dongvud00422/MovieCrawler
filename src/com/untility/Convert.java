package com.untility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {
    public static long dateTimeToMilisecond(String myDate) throws ParseException {
myDate ="30/12 08:50";
        myDate = new Date().toString().split(" ")[5]+"/"+myDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyy/dd/MM HH:mm");
        Date date = sdf.parse(myDate);
        return date.getTime();
    }
}
