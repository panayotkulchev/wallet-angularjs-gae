package adapter.http.validator;

import com.google.inject.Inject;
import core.ValidationRules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class RegexValidator implements Validator {

  private List<Rule> rules;
  private final ParamHolder paramHolder;

  @Inject
  public RegexValidator(@ValidationRules List<Rule> rules, ParamHolder paramHolder) {
    this.rules = rules;
    this.paramHolder = paramHolder;
  }

  @Override
  public List<String> validateRequestParams() {
    List<String> errorList = new ArrayList<String>() {{
    }};

    for (Rule each : rules) {

      String value = paramHolder.param(each.name());

      if (!each.isValid(value)) {
        errorList.add(each.errorMessage());
      }
    }
    return errorList;
  }

}
