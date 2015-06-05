package adapter.db;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterator;
//import com.google.appengine.repackaged.com.google.api.client.util.Lists;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.vercer.engine.persist.ObjectDatastore;
import core.Config;
import core.SessionRepository;

/**
 * Created on 15-5-19.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class PersistentSessionRepository implements SessionRepository {

  private final ObjectDatastore datastore;

  @Inject
  public PersistentSessionRepository(ObjectDatastore datastore) {

    this.datastore = datastore;
  }

  @Override
  public void refresh(String sid, Long expirationTime) {

    Session session = datastore.load(Session.class,sid);
    session.expirationTime=expirationTime;
    datastore.update(session);
  }

  @Override
  public boolean isExisting(String sid) {

    return (datastore.load(Session.class, sid) != null);
  }

  @Override
  public void create(String userId, String sid) {

    Session session = new Session(sid, System.currentTimeMillis() + Config.SESSION_REFRESH_RATE, userId);

    datastore.store(session);

  }

  @Override
  public void cleanExpired() {

    final QueryResultIterator<Session> expiredSessions = datastore
            .find()
            .type(Session.class)
            .addFilter("expirationTime", FilterOperator.LESS_THAN, System.currentTimeMillis())
            .returnResultsNow();

    datastore.deleteAll(Lists.newArrayList(expiredSessions));

  }

  @Override
  public void delete(String sid) {

    datastore.delete(datastore.load(Session.class, sid));
  }

  @Override
  public Integer count() {
    return datastore.find().type(Session.class).countResultsNow();
  }
}
