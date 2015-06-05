package adapter.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.SessionRepository;
import core.SidFetcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@Singleton
public class LoginFilter implements Filter {

  private final SessionRepository sessionRepository;
  private final SidFetcher sidFetcher;
  private final UserSession userSession;

  @Inject
  public LoginFilter(SessionRepository sessionRepository, SidFetcher sidFetcher, UserSession userSession) {
    this.sessionRepository = sessionRepository;
    this.sidFetcher = sidFetcher;
    this.userSession = userSession;
  }

  public void init(FilterConfig config) throws ServletException {
  }

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    System.out.println("LoginFilter");

    HttpServletResponse response = (HttpServletResponse) resp;

    String sid = sidFetcher.fetch();


        if (sid==null){
            chain.doFilter(req, resp);
            return;
        }

        if (!sessionRepository.isExisting(sid)){
            chain.doFilter(req, resp);
            return;
        }

        userSession.refresh();
        response.sendRedirect("/wallet");


//    if (sid == null) {
//      chain.doFilter(req, resp);
//      return;
//
//    } else {
//      if (!sessionRepository.isExisting(sid)) {
//        chain.doFilter(req, resp);
//        return;
//      }
//    }
//
//    userSession.refresh();
//    response.sendRedirect("/wallet");

  }

  public void destroy() {
  }

}
