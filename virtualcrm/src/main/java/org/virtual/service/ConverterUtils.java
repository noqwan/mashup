package org.virtual.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.virtual.service.exceptions.WrongDateFormatException;

public class ConverterUtils {

  public static Calendar toCalendarFromString(String string) throws WrongDateFormatException {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
      Date date = sdf.parse(string); // Parse string to Date
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar;
    } catch (Exception e) {
      throw new WrongDateFormatException(e);
    }
  }

  public static String toStringFromCalendar(Calendar calendar) {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    return sdf.format(calendar.getTime());
  }


}
