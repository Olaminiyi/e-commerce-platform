#Feature: User registration Authentication
#
#  Scenario Outline: Register a new user on the system
#    Given a new user enters his details
#    And the user register with theses details
#    Then a token should be generated to validate registration
#    When the registraion is being authenticate with "<username>" and "<password>"
#    Then a token should be generated to valida the authentication
#
#    Examples:
#      | username | password |
#      | John     | John123  |