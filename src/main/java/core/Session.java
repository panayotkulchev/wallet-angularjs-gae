package core;

/**
 * Created by Panayot Kulchev on 15-5-11
 * e-mail: panayotkulchev@gmail.com
 * happy codding ...
 */

public interface Session {

//  public void create(Integer userId);

  public void create(String userId);

  public void refresh();

  public void delete();
}
