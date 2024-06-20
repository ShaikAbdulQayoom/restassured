// File: src/test/java/restAssured/restAssured/chaining1.java

package restAssured.restAssured;

import static org.testng.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.aventstack.extentreports.Status;

public class chaining1 extends BaseTest {

    @Test(priority = 1)
    public void updateUserAndDelete() throws IOException {
        String baseUrl = BaseTest.getBaseUrl();

        // Log the beginning of the test
        BaseTest.logExtentTest(Status.INFO, "Starting test: updateUserAndDelete");

        // Read the JSON file
        File jsonFile = new File("src/test/java/resources/restAssured/create.json");

        // Send the createUser REST request using RestAssured
        RequestSpecification createUserRequest = RestAssured.given()
                .contentType("application/json")
                .body(jsonFile);

        Response createUserResponse = createUserRequest.post(baseUrl);
        createUserResponse.then().log().all();

        // Log response
        BaseTest.logExtentTest(Status.INFO, "createUserResponse: " + createUserResponse.asString());

        // Assert the response code for create
        assertEquals(createUserResponse.getStatusCode(), 201, "Expected status code 201 for create");
        BaseTest.logExtentTest(Status.PASS, "Create user passed");

        // Extract the id from the createUser response
        int extractedId = createUserResponse.jsonPath().getInt("id");

        // Construct the update URL with the extracted id
        String updateUrl = baseUrl + "/" + extractedId;
        String deleteUrl = baseUrl + "/" + extractedId;

        // Read the update JSON file
        File updateJsonFile = new File("src/test/java/resources/restAssured/create.json");

        // Send the update REST request using RestAssured
        RequestSpecification updateRequest = RestAssured.given()
                .contentType("application/json")
                .body(updateJsonFile);

        Response updateResponse = updateRequest.put(updateUrl);
        updateResponse.then().log().all();

        // Log response
        BaseTest.logExtentTest(Status.INFO, "updateResponse: " + updateResponse.asString());

        // Assert the response code for update
        assertEquals(updateResponse.getStatusCode(), 200, "Expected status code 200 for update");
        BaseTest.logExtentTest(Status.PASS, "Update user passed");

        // Send the delete REST request using RestAssured
        RequestSpecification deleteRequest = RestAssured.given();

        Response deleteResponse = deleteRequest.delete(deleteUrl);
        deleteResponse.then().log().all();

        // Log response
        BaseTest.logExtentTest(Status.INFO, "deleteResponse: " + deleteResponse.asString());

        // Assert the response code for delete
        assertEquals(deleteResponse.getStatusCode(), 200, "Expected status code 200 for delete");
        BaseTest.logExtentTest(Status.PASS, "Delete user passed");
    }
}
