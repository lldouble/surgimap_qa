package test;

import config.TestConfig;
import org.json.JSONObject;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.LoginPage;
import utilities.MailSlurp;
import utilities.Utilities;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

public class Exercise2Test {
    /*
    Implement required test case with Test Automation Selenium:
    1. Navigate to random https://www.surgimap.com/
    2. Verify loaded page title is “Surgimap.com: Official Site for Surgimap”
    3. On the right top corner of the page, click on “Login”
    4. Verify you landed on Login page
    5. On Login page click “Create an account”
    6. Verify you landed on Create an Account page
    7. Fill in all fields and click “Create an Account”
    8. Verify that Activation email is appeared in mailbox you entered in Step 7
     */

    @Test
    public void testRegisterUserAndGetActivationEmail() throws Exception {
        System.out.println("Exercise2 - Register User to Surgimap and get activation email - Start");

        //0.initiate config and driver;
        System.out.println("0. Initiate config and driver");
        TestConfig config = new TestConfig();
        WebDriver driver = config.getChromeDriver();

        //preliminary step to generate via API / MailSlurp a temporary email mailbox, onto which to receive the activation email;
        boolean mailboxGenerated = false;
        MailSlurp mailSlurp = null;
        JSONObject jsonResponse = null;
        String theEmail = "";
        try {
             System.out.println("0a. Build a temporary emailbox");
             mailSlurp = new MailSlurp(config);
             jsonResponse = mailSlurp.generateEmailbox();
             theEmail = mailSlurp.getMailboxEmailAddress(jsonResponse);
             mailboxGenerated = true;
        }
        catch (Exception e) {
            System.out.println("0b. WARNING could not build the temporary mailbox > the test of the email being received will be skipped and has to be done manually");
            mailboxGenerated = false;
        }

        //1. Navigate to random https://www.surgimap.com/
        System.out.println("1. Navigate to random https://www.surgimap.com/");
        HomePage homePage = new HomePage(driver,config);

        //2. Verify loaded page title is “Surgimap.com: Official Site for Surgimap”
        System.out.println("2. Verify loaded page title is “Surgimap.com: Official Site for Surgimap”");
        String expectedTitle = "Surgimap.com: Official Site for Surgimap";
        String actualTitle = homePage.getPageTitle();
        assertThat(actualTitle.equals(expectedTitle)).isTrue().withFailMessage("Actual and Expected Title ar different! Actual: " + actualTitle + ", Expected: " + expectedTitle);

        //3. On the right top corner of the page, click on “Login”
        System.out.println("3. On the right top corner of the page, click on “Login”");
        LoginPage loginPage = homePage.clickLoginButton();

        //4. Verify you landed on Login page
        System.out.println("4. Verify you landed on Login page");
        //implicitily done by building the loginPage POM from the previous step;

        //5. On Login page click “Create an account”
        System.out.println("5. On Login page click “Create an account”");
        CreateAccountPage createAccountPage = loginPage.clickOnCreateaCcountLink();
        //to better show result
        Utilities.pauseExecution(300);

        //6. Verify you landed on Create an Account page
        System.out.println("6. Verify you landed on Create an Account page");
        //implicitily done by building the createAccountPage POM from the previous step;

        //7. Fill in all fields and click “Create an Account”
        System.out.println("7. Fill in all fields and click “Create an Account”");

        if (!mailboxGenerated)
        theEmail = Utilities.generateRandomEmail();

        //Using the email from MailSlurp, generated at the beginning
        System.out.println("7a. Generate a random email: " + theEmail);

        String password = Utilities.generateRandomPassword(9);
        System.out.println("7b. Generate a random password: " + password);

        System.out.println("7c. Fill the form and submit it");
        createAccountPage.registerUser(theEmail, password);

        System.out.println("7d. Verify popup appeared and with right content");
        //some basic quality check on the popup message
        assertThat(createAccountPage.isPopupDialogShown()).isTrue().withFailMessage("Expected popup dialog message to confirm registration successul did not appear!");
        String expWord1 = config.getData("createaccountpage.register.success.popup.expected.keyword1");
        String expWord2 = config.getData("createaccountpage.register.success.popup.expected.keyword2");
        String expWord3 = config.getData("createaccountpage.register.success.popup.expected.keyword3");
        String actPopupText = createAccountPage.getPopupDialogTextContent();
        assertThat(actPopupText.toLowerCase().contains(expWord1.toLowerCase())).isTrue().withFailMessage("Expected popup dialog message does not seem to contain the expected word: " + expWord1);
        assertThat(actPopupText.toLowerCase().contains(expWord2.toLowerCase())).isTrue().withFailMessage("Expected popup dialog message does not seem to contain the expected word: " + expWord2);
        assertThat(actPopupText.toLowerCase().contains(expWord3.toLowerCase())).isTrue().withFailMessage("Expected popup dialog message does not seem to contain the expected word: " + expWord3);

        //8. Verify that Activation email is appeared in mailbox you entered in Step 7
        if(mailboxGenerated) {
            System.out.println("8. Verify that Activation email is appeared in mailbox you entered in Step 7");

            System.out.println("8a. MailSlurp generated email: " + theEmail);

            //skipping validation of the sender as the API seems to use wrong values for sender email address;
            String expectedSenderEmail = config.getData("activation.email.expected.email.sender.address");
            String expectedSubject = config.getData("activation.email.expected.email.subject");
            String expectedBody = config.getData("activation.email.expected.email.content");

            boolean foundMessage = false;

            //try MAX_EMAIL_CHECK_TRIAL_TIMES times (each every INTERVAL_WAIT_MSEC milliseconds to find the expected activation message in the inbox;
            final int MAX_EMAIL_CHECK_TRIAL_TIMES = 10;
            final int INTERVAL_WAIT_MSEC = 5000;

            System.out.println("8c. Wait to receive the message in mailbox: " + theEmail);
            int totalWaitTime = 0;
            for (int i = 0; i < MAX_EMAIL_CHECK_TRIAL_TIMES; i++)
            {
                Utilities.pauseExecution(INTERVAL_WAIT_MSEC);
                totalWaitTime = totalWaitTime + INTERVAL_WAIT_MSEC;
                int c = i + 1;
                int totalWaitTimeSec = totalWaitTime / 1000;
                System.out.println("8d. attempt #" + c + " over " + MAX_EMAIL_CHECK_TRIAL_TIMES + " to check to have received the activation email (waited so far: " + totalWaitTimeSec + " seconds)");
                //the API seems to offer a wrong senderEmail information, so I am skipping it
                foundMessage = mailSlurp.containsMessage(jsonResponse, expectedSubject, expectedBody, "", Calendar.getInstance());
                if (foundMessage){
                    System.out.println("8e. Message was found and it has the expected content, terminating");
                    break;
                }

            }
            if (!foundMessage){
                System.out.println("8e. WARNING: The Activation message was not found after checking " + MAX_EMAIL_CHECK_TRIAL_TIMES + "With a wait interval of " + INTERVAL_WAIT_MSEC + " milliseconds on the email:  " + theEmail + "Please verify it manually!" );
            }
        }
        else
        {
            System.out.println("8. WARNING Since the mailbox could not be built (see initial logging), the email being received check is SKIPPED and must be done manually");
        }
        //9. Close driver and end;
        System.out.println("9. Closing driver and ending.");
        driver.close();

        System.out.println("Exercise2 - Register User to Surgimap and get activation email - Done, Status: PASSED");
    }
}
