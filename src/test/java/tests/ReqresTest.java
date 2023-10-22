package tests;

import com.google.gson.Gson;
import io.restassured.response.Response;
import objects.reqres.NewUser;
import objects.reqres.Resource;
import objects.reqres.ResourceList;
import objects.reqres.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTest {

    @Test
    public void checkCreationUser() {
        User user = User.builder()
                .name("morpheus")
                .job("leader")
                .build();

        Response response = given()
                .body(user)
                .log().all()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_CREATED, "Error!");
    }

    @Test
    public void checkListUser() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(HTTP_OK);
    }

    @Test
    public void checkSingleUser() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK, "Error!");
    }

    @Test
    public void checkSingleUserNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND);
    }

    @Test
    public void checkResourceList() {
        String body = given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
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
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK, "Error!");
    }

    @Test
    public void checkNoFound() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log().all()
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
                .log().all()
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
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode(), HTTP_OK, "Error!");

    }
    @Test
    public void checkDelete(){
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(HTTP_NO_CONTENT);
    }
    @Test
    public void checkRegisterSuccessfull(){
        User userser = User.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
         given()
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().all()
                .statusCode(HTTP_UNSUPPORTED_TYPE);

    }



}
