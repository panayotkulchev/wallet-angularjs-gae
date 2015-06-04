package adapter.http;

import adapter.http.validator.Validator;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;
import core.Session;
import core.UserRepository;

import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@At("/login")
@Show("login.html")
public class LoginPage {

  public String email;
  public String password;
  public String message;
  public String errorMessage;

  private final UserRepository userRepository;
  private final Validator validator;
  private final Session userSession;

  @Inject
  public LoginPage(UserRepository userRepository,
                   Validator validator,
                   Session userSession) {

    this.userRepository = userRepository;
    this.validator = validator;
    this.userSession = userSession;
  }


  @Post
  public String login() {

    List<String> errorList = validator.validateRequestParams();

    if (errorList.size() != 0) {
      return "/login?errorMessage=" + errorList.get(0);
    }

    boolean authorizationResult = userRepository.authorize(email, password);

    if (!authorizationResult) {
      return "/login?errorMessage=User do not exist!";
    }

    userSession.create(email);

    return "/wallet";

  }
}
