package com.mailRu.tests;

import com.mailRu.MailRuBaseTest;
import com.mailRu.objects.Email;
import com.mailRu.pages.MailRuEmailBoxMainPage;
import com.mailRu.pages.MailRuEmailPage;
import com.mailRu.pages.MailRuMainPage;
import com.mailRu.pages.MailRuNewEmailModal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static com.utils.Utils.getFishText;

public class LoginAndAuthorisationTests extends MailRuBaseTest {

    @Test
    public static void enterMailBoxCheckDraftAndExitTest() {
        SoftAssert softAssert = new SoftAssert();
        String expectedEmailBoxTitle = "Входящие - Почта Mail.ru";
        String testToValue = "test@email.com";
        String testSubjectValue = "Test enterMailBoxCheckDraftAndExitTest subject.";
        String testBodyValue = getFishText();
        Email email = new Email
                .Builder(testToValue, testSubjectValue)
                .setBody(testBodyValue)
                .build();

        boolean isEmailBoxTitleCorrect = new MailRuMainPage()
                .loginToMailRuMailService(login, password)
                .getPageTitle()
                .contains(expectedEmailBoxTitle);

        softAssert.assertTrue(isEmailBoxTitleCorrect, "Email box title is not as expected");

        MailRuEmailBoxMainPage mailRuEmailBoxMainPage = new MailRuEmailBoxMainPage();

        mailRuEmailBoxMainPage
                .openNewEmailForm()
                .fillFormWithEmail(email)
                .saveDraft()
                .closeModal();

        MailRuNewEmailModal draftEmail = mailRuEmailBoxMainPage
                .openDraftFolder()
                .openFirstDraftMessageFromDraftMessageList();

        String actualDraftEmailToValue = draftEmail.getToTextValue();
        softAssert.assertEquals(actualDraftEmailToValue, testToValue, "Value of Email To field is not correct");

        String actualDraftEmailSubjectValue = draftEmail.getSubjectTextValue();
        softAssert.assertEquals(actualDraftEmailSubjectValue, testSubjectValue, "Value of Email Subject field is not correct");

        boolean isEmailBodyValid = draftEmail.getEmailBodyTextValue().contains(testBodyValue);
        softAssert.assertTrue(isEmailBodyValid, "Value of Email Body field is not correct");

        draftEmail
                .saveDraft()
                .closeModal();

        boolean doWeLoggedOutMailRuService = mailRuEmailBoxMainPage
                .logout()
                .checkLoginStatus();

        softAssert.assertTrue(doWeLoggedOutMailRuService, "Log out went unsuccessfully");
        softAssert.assertAll();
    }

    @Test
    public static void enterMailBoxSendEmailCheckOutgoingBoxAndExitTest() {
        SoftAssert softAssert = new SoftAssert();
        String expectedEmailBoxTitle = "Входящие - Почта Mail.ru";
        String testToValue = "test@email.com";
        String testSubjectValue = "Test enterMailBoxCheckDraftAndExitTest subject.";
        String testBodyValue = getFishText();
        Email email = new Email
                .Builder(testToValue, testSubjectValue)
                .setBody(testBodyValue)
                .build();

        boolean isEmailBoxTitleCorrect = new MailRuMainPage()
                .loginToMailRuMailService(login, password)
                .getPageTitle()
                .contains(expectedEmailBoxTitle);

        softAssert.assertTrue(isEmailBoxTitleCorrect, "Email box title is not as expected");

        MailRuEmailBoxMainPage mailRuEmailBoxMainPage = new MailRuEmailBoxMainPage();

        MailRuNewEmailModal mailRuNewEmailModal = mailRuEmailBoxMainPage
                .openNewEmailForm()
                .fillFormWithEmail(email);

        boolean isEmailSuccessfullySent = mailRuNewEmailModal
                .sendEmail()
                .checkSuccessfullySendedEmailAppeared();

        softAssert.assertTrue(isEmailSuccessfullySent, "Email was not sent successfully");

        mailRuNewEmailModal.closeEmailSuccessfullySendedNotification();

        MailRuEmailPage sentEmailPage = mailRuEmailBoxMainPage.openSentEmails().openFirstSentMessageFromList();

        String actualSentEmailToValue = sentEmailPage.getLetterToValue();
        softAssert.assertEquals(actualSentEmailToValue, testToValue, "Value of Email To field is not correct");

        String actualSentEmailSubjectValue = sentEmailPage.getLetterSubjectValue();
        softAssert.assertEquals(actualSentEmailSubjectValue, testSubjectValue, "Value of Email Subject field is not correct");

        boolean isEmailBodyValid = sentEmailPage.getLetterBodyValue().contains(testBodyValue);
        softAssert.assertTrue(isEmailBodyValid, "Value of Email Body field is not correct");

        boolean doWeLoggedOutMailRuService = mailRuEmailBoxMainPage
                .logout()
                .checkLoginStatus();

        softAssert.assertTrue(doWeLoggedOutMailRuService, "Log out went unsuccessfully");
        softAssert.assertAll();
    }

}
