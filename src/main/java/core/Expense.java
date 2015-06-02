package core;

import com.vercer.engine.persist.annotation.Key;

/**
 * Created on 15-5-27.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class Expense {

  @Key
  public Long id;
  public String type;
  public String amount;
  public String date;
  public String description;

  public Expense() {
  }

  public Expense(Long id, String type, String amount, String date, String description) {

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
