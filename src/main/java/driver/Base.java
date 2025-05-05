package driver;

import enums.ConfigProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import utils.ReadPropertyFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Base {
    private Base() {
    }

    private static WebDriver driver;

    public static void getDriver() {
        if (Objects.isNull(DriverManager.getDriver())) {
            String browser = ReadPropertyFile.getValue(ConfigProperties.BROWSER).toLowerCase();
            if (browser.equals("chrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-gpu", "--window-size=1920,1080");
                // Use your updated user-agent
                options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.7103.48 Safari/537.36");
                // Disable automation flags
                options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);
                // Add additional arguments that might help
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--no-sandbox");
                driver = new ChromeDriver(options);
            } else if (browser.equals("firefox")) {
                // Create Firefox profile with enhanced anti-detection settings
                FirefoxProfile profile = new FirefoxProfile();

                // Fix the duplicate FirefoxOptions declaration
                FirefoxOptions options = new FirefoxOptions();

                // Set a more common Firefox user agent (slightly older version might help)
                profile.setPreference("general.useragent.override",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:136.0) Gecko/20100101 Firefox/136.0");

                // Critical: Disable WebDriver mode
                profile.setPreference("dom.webdriver.enabled", false);
                profile.setPreference("useAutomationExtension", false);

                // Disable Firefox automation indicators
                profile.setPreference("marionette", false);
                profile.setPreference("marionette.port", 0);

                // Disable WebRTC to prevent IP leakage
                profile.setPreference("media.peerconnection.enabled", false);
                profile.setPreference("media.navigator.enabled", false);

                // Disable various telemetry and tracking
                profile.setPreference("privacy.trackingprotection.enabled", true);
                profile.setPreference("browser.tabs.remote.autostart", false);
                profile.setPreference("browser.tabs.remote.autostart.2", false);

                // Disable Firefox-specific automation flags
                profile.setPreference("devtools.selfxss.count", 5);

                // Mimic regular user behavior
                profile.setPreference("app.shield.optoutstudies.enabled", false);
                profile.setPreference("dom.disable_beforeunload", true);
                profile.setPreference("browser.sessionstore.resume_from_crash", false);

                // Disable Firefox updates and notifications
                profile.setPreference("app.update.enabled", false);
                profile.setPreference("app.update.auto", false);

                // Set language and locale to common values
                profile.setPreference("intl.accept_languages", "en-US, en");

                // Add cookie settings that mimic regular browser
                profile.setPreference("network.cookie.cookieBehavior", 0);
                profile.setPreference("network.cookie.lifetimePolicy", 0);

                // Apply the profile to options
                options.setProfile(profile);

                // Add Firefox-specific arguments
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--no-sandbox");

                // Set page load strategy
                options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);

                // Create the driver with enhanced options
                driver = new FirefoxDriver(options);

                // Execute JavaScript to modify navigator properties (additional stealth)
                try {
                    org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                    js.executeScript(
                            "Object.defineProperty(navigator, 'webdriver', {get: () => undefined});"
                    );
                } catch (Exception e) {
                    System.out.println("Failed to execute JavaScript: " + e.getMessage());
                }
            }

            DriverManager.setDriver(driver);
            DriverManager.getDriver().manage().window().maximize();
            DriverManager.getDriver().get(ReadPropertyFile.getValue(ConfigProperties.URL));
        }
    }

    public static void quitDriver() {
        if (Objects.nonNull(DriverManager.getDriver())) {
            DriverManager.getDriver().quit();
            DriverManager.unload();
        }
    }
}