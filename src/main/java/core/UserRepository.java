package core;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface UserRepository {

  void register(String email, String password);

  boolean isExisting(String email);

  boolean authorize(String email, String password);

  CurrentUser getBySid(String sid);
}
