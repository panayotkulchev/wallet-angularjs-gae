package core;

import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class SidFetcher {

    private final Provider<HttpServletRequest> requestProvider;

    @Inject
    public SidFetcher(Provider<HttpServletRequest> requestProvider) {
        this.requestProvider = requestProvider;
    }

    public String fetch() {

        HttpServletRequest httpServletRequest = requestProvider.get();
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            return null;
        }

        for (Cookie each : cookies) {
            if (each.getName().equalsIgnoreCase("sid")) {
                return each.getValue();
            }
        }

        return null;
    }

}
