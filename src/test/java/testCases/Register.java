package testCases;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pages.BasePage;
import pages.RegisterPage;

public class Register {
    
    WebDriver driver;

    RegisterPage register;

    public static ExtentReports report;
    public static ExtentTest test;


    @BeforeTest
    @Parameters("browser")
    public void setup(String browser){

        driver = BasePage.setupWebDriver(browser);
        driver.get("https://responsivefight.herokuapp.com/");
       // driver.manage().window().maximize();
    }

    @BeforeClass
    public static void startTest(){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportPath = System.getProperty("user.dir")+"\\output-report\\"+timeStamp+"-ExtentReportResults.html";
        report = new ExtentReports(reportPath);

        test = report.startTest("Registration test cases");

    }

    //note: password not suppose in the code, this is a temporary solution
    @DataProvider (name = "pos_user_data")
    public Object[][] dpMethod(){
        return new Object[][] {
            {"TC1","Register user positive flow login","luke000003", "abc12345"}

        };
    }

    @Test(dataProvider = "pos_user_data", priority=1)
    public void register_user_pos_flow(String tcNum, String tcDescription, String usernameStr, String paswordStr) throws IOException{

        test.log(LogStatus.INFO,tcNum+" - "+tcDescription);

        register = new RegisterPage(driver);

        register.registerSuccessful(test, usernameStr, paswordStr);

    }


     //note: password not suppose in the code, this is a temporary solution
     @DataProvider (name = "neg_user_data")
     public Object[][] dpMethod2(){
         return new Object[][] {
             {"TC2","Register user with exists user","luke000002", "abc12345"},
             {"TC3","Register user with different password 1","luke100002", "abc12345"},
             {"TC4","Register user with different password 2","luke100002", "abc12345"},
             {"TC5","Register user with empty username","luke100002", "abc12345"},
             {"TC6","Register user with empty password 1","luke100002", "abc12345"},
             {"TC7","Register user with empty password 2","luke100002", "abc12345"}

         };
     }
 
     @Test(dataProvider = "neg_user_data", priority=2)
     public void register_user_neg_flow(String tcNum, String tcDescription, String usernameStr, String paswordStr) throws IOException{

        String negScenario ="";
 
        test.log(LogStatus.INFO,tcNum+" - "+tcDescription);
 
        register = new RegisterPage(driver);

        negScenario = BasePage.locateWordForTC(tcDescription, "with");

        register.registerUnsuccessful(test, usernameStr, paswordStr, negScenario);
 
     }


    @AfterClass
    public static void endTest(){
        report.flush();
    }


    @AfterTest
    public void closeWebDriver(){
        driver.close();
    }


}
