package com.mailRu.pages;

import com.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.utils.Utils.waitForAjax;
import static com.utils.Utils.clickWithSelenium;
import static com.utils.Utils.elementIsDisplayed;
import static com.utils.Utils.getCurrentPageTitle;

public class MailRuEmailBoxMainPage implements IMailRuPage {
    private static Logger LOGGER = LogManager.getLogger(MailRuMainPage.class);

    @FindBy(xpath = "//span[@class='compose-button__wrapper']/span")
    private WebElement newEmailButton;

    @FindBy(xpath = "//div[@class='compose-app__compose']")
    private WebElement newEmailModal;

    @FindBy(xpath = "//a[@href='/drafts/']//div[@class='nav__folder-name__txt']")
    private WebElement draftFolder;

    @FindBy(xpath = "//a[@title='выход']")
    private WebElement logoutExitButton;

    @FindBy(xpath = "//a[@id='PH_authLink']")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='nav__folder-name__txt' and text()='Отправленные']")
    private WebElement sentMessagesFolder;

    public MailRuEmailBoxMainPage() {
        WebDriver driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return getCurrentPageTitle();
    }

    public MailRuNewEmailModal openNewEmailForm() {
        LOGGER.info("Press new email button");
        clickWithSelenium(newEmailButton);
        if (!elementIsDisplayed(newEmailModal)) {
            LOGGER.info("WARNING: Modal does not appear");
        } else {
            LOGGER.info("Email modal opened");
        }
        return new MailRuNewEmailModal(this);
    }

    public MailRuDraftEmailsPage openDraftFolder() {
        LOGGER.info("Open draft folder");
        clickWithSelenium(draftFolder);
        waitForAjax();
        return new MailRuDraftEmailsPage();
    }

    public MailRuEmailBoxMainPage logout() {
        LOGGER.info("Press logout button");
        clickWithSelenium(logoutExitButton);
        return this;
    }

    public boolean checkLoginStatus() {
        LOGGER.info("Check login status");
        boolean loggedOut = elementIsDisplayed(loginButton);
        LOGGER.info("Do we logged out: " + loggedOut);
        return loggedOut;
    }

    public MailRuSentEmailsFolderPage openSentEmails() {
        LOGGER.info("Open sent messages folder");
        clickWithSelenium(sentMessagesFolder);
        return new MailRuSentEmailsFolderPage();
    }
}
