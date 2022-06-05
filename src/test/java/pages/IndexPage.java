package pages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class IndexPage extends BasePage {

    WebDriver driver;

    public IndexPage(WebDriver driver){

        this.driver=driver;
        
    }

    //locates the buttons

    By registerBtn = By.xpath("//a[@id='rego']");

    By loginBtn = By.xpath("//a[@id='login']");

    //locates form fields and buttons

    By username = By.xpath("//input[@id='uname']");

    By password = By.xpath("//input[@id='pwd']");

    By rPassword = By.xpath("//input[@id='psw-repeat']");

    By signUpBtn = By.xpath("//button[@id='signupbtn']");

    By cancelBtn = By.xpath("//button[@class='cancelbtn']");

    //locates form fields and buttons in the login form

    By loginWarrBtn = By.xpath("//a[@id='warrior']");

    By loginUsername = By.xpath("//input[@id='worrior_username']");

    By loginPassword = By.xpath("//input[@id='worrior_pwd']");


    //locate the image(s)

    By indexImage = By.xpath("//img[@class='centered responsive']");

    //locate the header
    By heading = By.xpath("//p[@class='option-label']");

    //locate welcome pop up
    By welcomeVisible = By.xpath("//div[@id='loginmodal'][contains(@style,'display: inline-block')]");

    By welcomeUser = By.xpath("//h3[@id='user_txt']");

    By welcomeHeader = By.xpath("//h1[@id='login_title']");

    By startGameBtn = By.xpath("//a[@id='start']");

    //login error
    By warningMsg = By.xpath("//span[@id='login_popup'][contains(@style,'display: inline-block')]");


    public void login (String usernameStr, String paswordStr){

        click(loginBtn);

        enterText(loginUsername, usernameStr);

        enterText(loginPassword, paswordStr);

        click(loginWarrBtn);
 
    }

    public void startGame(){

        click(startGameBtn);
        
    }



    public boolean validateErrorMessage(ExtentTest test,boolean expectResult) throws IOException{

        boolean matchActual =false;
        WebElement warningMessage = null;
        String warningMsgStr="";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //wait.until(ExpectedConditions.elementToBeClickable(warningMsg));
        //wait.until(ExpectedConditions.visibilityOfElementLocated(warningMsg));

        try{
            warningMessage = wait.until(ExpectedConditions.elementToBeClickable(warningMsg));

            warningMsgStr=warningMessage.getText();
        }
        
        catch(Exception e){
            System.out.println("");
        }

        
        //found the error message
        if(warningMsgStr.equalsIgnoreCase("Wrong username or password")){
            //if expect no error message
            if(expectResult){
                //fail
                test.log(LogStatus.FAIL, test.addScreenCapture(capture())+"Error message should not display");
            }
            else{
                //pass
                test.log(LogStatus.PASS, test.addScreenCapture(capture())+"Error message displayed");

            }
        }
        //no error mesage
        else{
            if(expectResult){
                //pass
                matchActual=true;
                test.log(LogStatus.PASS, "Error message does not display");

            }
            else{
                //fail
                test.log(LogStatus.FAIL, test.addScreenCapture(capture())+"Error message should display");

            }

        }


        return matchActual;

    }




    public void validateTextField(ExtentTest test, String fieldName, String fieldText) throws IOException{

        By fieldToBeValidate=null;
        switch (fieldName) {
            case "Welcome username":
                fieldToBeValidate=welcomeUser;
                break;
            case "Welcome header":
                fieldToBeValidate=welcomeHeader;
                break;
        
            default:
                break;
        }

        validateField(test, fieldToBeValidate, fieldName, fieldText);
    }


    


    


    
    
}
