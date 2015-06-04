package adapter.http;

import adapter.http.validator.Validator;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;
import core.UserRepository;

import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@At("/register")
@Show("register.html")
public class RegisterPage {

  public String email;
  public String password;
  public String message;
  public String errorMessage;
  public String clazz = "alert alert-danger";

  private final Validator validator;
  private final UserRepository userRepository;


  @Inject
  public RegisterPage(UserRepository userRepository,
                      Validator validator) {

    this.userRepository = userRepository;
    this.validator = validator;
  }

  @Post
  public String register() {

    List<String> errorList = validator.validateRequestParams();

    if (errorList.size() != 0) {
      return "/register?errorMessage=" + errorList.get(0);
    }

    if (userRepository.isExisting(email)) {
      return "/register?errorMessage=Email is already occupied!";
    }

    userRepository.register(email, password);

    userRepository.isExisting(email);

    return "/login?message=Congratulation! Now you can log in to the system.";
  }

}
