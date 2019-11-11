package aviasales.support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHelper extends HelperBase{
    public FileHelper(WebDriver wd) {
        super(wd);
    }

    public void saveTickets(String path) {
        List<String> prices = getListOfTicketPrices();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            for (String price : prices) {
                bw.write(price + System.lineSeparator());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getListOfTicketPrices() {
        List<WebElement> pricesOnButton = wd.findElements(By.cssSelector("span.buy-button__price span.price.--rub"));
        List<String> prices = new ArrayList<>();

        int count = 1;
        for (WebElement el : pricesOnButton) {
            prices.add(count + ". " + el.getText() + " рублей");
            count++;
        }
        return prices;
    }
}
