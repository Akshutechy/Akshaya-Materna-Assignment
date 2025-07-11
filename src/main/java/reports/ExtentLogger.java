package reports;

import com.aventstack.extentreports.MediaEntityBuilder;
import enums.ConfigProperties;
import utils.ReadPropertyFile;
import utils.ScreenshotUtils;

public final class ExtentLogger {
    private ExtentLogger() {
    }

    public static void pass(String message) {
        try {
            if (ReadPropertyFile.getValue(ConfigProperties.PASSEDSTEPSSCREENSHOT).equalsIgnoreCase("yes")) {
                ExtentManager.getExtentTest().pass(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            } else {
                ExtentManager.getExtentTest().pass(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void fail(String message) {
        try {
            ExtentManager.getExtentTest().fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void skip(String message) {
        try {
            if (ReadPropertyFile.getValue(ConfigProperties.PASSEDSTEPSSCREENSHOT).equalsIgnoreCase("yes")) {
                ExtentManager.getExtentTest().skip(message, MediaEntityBuilder.createScreenCaptureFromBase64String(ScreenshotUtils.getBase64Image()).build());
            } else {
                ExtentManager.getExtentTest().skip(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
