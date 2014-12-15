@searchbooks
Feature: Search Books
  As a LitPro User
  I should be able to search books
  So that I can ....

  @student
  Scenario: Search Books, Authors as Student
    Given I browse to Search Page as "Student"
      | UserName | StudOne  |
      | Password | welcome1 |
    Then I should see Search Page Header "Search"
    When I search for books having Title "Music"
    Then Search results should show Book Titles that contain the word - "Music"
    When I search for books having Title "Music" with Quiz
    Then Search results should show Book Titles that contain the word - "Music"
    And All books should have an associated Quiz
    

  @teacher
  Scenario: Search Books, Authors as Teacher
    Given I browse to Search Page as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see Search Page Header "Search"
    When I search for books having Title "Music"
    Then Search results should show Book Titles that contain the word - "Music"
    When I search for books having Title "Music" with Quiz
    Then Search results should show Book Titles that contain the word - "Music"
    And All books should have an associated Quiz
