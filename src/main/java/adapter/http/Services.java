package adapter.http;

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
