package adapter.http;

import core.Expense;
import core.ExpenseDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 15-6-2.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class ObjectTransformer {

  public static Expense transformFromDto(ExpenseDto expenseDto) {

    try {
      Date date = new SimpleDateFormat("dd-MMM-yyyy", Locale.US).parse(expenseDto.date);
      return new Expense(expenseDto.id, expenseDto.type, expenseDto.amount, date.getTime(), expenseDto.description);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ExpenseDto transformToDto(Expense expense) {
    // todo change depricated things
    String dateAsString = "";
    String monthNames[] = {"January", "February", "March",
            "April", "May", "June", "July",
            "August", "September", "October",
            "November", "December"};
    Date date = new Date(expense.date);
    int day = date.getDate();
    int monthIndex = date.getMonth();
    int year = date.getYear() + 1900;

    if (day < 11) {
      dateAsString = "0" + day + "-" + monthNames[monthIndex] + "-" + year;
    } else {
      dateAsString = day + "-" + monthNames[monthIndex] + "-" + year;
    }
    return new ExpenseDto(expense.id, expense.type, expense.amount, dateAsString, expense.description);
  }

}
