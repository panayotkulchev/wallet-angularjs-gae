package adapter.http;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Delete;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import com.google.sitebricks.http.Put;
import core.Expense;
import core.ExpenseDto;
import core.ExpensesRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import java.util.Arrays;
import java.util.List;

/**
 * Created on 15-5-27.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

@Service
@At("/rest")

public class Services {

  private final ExpensesRepository expensesRepository;

  @Inject
  public Services(ExpensesRepository expensesRepository) {
    this.expensesRepository = expensesRepository;
  }

  @At("/get")
  @Get
  private Reply<List<ExpenseDto>> getAllExpences() {

    List<ExpenseDto> expensesDtos = Lists.newArrayList();
    List<Expense> expenses = expensesRepository.retrieveAll();

    for (Expense each : expenses) {
      expensesDtos.add(ObjectTransformer.transformToDto(each));
    }
    try { //TODO ONLY FOR DEMO REMOVE LTR
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Reply.with(expensesDtos).as(Json.class);
  }

  @At("/get/:id")
  @Get
  private Reply<?> getExpencesById(@Named("id") String id) {
    try {
      Expense expense = expensesRepository.retrieveById(Long.parseLong(id));
      ExpenseDto expenseDto = ObjectTransformer.transformToDto(expense);
      return Reply.with(expenseDto).as(Json.class);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return Reply.saying().error();
  }

  @At("/put")
  @Post
  private Reply<?> add(Request request) {
    try {

      ExpenseDto expenseDto = request.read(ExpenseDto.class).as(Json.class);
      Expense expense = ObjectTransformer.transformFromDto(expenseDto);
      expensesRepository.add(expense);

    } catch (Exception e) {
      return Reply.saying().error();
    }
    return Reply.saying().ok();
  }

//  @At("/put/:token")
//  @Post
//  private Reply<?> verifyToken(Request request,@Named("token") String token) {

//    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance()/*transport, jsonFactory*/)
//            .setAudience(Arrays.asList(CLIENT_ID))
//            .build();
//
//// (Receive idTokenString by HTTPS POST)
//
//    GoogleIdToken idToken = verifier.verify(idTokenString);
//    if (idToken != null) {
//      Payload payload = idToken.getPayload();
//      if (payload.getHostedDomain().equals(APPS_DOMAIN_NAME)
//              // If multiple clients access the backend server:
//              && Arrays.asList(ANDROID_CLIENT_ID, IOS_CLIENT_ID).contains(payload.getAuthorizedParty())) {
//        System.out.println("User ID: " + payload.getSubject());
//      } else {
//        System.out.println("Invalid ID token.");
//      }
//    } else {
//      System.out.println("Invalid ID token.");
//    }

//    return Reply.saying().ok();
//  }

  @At("/edit/:id")
  @Put
  private Reply<?> editExpenceById(Request request, @Named("id") String id) {
    try {

      ExpenseDto expenseDto = request.read(ExpenseDto.class).as(Json.class);
      Expense expense = ObjectTransformer.transformFromDto(expenseDto);
      expensesRepository.update(id,expense);

      return Reply.saying().ok();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return Reply.saying().error();
  }

  @At("/del/:id")
  @Delete
  private Reply<?> del(@Named("id") String id) {
    try {

      if (id.equals("all")) {
        expensesRepository.deleteAll();
      } else {
        expensesRepository.delete(Long.parseLong(id));
      }

    } catch (Exception e) {
      return Reply.saying().error();
    }
    return Reply.saying().ok();
  }
}
