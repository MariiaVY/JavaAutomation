Feature: Customer Sign In

  Scenario Outline: User is able to Sign In to application
    Given User opens registration page
    When User enters valid "<login>" and "<password>"
    And User repeats the "<password>"
    And User select question and answers
    And User clicks on Register button
    Then User is registered and can log in to application
    Examples:
    |login                |password                  |
    |test12341!!!467@gmail.com|123456789Test!        |
    |test12**!55@gmail.com|123456789Test!           |


