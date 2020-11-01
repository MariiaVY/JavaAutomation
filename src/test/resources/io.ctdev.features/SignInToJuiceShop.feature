Feature: Customer Sign In

  Scenario Outline: User is able to Sign In to application
    Given User opens registration page
    When User enters valid "<login>" and "<password>"
    And User repeats the "<password>"
    And User select question and "<answer>"
    And User clicks on Register button
    Then User is registered and can log in to application
    Examples:
    |login                    |password       | answer |
    |test12843!!4a7@gmail.com |123456789Test! | Test   |
    |test1265s*!hg@gmail.com  |123456789Test! | Maiden |


