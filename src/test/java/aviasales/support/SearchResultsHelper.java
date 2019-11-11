package aviasales.support;

import org.apache.commons.collections4.ListUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class SearchResultsHelper extends HelperBase {
    public SearchResultsHelper (WebDriver wd) {
        super(wd);
    }

    public void filterByCheapest() {
        waitForSearchResultsToLoad();
        if (wd.findElement(By.xpath("//li[@class='sorting__tab is-active']//span[@class='sorting__title-wrap']")).getText()
                != "Самый дешевый") {
            wd.findElement(By.xpath("//span[@class='sorting__title-wrap'][contains(text(),'Самый дешевый')]")).click();
        }
    }

    private void waitForSearchResultsToLoad() {
        switchToSearchResultsInNewWindow();
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(wd, 40);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.sorting__tab.is-active")));
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public boolean isAnySearchResultsFound() {
        switchToSearchResultsInNewWindow();
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(wd, 30);
        boolean searchResultsFound = true;
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.sorting__tab.is-active")));
        } catch (TimeoutException e) {
            searchResultsFound = false;
        }
        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return searchResultsFound;
    }

    public void switchToSearchResultsInNewWindow() {
        for(String winHandle : wd.getWindowHandles()){
            wd.switchTo().window(winHandle);
        }
    }

    public void filterByTransfer(int transfer) {
        if (transfer == 0) {
            try {
                if (wd.findElement(By.cssSelector("input#stops_1")).isSelected()) {
                    clickWithRetrial(By.cssSelector("label[for='stops_1'] span.checkbox__face"));
                } else if (isElementPresent(By.cssSelector("label[for='stops_2'] span.checkbox__face"))) {
                    if(wd.findElement(By.cssSelector("input#stops_2")).isSelected())
                        clickWithRetrial(By.cssSelector("label[for='stops_2'] span.checkbox__face"));
                } else if(isElementPresent(By.cssSelector("label[for='stops_3'] span.checkbox__face"))) {
                    if (wd.findElement(By.cssSelector("input#stops_3")).isSelected())
                        clickWithRetrial(By.cssSelector("label[for='stops_3'] span.checkbox__face"));
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    public void filterByBuggage(boolean hasBuggage) {
        WebElement depAndArrTime = wd.findElement(By.cssSelector("div.filter__content div:nth-child(2) div.filter__segment:nth-child(2)"));

        ((JavascriptExecutor)wd).executeScript("arguments[0].scrollIntoView();", depAndArrTime);
        clickWithRetrial(By.cssSelector("div.--baggage div"));

        if (hasBuggage) {
            if (isElementPresent(By.cssSelector("label[for='baggage_no_baggage'] span")))
                wd.findElement(By.cssSelector("label[for='baggage_no_baggage'] span")).click();
        } else {
            wd.findElement(By.cssSelector("label[for='baggage_full_baggage'] span.checkbox")).click();
        }

        scrollToTop();
    }

    private void scrollToTop() {
        WebElement element = wd.findElement(By.cssSelector("span.navbar__logo-text"));

        JavascriptExecutor js = (JavascriptExecutor)wd;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public String getDepartureAirport(int routeNumber) {
        String airport = "";

        while (airport == "") {

            try {
                if (isElementPresent(By.xpath("(//div[@class='ticket-desktop__segment'])[" + routeNumber + "]//div[@class='segment-route__endpoint origin']//div[@class='segment-route__city']"))) {
                    airport = wd.findElement(By.xpath("(//div[@class='ticket-desktop__segment'])[" + routeNumber + "]//div[@class='segment-route__endpoint origin']//div[@class='segment-route__city']")).getText();
                }
            } catch (StaleElementReferenceException e) {
                e.printStackTrace();
            }
        }

        return airport;
    }

    public String getArrivalAirport(int routeNumber) {
        return wd.findElement(By.xpath("(//div[@class='ticket-desktop__segment'])[" + routeNumber + "]//div[@class='segment-route__endpoint destination']//div[@class='segment-route__city']")).getText();
    }

    public List<WebElement> getTicketsWithBaggageOption() {
        int size = 0;
        List<WebElement> withBuggage = new ArrayList<>();
        List<WebElement> baggageUnknown = new ArrayList<>();

        try {
            while (size == 0) {
                baggageUnknown =  wd.findElements(By.xpath("//div[@class='ticket-tariffs__title'][contains(text(),'Багаж неизвестен')]"));
                withBuggage = wd.findElements(By.xpath("//div[@class='ticket-tariffs__title'][contains(text(),'С багажом')]"));
                size = withBuggage.size();
            }
        } catch (StaleElementReferenceException e) {
            System.err.println("StaleElementReferenceException caught");
        }
        List<WebElement> result = ListUtils.union(withBuggage, baggageUnknown);

        return result;
    }

    public int getAmountOfTicketsFound() {
        int amountOfTickets = wd.findElements(By.xpath("//a[@class='b-button buy-button__button --primary --size-l']")).size();
        return amountOfTickets;
    }
}
