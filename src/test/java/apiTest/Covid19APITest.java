package apiTest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.parser.ParseException;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Covid19APITest {

    public static ExtentReports report;
    public static ExtentTest test;

    @BeforeClass
    public static void startTest(){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportPath = System.getProperty("user.dir")+"\\output-report\\"+timeStamp+"-ExtentReportAPIResults.html";
        report = new ExtentReports(reportPath);
        test = report.startTest("API test cases");
    }

    @Test(priority = 1)
    public void validateToken(){

        test.log(LogStatus.INFO,"Verify token");

        Request request = new Request();

        int statusCode = request.verifyToken();

        if(statusCode == 200){
            test.log(LogStatus.PASS,"Status Code"+ statusCode);
        }
        else if(statusCode == 400){
            test.log(LogStatus.FAIL,"Status Code"+ statusCode+", App name not found");
        }
        else if(statusCode == 403){
            test.log(LogStatus.FAIL,"Status Code"+ statusCode+", Invalid token");
        }
        else{
            test.log(LogStatus.FAIL,"Status Code"+ statusCode+", unknown error");
        }

    }


    @Test(priority = 2)
    public void registerUser_pos(){

        String username = "luke1002";
        String password ="abc12345";

        test.log(LogStatus.INFO,"Valiadte register user");

        Request request = new Request();

        int statusCode = request.registerUser(username, password);

        if(statusCode == 200){
            test.log(LogStatus.PASS,"Status code:"+ statusCode);
        }
        else if(statusCode == 400){
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", User fail to be created");
        }
        else if(statusCode == 401){
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", Application authetication declined");
        }
        else{
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", unknown error");
        }

    }

    //note: password not suppose in the code, this is a temporary solution
    @DataProvider (name = "neg_user_data")
    public Object[][] dpMethod(){
        return new Object[][] {
            {"TC1","Validate exist user can be register","luke1002", "abc12345", 400},
            {"TC2", "Validate when username is empty can be register","", "abc12345", 401},
            {"TC3", "Validate when password is empty can be register","luke23455", "", 401},
            {"TC4", "Validate when username more than 10 characters can be register","luke5678945", "abc12345", 401}

        };
    }

    @Test(priority = 3, dataProvider = "neg_user_data")
    public void registerUser_neg(String tc, String desc, String username, String password, int expectedStatusCode){

        test.log(LogStatus.INFO,"Validate register user negative scenarios");

        Request request = new Request();

        int statusCode = request.registerUser(username, password);

        // System.out.println(tc);

        // System.out.println("actual status code: "+statusCode);

        // System.out.println("expected status code: "+expectedStatusCode);

        if(statusCode==expectedStatusCode){

            test.log(LogStatus.PASS,"Status code:"+ statusCode);

        }
        else{
            test.log(LogStatus.FAIL,"Incorrect status code:"+ statusCode+" expected status code: "+expectedStatusCode);
        }


    }


    @Test(priority = 4)
    public void loginUser_pos(){

        String username = "luke1001";
        String password ="abc12345";

        test.log(LogStatus.INFO,"Valiadte login user");

        Request request = new Request();

        int statusCode = request.loginUser(username, password);

        if(statusCode == 200){
            test.log(LogStatus.PASS,"Status code:"+ statusCode);
        }
        else if(statusCode == 400){
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", Unable to login");
        }
        else if(statusCode == 401){
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", Application authetication declined");
        }
        else{
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", unknown error");
        }

    }





     //note: password not suppose in the code, this is a temporary solution
     @DataProvider (name = "neg_login_user_data")
     public Object[][] dpMethod_login(){
         return new Object[][] {
             {"TC1","Validate wrong username can be login","luke88", "abc12345", 400},
             {"TC2", "Validate wrong password can be login","luke85", "abc123456", 400},
             {"TC3", "Validate empty username can be login","", "abc12345", 400},
             {"TC4", "Validate empty password can be login","luke5678945", "", 400}
 
         };
     }
 
     @Test(priority = 5, dataProvider = "neg_login_user_data")
     public void loginUser_neg(String tc, String desc, String username, String password, int expectedStatusCode){
 
         test.log(LogStatus.INFO,"Validate login user negative scenarios");
 
         Request request = new Request();
 
         int statusCode = request.loginUser(username, password);
 
         System.out.println(tc);
 
         System.out.println("actual status code: "+statusCode);
 
         System.out.println("expected status code: "+expectedStatusCode);
 
         if(statusCode==expectedStatusCode){
 
             test.log(LogStatus.PASS,"Status code:"+ statusCode);
 
         }
         else{
             test.log(LogStatus.FAIL,"Incorrect status code:"+ statusCode+" expected status code: "+expectedStatusCode);
         }
 
 
     }
     @Test(priority = 6)
     public void addUser(){
         String username = "adduser1";
         int score = 4000;

        test.log(LogStatus.INFO,"Validate user score");
 
         Request request = new Request();

         int statusCode = request.addUser(username, score);


        if(statusCode == 201){
            test.log(LogStatus.PASS,"Status code:"+ statusCode);
        }
        else if(statusCode == 400){
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", Invalid request");
        }
        else{
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", unknown error");
        }

    }

     @Test(priority = 7)
     public void validateUsers() throws ParseException{

        test.log(LogStatus.INFO,"Validate users");
 
         Request request = new Request();

         int statusCode = request.getUsers();


         System.out.println("Status Code: "+statusCode);
    

         if(statusCode == 200){
            test.log(LogStatus.PASS,"Status code:"+ statusCode);
        }
        else if(statusCode == 400){
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", Invalid request");
        }
        else{
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", unknown error");
        }

     }


     @Test(priority = 8)
     public void updateUser(){

        String username = "adduser1";

        int newScore = 7654;

        test.log(LogStatus.INFO,"Update users' score");
 
        Request request = new Request();

        int statusCode = request.updateUser(username, newScore);


        System.out.println("Status Code: "+statusCode);

        if(statusCode == 204){
            test.log(LogStatus.PASS,"Status code:"+ statusCode+" record updated");
        }
        else if(statusCode == 400){
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", Invalid request");
        }
        else{
            test.log(LogStatus.FAIL,"Status code:"+ statusCode+", unknown error");
        }

     }

     @Test(priority = 9)
     public void getUserScore() throws InterruptedException{

        String username = "luke85";
        String password = "abc12345";
        String targetUser = "apachai";

        test.log(LogStatus.INFO,"Get users' score");
        
        Request request = new Request();

        int[] results = request.getUser(username, password, targetUser,0);


        if(results[0] == 200){
            test.log(LogStatus.PASS,"Login user's status code:"+ results[0]);
        }
        else if(results[0] == 400){
            test.log(LogStatus.FAIL,"Login user's status code:"+ results[0]+", Unable to login");
        }
        else if(results[0] == 401){
            test.log(LogStatus.FAIL,"Login user's status code:"+ results[0]+", Application authetication declined");
        }
        else{
            test.log(LogStatus.FAIL,"Login user's status code:"+ results[0]+", unknown error");
        }


        if(results[1] == 200){
            test.log(LogStatus.PASS,"Get user's score status code:"+ results[1]+" and score: "+ results[2]);
        }
        else if(results[1] == 400){
            test.log(LogStatus.FAIL,"Login user's score status code:"+ results[1]+", Invalid request");
        }
        else if(results[1] == 403){
            test.log(LogStatus.FAIL,"Login user's score status code:"+ results[1]+", Token Authentication failed - Incorrect token used");
        }
        else{
            test.log(LogStatus.FAIL,"Login user's score status code:"+ results[1]+", unknown error");
        }

    }


         
        @DataProvider (name = "neg_user_score_data")
        public Object[][] dpUserScoreMethod(){
        return new Object[][] {
            {"TC1","Validate if user not exist exist user ","ysgdtac", 200, 0},
            {"TC2", "Validate after 3 mins if the token still valid","apachai", 403, 3}

        };
    }

        @Test(priority = 10, dataProvider = "neg_user_score_data")
        public void getUserScore_neg(String tc, String desc, String targetUser, int expectedStatusCode, int waitTime) throws InterruptedException{
   
           String username = "luke85";
           //note: password not suppose in the code, this is a temporary solution
           String password = "abc12345";
   
           test.log(LogStatus.INFO,tc+" - "+desc);
           
           Request request = new Request();
   
           int[] results = request.getUser(username, password, targetUser,waitTime);
   
   
           if(results[0] == 200){
               test.log(LogStatus.PASS,"Login user's status code:"+ results[0]);
           }
           else if(results[0] == 400){
               test.log(LogStatus.FAIL,"Login user's status code:"+ results[0]+", Unable to login");
           }
           else if(results[0] == 401){
               test.log(LogStatus.FAIL,"Login user's status code:"+ results[0]+", Application authetication declined");
           }
           else{
               test.log(LogStatus.FAIL,"Login user's status code:"+ results[0]+", unknown error");
           }
   
           if(results[1] == expectedStatusCode){
               test.log(LogStatus.PASS,"Get user's score status code:"+ results[1]);
           }
           else{
               test.log(LogStatus.FAIL,"Actual status code:"+ results[1]+", expected status code: "+expectedStatusCode);
           }
   

     }

 

     //delete user not working, unsure what is the delete-key 



     @AfterClass
     public static void endTest(){
         report.flush();
     }
     

     









    
}
