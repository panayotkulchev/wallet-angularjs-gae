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

    DateFormat format = new SimpleDateFormat("MMM-dd-yyyy", Locale.US);
    long futureTime = 0;
    try {
      Date date = format.parse("May-29-2015");
        System.out.println(date);
      futureTime = date.getTime();
      System.out.println(futureTime);
    } catch (ParseException e) {
      System.out.println("error");
      }
  }
}
