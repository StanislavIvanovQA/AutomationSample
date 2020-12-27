package com.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listeners implements ITestListener {
    private ExtentReports extent = ExtentReporter.getReportObject();
    private ExtentTest test;
    private static Logger LOGGER = LogManager.getLogger(Listeners.class);

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("Starting test");
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info(result.getTestClass().getTestName() + " passes successfully.");
        test.log(Status.PASS, result.getTestClass().getTestName() + ": Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver;

        test.fail(result.getThrowable());

        try {
            driver = DriverManager.getDriver();
            String screenShotPath = Utils.getScreenShotPath(result.getMethod().getMethodName(), driver);
            String[] splittedScreenshotPathArray = screenShotPath.split("\\\\");
            test.addScreenCaptureFromPath(splittedScreenshotPathArray[splittedScreenshotPathArray.length - 1]);
            LOGGER.info(result.getTestClass().getTestName() + " failed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
