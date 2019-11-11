Feature: Calendar

  In order to find a flight on desired day
  As a user
  I want to use calendar to find a flight

  Acceptance criteria:
  - user is able to select month/months as a range for search
  - user is able to select a season as a range for search
  - user is able to select a specific day for search
  - user is able to see the cheapest price for the selected date/dates

  Background:
    Given User is on the main page
    And opens calendar

  Scenario Outline: User searches the cheapest flight within selected months
    When User fills in destination '<from>' and '<to>'
    And Selects month - '<month>'
    And Selects duration 10 days and starts search
    Then If there are any search results found
    Then User gets a table of prices for selected month - '<month>'
    And User gets the cheapest flight
    Examples:
      | from   | to              | month |
      | Москва | Санкт-Петербург | июнь  |
      | Москва | Анталия         | июль  |


  Scenario Outline: User searches the cheapest flight within selected season
    When User fills in destination '<from>' and '<to>'
    And Selects desired season - '<season>'
    And Selects duration 10 days and starts search
    Then If there are any search results found
    Then User gets a table of prices for selected season - '<season>'
    And User gets the cheapest flight
    Examples:
      | from   | to              | season |
      | Москва | Санкт-Петербург | Лето   |
      | Москва | Анталия         | Осень  |

  Scenario Outline: User searches the cheapest flight within selected day
    When User fills in destination '<from>' and '<to>'
    And Selects desired date - '<date>'
    And Selects duration 10 days and starts search
    Then If there are any search results found
    Then User gets a table of prices for selected day - '<date>'
    And User gets the cheapest flight
    Examples:
      | from   | to              | date       |
      | Москва | Санкт-Петербург | 01/01/2020 |
      | Москва | Анталия         | 10/02/2020 |