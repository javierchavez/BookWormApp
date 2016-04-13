package bookworm.db;

//Necessary for the Connection method

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static spark.Spark.before;
import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

/**
 * @author dsalas
 *         Single place that will allow us to interact with the database. It's an API
 *         that will allow us to add, update, delete, select, etc. Single point of maintenance.
 *
 *         Data Access Objects (DAO)
 */

public class ApplicationsDAO
{

  /**
   * Connection method to our mysql database
   *
   * CheatSheet of calls to the database:
   *
   * Instantiates the mysql driver and creates a new instance:
   * Class.forName("com.mysql.jdbc.Driver").newInstance();
   *
   * String connectionUrl = "jdbc:mysql://localhost:3306/onlineide";
   *
   * Using the connection Url we're specifying what driver we're using:
   * connection = DriverManager.getConnection(connectionUrl, "root", null);
   *
   * @return
   */
  public Connection getConnection ()
  {
    String connectionUrl = "jdbc:mysql://localhost:3306/onlineide";
    Connection connection = null;

    try
    {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      connection = DriverManager.getConnection(connectionUrl, "root", null);
      //Could also replace all of the exceptions with a single catch (Exception e)
      // However, this is considered lazy and bad practice
    }
    catch(InstantiationException e)
    {
      e.printStackTrace();
    }
    catch(IllegalAccessException e)
    {
      e.printStackTrace();
    }
    catch(ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }

    return connection;
  }

  /**
   * Closes the connection
   */
  public void closeConnection (Connection connection)
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

  public void create (Application application)
  {
    // The ?'s are place holders in the sql statement
    String sql = "insert into applications (name, price) values (?, ?)";

    Connection connection = getConnection();

    try
    {
      //Allows us to validate/sanitize input
      PreparedStatement statement = connection.prepareStatement(sql);

      statement.setString(1, application.getName());
      statement.setDouble(2, application.getPrice());
      statement.execute();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {  // This will ensure the that connection is closed no matter what
      closeConnection(connection);
    }

    //We could've done the following, but this would've opened us up to
    //sql injection. The reason for that is because the code below will actually run
    //the code, which could be something like drop table, create user, etc.
    // Funny Example: https://xkcd.com/327/
    //String sql = "insert into applications (name, price) values ('"+application.getName()+"', ?)";
  }

  public List<Application> selectAll ()
  {
    List<Application> applications = new ArrayList<Application>();

    //Does a query on everything to see what's available
    String sql = "select * from applications";

    Connection connection = getConnection();
    try
    {
      PreparedStatement statement = connection.prepareStatement(sql);
      //Executes a query that returns the set of records
      //RestultSet is smart enough to only take in subsets of the results to prevent
      //overloading the system (memory) in the event of a large data set.
      ResultSet results = statement.executeQuery();
      //Iterate through all the records and instantiate
      while(results.next())
      {
        //Instantiate variables to match our columns
        int id = results.getInt("id");
        String name = results.getString("name");
        double price = results.getDouble("price");
        //Create new application object with the following params
        Application application = new Application(id, name, price);
        //Add the new application object to your List
        applications.add(application);
      }
    }
    catch(SQLException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally
    {
      closeConnection(connection);
    }

    return applications;
  }

  public void remove (int id)
  {
    String sql = "delete from applications where id=?";
    Connection connection = getConnection();

    try
    {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, id);
      statement.execute();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeConnection(connection);
    }
  }

  public Application selectOne (int id)
  {
    Application app = null;

    String sql = "select * from applications where id=?";
    Connection connection = getConnection();
    try
    {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, id);
      ResultSet results = statement.executeQuery();
      if (results.next())
      {
        id = results.getInt("id");
        String name = results.getString("name");
        double price = results.getDouble("price");
        app = new Application(id, name, price);
      }

    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeConnection(connection);
    }

    return app;
  }

  public void update (int id, Application app)
  {
    String sql = "update applications set name=?, price=? where id=?";
    Connection connection = getConnection();
    try
    {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, app.getName());
      statement.setDouble(2, app.getPrice());
      statement.setInt(3, id);
      statement.execute();
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      closeConnection(connection);
    }


  }

  private static final Map<String, Object> model = new HashMap<>();

  /**
   * A lot of main's code is commented out because it was only used for testing.
   *
   * For establishing the connection, we created new methods above.
   *
   * For passing in data, we want to actually do it from our web application instead.
   */
  public static void main (String[] args)
  {
    model.put("key", "value");
    model.put("key1", "value");
    model.put("key2", "value");
    staticFileLocation("/public");
    port(8081);
    before((req, res) -> res.type("application/json"));
//    get("/hello", "application/json", (req, res) -> toJson(model));
//    get("/login", "application/json", (req, res) -> toJson(model));
    //System.out.println("Hello from ApplicationsDAO");
    //Creates a new instance of our ApplicationDAO object
    ApplicationsDAO dao = new ApplicationsDAO();

    //creates a new instance of the Applications object with the appropriate params
    Application app1 = new Application("Contact List", 3.99);
    dao.create(app1);

    // Below we're creating a connection object, testing it, then closing it
    // Simple Test:
    //Connection connection = dao.getConnection();
    //System.out.println(connection);
    //Good practice to close the connection after use.
    //Would timeout eventually, but good practice
    //dao.closeConnection(connection);
  }

}
