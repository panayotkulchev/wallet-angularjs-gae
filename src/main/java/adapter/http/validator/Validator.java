package adapter.http.validator;

import java.util.List;

/**
 * Created on 15-5-18.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface Validator {

  List <String> validateRequestParams();

}
