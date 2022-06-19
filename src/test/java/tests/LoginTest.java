package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class LoginTest extends TestBase {

    Response postResponse;
    JsonPath jsonPath;

    @Test
    @Order(1)
    @DisplayName("Login")
    public void login(){

        postResponse = given().accept(ContentType.JSON).and()
                .and().contentType(ContentType.JSON)
                .and().body("{\"Password\":\""+ConfigurationReader.get("password")+"\",\"Username\":\""+ConfigurationReader.get("username")+"\"}")
                .when().post("/login");

        jsonPath = postResponse.jsonPath();
        assertEquals(200, postResponse.statusCode(), "Status code is different than expected");
        assertEquals("application/json; charset=utf-8", postResponse.contentType(), "Content Type is different than expected");
        assertEquals(true, jsonPath.getBoolean("success"), "Success message is different than expected");
        assertEquals(null, jsonPath.getString("errors"), "Errors is NOT null");
        assertEquals(154, jsonPath.getInt("data.Authentication.PlayerId"), "PlayerId is different than expected");
        assertEquals("load4", jsonPath.getString("data.Player.Username"), "Username is different than expected");
        assertEquals(2, jsonPath.getInt("data.Player.CountryId"), "CountryId is different than expected");
        assertEquals(1, jsonPath.getInt("data.Player.LanguageId"), "LanguageId is different than expected");
        assertEquals("en", jsonPath.getString("data.Player.LanguageCode"), "LanguageCode is different than expected");
        assertEquals("SE", jsonPath.getString("data.Player.CountryCode"), "CountryCode is different than expected");
        assertEquals("EUR", jsonPath.getString("data.Player.CurrencyCode"), "CurrencyCode is different than expected");
    }

}
