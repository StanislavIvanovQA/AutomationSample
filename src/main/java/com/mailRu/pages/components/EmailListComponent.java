package com.mailRu.pages.components;

import com.mailRu.pages.MailRuNewEmailModal;
import com.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.utils.Utils.waitForAjax;
import static com.utils.Utils.clickWithSelenium;
import static com.utils.Utils.lowWait;

public class EmailListComponent {
    private static Logger LOGGER = LogManager.getLogger(MailRuNewEmailModal.class);

    @FindBy(xpath = "(//div[@class='dataset__items']/a/div[@class='llc__container'])[1]")
    private WebElement firstMessage;

    public EmailListComponent() {
        WebDriver driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void openFirstMessageFromMessageList() {
        waitForAjax();
        //Need to figure out how to get rid of this static wait here
        lowWait();
        clickWithSelenium(firstMessage);
    }
}
