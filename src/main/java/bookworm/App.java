package bookworm;


import bookworm.db.Users;
import bookworm.db.backends.Sqlite;
import bookworm.model.User;

import static bookworm.JsonTransformer.toJson;
import static spark.Spark.*;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

public class App
{

  private Users db = new Users(new Sqlite("."));

  public App()
  {

    staticFileLocation("/public");
    port(8081);

    // before any response is sent, set the type of response to be json
    before((req, res) -> res.type("application/json"));
    get("/hello", "application/json", (req, res) -> toJson(""));
    get("/login", "application/json", (req, res) -> toJson(""));
    get("/register", "application/json", (req, res) -> toJson(""));

    post("/register", "application/json", (request, response) -> {
      return toString();
    });


    get("/checkout", "application/json", (request, response) -> {
      User u = db.isLoggedIn(request);
      if (u == null)
      {
        halt("Login required!");
      }

      return toString();
    });
  }
}
