package apiTestingPack;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class API_TestCases {


    @Test
    public void verifyStatusCodeForBooks() {
        RestAssured.baseURI = "https://demoqa.com/BookStore/v1/Books";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get();
        //System.out.println(response.asString());
        System.out.println(response.prettyPrint());
        System.out.println(response.statusCode());
        System.out.println(response.statusLine());

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK");
    }

    @Test
    public void verifyHeaderDetails() {

        RestAssured.baseURI = "https://demoqa.com/BookStore/v1";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Books");
        //System.out.println(response.asString());

        Headers headers = response.getHeaders();

        for (Header header : headers) {
            System.out.println(header.getName() + " : " + header.getValue());
        }

        Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");

    }


    @Test
    public void verifyGetResponseBody() {

        RestAssured.baseURI = "https://demoqa.com/BookStore/v1";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Books");
        //System.out.println(response.asString());

        String actualResponse = response.prettyPrint();

        System.out.println(actualResponse);

        Assert.assertTrue(actualResponse.contains("9781449331818"), "isbn number is not present in reponse");

    }

    @Test
    public void verifyGetAuthorNameBook() {

        RestAssured.baseURI = "https://demoqa.com/BookStore/v1";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Books");
        //System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String authorName = jsonPath.get("books[0].author").toString();

        System.out.println(authorName);

        Assert.assertEquals(authorName, "Richard E. Silverman", "authorName is not matching");


    }

    @Test
    public void verifyGetAuthorNameListForBook() {

        RestAssured.baseURI = "https://demoqa.com/BookStore/v1";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Books");
        //System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        List<String> authorNameList = jsonPath.getList("books.author");
        for (String author : authorNameList) {
            System.out.println(author);
        }

    }
}
