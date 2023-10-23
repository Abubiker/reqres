package tests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import objects.reqres.ResourceList;
import objects.reqres.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;

public class ReqresTest {

    @Test
    public void checkCreationUser() {
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();

        Response response = given()
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_CREATED, "Error!");
    }

    @Test
    public void checkListUser() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(HTTP_OK);
    }

    @Test
    public void checkSingleUser() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK, "Error!");
    }

    @Test
    public void checkSingleUserNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    public void checkResourceList() {
        String body = given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(HTTP_OK)
                .extract().body().asString();
        ResourceList resourceList = new Gson().fromJson(body, ResourceList.class);
        String name = resourceList.getData().get(2).getName();
        System.out.println(name);
    }

    @Test
    public void checkSingleResource() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK, "Error!");
    }

    @Test
    public void checkNoFound() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    public void checkUserUpdate() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = given()
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK, "Error!");
    }

    @Test
    public void checkUserPatch() {
        User user = User.builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        Response response = given()
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK, "Error!");

    }

    @Test
    public void checkDelete() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(HTTP_NO_CONTENT);
    }

    @Test
    public void checkRegisterSuccessfull() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        given()
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(HTTP_UNSUPPORTED_TYPE);

    }

    @Test
    public void checkUnsuccessfulRegister() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .build();
        given()
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Test
    public void checkLoginSuccessful() {
        User user = User.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        Response response = given()
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST, "Error");
    }

    @Test
    public void checkLoginUnsuccessful() {
        User user = User.builder()
                .email("peter@klaven")
                .build();
        Response response = given()
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_BAD_REQUEST, "Error");
    }
    @Test
    public void checkDelayedResponse(){
        given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(HTTP_OK);
    }

}
