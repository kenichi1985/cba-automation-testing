package pages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class BasePage {

    static WebDriver driver;



    public static WebDriver setupWebDriver(){
        
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        
        return driver;
    }


    public void enterText (By field, String text){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='footer']")));

        wait.until(ExpectedConditions.elementToBeClickable(field));

        driver.findElement(field).sendKeys(text);

    }

    public void click (By field){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(ExpectedConditions.elementToBeClickable(field)).click();
        //WebElement button = wait.until(ExpectedConditions.elementToBeClickable(field));

       // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

        
    }

    public void validateField(ExtentTest test, By field, String fieldName, String text) throws IOException{

        String actualText = "";
        //remove all the special characters and white spaces
        String expectedText =text.replaceAll("[^a-zA-Z]+","").trim();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(field));

        String textOnPage = driver.findElement(field).getText();

        //remove all the special characters and white spaces
        actualText=textOnPage.replaceAll("[^a-zA-Z]+","").trim();

        // if(textOnPage.contains("Mary")){
        //     System.out.println("expected text: "+expectedText);

        //     System.out.println("actual text: "+actualText);

        // }

        if(actualText.equalsIgnoreCase(expectedText)){
            test.log(LogStatus.PASS, fieldName+" display correctly with "+textOnPage);
        }
        else{
            test.log(LogStatus.FAIL, test.addScreenCapture(capture())+fieldName+" display incorrect with "+ textOnPage);
        }
       
    }

    public void checkImage(ExtentTest test,WebElement img, String fieldName) throws IOException{

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(img.getAttribute("src"));
        HttpResponse response = client.execute(request);
        /* For valid images, the HttpStatus will be 200 */

        int respondCode = response.getCode();
        System.out.println("respond code: "+respondCode);

        if(respondCode !=200){
            System.out.println(img.getAttribute("outerHTML") + " is broken.");
    

            test.log(LogStatus.FAIL, test.addScreenCapture(capture())+fieldName+" does not displayed");
        }
        else{

            test.log(LogStatus.PASS, fieldName+" displayed");

        }
    }




    public static String capture() throws IOException {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File Dest = new File("src/../ErrImages/" + System.currentTimeMillis() + ".png");
        String errflpath = Dest.getAbsolutePath();
        FileUtils.copyFile(scrFile, Dest);
        return errflpath;
    }

    public String[] getQuestions() throws FileNotFoundException{

        String [] questions = new String[13];
        int count =0;

        //read file
        File file = new File(System.getProperty("user.dir")+"\\test-data\\quiz.txt");
        Scanner sc = new Scanner(file);

        
        while (sc.hasNextLine()){
            questions[count]=sc.nextLine();
            //System.out.println(questions[count]);
            count++;

        }

        return questions;
    }


    public void waitForPopUpDisappear(By popup){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(popup));

    }


    public static String locateWordForTC(String text, String keyWord){

        String tcName = "";


        int i = text.indexOf(keyWord);

        if(i>0){
            tcName = text.substring(i, text.length());
			System.out.println(tcName);
        }
		else 
			System.out.println("string not found");

        return tcName;

    }



    
}
