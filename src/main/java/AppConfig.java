import adapter.db.PersistentExpensesRepository;
import adapter.http.Services;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.sitebricks.SitebricksModule;
import com.vercer.engine.persist.ObjectDatastore;
import com.vercer.engine.persist.annotation.AnnotationObjectDatastore;
import core.ExpensesRepository;

/**
 * Created on 15-5-5.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class AppConfig extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(

            new SitebricksModule() {
              @Override
              protected void configureSitebricks() {

                at("/rest").serve(Services.class);

              }
            },

            new AbstractModule() {
              @Override
              protected void configure() {

                bind(ExpensesRepository.class).to(PersistentExpensesRepository.class);
              }

              @Provides
              public ObjectDatastore provideDataStore() {
                return new AnnotationObjectDatastore();
              }
            });
  }


}

