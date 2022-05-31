package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SampleTest {

    WebDriver driver;

    @BeforeTest
    public void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

    }
    
    @Test
    public void Test1() throws InterruptedException{

        

        driver.get("https://google.com.au");

        Thread.sleep(3000);
        

        

        System.out.println("print something");

    }

}
