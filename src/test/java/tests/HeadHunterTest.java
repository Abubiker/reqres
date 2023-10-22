package tests;

import com.google.gson.Gson;
import objects.head_hunter.VacanciesList;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HeadHunterTest {
    @Test
    public void checkVacancyList(){
        String body = given()
                .log().all()
                .when()
                .get("https://api.hh.ru/vacancies?text=QA automation")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().asString();
        VacanciesList vacanciesList = new Gson().fromJson(body, VacanciesList.class);
        int salaryTo = vacanciesList.getItems().get(0).getSalary().getTo();
    }

}
