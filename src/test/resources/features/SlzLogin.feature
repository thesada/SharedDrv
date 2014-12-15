@login
Feature: Scholastic Learning Zone Login
  As a School Admin
  I should have access to Scholastic Learning Zone
  So that I can ..

  @positive @sanity
  Scenario: Valid Credentials
    Given I am on Scholastic Learning Zone Login Page
    When I login with following credentials
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see Scholastic Learning Zone Home Page

  @negative
  Scenario Outline: Invalid Credentials
    Given I am on Scholastic Learning Zone Login Page
    When I login using '<UserName>' and '<Password>'
    Then I should see Error Message "Invalid username or password. Please try again."

    Examples: 
      | UserName     | Password  |
      | HarryPotter  | welcome1X |
      | HarryPotterX | welcome1  |
