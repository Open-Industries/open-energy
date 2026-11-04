package org.openindustries.energy.dispatch;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class DispatchResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"assetId\":\"11111111-1111-1111-1111-111111111111\",\"capacityMw\":500}")
                .when().post("/api/v1/dispatch")
                .then().statusCode(201).body("capacityMw", is(500))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"sourceType\":\"SOLAR\",\"mw\":120}")
                .when().post("/api/v1/dispatch/" + id + "/orders")
                .then().statusCode(200)
                .body("remainingAfter", is(380));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"assetId\":\"11111111-1111-1111-1111-111111111111\",\"capacityMw\":500}")
                .when().post("/api/v1/dispatch")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"sourceType\":\"SOLAR\",\"mw\":900}")
                .when().post("/api/v1/dispatch/" + id + "/orders")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/dispatch/" + id)
                .then().statusCode(200).body("capacityMw", is(500));
    }
}
