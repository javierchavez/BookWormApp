package bookworm.db.backends;



import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Mysql implements Backend
{
  private final String url;// = "jdbc:mysql://localhost:3306/onlineide";
  private Connection connection = null;
  private Properties properties = null;

  public Mysql(String url)
  {
    this.url = url;
  }


  @Override
  public boolean connect ()
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
      connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/");

    }
    catch(SQLException e)
    {
      e.printStackTrace();
      return false;
    }
    catch(ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    return true;
  }

  @Override
  public void createTable (String table, Hashtable<String, Object> properties)
  {

  }

  @Override
  public void insert (String table, Set<String> cols, Set<Object> values)
  {
    // The ?'s are place holders in the sql statement
    String sql = "insert into applications (name, price) values (?, ?)";

    try
    {
      //Allows us to validate/sanitize input
//      PreparedStatement statement = connection.prepareStatement(sql);
//
//      statement.setString(1, application.getName());
//      statement.setDouble(2, application.getPrice());
//      statement.execute();
    }
    finally
    {  // This will ensure the that connection is closed no matter what
    }
  }

  @Override
  public ResultSet select (String table, String where)
  {
    // List<Application> applications = new ArrayList<Application>();

    //Does a query on everything to see what's available
    String sql = "select * from applications";


    try
    {
      // PreparedStatement statement = connection.prepareStatement(sql);
      //Executes a query that returns the set of records
      //RestultSet is smart enough to only take in subsets of the results to prevent
      //overloading the system (memory) in the event of a large data set.
//      ResultSet results = statement.executeQuery();
      //Iterate through all the records and instantiate
//      while(results.next())
//      {
//        //Instantiate variables to match our columns
//        int id = results.getInt("id");
//        String name = results.getString("name");
//        double price = results.getDouble("price");
//        //Create new application object with the following params
//        Application application = new Application(id, name, price);
//        //Add the new application object to your List
//        applications.add(application);
//      }
    }
    finally
    {
//      closeConnection(connection);
    }

    return null;
  }

  @Override
  public void close ()
  {
    try
    {
      connection.close();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
  }
}
