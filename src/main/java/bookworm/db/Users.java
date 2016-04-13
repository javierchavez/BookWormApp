package bookworm.db;


import bookworm.db.backends.Backend;
import bookworm.io.exceptions.NotImplementedException;
import bookworm.model.User;
import spark.Request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashSet;


public class Users extends Transaction<User>
{
  private static ArrayList<User> cache = new ArrayList<>();
  private static Hashtable<String, User> loggedIn = new Hashtable<>();
  private boolean dirty = true;

  public Users (Backend db)
  {
    super(db);
  }

  @Override
  public ArrayList<User> getAll ()
  {
    ResultSet results = getDb().select("user", "");
    if (!dirty)
    {
      return cache;
    }
    cache.clear();
    try
    {

      while(results.next())
      {
        cache.add(initUser(results));
      }


      results.close();
      dirty = false;
      return cache;
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public <V> User get (V user)
  {
    if (!dirty)
    {
      for (User _user : cache)
      {
        if(_user.equals(user))
        {
          return _user;
        }
      }
    }

    ResultSet results = getDb().select("user", "where username='" + ((User) user).getUsername() + "'");
    try
    {
      int i =results.getFetchSize();
      if (i > 0)
      {
        if (results.first())
        {
          return initUser(results);
        }
      }
      results.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public <V> User create (V data)
  {
    dirty = true;
    User user = ((User) data);
    LinkedHashSet<Object> values = new LinkedHashSet<>();
    values.add(user.getUsername());
    values.add(user.getPassword());
    values.add(user.getSalt());

    LinkedHashSet<String> cols = new LinkedHashSet<>();
    cols.add("username");
    cols.add("password");
    cols.add("salt");

    getDb().insert("user",cols, values);
    return user;
  }

  @Override
  public <V> void delete (V data)
  {
    dirty = true;
    throw new NotImplementedException();
  }

  @Override
  public <V> void update (V username, User data)
  {
    dirty = true;
    throw new NotImplementedException();
  }


  public User isLoggedIn(Request request)
  {
    return loggedIn.get(request.session().id());
  }


  private User initUser (ResultSet results) throws SQLException
  {
    String username = results.getString("username");
    String password = results.getString("password");
    String salt = results.getString("salt");

    User u = new User();
    u.setEncryptedPassword(password, salt);
    u.setUsername(username);

    return u;
  }
}
