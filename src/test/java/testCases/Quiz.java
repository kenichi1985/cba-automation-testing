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
import pages.QuizPage;

public class Quiz {

    WebDriver driver;

    IndexPage index;
    QuizPage quiz;

    public static ExtentReports report;
    public static ExtentTest test;


    @BeforeTest
    @Parameters("browser")
    public void setup(String browser){

        driver = BasePage.setupWebDriver(browser);
        driver.get("https://responsivefight.herokuapp.com/");
        //driver.manage().window().maximize();
    }

    @BeforeClass
    public static void startTest(){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportPath = System.getProperty("user.dir")+"\\output-report\\"+timeStamp+"-ExtentReportResults.html";
        report = new ExtentReports(reportPath);

        

       
        test = report.startTest("Quiz Test Cases");
    }

    //note: password not suppose in the code, this is a temporary solution
    @DataProvider (name = "quiz_data")
    public Object[][] dpMethod(){
        int[] quizAns1 = {2};
        int[] quizAns2 = {1,1};
        int[] quizAns3 = {1,2,1};
        int[] quizAns4 = {1,2,2,1};
        int[] quizAns5 = {1,2,2,2,2};
        int[] quizAns6 = {1,2,2,2,1,2};
        int[] quizAns7 = {1,2,2,2,1,1,2};
        int[] quizAns8 = {1,2,2,2,1,1,1,1};
        int[] quizAns9 = {1,2,2,2,1,1,1,2,1};
        int[] quizAns10 = {1,2,2,2,1,1,1,2,2,2};
        int[] quizAns11 = {1,2,2,2,1,1,1,2,2,1,2};
        int[] quizAns12 = {1,2,2,2,1,1,1,2,2,1,1,1};
        int[] quizAns13 = {1,2,2,2,1,1,1,2,2,1,1,2,2};
        int[] quizAns14 = {1,2,2,2,1,1,1,2,2,1,1,2,1};


        return new Object[][] {
           {"TC1","Validate answer respond 1","luke85", "abc12345", quizAns1, "fail"},
            {"TC2", "Validate answer respond 2","luke85", "abc12345",quizAns2, "fail"},
            {"TC3", "Validate answer respond 3","luke85", "abc12345",quizAns3, "fail"},
            {"TC4", "Validate answer respond 4","luke85", "abc12345",quizAns4, "fail"},
            {"TC5", "Validate answer respond 5","luke85", "abc12345",quizAns5, "fail"},
            {"TC6", "Validate answer respond 6","luke85", "abc12345",quizAns6, "fail"},
            {"TC7", "Validate answer respond 7","luke85", "abc12345",quizAns7, "fail"},
            {"TC8", "Validate answer respond 8","luke85", "abc12345",quizAns8, "fail"},
            {"TC9", "Validate answer respond 9","luke85", "abc12345",quizAns9, "fail"},
            {"TC10", "Validate answer respond 10","luke85", "abc12345",quizAns10, "fail"},
            {"TC11", "Validate answer respond 11","luke85", "abc12345",quizAns11, "fail"},
            {"TC12", "Validate answer respond 12","luke85", "abc12345",quizAns12, "fail"},
            {"TC13", "Validate answer respond 13","luke85", "abc12345",quizAns13, "fail"},
             {"TC14", "Validate answer respond 14","luke85", "abc12345",quizAns14, "pass"}

        };
    }

    @Test(dataProvider = "quiz_data")
    public void login_pos_user1(String tcNum, String tcDescription, String usernameStr, String paswordStr, int[] ans, String expectedResult) throws IOException {

        test.log(LogStatus.INFO,tcNum+" - "+tcDescription);

        index = new IndexPage(driver);

        quiz = new QuizPage(driver);

        test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Index page");

        index.login(usernameStr, paswordStr);

        test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Landing page");

        index.startGame();

        test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Welcome page");

        quiz.startQuiz(test, ans, usernameStr, expectedResult);

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
