package com.utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected static WebDriver driver;
    protected static String url;
    protected static String login;
    protected static String password;
    protected static String browserName;
    protected static boolean isHeadless;

    protected static void getProperties(String propertiesPath) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(System.getProperty("user.dir") + propertiesPath));
        url = properties.getProperty("url");
        login = properties.getProperty("login");
        password = properties.getProperty("password");
        browserName = properties.getProperty("browser");
        isHeadless = Boolean.parseBoolean(properties.getProperty("isHeadless"));
    }

    @BeforeMethod
    protected static void openBrowser() throws IOException {
        driver = initializeDriver();
        driver.get(url);
    }

    @AfterMethod
    protected static void closeBrowser() {
        driver.quit();
    }

    protected static WebDriver initializeDriver() {
        return DriverManager.initialize(browserName, isHeadless);
    }
}
