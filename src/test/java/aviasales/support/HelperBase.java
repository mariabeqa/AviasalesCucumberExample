package aviasales.support;

import org.openqa.selenium.*;

public class HelperBase {
    protected WebDriver wd;

    public HelperBase(WebDriver wd) {
        this.wd = wd;
    }

    public boolean clickWithRetrial(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 3) {
            try {
                wd.findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException | ElementClickInterceptedException | NoSuchElementException e) {
            } catch (ElementNotInteractableException e) {
                wd.navigate().refresh();
            }
            attempts++;
        }
        return result;
    }

    protected boolean isElementPresent(By locator) {
        try {
            wd.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getAttributeTextWithRetrial(By by, String attribute) {
        String result = null;

        while(result == null) {
            try {
                String text = wd.findElement(by).getAttribute(attribute);
                result = text;
            } catch(StaleElementReferenceException | ElementClickInterceptedException | NoSuchElementException e) {
            }
        }
        return result;
    }
}
