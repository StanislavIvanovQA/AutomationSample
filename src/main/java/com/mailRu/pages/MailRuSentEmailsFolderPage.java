package com.mailRu.pages;

import com.mailRu.pages.components.EmailListComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.utils.Utils.getCurrentPageTitle;

public class MailRuSentEmailsFolderPage implements IMailRuPage {
    private static Logger LOGGER = LogManager.getLogger(MailRuNewEmailModal.class);
    private EmailListComponent emailListComponent = new EmailListComponent();

    public String getPageTitle() {
        return getCurrentPageTitle();
    }

    public MailRuEmailPage openFirstSentMessageFromList() {
        LOGGER.info("Open first sent message from sent message list");
        emailListComponent.openFirstMessageFromMessageList();
        return new MailRuEmailPage();
    }
}
