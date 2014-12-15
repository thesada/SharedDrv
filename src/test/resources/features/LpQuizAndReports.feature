@quizandresults
Feature: LitPro Quiz, Results
  As a LitPro User
  I should be able to take Quiz on Books
  So that I can ....

  Scenario: Change required settingsto take quiz, Take quiz and check results in My Results
    Given I browse to Settings Page as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see class name
    And I change following Quiz settings:
      | Setting Name                         | Action  | New Value |
      | Restrict number of quiz attempts to  | Check   | 99        |
      | Number of days between quiz attempts | Check   | 0         |
      | Display incorrect answers            | Uncheck | NA        |
      | Quiz pass mark                       | NA      | 10        |
      | Allow student to print               | Check   | NA        |
    And I click Save
    Then 'Settings Saved' Message should be displayed
    Given As a student, I am on the search page showing results for word "Tale, Story, Book"
      | UserName | StudFour |
      | Password | welcome1 |
    When I click Take Quiz button for a random book
    Then I should see quiz popup with header text starting with "Quizzes for"
    When I click Take Quiz button in Quiz Popup
    And I complete the quiz
    Then I should see score and percentage
    When I goto My Results page
    Then I should see book details on which quiz was taken
    And Score and Quiz date should match
