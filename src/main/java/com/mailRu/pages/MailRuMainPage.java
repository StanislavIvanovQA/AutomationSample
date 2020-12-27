package com.mailRu.pages;

import com.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.utils.Utils.checkElementText;
import static com.utils.Utils.clearFieldAndSendKeys;
import static com.utils.Utils.clickWithSelenium;
import static com.utils.Utils.getCurrentPageTitle;
import static com.utils.Utils.waitForElementToBeVisible;

public class MailRuMainPage implements IMailRuPage {
    private static Logger LOGGER = LogManager.getLogger(MailRuMainPage.class);

    @FindBy(xpath = "//div[@id='mailbox']//input[@name='login']")
    private WebElement loginField;

    @FindBy(xpath = "//div[@id='mailbox']//select[@name='domain']")
    private WebElement domainDropbox;

    @FindBy(xpath = "//div[@id='mailbox']//button[@type='button' and contains(text(), 'Ввести пароль')]")
    private WebElement enterPasswordButton;

    @FindBy(xpath = "//div[@id='mailbox']//input[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//div[@id='mailbox']//button[@type='button' and contains(text(), 'Войти')]")
    private WebElement enterMailBoxButton;

    @FindBy(xpath = "//span[@class='compose-button__wrapper']/span")
    private WebElement newEmailButton;

    public String getPageTitle() {
        return getCurrentPageTitle();
    }

    public MailRuMainPage() {
        WebDriver driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public MailRuEmailBoxMainPage loginToMailRuMailService(String login, String password) {
        LOGGER.info("Login to email box");
        LOGGER.info("Enter login to login field");
        clearFieldAndSendKeys(loginField, login);
        LOGGER.info("Check if default domain is right: @mail.ru");
        checkElementText(domainDropbox, "@mail.ru");
        LOGGER.info("Click enter password button");
        clickWithSelenium(enterPasswordButton);
        LOGGER.info("Enter password");
        clearFieldAndSendKeys(passwordField, password);
        LOGGER.info("Click enter mail box button");
        clickWithSelenium(enterMailBoxButton);

        waitForElementToBeVisible(newEmailButton);

        return new MailRuEmailBoxMainPage();
    }
}
