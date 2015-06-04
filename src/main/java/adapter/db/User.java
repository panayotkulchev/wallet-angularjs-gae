package adapter.db;

import com.vercer.engine.persist.annotation.Child;
import com.vercer.engine.persist.annotation.Key;

/**
 * Created on 15-5-19.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class User {

  @Key
  public String email;
  public String password;

  public User() {
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public User(String email, String password,Account account) {
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "USER:      email: " + email + " pass: " + password;
  }
}
