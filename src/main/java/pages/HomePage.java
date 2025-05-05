package pages;

import enums.WaitStrategy;
import org.openqa.selenium.By;

public final class HomePage extends BasePage {

    private final By searchBar = By.id("APjFqb");
    private final By searchButton = By.xpath("(//input[@value='Google Search'])[1]");

    public HomePage enterSearchText(String textToEnter) {
        sendKeys(searchBar, textToEnter, WaitStrategy.PRESENT, "Search Bar");
        return this;
    }

    public CaptchaPage clickSearchButton() {
        click(searchButton, WaitStrategy.CLICKABLE, "Search Button");
        return new CaptchaPage();
    }
}