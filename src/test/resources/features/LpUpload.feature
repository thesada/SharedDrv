@importbooks
Feature: Search Books
  As a LitPro User
  I should be able to search books
  So that I can ....

  Scenario: Upload Books to Library
    Given I browse to In Library Page as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see In Library Page Header - "Match Your Library Books"
    When I select csv file
    And I click Upload button
    Then Import status should be "Done"
