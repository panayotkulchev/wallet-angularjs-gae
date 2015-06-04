package adapter.db;

import com.vercer.engine.persist.annotation.Key;

/**
 * Created on 15-5-19.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class Session {

  @Key
  public String sid;
  public Long expirationTime;
  public String userId;

  public Session() {
  }

  public Session(String sid, Long expirationTime, String userId) {
    this.sid = sid;
    this.expirationTime = expirationTime;
    this.userId = userId;
  }

  @Override
  public String toString() {
    return sid + " " + expirationTime;
  }
}
