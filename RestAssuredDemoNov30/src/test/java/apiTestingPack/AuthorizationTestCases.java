package apiTestingPack;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthorizationTestCases {

    static String token="";
    @Test
    public void getCheckBasicAuth() {
        //Using the preemptive directive of basic auth to send credentials to the server
        RequestSpecification httpRequest = RestAssured.given().auth().preemptive().basic("postman", "password");
        Response res = httpRequest.get("https://postman-echo.com/basic-auth");
        ResponseBody body = res.body();
        //Converting the response body to string
        String rbdy = body.asString();
        System.out.println("Data from the GET API- " + rbdy);
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @BeforeMethod
    public void getBearerTokenValue() {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "emilys");
        jsonObject.put("password", "emilyspass");
        jsonObject.put("expiresInMins", 30);

        httpRequest.body(jsonObject.toJSONString());

        Response response = httpRequest.post("https://dummyjson.com/auth/login");
        token = response.jsonPath().get("token");
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println(response.prettyPrint());

    }

    @Test
    public void getCheckTokenBased() {
        //Using the preemptive directive of basic auth to send credentials to the server
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Authorization", "Bearer " + token);
        Response res = httpRequest.get("https://dummyjson.com/auth/me");
        ResponseBody body = res.body();
        //Converting the response body to string
        String rbdy = body.asString();
        System.out.println("Data from the GET API- " + body.prettyPrint());
        Assert.assertEquals(res.getStatusCode(), 200);
    }
}
