package pages;

import enums.WaitStrategy;
import org.openqa.selenium.By;

public final class SearchResultPage extends BasePage {

    private final By firstSearchResultLink = By.xpath("(//div[@id='search']//a)[1]");

    public void clickFirstSearchResult() {
        click(firstSearchResultLink, WaitStrategy.CLICKABLE, "First Search Result Link");
    }
}