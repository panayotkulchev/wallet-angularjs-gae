package core;

import java.util.List;

/**
 * Created on 15-5-27.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface ExpensesRepository {

  void add(Expense expense);

  void edit(Expense expense);

  List<Expense> retrieveAll();

  void delete (Long id);

}
