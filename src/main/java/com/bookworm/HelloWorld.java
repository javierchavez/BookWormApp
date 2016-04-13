package com.bookworm;

import java.util.HashMap;
import java.util.Map;

import static com.bookworm.JsonTransformer.toJson;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

/**
 * Test Server
 */
public class HelloWorld {

/*    private static final Map<String, Object> model = new HashMap<>();

    public static void main(String[] args) {
        model.put("key", "value");
        model.put("key1", "value");
        model.put("key2", "value");
        staticFileLocation("/public");
        port(8081);
        before((req, res) -> res.type("application/json"));
        get("/hello", "application/json", (req, res) -> toJson(model));
        get("/login", "application/json", (req, res) -> toJson(model));
    }*/

}
