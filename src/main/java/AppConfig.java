import adapter.db.PersistentExpensesRepository;
import adapter.db.PersistentSessionRepository;
import adapter.db.PersistentUserRepository;
import adapter.http.LoginFilter;
import adapter.http.LoginPage;
import adapter.http.LogoutPage;
import adapter.http.RegisterPage;
import adapter.http.SecurityFilter;
import adapter.http.Services;
import adapter.http.UserSession;
import adapter.http.validator.ParamHolder;
import adapter.http.validator.RegexValidator;
import adapter.http.validator.RequestParamHolder;
import adapter.http.validator.Rule;
import adapter.http.validator.ValidationRule;
import adapter.http.validator.Validator;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;
import com.vercer.engine.persist.ObjectDatastore;
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore;
import core.CurrentUser;
import core.ExpensesRepository;
import core.Session;
import core.SessionRepository;
import core.SidFetcher;
import core.UserRepository;
import core.ValidationRules;

import java.util.List;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class AppConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(

            new ServletModule() {
              @Override
              protected void configureServlets() {

//                filter("/login").through(LoginFilter.class);
//                filter("/rest/*").through(SecurityFilter.class);
              }
            },

            new SitebricksModule() {
              @Override
              protected void configureSitebricks() {

                at("/rest").serve(Services.class);
                at("/logout").serve(LogoutPage.class);

                at("/register").show(RegisterPage.class);
                at("/login").show(LoginPage.class);
              }
            },

            new AbstractModule() {
              @Override
              protected void configure() {

                bind(ExpensesRepository.class).to(PersistentExpensesRepository.class);

                bind(UserRepository.class).to(PersistentUserRepository.class);
                bind(SessionRepository.class).to(PersistentSessionRepository.class);

                bind(ParamHolder.class).to(RequestParamHolder.class);
                bind(Session.class).to(UserSession.class);
                bind(Validator.class).to(RegexValidator.class);
              }

              @Provides
              public ObjectDatastore provideDataStore() {
                return new AnnotationObjectDatastore();
              }

              @Provides
              @RequestScoped
              public CurrentUser getCurrentUser(SidFetcher sidFetcher, UserRepository userRepository) {

                String sid = sidFetcher.fetch();

                return userRepository.getBySid(sid);
              }


              @Provides
              @Singleton
              @ValidationRules
              public List<Rule> getValidationRules() {

                List<Rule> rules = Lists.newArrayList();
                rules.add(new ValidationRule("email", "Email is not valid", "^[a-z]{3,30}+$"));
                rules.add(new ValidationRule("password", "Password is not valid", "^[a-z]{3,10}+$"));

                return rules;
              }

            });
  }


}

