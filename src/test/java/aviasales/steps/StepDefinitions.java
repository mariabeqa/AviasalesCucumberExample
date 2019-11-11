package aviasales.steps;

import aviasales.Booking;
import aviasales.support.ApplicationManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StepDefinitions {
    private final ApplicationManager app;
    private List<String> selectedSeasonMonths = new ArrayList<>();

    public StepDefinitions(ApplicationManager applicationManager) {
        this.app = applicationManager;
    }

    @Given("^User is on the main page$")
    public void user_is_on_the_main_page() throws IOException {
        app.checkUserIsOnMainPage();
    }

    @Given("^opens calendar$")
    public void opens_calendar() {
        app.openCalendar();
    }

    @When("User fills in destination {string} and {string}")
    public void user_fills_in_destination(String from, String to) {
        Booking booking = new Booking().withDepartureAirports(Arrays.asList(from))
                .withDestinationAirports(Arrays.asList(to));
        app.calendar().enterSearchParameters(booking, false);
    }

    @When("Selects month - {string}")
    public void selects_desired_month(String month) {
        String monthTruncated = month.toLowerCase().substring(0, 3);
        app.calendar().selectMonth(monthTruncated);
    }

    @When("Selects desired season - {string}")
    public void selects_desired_season(String range) {
        selectedSeasonMonths = app.calendar().selectSeason(range);
    }

    @When("^Selects duration (\\d+) days and starts search$")
    public void selects_duration_days_and_starts_search(int duration) {
        app.calendar().setDurationTo10Days();
        app.calendar().search();
    }

    @Then("^If there are any search results found$")
    public void if_there_are_any_search_results_found() {
        if (!app.calendar().isSearchResultsFound()) {
            System.out.println("No search results found");
            app.stop();
        }
    }

    @When("Selects desired date - {string}")
    public void selects_desired_date(String date) {
        app.calendar().selectExactDepartureDate(date);
    }

    @Then("User gets a table of prices for selected month - {string}")
    public void user_gets_a_table_of_prices_for_selected_month(String month) {
        Assert.assertEquals(app.calendar().getMonthInSearchResults(), month.toLowerCase().substring(0,3));
    }

    @Then("User gets a table of prices for selected season - {string}")
    public void user_gets_a_table_of_prices_for_selected_season(String season) {
        Assert.assertEquals(app.calendar().getMonthsInSearchResults(), selectedSeasonMonths);
    }

    @Then("User gets a table of prices for selected day - {string}")
    public void user_gets_a_table_of_prices_for_selected_day(String day) {
        Assert.assertEquals(app.calendar().getDayInSearchResults(), app.calendar().parseDate(day).get("day"));
    }

    @Then("^User gets the cheapest flight$")
    public void user_gets_the_cheapest_flight() {
        String cheapestPriceOnTheButton = app.calendar().getCheapestPrice();
        Assert.assertEquals(app.calendar().getCheapestPriceFromTheTable(), cheapestPriceOnTheButton);
    }
}
