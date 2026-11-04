package org.openindustries.energy.grid;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GridRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"urban-standard\",\"zoneType\":\"URBAN\",\"maxLoadMw\":800,\"maxOutageMinutes\":30,\"priority\":1}")
                .when().post("/api/v1/grid-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/grid-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"zoneType\":\"URBAN\",\"requiredMaxOutageMinutes\":45,\"requiredMaxLoadMw\":600}")
                .when().post("/api/v1/grid-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"zoneType\":\"ISLAND\",\"requiredMaxOutageMinutes\":45,\"requiredMaxLoadMw\":600}")
                .when().post("/api/v1/grid-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
