package adapter.http;

import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.client.transport.Json;
import com.google.sitebricks.headless.Reply;
import com.google.sitebricks.headless.Request;
import com.google.sitebricks.headless.Service;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;
import core.Expense;
import core.ExpensesRepository;

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
  private Reply<List<Expense>> getAllExpences() {

    List<Expense> expenses = expensesRepository.retrieveAll();
    return Reply.with(expenses).as(Json.class);
  }

  @At("/put")
  @Post
  private Reply<?> add(Request request) {
    try {
      Expense expense = request.read(Expense.class).as(Json.class);
      expensesRepository.add(expense);
    } catch (Exception e) {
      return Reply.saying().error();
    }
    return Reply.saying().ok();

  }
}
