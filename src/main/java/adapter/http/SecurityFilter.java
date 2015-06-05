package adapter.http;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import core.Config;
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
public class SecurityFilter implements Filter {

  private final SessionRepository sessionRepository;
  private final SidFetcher sidFetcher;
  private final UserSession userSession;

  @Inject
  public SecurityFilter(SessionRepository sessionRepository,
                        SidFetcher sidFetcher,
                        UserSession userSession) {

    this.sessionRepository = sessionRepository;
    this.sidFetcher = sidFetcher;
    this.userSession = userSession;
  }

  public void init(FilterConfig config) throws ServletException {
  }

  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
    //todo session clean should not be here
    sessionRepository.cleanExpired();

    HttpServletResponse response = (HttpServletResponse) resp;
    String sid = sidFetcher.fetch();


    if (sid == null) {
      response.sendError(response.SC_UNAUTHORIZED, "you are not authorized");
      return;
    }

    if (!sessionRepository.isExisting(sid)) {
      response.sendError(response.SC_UNAUTHORIZED, "you are not authorized");
      return;
    }

    userSession.refresh();
    chain.doFilter(req, resp);
  }

  public void destroy() {
  }
}
