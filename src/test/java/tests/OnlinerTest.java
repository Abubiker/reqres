package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OnlinerTest {
    @Test
    public void checkCurrencyUsdRateTest(){
        given()
                .log().all()
                .when()
                .get("https://www.onliner.by/sdapi/kurs/api/bestrate?currency=USD&type=nbrb")
                .then()
                .log().all()
                .statusCode(200);
    }
}
