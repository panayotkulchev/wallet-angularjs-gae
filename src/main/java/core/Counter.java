package core;

import com.vercer.engine.persist.annotation.Key;

/**
 * Created on 15-5-27.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class Counter {

  @Key
  private String id;
  private Long numOfExpences;

  public Counter() {
  }

  public Counter(String id, Long numOfExpences) {
    this.id = id;
    this.numOfExpences = numOfExpences;
  }

  public Long getNumOfExpences() {
    return numOfExpences;
  }

  public void decreaseNumOfExpenses(){
    this.numOfExpences--;
  }

  public void increaseNumOfExpenses(){
    this.numOfExpences++;
  }

  @Override
  public String toString() {
    return "Counter{" +
            "id='" + id + '\'' +
            ", numOfExpences=" + numOfExpences +
            '}';
  }
}
