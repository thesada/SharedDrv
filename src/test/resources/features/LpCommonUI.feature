@common
Feature: LitPro Common UI
  As a LitPro User
  I should have access to Literacy Pro
  So that I can take quizzes, view my results and ...

  @student
  Scenario: LitPro Common UI - Student
    When I launch LitPro as "Student"
      | UserName | StudOne  |
      | Password | welcome1 |
    Then I should see Scholastic Literacy Pro Home Page
    And I should see user greeting text "Welcome, "
    And I should see below tabs:
      | Home | Search | My Results | LitPro Test |
    And I should see Logout link

  @teacher
  Scenario: LitPro Common UI - Teacher
    When I launch LitPro as "Teacher"
      | UserName | HarryPotter |
      | Password | welcome1    |
    Then I should see Scholastic Literacy Pro Home Page
    And I should see user greeting text "Welcome, "
    And I should see below tabs:
      | Home | Reports | Search | Settings | Benchmarks |
    And I should see Logout link
