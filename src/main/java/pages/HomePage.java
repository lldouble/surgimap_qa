package pages;

import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
    import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 */

/*
POM (Page Object Model) of the SurgiMap HomePage

 */
public class HomePage {
    //define UI object as WebElements
    private WebElement loginButton;
    private WebDriver driver;
    private TestConfig config;



    //TODO add remaining needed UI elements

    //constructor: connect the UI elements to the WebElements via locators (read fro prop file).
    //It can be invoked from any page, as it will load the homepage url as first, to bootstrap;
    public HomePage(WebDriver driver, TestConfig config){
        //load homepage;
        driver.get(config.getData("homepage.url"));
        //make sure to be in the homepage
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlContains("www.surgimap.com"));

        //build page and variables
        loginButton = driver.findElement(By.xpath(config.getData("homepage.login.button")));
        this.driver = driver;
        this.config = config;
    }


    //get the page title
    public String getPageTitle() {
        return driver.getTitle();
    }

    //click on the Login button to access LoginPage
    public LoginPage clickLoginButton() {
        loginButton.click();
        return new LoginPage(driver, config);
    }

    //close the driver
    public void close() {
        driver.close();
    }

    //TODO: build the remaining API here to interact with the page UI elements;

}
