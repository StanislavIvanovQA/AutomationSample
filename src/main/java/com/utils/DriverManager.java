package com.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static WebDriver driver;

    public static WebDriver initialize(String browser, boolean isHeadless) {

        switch (browser) {
            case "chrome":
                DriverManager.setChrome(isHeadless);
                driver = DriverManager.getDriver();
                break;

            case "firefox":
                DriverManager.setFirefox();
                driver = DriverManager.getDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return getDriver();
    }

    public static void setChrome(boolean isHeadless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        if (isHeadless) {
            options.setHeadless(true);
        }
        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();
    }

    public static void setFirefox() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    protected static WebDriverWait getWebDriverWaitWithPolling(int wait) {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), wait);
        webDriverWait.pollingEvery(Duration.ofMillis(100));
        return webDriverWait;
    }

    protected static WebDriverWait getWebDriverWait(int wait) {
        WebDriverWait webDriverWait = new WebDriverWait(getDriver(), wait);
        return webDriverWait;
    }

    protected static JavascriptExecutor getJavaScripExecutor() {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        return executor;
    }

    protected static Actions getActions() {
        Actions actions = new Actions(getDriver());
        return actions;
    }

}
