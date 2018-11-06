package pages;

import config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateAccountPage {
    //define UI object as WebElements
    private WebElement emailField;
    private WebElement passwordField;
    private WebElement confirmPasswordField;
    private WebElement registerButton;

    private WebElement surgimapTermsOfUseCheckbox;
    private WebElement nemarisPrivacyPolicyCheckbox;
    private WebElement nemarisServiceAgreementCheckbox;
    private WebElement surgimapBusinessAgregateAgreementCheckbox;

    private WebElement registerSuccessPopup;


    private WebDriver driver;
    private TestConfig config;

    //constructor
    public CreateAccountPage(WebDriver driver, TestConfig config){
        this.driver = driver;
        this.config = config;

        //make sure to be in the homepage
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlContains("www.surgimap.com/access/#/register"));

        //connect UI elements
        emailField = driver.findElement(By.id(config.getData("createaccountpage.emailField")));
        passwordField = driver.findElement(By.id(config.getData("createaccountpage.passwordField")));
        confirmPasswordField = driver.findElement(By.id(config.getData("createaccountpage.confirmPasswordField")));
        registerButton = driver.findElement(By.id(config.getData("createaccountpage.registerButton")));

        surgimapTermsOfUseCheckbox = driver.findElement(By.id(config.getData("createaccountpage.surgimapTermsOfUseCheckbox")));
        nemarisPrivacyPolicyCheckbox = driver.findElement(By.id(config.getData("createaccountpage.nemarisPrivacyPolicyCheckbox")));
        nemarisServiceAgreementCheckbox = driver.findElement(By.id(config.getData("createaccountpage.nemarisServiceAgreementCheckbox")));
        surgimapBusinessAgregateAgreementCheckbox = driver.findElement(By.id(config.getData("createaccountpage.surgimapBusinessAgregateAgreementCheckbox")));

    }

    //click to insert the input email
    public void setEmail(String email) {
        emailField.click();
        emailField.sendKeys("");
        emailField.sendKeys(email);
    }

    //click to insert the input password
    public void setPassword(String password) {
        passwordField.click();
        passwordField.sendKeys("");
        passwordField.sendKeys(password);
    }

    //click to insert the input confimation password
    public void setConfirmationPassword(String confirmationPassword) {
        confirmPasswordField.click();
        confirmPasswordField.sendKeys("");
        confirmPasswordField.sendKeys(confirmationPassword);
    }

    //click to select checkbox: Surgimap Terms of Use
    public void selectSurgmapTOEcheckbox(){
        if (!surgimapTermsOfUseCheckbox.isSelected())
        surgimapTermsOfUseCheckbox.click();
    }

    //click to deselect checkbox: Surgimap Terms of Use
    public void deselectSurgmapTOEcheckbox(){
        if (surgimapTermsOfUseCheckbox.isSelected())
        surgimapTermsOfUseCheckbox.click();
    }

    //click to select checkbox: Nemaris Privacy Policy
    public void selectNemarisPPcheckbox(){
        if (!nemarisPrivacyPolicyCheckbox.isSelected())
        nemarisPrivacyPolicyCheckbox.click();
    }

    //click to deselect checkbox: Nemaris Privacy Policy
    public void deselectNemarisPPcheckbox(){
        if (nemarisPrivacyPolicyCheckbox.isSelected())
        nemarisPrivacyPolicyCheckbox.click();
    }

    //click to select checkbox: Nemaris Service Agreement
    public void selectNemarisSAcheckbox(){
        if (!nemarisServiceAgreementCheckbox.isSelected())
        nemarisServiceAgreementCheckbox.click();
    }

    //click to deselect checkbox: Nemaris Service Agreement
    public void deelectNemarisSAcheckbox(){
        if (nemarisServiceAgreementCheckbox.isSelected())
        nemarisServiceAgreementCheckbox.click();
    }

    //click to select checkbox: Surgimap BAA
    public void selectSurgimapBAAcheckbox(){
        if (!surgimapBusinessAgregateAgreementCheckbox.isSelected())
        surgimapBusinessAgregateAgreementCheckbox.click();
    }

    //click to deselect checkbox: Surgimap BAA
    public void deselectSurgimapBAAcheckbox(){
        if (surgimapBusinessAgregateAgreementCheckbox.isSelected())
        surgimapBusinessAgregateAgreementCheckbox.click();
    }

    //click on the register button
    public void clickRegister() {
        registerButton.click();
    }

    //register a new user, given email and password
    public void registerUser(String email, String password){
        //provide email, password and confirmation password
        setEmail(email);
        setPassword(password);
        setConfirmationPassword(password);

        //select all checkboxes
        selectSurgmapTOEcheckbox();
        selectNemarisPPcheckbox();
        selectNemarisSAcheckbox();
        selectSurgimapBAAcheckbox();

        //click to register
        clickRegister();
    }

    //check if a popup is shown
    public boolean isPopupDialogShown() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(config.getData("createaccountpage.register.success.popup"))));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //get popup text content if shown, empty string otherwise
    public String getPopupDialogTextContent(){
        if (isPopupDialogShown()){
            registerSuccessPopup = driver.findElement(By.xpath(config.getData("createaccountpage.register.success.popup")));
            return registerSuccessPopup.getText();
        }
        else return "";
    }

    //TODO: add API / methods to type in / read / delete remaining UI elements, where it makes sense

}
