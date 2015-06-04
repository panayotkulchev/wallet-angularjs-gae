package adapter.db;

/**
 * Created on 15-5-19.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class Account {

  public Long amount=0l;
//  @Parent
//  public User user=null;


  public Account() {
  }

  public Account(Long amount, User user) {
    this.amount = amount;
//    this.user = user;
  }

  public Account(Long amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return amount.toString();
  }
}
