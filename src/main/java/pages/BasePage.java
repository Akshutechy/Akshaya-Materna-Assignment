package pages;

import driver.DriverManager;
import enums.WaitStrategy;
import factories.ExplicitWaitFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import reports.ExtentLogger;

import java.util.List;

public class BasePage {

    protected void sendKeys(By by, String valueToEnter, WaitStrategy waitStrategy, String elementName) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy).sendKeys(valueToEnter);
        ExtentLogger.pass(valueToEnter + " is entered in the " + elementName);
    }

    protected void click(By by, WaitStrategy waitStrategy, String elementName) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy).click();
        ExtentLogger.pass(elementName + " is clicked");
    }

    protected List<WebElement> findElements(By by, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy);
        return DriverManager.getDriver().findElements(by);
    }

    protected void switchToFrame(By by, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy);
        DriverManager.getDriver().switchTo().frame(DriverManager.getDriver().findElement(by));
    }

    protected void switchToParentFrame() {
        DriverManager.getDriver().switchTo().parentFrame();
    }

    //Other mostly used Base methods. Added for reference
    protected void sendKeyboardButtons(By by, Keys keyToPress, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy);
        DriverManager.getDriver().findElement(by).sendKeys(keyToPress);
    }


    protected void clickAndSendKeys(By by, String valueToEnter, WaitStrategy waitStrategy, String elementName) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy).click();
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy).sendKeys(valueToEnter);
        ExtentLogger.pass(valueToEnter + " is entered in the " + elementName);
    }

    protected void sendKeysSlowType(By by, String valueToEnter, WaitStrategy waitStrategy, String elementName) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy);
        for (char c : valueToEnter.toCharArray()) {
            String s = String.valueOf(c);
            DriverManager.getDriver().findElement(by).sendKeys(s);
            waitForDuration(200);
        }
        ExtentLogger.pass(valueToEnter + " is entered in the " + elementName);
    }

    protected void jsSendKeys(By by, String valueToEnter, WaitStrategy waitStrategy, String elementName) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].setAttribute('value', arguments[1])", DriverManager.getDriver().findElement(by), valueToEnter);
        ExtentLogger.pass(valueToEnter + " is entered in the " + elementName);
    }


    protected void forceClick(By by, WaitStrategy waitStrategy, String elementName) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].click();", DriverManager.getDriver().findElement(by));
        ExtentLogger.pass(elementName + " is clicked");
    }

    protected void selectCheckbox(By by, String elementName) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].click();", DriverManager.getDriver().findElement(by));
        ExtentLogger.pass(elementName + " is clicked");
    }

    protected void selectNonStaticDropDown(By dropDownField, By dropDownContainer, String dropDownName, String valueToSelect) {
        click(dropDownField, WaitStrategy.CLICKABLE, dropDownName);
        List<WebElement> allOptions = findElements(dropDownContainer, WaitStrategy.PRESENT);
        // Iterate the list using for loop
        for (WebElement allOption : allOptions) {
            if (allOption.getText().contains(valueToSelect)) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                allOption.click();
                break;
            }
        }
        ExtentLogger.pass(dropDownName + " dropdown is selected with value " + valueToSelect);
    }

    protected String getText(By by, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy);
        return DriverManager.getDriver().findElement(by).getText();
    }

    protected void uploadFiles(By by, String filePath, WaitStrategy waitStrategy) {
        ExplicitWaitFactory.performExplicitWait(by, waitStrategy).sendKeys(filePath);
        ExtentLogger.pass("File is Uploaded");
    }

    public static void waitForDuration(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
