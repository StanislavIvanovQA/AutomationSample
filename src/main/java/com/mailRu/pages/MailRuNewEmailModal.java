package com.mailRu.pages;

import com.mailRu.objects.Email;
import com.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.utils.Utils.clickWithSelenium;
import static com.utils.Utils.elementIsDisplayed;
import static com.utils.Utils.getAttributeValue;
import static com.utils.Utils.getElementText;
import static com.utils.Utils.lowWait;
import static com.utils.Utils.sendKeys;

public class MailRuNewEmailModal<T extends IMailRuPage> {
    private T parentPage;
    private static Logger LOGGER = LogManager.getLogger(MailRuNewEmailModal.class);

    public MailRuNewEmailModal(T parentPage) {
        this.parentPage = parentPage;
        WebDriver driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='compose-app__compose']//div[@data-name='to']//input")
    private WebElement toInputField;

    @FindBy(xpath = "//div[@data-type='to']//div[@data-type='to']//span")
    private WebElement toInputFieldPopulated;

    @FindBy(xpath = "//div[@class='compose-app__compose']//input[@name='Subject']")
    private WebElement subjectInputField;

    @FindBy(xpath = "//div[@class='compose-app__compose']//div[@role='textbox']")
    private WebElement emailBodyInputField;

    @FindBy(xpath = "//span[@class='button2 button2_base button2_hover-support js-shortcut']")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//span[@class='notify__message__text']")
    private WebElement saveDraftNotification;

    @FindBy(xpath = "//button[@type='button' and @title='Закрыть']")
    private WebElement closeNewEmailModalButton;

    @FindBy(xpath = "//span[@class='button2__txt' and text()='Отправить']")
    private WebElement sendEmailButton;

    @FindBy(xpath = "//a[@class='layer__link' and text()='Письмо отправлено']")
    private WebElement emailSuccessfullySendedNotification;

    @FindBy(xpath = "//span[@title='Закрыть']")
    private WebElement emailSuccessfullySendedNotificationCloseButton;

    public MailRuNewEmailModal fillFormWithEmail(Email email) {
        String to = email.getTo();
        String subject = email.getSubject();
        String body = email.getBody();
        LOGGER.info("Filling Email Form with data:");
        LOGGER.info("Field To: " + to);
        LOGGER.info("Field Subject: " + subject);
        LOGGER.info("Field Body: " + body);
        sendKeys(toInputField, to);
        sendKeys(subjectInputField, subject);
        sendKeys(emailBodyInputField, body);
        return this;
    }

    public MailRuNewEmailModal saveDraft() {
        LOGGER.info("Press save draft button");
        clickWithSelenium(saveDraftButton);
        if (!elementIsDisplayed(saveDraftNotification)) {
            LOGGER.info("WARNING: Notification does not appear");
        } else {
            LOGGER.info("Draft notification appeared");
        }
        return this;
    }

    public MailRuNewEmailModal sendEmail() {
        LOGGER.info("Press send email button");
        clickWithSelenium(sendEmailButton);
        return this;
    }

    public boolean checkSuccessfullySendedEmailAppeared() {
        LOGGER.info("Check that email successfully sended notification appeared");
        return elementIsDisplayed(emailSuccessfullySendedNotification);
    }

    public MailRuEmailBoxMainPage closeEmailSuccessfullySendedNotification() {
        LOGGER.info("Close email successfully sended notification");
        clickWithSelenium(emailSuccessfullySendedNotificationCloseButton);
        return new MailRuEmailBoxMainPage();
    }

    public T closeModal() {
        LOGGER.info("Close Email Form modal window");
        clickWithSelenium(closeNewEmailModalButton);
        //Need to find a way for proper handling with unhandled alert exception that appears without this wait
        lowWait();
        return parentPage;
    }

    public String getToTextValue() {
        LOGGER.info("Get text from To field");
        String toValue = getElementText(toInputFieldPopulated);
        LOGGER.info("Text of To field is: " + toValue);
        return toValue;
    }

    public String getSubjectTextValue() {
        LOGGER.info("Get text from Subject field");
        String subjectValue = getAttributeValue(subjectInputField, "value");
        LOGGER.info("Text of Subject field is: " + subjectValue);
        return subjectValue;
    }

    public String getEmailBodyTextValue() {
        LOGGER.info("Get text from Email Body field");
        String emailBodyValue = getElementText(emailBodyInputField);
        LOGGER.info("Text of Email Body field is: " + emailBodyValue);
        return emailBodyValue;
    }
}
