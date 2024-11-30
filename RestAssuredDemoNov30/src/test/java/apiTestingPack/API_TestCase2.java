package apiTestingPack;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Book;

import java.util.ArrayList;
import java.util.List;

public class API_TestCase2 {

    @Test
    public void verifyPostRequest() {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");

        JsonObject object = new JsonObject();
        object.addProperty("id", "13");
        object.addProperty("title", "a title 1300");
        object.addProperty("views", "1300");

        httpRequest.body(object.getAsJsonObject());

        Response response = httpRequest.post("http://localhost:3000/posts");

        System.out.println(response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 201);

    }


    @Test
    public void verifyPutRequest() {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");

        JsonObject object = new JsonObject();
        object.addProperty("id", "13");
        object.addProperty("title", "a title three hundred nine ninty");
        object.addProperty("views", "1390");

        httpRequest.body(object.getAsJsonObject());

        Response response = httpRequest.put("http://localhost:3000/posts/13");

        System.out.println(response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test
    public void verifyDeleteRequest() {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");

        Response response = httpRequest.delete("http://localhost:3000/posts/13");

        System.out.println(response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test
    public void verifyPatchRequest() {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");

        JsonObject object = new JsonObject();
        object.addProperty("views", "1301");

        httpRequest.body(object.getAsJsonObject());

        Response response = httpRequest.patch("http://localhost:3000/posts/13");

        System.out.println(response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 200);

    }


    @Test
    public void verifyDeserilizetoBookObject() {

        RestAssured.baseURI = "https://demoqa.com/BookStore/v1";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Books");

        JsonPath jsonPath = response.jsonPath();

        List<Book> allBooks = jsonPath.getList("books", Book.class);

        List<String> expectedList = new ArrayList<>();
        expectedList.add("Git Pocket Guide");
        expectedList.add("Learning JavaScript Design Patterns");
        expectedList.add("Designing Evolvable Web APIs with ASP.NET");
        expectedList.add("Speaking JavaScript");
        expectedList.add("You Don't Know JS");
        expectedList.add("Programming JavaScript Applications");
        expectedList.add("Eloquent JavaScript, Second Edition");
        expectedList.add("Understanding ECMAScript 6");
        for (Book book : allBooks) {
            System.out.println("isbn : " + book.getIsbn());
            System.out.println("title : " + book.getTitle());
            Assert.assertTrue(expectedList.contains(book.getTitle()));
        }

    }
}
