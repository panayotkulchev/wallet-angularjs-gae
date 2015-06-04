package adapter.db;

import com.google.inject.Inject;
import com.vercer.engine.persist.ObjectDatastore;
import core.CurrentUser;
import core.UserRepository;

/**
 * Created on 15-5-19.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class PersistentUserRepository implements UserRepository {

  private final ObjectDatastore datastore;

  @Inject
  public PersistentUserRepository(ObjectDatastore datastore) {
    this.datastore = datastore;
  }

  @Override
  public void register(String email, String password) {
    User user = new User(email, password);
    datastore.store(user);
  }

  @Override
  public boolean isExisting(String email) {

    if (datastore.load(User.class, email) == null) {
      return false;
    }
    return true;
  }

  @Override
  public boolean authorize(String email, String password) {

    User user = datastore.load(User.class, email);
    return user != null && user.email.equals(email) && user.password.equals(password);

  }

  @Override
  public CurrentUser getBySid(String sid) {

    Session session = datastore.load(Session.class, sid);
    User user = datastore.load(User.class, session.userId);

    return new CurrentUser(user.email);
  }
}
