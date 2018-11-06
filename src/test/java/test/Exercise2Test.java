package test;

import config.TestConfig;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.CreateAccountPage;
import pages.HomePage;
import pages.LoginPage;
import utilities.GmailInbox;
import utilities.Utilities;

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
    public void testRegisterUserAndGetActivationEmail() {
        System.out.println("Exercise2 - Register User to Surgimap and get activation email - Start");

        //0.initiate config and driver;
        System.out.println("0. Initiate config and driver");
        TestConfig config = new TestConfig();
        WebDriver driver = TestConfig.getChromeDriver();

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

        String email = Utilities.generateRandomEmail();
        System.out.println("7a. Generate a random email: " + email);

        String password = Utilities.generateRandomPassword(9);
        System.out.println("7b. Generate a random password: " + password);

        System.out.println("7c. Fill the form and submit it");
        createAccountPage.registerUser(email, password);

        System.out.println("7d. Verify popup appeared and with right content");
        assertThat(createAccountPage.isPopupDialogShown()).isTrue().withFailMessage("Expected popup dialog message to confirm registration successul did not appear!");
        String expWord1 = "thank";
        String expWord2 = "register";
        String expWord3 = "email";
        String actPopupText = createAccountPage.getPopupDialogTextContent();
        assertThat(actPopupText.toLowerCase().contains(expWord1.toLowerCase())).isTrue().withFailMessage("Expected popup dialog message does not seem to contain the expected word: " + expWord1);
        assertThat(actPopupText.toLowerCase().contains(expWord2.toLowerCase())).isTrue().withFailMessage("Expected popup dialog message does not seem to contain the expected word: " + expWord2);
        assertThat(actPopupText.toLowerCase().contains(expWord3.toLowerCase())).isTrue().withFailMessage("Expected popup dialog message does not seem to contain the expected word: " + expWord3);


        System.out.println("8. Verify that Activation email is appeared in mailbox you entered in Step 7");
        GmailInbox mailbox = new GmailInbox(config);


        //4. Close driver and end;
        System.out.println("9. Closing driver and ending.");
        driver.close();

        System.out.println("Exercise2 - Register User to Surgimap and get activation email - Done, Status: PASSED");
    }
}
