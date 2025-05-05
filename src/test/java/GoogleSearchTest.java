import driver.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public final class GoogleSearchTest extends BaseTest {
    String searchText = "Active sync";
    String titleNotExpected = "Google";

    @Test
    public void googleKeywordSearchTest() throws InterruptedException {
        HomePage homePage = new HomePage();

        homePage.enterSearchText(searchText)
                .clickSearchButton()
                .acceptCaptcha()
                .clickFirstSearchResult();
        Assert.assertNotEquals(DriverManager.getDriver().getTitle(), titleNotExpected, "The page title should not be Google after clicking first search result");
    }
}