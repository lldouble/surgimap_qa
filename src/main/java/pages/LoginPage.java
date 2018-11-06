package pages;

import config.TestConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/*
POM (Page Object Model) of the SurgiMap LoginPage

 */
public class LoginPage {
    //define UI object as WebElements
    private WebElement xxx;
    private WebDriver driver;
    private TestConfig config;

    public LoginPage(WebDriver driver, TestConfig config){
       // loginButton = driver.findElement(By.xpath(config.getData("homepage.login.button")));
        this.driver = driver;
        this.config = config;
    }
}
