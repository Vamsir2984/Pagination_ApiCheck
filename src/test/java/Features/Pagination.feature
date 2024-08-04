Feature: Testing Pagination API

  Scenario: Verify Pagination
    Given I have the API endpoint for pagination
    When I request page number "3"
    Then I should receive a successful response with status code "200"
    And the response should contain "20" items
    And the response should contain the correct items for page "3"

