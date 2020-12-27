package com.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class Utils {
    private static Logger LOGGER = LogManager.getLogger(Utils.class);
    private static final int WAIT_STANDART_VALUE = 5;
    private static final int WAIT_LOW_VALUE = 1;

    private static WebDriver getWebDriver() {
        return DriverManager.getDriver();
    }

    public static String getCurrentTimestamp() {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss-SSS");
        String timestampString = dateFormat.format(timestamp);
        return timestampString;
    }

    public static String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File file = screenshot.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + getCurrentTimestamp() + ".png";
        FileUtils.copyFile(file, new File(destinationFile));
        return destinationFile;
    }

    public static void clearFieldAndSendKeys(WebElement element, String keys) {
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        element.clear();
        element.sendKeys(keys);
    }

    public static void sendKeys(WebElement element, String keys) {
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        element.sendKeys(keys);
    }

    public static boolean checkElementText(WebElement element, String expectedText) {
        return element.getText().equals(expectedText);
    }

    public static String getElementText(WebElement element) {
        return element.getText();
    }

    public static String getAttributeValue(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    public static boolean elementIsDisplayed(WebElement element) {
        waitForElementToBeVisible(element);
        return element.isDisplayed();
    }

    public static void clickWithSelenium(WebElement element) {
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        element.click();
    }

    public static void clickWithSeleniumWithoutVisibilityAndClickabilityChecks(WebElement element) {
        element.click();
    }

    public static void clickWithJS(WebElement element) {
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        JavascriptExecutor executor = DriverManager.getJavaScripExecutor();
        executor.executeScript("arguments[0].click();", element);
        element.click();
    }

    private static void waitForElementToBeClickable(WebElement element) {
        DriverManager.getWebDriverWaitWithPolling(WAIT_STANDART_VALUE).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementToBeVisible(WebElement element) {
        DriverManager.getWebDriverWaitWithPolling(WAIT_STANDART_VALUE).until(ExpectedConditions.visibilityOf(element));
    }

    private static void longWait(WebElement element) {
        for (int i = 0; i < 10; i++) {
            try {
                DriverManager.getWebDriverWait(WAIT_LOW_VALUE).until(ExpectedConditions.visibilityOf(element));
                break;
            } catch (TimeoutException te) {
                if (i == 9) {
                    te.printStackTrace();
                }
            }
        }

    }

    public static String getCurrentPageTitle() {
        String title = getWebDriver().getTitle();
        LOGGER.info("Get current page title. Current page title is: " + title);
        return title;
    }

    //This method is just for fun. Using independent online services like this in tests leads to low stability.
    public static String getFishText() {
        HttpGet httpGet = new HttpGet("https://fish-text.ru/get");
        httpGet.addHeader("type", "sentence");
        httpGet.addHeader("number", "30");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JsonObject jsonObject = null;

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            jsonObject = new Gson().fromJson(EntityUtils.toString(httpResponse.getEntity()), JsonObject.class);
        } catch (ParseException pe) {
            pe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        String result = jsonObject.get("text").getAsString();

        return result;
    }

    public static void waitForAjax() {
        JavascriptExecutor executor = DriverManager.getJavaScripExecutor();
        Duration duration = Duration.of(100, ChronoUnit.MILLIS);
        Actions wait = DriverManager.getActions().pause(duration);
        while (true) {
            boolean ajaxIsComplete = Boolean.parseBoolean(executor.executeScript("return jQuery.active == 0").toString());
            if (ajaxIsComplete)
                break;
            wait.build().perform();
        }
    }

    public static String getTextFromFirstNode(WebElement e) {
        String text = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children) {
            text = text.replaceFirst(child.getText(), "").trim();
        }
        return text;
    }

    public static void lowWait() {
        Duration duration = Duration.of(1000, ChronoUnit.MILLIS);
        DriverManager.getActions().pause(duration).build().perform();
    }

}
