package adapter.http;

import sun.rmi.runtime.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 15-5-28.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class maindellme {
  public static void main(String[] args) {

    DateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
    long futureTime = 0;
    long futureTime2 = 0;
    try {
      Date date = format.parse("29-Feb-2015");
      Date date2 = format.parse("29-April-2015");
        System.out.println(date);
        System.out.println(date2);
      futureTime = date.getTime();
      futureTime2 = date2.getTime();
      System.out.println(futureTime);
      System.out.println(futureTime2);
      System.out.println("-----------");
      Date date3 = new Date(Long.parseLong("1433192400000"));
      System.out.println(date3+"===");
      int day = date3.getDate();
      int monthIndex = date3.getMonth();
      int year = date3.getYear()+1900;
      String monthNames [] = {"January", "February", "March",
              "April", "May", "June", "July",
              "August", "September", "October",
              "November", "December"};
      System.out.println(
              day+"-"+monthNames[monthIndex+1]+"-"+year
      );
    } catch (ParseException e) {
      System.out.println("error");
      }
  }
}
