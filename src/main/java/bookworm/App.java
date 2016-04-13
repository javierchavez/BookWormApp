package bookworm;


import bookworm.db.Users;
import bookworm.db.backends.Sqlite;
import bookworm.model.Asset;
import bookworm.model.Category;
import bookworm.model.User;

import static bookworm.model.JsonTransformer.toJson;
import static spark.Spark.*;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

public class App
{

  private Users db = new Users(new Sqlite("jdbc:sqlite:test_db.db"));

  public App()
  {

    staticFileLocation("/public");
    port(8081);

    // before any response is sent, set the type of response to be json
    before((req, res) -> res.type("application/json"));

    // Login endpoint
    get("/login", "application/json", (req, res) -> toJson(""));
    post("/login", "application/json", (req, res) -> toJson(""));

    // Registration endpoint
    get("/register", "application/json", (req, res) -> toJson(""));
    post("/register", "application/json", (request, response) -> {
      return toString();
    });


    get("/checkout", "application/json", (request, response) -> {
      User u = db.isLoggedIn(request);
      if (u == null)
      {
        // halt("Login required!");
      }

      return toJson(new User("javierc", "Javier", "Chavez"));
    });

    get("/search", "application/json", (request, response) -> {
      User u = db.isLoggedIn(request);
      if (u == null)
      {
        // halt("Login required!");
      }

      return toJson(new Asset("Gone Fishing", Category.BOOK));
    });
  }
}
