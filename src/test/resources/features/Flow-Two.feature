@f2
Feature: LitPro Sanity - Flow Two
  As a school teacher and student
  I should be able to do set of activities
  So that I can ..

  @student @common
  Scenario: LitPro Common UI - Student
    When I launch LitPro as "Student"
      | UserName | StudOne  |
      | Password | welcome1 |
    Then I should see Scholastic Literacy Pro Home Page
    And I should see user greeting text "Welcome, "
    And I should see below tabs:
      | Home | Search | My Results | LitPro Test |
    And I should see Logout link

  @teacher @common
  Scenario: LitPro Common UI - Teacher
    When I launch LitPro as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see Scholastic Literacy Pro Home Page
    And I should see user greeting text "Welcome, "
    And I should see below tabs:
      | Home | Reports | Search | Settings | Benchmarks |
    And I should see Logout link

  @student @search
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

  @teacher @search
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

  @teacher @settings
  Scenario: Default Settings
    Given I browse to Settings Page as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see class name
    And I should see following settings under LitPro Test Settings:
      | Setting Name                                                  | Enabled | Default Value |
      | Minimum number of days between completed tests                | Yes     | 50            |
      | Require students to take practice test                        | Yes     | NA            |
      | Limit reading list titles to only those in the school library | No      | NA            |
      | Limit number of books in reading list to                      | No      |               |
      | Show student Lexile measure after test completion             | Yes     | NA            |
      | Limit test access to certain days and times                   | No      | NA            |
      | Allow students to see their reading list                      | Yes     | NA            |
      | Limit reading list to titles with book quizzes                | Yes     | NA            |
      | Limit practice test to one per student                        | Yes     | NA            |
      | Allow students to change reading interests                    | Yes     | NA            |
    And I should see following settings under Book Quiz Settings:
      | Setting Name                                       | Enabled | Default Value |
      | Display incorrect answers                          | Yes     | NA            |
      | Allow student to print                             | No      | NA            |
      | Quiz pass mark                                     | NA      | 70            |
      | Limit quiz access to certain days and times        | No      | NA            |
      | Include teacher-added points in report totals      | No      | NA            |
      | Restrict number of quiz attempts to                | Yes     | 3             |
      | Number of days between quiz attempts               | Yes     | 7             |
      | Allow students to search outside educational level | No      | NA            |

  @teacher @student @quiz @settings @myresults
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

  @teacher @benchmark
  Scenario: Benchmark UI
    Given I browse to Benchmark Page as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see Benchmark Page Page Header starting with "Benchmark Proficiency Bands for"
    And I should see default selected benchmark as "LitPro Standard"
    And I should see benchmark table with following category
      | Below Basic | Basic | Proficient | Advanced |
    When I click on Save
    Then 'Banchmark Saved' Message should display

  @teacher @student @test @settings @reports @metrics
  Scenario: Change Settings, Take Test,  Metrics, Charts and Reports
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
    Given I browse to LitPro Test Page as "Student"
      | UserName | StudOne  |
      | Password | welcome1 |
    Then I should see welcome message with get started button
    When I click on lets get started button
    And I complete the assessment
    Then I should be shown 'Create My Reading list' Button
    When I click 'Create My Reading list' Button
    Then Home Page should be shown
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
