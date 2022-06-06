package pages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class RegisterPage extends BasePage{

    WebDriver driver;

    public RegisterPage(WebDriver driver){

        this.driver=driver;
        
    }

    By registerBtn = By.xpath("//a[@id='rego']");

    By signUpHeader = By.xpath("//div[@id='regomodal']/div/div/h1");

    By userNameField = By.xpath("//div[@id='regomodal']/div/div/input[@id='uname']");

    By passwordField = By.xpath("//div[@id='regomodal']/div/div/input[@id='pwd']");

    By repeatPasswordField = By.xpath("//div[@id='regomodal']/div/div/input[@id='psw-repeat']");

    By cancelBtn = By.xpath("//button[@class='cancelbtn']");

    By signUpBtn = By.xpath("//button[@id='signupbtn']");

    By signUpForm = By.xpath("//div[@id='regomodal']");

    By loginForm = By.xpath("//div[@id='loginmodal']");

    By errorMsg = By.xpath("//span[@id='popup']");



    public void registerSuccessful(ExtentTest test, String username, String password) throws IOException{

        click(registerBtn);

        if(driver.findElement(signUpForm).isDisplayed()){

            //pass with screenshot 
            test.log(LogStatus.PASS, test.addScreenCapture(capture())+"Sign up form is displayed");

            validateTextField(test, "Sign up header", "Sign Up");

            enterText(userNameField, username);

            enterText(passwordField, password);

            enterText(repeatPasswordField, password);

            click(cancelBtn);

            if(!driver.findElement(signUpForm).isDisplayed()){
                //pass with the sign up form disappear
                test.log(LogStatus.PASS, "Sign up form is disappeared after click cancel button");
                
            }

            waitForPopUpDisappear(signUpForm);

            click(registerBtn);

            click(signUpBtn);

            waitForPopUpDisappear(signUpForm);

            if(driver.findElement(signUpForm).isDisplayed()){

                //report pass with login form displayed
                test.log(LogStatus.PASS, test.addScreenCapture(capture())+"Login form is displayed");

            }

        }

    }


    public void registerUnsuccessful(ExtentTest test, String username, String password, String negScenario) throws IOException{
        String repeatPassword = password;
        boolean isErrorMsgDisplayed = false;
        String expectedErrorMsg ="";
        String actualErrorMsg ="";


        switch (negScenario) {
            case "with exists user":
                expectedErrorMsg = "User already exists";
                
                break;
            case "with different password 1":
                repeatPassword = repeatPassword+"6";
                expectedErrorMsg = "Passwords do not match";
                break;
            case "with different password 2":
                password = password+"6";
                expectedErrorMsg = "Passwords do not match";
                break;
            case "with empty username":
                username="";
                expectedErrorMsg = "Username is empty";
                break;
            case "with empty password 1":
                password="";
                expectedErrorMsg = "Passwords do not match";
                break;
            case "with empty password 2":
                repeatPassword="";
                expectedErrorMsg = "Passwords do not match";
                break;
        
            default:
                break;
        }


        click(registerBtn);

        waitForPageLoad();

        enterText(userNameField, username);

        enterText(passwordField, password);

        enterText(repeatPasswordField, repeatPassword);

        click(signUpBtn);


        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            wait.until(ExpectedConditions.elementToBeClickable(errorMsg)).click();

            isErrorMsgDisplayed=true;
        }
        catch(Exception e){
            System.out.println("Error message not display");
            test.log(LogStatus.FAIL, test.addScreenCapture(capture())+"Error message does not display "+ negScenario);

        }

        if(isErrorMsgDisplayed){
            //pass with the sign up form disappear
            actualErrorMsg=driver.findElement(errorMsg).getText();

            System.out.println("error message: "+actualErrorMsg);

            test.log(LogStatus.PASS, test.addScreenCapture(capture())+"Sign up form still appear "+ negScenario);

            if(actualErrorMsg.replaceAll("\\s+","").trim().equalsIgnoreCase(expectedErrorMsg.replaceAll("\\s+",""))){

                test.log(LogStatus.PASS, "Error message: "+ actualErrorMsg);
            
            }
            else{
                test.log(LogStatus.FAIL, "Actual error message: "+ actualErrorMsg+", Expected error message: "+expectedErrorMsg);
            }
        }

        


        driver.navigate().refresh();


    }




    public void validateTextField(ExtentTest test, String fieldName, String fieldText) throws IOException {

        By fieldToBeValidate=null;
        switch (fieldName) {
            case "Sign up header":
                fieldToBeValidate=signUpHeader;
                break;
            default:
                break;
        }

        validateField(test, fieldToBeValidate, fieldName, fieldText);
    }





    
}
