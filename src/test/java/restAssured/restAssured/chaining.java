package restAssured.restAssured;

import static org.testng.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class chaining {

    @Test(priority = 1)
    public void updateUserAndDelete() throws IOException {
        String baseUrl = BaseTest.getBaseUrl();
        // Read the JSON file
        File jsonFile = new File("src/test/resources/restassured/create.json");

        // Send the createUser REST request using RestAssured
        RequestSpecification createUserRequest = RestAssured.given()
                .contentType("application/json")
                .body(jsonFile);

        Response createUserResponse = createUserRequest.post(baseUrl);
        createUserResponse.then().log().all();

        // Assert the response code for create
        assertEquals(createUserResponse.getStatusCode(), 201, "Expected status code 201 for create");

        // Extract the id from the createUser response
        int extractedId = createUserResponse.jsonPath().getInt("id");

        // Construct the update URL with the extracted id
        String updateUrl = baseUrl + "/" + extractedId;
        String deleteUrl = baseUrl + "/" + extractedId;

        // Read the update JSON file
        File updateJsonFile = new File("src/test/resources/restassured/update.json");

        // Send the update REST request using RestAssured
        RequestSpecification updateRequest = RestAssured.given()
                .contentType("application/json")
                .body(updateJsonFile);

        Response updateResponse = updateRequest.put(updateUrl);
        updateResponse.then().log().all();

        // Assert the response code for update
        assertEquals(updateResponse.getStatusCode(), 200, "Expected status code 200 for update");

        // Send the delete REST request using RestAssured
        RequestSpecification deleteRequest = RestAssured.given();

        Response deleteResponse = deleteRequest.delete(deleteUrl);
        deleteResponse.then().log().all();

        // Assert the response code for delete
        assertEquals(deleteResponse.getStatusCode(), 200, "Expected status code 200 for delete");
    }
}
