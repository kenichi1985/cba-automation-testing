package pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class QuizPage extends BasePage{

    WebDriver driver;

    public QuizPage(WebDriver driver){

        this.driver=driver;
        
    }

    //quiz welcome page

    By header = By.xpath("//p[@class='alpha-heading']");

    By welcomeUser = By.xpath("//p[@id='welcome_text']");

    By imageWorld = By.xpath("//img[@id='world_img']");

    By enterBtn = By.xpath("//a[@id='news']");


    //pop up
    By popUpIntro = By.xpath("//div[@id='introModal']");

    By popUpWarningImage = By.xpath("//img[@class='center']");

    By popUpStartBtn = By.xpath("//button[@id='start']");


    //answer selection

    By question = By.xpath("//p[@id='question']");

    By ans1 = By.xpath("//div/div[@class='box']/div/div[@class='caption']/a[@id='answer_1']");

    By ans2 = By.xpath("//div/div[@class='box']/div/div[@class='caption']/a[@id='answer_2']");

    //pop up after first quiz answer correctly
    By popUpCorrect = By.xpath("//div[@id='correctModal']");

    By imageWFH = By.xpath("//img[@id='img-wfh']");

    By continueBtn = By.xpath("//button[@id='continue']");

    //pop up after answer wrongly
    By popUpIncorrect = By.xpath("//div[@id='incorrectModal']");

    By ohNoImage = By.xpath("//div[@id='incorrectModal']/div/div/div/img");

    By goHomeBtn = By.xpath("//button[@id='close_modal_btn_2']");

    //quiz image
    By imageDyno = By.xpath("//img[@id='dyno_img']");

    //leaderboard
    By leaderboardHeader = By.xpath("//body/div[1]/div/p[@class='option-label']");

    By leaderboardUsername = By.xpath("//p[@id='showData']/table/tbody/tr[2]/td[1]");

    By leaderboardContinueBtn = By.xpath("//button[@id='leaderboard_link']");

    public void startQuiz(ExtentTest test, int[] ans, String username, String expectedResult) throws IOException{

        String[] questions = getQuestions();

        boolean isCorrectAnswerPopUp = false;

        //validate header
        validateTextField(test, "Welcome header", "COVID-19 THE GAME");
        //validate welcome message by username
        validateTextField(test, "Welcome username", "Welcome "+username);
        //validate image - imageWorld
        validateImageField(test, "Welcome image");

        //click enter btn
        click(enterBtn);
        //pop up appear and validate pop up image - popUpImage
        validateImageField(test, "Warning image");

        click(popUpStartBtn);

        waitForPopUpDisappear(popUpIntro);

        String answer ="";



        for (int i=1; i<=ans.length; i++){

            waitForPageLoad();

            //log info current quiz number and answer to be select
            System.out.println("current quiz no: "+i);

            test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Quiz number "+i);

            //validate images
            validateImageField(test, "Quiz image");

            //System.out.println(questions[i-1]);
            //validate question
            validateTextField(test, "Question", questions[i-1]);

            
            if(ans[i-1]==1){
                answer=driver.findElement(ans1).getText();
                click(ans1);
                test.log(LogStatus.INFO,"Quiz num "+ i +" and selected left(1) side answer with "+ answer);
            }
            else{
                answer=driver.findElement(ans2).getText();
                click(ans2);
                test.log(LogStatus.INFO,"Quiz num "+ i +" and selected right(2) side answer with "+answer);
            }
                


            //last quiz
            if (i==ans.length){
                if(expectedResult.equals("fail")){

                    validateImageField(test, "Oh no image");
        
                    click(goHomeBtn);

                    //validate back to login page
                    test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Validate back to login page");
                }
                else{
                    validateImageField(test, "WFH image");
        
                    click(continueBtn);

                    test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Leaderboard");

                    //validate header
                    validateTextField(test, "Leaderboard header", "COVID-19 THE GAME - LEADERBOARD");
    
                    //validate user name is displayed
                    validateTextField(test, "Leaderboard username", username);
    
                    //click continue
                    click(leaderboardContinueBtn);
    
                    test.log(LogStatus.INFO,test.addScreenCapture(BasePage.capture())+"Validate back to welcome page");

        
                }

            }
            else{

                //validate pop up type

                if(driver.findElement(popUpCorrect).isDisplayed()){

                    isCorrectAnswerPopUp=true;

                }
                


                validateImageField(test, "WFH image");
        
                click(continueBtn);

                if(isCorrectAnswerPopUp)
                    waitForPopUpDisappear(popUpCorrect);
                else
                    waitForPopUpDisappear(popUpIncorrect);

            }

        }
    }

    public void validateTextField(ExtentTest test, String fieldName, String fieldText) throws IOException {

        By fieldToBeValidate=null;
        switch (fieldName) {
            case "Welcome username":
                fieldToBeValidate=welcomeUser;
                break;
            case "Welcome header":
                fieldToBeValidate=header;
                break;
            case "Question":
                fieldToBeValidate=question;
                break;
            case "Leaderboard header":
                fieldToBeValidate=leaderboardHeader;
                break;
            case "Leaderboard username":
                fieldToBeValidate=leaderboardUsername;
                break;

            default:
                break;
        }

        validateField(test, fieldToBeValidate, fieldName, fieldText);
    }


    public void validateImageField(ExtentTest test, String fieldName) throws IOException{

        By fieldToBeValidate=null;
        switch (fieldName) {
            case "Welcome image":
                fieldToBeValidate=imageWorld;
                break;
            case "Warning image":
                fieldToBeValidate=popUpWarningImage;
                break;
            case "Oh no image":
                fieldToBeValidate=ohNoImage;
                break;
            case "WFH image":
                fieldToBeValidate=imageWFH;
                break;
            case "Quiz image":
                fieldToBeValidate=imageDyno;
                break;
            default:
                break;
        }

        WebElement image = driver.findElement(fieldToBeValidate);

        checkImage(test, image, fieldName);

    }







    


    
}
