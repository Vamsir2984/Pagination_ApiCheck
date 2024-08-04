package StepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Page {

    private RequestSpecification request;
    private Response response;

    @Given("^I have the API endpoint for pagination$")
    public void iHaveTheAPIEndpoint() {

        request = given().baseUri("https://rickandmortyapi.com/api/character/");
    }

    @When("^I request page number \"([^\"]*)\"$")
    public void iRequestPage(int page) {
        // Make the API request with specified page and page size
        response = request.param("page", page).get();
    }

    @Then("^I should receive a successful response with status code \"([^\"]*)\"$")
    public void iShouldReceiveASuccessfulResponse(int statusCode) {
        // Verify the status code of the response
        assertEquals(statusCode, response.getStatusCode());
        System.out.println("The Response code is "+response.getStatusCode());
    }

    @Then("^the response should contain \"([^\"]*)\" items$")
    public void theResponseShouldContainItems(int expectedItemCount) {
        // Verify the number of items in the response
        assertEquals(expectedItemCount, response.jsonPath().getList("results").size());
        System.out.println("The items in the page are "+response.jsonPath().getList("results").size());
    }

    @Then("^the response should contain the correct items for page \"([^\"]*)\"$")
    public void theResponseShouldContainCorrectItemsForPage(int page) {
        int pageSize = 20;
        int startIndex = ((page - 1) * pageSize) + 1;
        int endIndex = startIndex + pageSize - 1;

        List<Integer> actualIds = response.jsonPath().getList("results.id");

        for (int id : actualIds) {
            assertTrue("Item with id " + id + " is not within the expected range", id >= startIndex && id <= endIndex);
        }

        System.out.println("Expected items matched with the Actual items");
    }


    @Then("^the response should contain a next page link$")
    public void theResponseShouldContainNextPageLink() {

        assertTrue(response.jsonPath().get("info.next") != null);
        System.out.println("page contains next page link");
    }

    @Then("^the response should contain a previous page link$")
    public void theResponseShouldContainPreviousPageLink() {

        assertTrue(response.jsonPath().get("info.prev") != null);
        System.out.println("Page contains a previous page link");

    }
}

