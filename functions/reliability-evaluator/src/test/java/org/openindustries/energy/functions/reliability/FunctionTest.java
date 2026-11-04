package org.openindustries.energy.functions.reliability;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"assetId\":\"a-1\",\"measuredOutageMinutes\":12,\"targetOutageMinutes\":30,\"measuredAvailabilityPct\":99.95,\"targetAvailabilityPct\":99.9}")
                .when().post("/reliabilityEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"assetId\":\"a-2\",\"measuredOutageMinutes\":95,\"targetOutageMinutes\":30,\"measuredAvailabilityPct\":99.95,\"targetAvailabilityPct\":99.9}")
                .when().post("/reliabilityEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
