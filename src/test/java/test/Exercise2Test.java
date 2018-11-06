package test;

import config.TestConfig;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;

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

        //4. Close driver and end;
        System.out.println("4. Close driver and end");
        driver.close();

        System.out.println("Exercise2 - Register User to Surgimap and get activation email - Done, Status: PASSED");
    }
}
