package adapter.http.validator;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface Rule {

    String name();

    boolean isValid(String value);

    String errorMessage();

}
