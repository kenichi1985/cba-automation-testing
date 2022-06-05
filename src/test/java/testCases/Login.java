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
import pages.IndexPage;

public class Login {

    WebDriver driver;

    IndexPage index;

    public static ExtentReports report;
    public static ExtentTest test;


    @BeforeTest
    public void setup(){

        driver = BasePage.setupWebDriver();
        driver.get("https://responsivefight.herokuapp.com/");
       // driver.manage().window().maximize();
    }

    @BeforeClass
    public static void startTest(){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportPath = System.getProperty("user.dir")+"\\output-report\\"+timeStamp+"-ExtentReportResults.html";
        report = new ExtentReports(reportPath);
        test = report.startTest("Login test cases");
    }
    
    //note: password not suppose in the code, this is a temporary solution
    @DataProvider (name = "user_data")
    public Object[][] dpMethod(){
        return new Object[][] {
            {"TC1","Positive flow login","luke85", "abc12345",true, "return"},
            {"TC2", "Negative flow login","lukie85", "abc12345",false, "new"}

        };
    }

    @Test(dataProvider = "user_data")
    public void login_pos_user1(String tcNum, String tcDescription, String usernameStr, String paswordStr, boolean expectedStatus, String userType) throws IOException{

        test.log(LogStatus.INFO,tcNum+" - "+tcDescription);

        index = new IndexPage(driver);

        test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Index page");
        index.login(usernameStr, paswordStr);

        //validate whether it's logged in
        boolean actualStatus = index.validateErrorMessage(test,expectedStatus);

        //if the warning message not appear
        if(actualStatus){
            test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Welcome page");
            //logged in and validate welcome page
            index.validateTextField(test, "Welcome username", usernameStr);

            String welcomeMsg = "";
            if(userType.equalsIgnoreCase("new"))
                welcomeMsg = "Welcome to the game";
            else
                welcomeMsg = "Continue your Journey";

            index.validateTextField(test, "Welcome header", welcomeMsg);
                
        }
        else{
            test.log(LogStatus.SKIP,"Skip welcome page");
        }

        report.endTest(test);

        driver.navigate().refresh();

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
