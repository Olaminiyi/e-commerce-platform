Feature: User registration Authentication

  Scenario Outline: Authenticate a registered user on the system
    Given a user enters his "<username>" and "<password>"
    When we authenticate with "<username>"
    Then a token should be generated to validate registration


    Examples:
      | username | password |
      | johnbull@gmail.com    | johnbull001  |