package core;

import java.util.List;

/**
 * Created on 15-5-27.
 *
 * @author Panayot Kulchev <panayotkulchev@gmail.com>
 */

public interface ExpensesRepository {

  void add(Expense expense);

  Expense retrieveById(Long id);

  List<Expense> retrieveAll();

  void delete (Long id);

  void deleteAll ();

  void update(String id, Expense expense);
}
