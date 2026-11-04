package org.openindustries.energy.meter;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class MeterResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Meter M-100\",\"meterType\":\"SMART\",\"readingsPerDay\":96}")
                .when().post("/api/v1/meters")
                .then().statusCode(201)
                .body("status", is("INSTALLED"))
                .body("meterType", is("SMART"))
                .extract().path("id");

        given().when().post("/api/v1/meters/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));
    }
}
