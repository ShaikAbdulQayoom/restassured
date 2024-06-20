// File: src/main/java/restAssured/restAssured/BaseTest.java

package restAssured.restAssured;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    private static Properties properties = new Properties();
    private static ExtentTest extentTest;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void setupExtentReports() {
        ExtentReportManager.initializeExtentReports();
    }

    @BeforeMethod
    public void setupTestMethod() {
        String testName = Thread.currentThread().getStackTrace()[2].getMethodName();
        extentTest = ExtentReportManager.createTest(testName, "Test Description");
    }

    @AfterMethod
    public void tearDownTestMethod() {
        // Log that the test has ended.
        extentTest.log(Status.INFO, "Test completed");
    }

    @AfterSuite
    public void tearDownSuite() {
        ExtentReportManager.flushReport();
    }

    public static String getBaseUrl() {
        return properties.getProperty("base_url");
    }

    public static ExtentTest getExtentTest() {
        return extentTest;
    }

    public static void logExtentTest(Status status, String message) {
        extentTest.log(status, message);
    }
}
