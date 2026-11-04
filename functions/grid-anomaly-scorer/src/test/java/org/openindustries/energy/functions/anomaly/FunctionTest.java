package org.openindustries.energy.functions.anomaly;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"assetId\":\"a-1\",\"voltageDeviationIndex\":4,\"frequencyDeviationIndex\":3,\"lossIndex\":6}")
                .when().post("/gridAnomalyScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"assetId\":\"a-2\",\"voltageDeviationIndex\":88,\"frequencyDeviationIndex\":72,\"lossIndex\":64}")
                .when().post("/gridAnomalyScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
