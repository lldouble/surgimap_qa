package pages;

import config.TestConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
POM (Page Object Model) of the SurgiMap LoginPage

 */
public class LoginPage {
    //define UI object as WebElements
    private WebElement emailField;
    private WebElement passwordField;
    private WebElement loginButton;
    private WebElement rememberCheckbox;
    private WebElement forgottenPasswordLink;
    private WebElement createAccountLink;


    private WebDriver driver;
    private TestConfig config;

    //constructor
    public LoginPage(WebDriver driver, TestConfig config){
        this.driver = driver;
        this.config = config;

        //make sure to be in the homepage
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlContains("www.surgimap.com/access/#/login"));

        //connect UI elements
        //TODO: add remaining elements
        createAccountLink = driver.findElement(By.id(config.getData("loginpage.create.account.link")));
    }

    //click on the create account page link, to land onto the Create Account Page
    public CreateAccountPage clickOnCreateaCcountLink() {
        createAccountLink.click();
        return new CreateAccountPage(driver, config);
    }

    //TODO: add API / methods to type in / read / delete username and password, and to click to login


}
