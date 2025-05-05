package listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentLogger;
import reports.ExtentReport;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Listener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        ExtentReport.initReports();
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            ExtentReport.flushReports();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReport.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentLogger.pass(result.getMethod().getMethodName() + " is passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentLogger.fail(result.getMethod().getMethodName() + " is failed");
        ExtentLogger.fail(result.getThrowable().toString());
        Throwable throwable = result.getThrowable();

        if (throwable != null) {
            // Get the stack trace elements
            StackTraceElement[] stackTrace = throwable.getStackTrace();

            // Create a StringWriter to format the stack trace
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            // Write each stack trace element with a better format
            pw.println("Exception Stack Trace:");
            for (StackTraceElement element : stackTrace) {
                pw.println("   " + element.toString()); // Customize the format as needed
            }

            // Get the formatted stack trace as a string
            String formattedStackTrace = sw.toString();

            // Log the formatted stack trace to Extent report
            ExtentLogger.fail(formattedStackTrace);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentLogger.skip(result.getMethod().getMethodName() + " is skipped");
    }
}
