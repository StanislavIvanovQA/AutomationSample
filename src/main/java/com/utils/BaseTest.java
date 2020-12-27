package com.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

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

        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/log4j2.xml");
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
