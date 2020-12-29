package com.mailRu.pages;

import com.mailRu.pages.components.EmailListComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.utils.Utils.getCurrentPageTitle;

public class MailRuDraftEmailsPage implements IMailRuPage {
    private static Logger LOGGER = LogManager.getLogger(MailRuNewEmailModal.class);
    private EmailListComponent emailListComponent = new EmailListComponent();

    public String getPageTitle() {
        return getCurrentPageTitle();
    }

    public MailRuNewEmailModal openFirstDraftMessageFromDraftMessageList() {
        LOGGER.info("Open first draft message from draft message list");
        emailListComponent.openFirstMessageFromMessageList();
        return new MailRuNewEmailModal(this);
    }
}
