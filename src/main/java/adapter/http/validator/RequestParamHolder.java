package adapter.http.validator;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestParameters;

import java.util.Map;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class RequestParamHolder implements ParamHolder {

  private final Map<String, String[]> map;

  @Inject
  public RequestParamHolder(@RequestParameters Map<String, String[]> map) {
    this.map = map;
  }

  @Override
  public String param(String name) {
    return map.get(name)[0];
  }

}
