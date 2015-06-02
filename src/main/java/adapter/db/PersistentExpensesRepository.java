package adapter.db;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.vercer.engine.persist.ObjectDatastore;
import core.Counter;
import core.Expense;
import core.ExpensesRepository;

import java.util.List;

/**
 * Created on 15-5-27.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public class PersistentExpensesRepository implements ExpensesRepository {

  private final ObjectDatastore datastore;

  @Inject
  public PersistentExpensesRepository(ObjectDatastore datastore) {
    this.datastore = datastore;
    initExpensesCounterIfDoNotExist();
  }

  @Override
  public void add(Expense expense) {
    Counter counter = datastore.load(Counter.class, "expensesCounter");
    counter.increaseNumOfExpenses();
    expense.setId(counter.getNumOfExpences());

    datastore.update(counter);
    datastore.store(expense);
  }

  @Override
  public void edit(Expense expense) {

  }

  @Override
  public List<Expense> retrieveAll() {
    return Lists.newArrayList(datastore.find(Expense.class));
  }

  @Override
  public void delete(Long id) {
    Expense expense = datastore.load(Expense.class, id);
    datastore.delete(expense);
  }

  @Override
  public void deleteAll() {
    System.out.println("into delAll");
    datastore.deleteAll(Expense.class);
  }

  private void initExpensesCounterIfDoNotExist() {
    if (datastore.load(Counter.class, "expensesCounter") == null) {
      Counter counter = new Counter("expensesCounter", 0l);
      datastore.store(counter);
    }
  }
}
