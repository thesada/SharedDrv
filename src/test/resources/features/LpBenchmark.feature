@benchmark
Feature: LitPro Benchmark
  As a LitPro User
  I should be able to changes Benchmarks
  So that I can ....

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

   
