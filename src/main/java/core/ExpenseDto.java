package core;

import com.vercer.engine.persist.annotation.Key;

/**
 * Created on 15-6-2.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class ExpenseDto {

  public Long id;
  public String type;
  public String amount;
  public String date;
  public String description;

  public ExpenseDto() {
  }

  public ExpenseDto(Long id, String type, String amount, String date, String description) {

    this.id = id;
    this.type = type;
    this.amount = amount;
    this.date = date;
    this.description = description;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Expense{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", amount='" + amount + '\'' +
            ", date='" + date + '\'' +
            ", description='" + description + '\'' +
            '}';
  }
}
