Feature: User registration

  Scenario: Authenticate a new registration on the system
    Given a new user enters his details
    Given the user register with theses details
    Then a token should be generated
    Then username should be "<paul@gmail.com>"
