package core;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface SessionRepository {

    void refresh(String sid, Long expirationTime);

    boolean isExisting(String sid);

    void create(String userId, String sid);

    void cleanExpired();

    void delete(String sid);

    Integer count();

}
