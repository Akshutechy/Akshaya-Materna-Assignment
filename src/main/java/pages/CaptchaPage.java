package pages;

import enums.WaitStrategy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

public final class CaptchaPage extends BasePage {

    private final By captchaIframe = By.cssSelector("iframe[title='reCAPTCHA']");
    private final By captchaCheckBox = By.cssSelector("#recaptcha-anchor>.recaptcha-checkbox-border");

    public SearchResultPage acceptCaptcha() {
        try {
            switchToFrame(captchaIframe, WaitStrategy.PRESENT);
            click(captchaCheckBox, WaitStrategy.CLICKABLE, "Captcha Check box");
            switchToParentFrame();
        } catch (TimeoutException e) {
            System.out.println("Captcha did not appear");
        }
        return new SearchResultPage();
    }
}