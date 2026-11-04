package org.openindustries.energy.asset;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GridAssetResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Substation North 1\",\"assetType\":\"SUBSTATION\",\"regionCode\":\"GRID-N1\"}")
                .when().post("/api/v1/grid-assets")
                .then().statusCode(201)
                .body("status", is("IN_SERVICE"))
                .body("assetType", is("SUBSTATION"))
                .extract().path("id");

        given().when().get("/api/v1/grid-assets/" + id)
                .then().statusCode(200).body("status", is("IN_SERVICE"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"name\":\"Substation North 1\",\"assetType\":\"SUBSTATION\",\"regionCode\":\"GRID-N1\"}")
                .when().post("/api/v1/grid-assets")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/grid-assets/" + id + "/maintain")
                .then().statusCode(200).body("status", is("MAINTENANCE"));
    }
}
