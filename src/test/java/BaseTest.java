import driver.Base;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected BaseTest() {
    }

    @BeforeMethod
    public void startup() {
        Base.getDriver();
    }

    @AfterMethod
    public void tearDown() {
        Base.quitDriver();
    }
}
