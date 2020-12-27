package com.mailRu.pages;

import com.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.utils.Utils.getCurrentPageTitle;
import static com.utils.Utils.getElementText;

public class MailRuEmailPage implements IMailRuPage {
    private static Logger LOGGER = LogManager.getLogger(MailRuMainPage.class);

    @FindBy(xpath = "//div[@class='letter__recipients letter__recipients_short']/span[@class='letter-contact']")
    private WebElement to;

    @FindBy(xpath = "//h2[@class='thread__subject']")
    private WebElement subject;

    @FindBy(xpath = "//div[@class='letter-body']")
    private WebElement body;

    public MailRuEmailPage() {
        WebDriver driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        return getCurrentPageTitle();
    }

    public String getLetterToValue() {
        return getElementText(to);
    }

    public String getLetterSubjectValue() {
        return getElementText(subject);
    }

    public String getLetterBodyValue() {
        return getElementText(body);
    }
}
