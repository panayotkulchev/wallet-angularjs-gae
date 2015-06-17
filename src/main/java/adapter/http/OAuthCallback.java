package adapter.http;

import com.clouway.Util;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import core.Session;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 15-6-17.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@At("/oauth-callback")
@Show("oauth-callback.html")
public class OAuthCallback {

  public String userInfo = "initValueForTest";

  private final Provider<HttpServletRequest> requestProvider;
  private final Session userSession;

  @Inject
  public OAuthCallback(Provider<HttpServletRequest> requestProvider, Session userSession) {
    this.requestProvider = requestProvider;
    this.userSession = userSession;
  }

  @Get
  public String getTheToken() throws IOException {

    HttpServletRequest request = requestProvider.get();

    String incomingState = request.getParameter("state");
    String expectedState = (String) request.getSession().getAttribute("oauth2-state");

    if (!StringUtils.equals(incomingState, expectedState)) {
//      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid state");
      return "/login?errorMessage=BAD_REQUEST Invalid state!";
    } //todo security tbd


    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
//    parameters.add(new BasicNameValuePair("client_id", "975229642235-slatste5to3kb3f0gv100qm27ril6jfs.apps.googleusercontent.com"));
    parameters.add(new BasicNameValuePair("client_id", "334412481728-odc16ekeohanqdqjiqng1b9finubf8gn.apps.googleusercontent.com"));
//    parameters.add(new BasicNameValuePair("client_secret", "o8RD7MMbADzTISr5eAl-HI99"));
    parameters.add(new BasicNameValuePair("client_secret", "D1ynyDCNClbuPxUZYZ1CGth7"));
//    parameters.add(new BasicNameValuePair("redirect_uri", request.getRequestURL().toString())); //add this
    parameters.add(new BasicNameValuePair("redirect_uri", "http://wallet-angularjs-gae.appspot.com/oauth-callback"));
    parameters.add(new BasicNameValuePair("code", request.getParameter("code")));

    String output = Util.sendHttpPost("https://accounts.google.com/o/oauth2/token", parameters);
    Map<String, String> values = Util.parseJson(output);

    if (values.size()==0) {
      return "/login?errorMessage=Please login!";
    }

    HttpGet get = new HttpGet("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=" + values.get("access_token"));
    userInfo = Util.sendHttpRequest(get);

    Map<String, String> userInfoMap = Util.parseJson(userInfo);
    String email = userInfoMap.get("email");

    userSession.create(email);
    return "/wallet"; //todo
  }
}
