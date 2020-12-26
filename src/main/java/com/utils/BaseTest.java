package com.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;
    Properties properties = new Properties();
    String url;
    String propertiesPath = "\\src\\main\\resources\\data.properties";

    @BeforeTest
    public void openBrowser() throws IOException {
        driver = initializeDriver();
        driver.get(url);
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    public WebDriver initializeDriver() throws IOException {
        System.out.println(System.getProperty("user.dir"));
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + propertiesPath);
        properties.load(fis);
        String browserName = properties.getProperty("browser");
        boolean isHeadless = Boolean.parseBoolean(properties.getProperty("isHeadless"));
        url = properties.getProperty("url");

        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (isHeadless) {
                    options.setHeadless(true);
                }
                driver = new ChromeDriver(options);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return driver;
    }
}
