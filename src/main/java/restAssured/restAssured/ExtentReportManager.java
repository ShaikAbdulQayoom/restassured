// File: src/main/java/restAssured/restAssured/ExtentReportManager.java

package restAssured.restAssured;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;

    public static synchronized void initializeExtentReports() {
        if (extent == null) {
            sparkReporter = new ExtentSparkReporter("extent-report.html");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            initializeExtentReports();
        }
        return extent;
    }

    public static synchronized ExtentTest createTest(String name, String description) {
        return getInstance().createTest(name, description);
    }

    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
