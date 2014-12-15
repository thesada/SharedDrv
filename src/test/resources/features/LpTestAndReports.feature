@testsandreports
Feature: LitPro Tests, Metrics, Reports and Charts
  As a LitPro User
  I should be able to changes Test and Quiz Setting
  So that I can ....

  Scenario: Change required settingsto take test
    Given I browse to Settings Page as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see class name
    When I change following Test settings:
      | Setting Name                                      | Action  | New Value |
      | Minimum number of days between completed tests    | Check   | 0         |
      | Limit number of books in reading list to          | Check   | 5         |
      | Allow students to see their reading list          | Check   | NA        |
      | Require students to take practice test            | Uncheck | NA        |
      | Limit reading list to titles with book quizzes    | Uncheck | NA        |
      | Limit practice test to one per student            | Uncheck | NA        |
      | Show student Lexile measure after test completion | Uncheck | NA        |
    And I change following Quiz settings:
      | Setting Name              | Action  | New Value |
      | Display incorrect answers | Uncheck | NA        |
      | Quiz pass mark            | NA      | 30        |
      | Allow student to print    | Check   | NA        |
    And I click Save
    Then 'Settings Saved' Message should be displayed

  Scenario: Take Test
    Given I browse to LitPro Test Page as "Student"
      | UserName | StudOne |
      | Password | welcome1 |
    Then I should see welcome message with get started button
    When I click on lets get started button
    And I complete the assessment
    Then I should be shown 'Create My Reading list' Button
    When I click 'Create My Reading list' Button
    Then Home Page should be shown


  Scenario: Metrics, Charts and Reports
    Given I browse to Metrics section of Home Page as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see Header Text stating with "Metrics for"
    And Metrics Section should display following metrics
      | Metric Name                     | Value Type |
      | Average Lexile                  | String     |
      | Average Lexile Growth This Year | String     |
      | Average Quiz Score              | Percentage |
      | Number of Quizzes Taken         | Number     |
      | Quiz Pass Rate                  | Percentage |
      | Words Read                      | Number     |
    And Following charts should display
      | Chart Name      |
      | Expected Growth |
      | Proficiency     |
    When I browse to Reports page
    Then I should see following reports link
      | Report Name                    |
      | Lexile Growth Report           |
      | Lexile Compared to Norm Report |
      | Reading Proficiency Report     |
      | Expected Lexile Growth Report  |
      | Book Comprehension Report      |
      | Quiz Pass Rate Report          |
    When I open "Lexile Growth Report" report
    Then Popup should open with header "Lexile Growth Report"
    When I search report card for student "StudOne"
    Then I should see report card with following items
      | Report Element Name                |
      | Current Lexile                     |
      | Lexile Growth                      |
      | Date of Last Completed LitPro Test |
      | Proficiency Band                   |
      | # of Quizzes Passed/Attempted      |
      | Average Quiz Score                 |
      | Average Lexile of Quizzes Passed   |
      | Words Read                         |
      | Quiz Points Earned                 |
      | Teacher-Added Points               |
