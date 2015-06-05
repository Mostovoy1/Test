package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;


public class GooglePageParser {
    private static WebDriver driver;

    private static String textRequest;
    private static final String URL = "http://google.com";
    private static final By SEARCH_FORM = By.xpath("//div[@id='searchform']/form/div[@class='tsf-p']//div[@id='sfdiv']//input[@id='lst-ib']");
    private static final By SEARCH_BUTTON = By.xpath("//button[@type='submit']");
    private static final By SEARCH_RESULT = By.xpath("//div[@id='search']/div/div/ol/div[@class='srg']/li/div/h3/a");

    public static void main(String[] args) throws IOException {
        System.out.println("Please, input request:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        textRequest = br.readLine();
        googlePageParser();
        postCondition();
    }

    private static void googlePageParser() {
        List<String> links = new ArrayList<>();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, 15);

        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_FORM));
        driver.findElement(SEARCH_FORM).click();
        driver.findElement(SEARCH_FORM).sendKeys(textRequest);
        driver.findElement(SEARCH_BUTTON).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_RESULT));

        List<WebElement> searchResultList = driver.findElements(SEARCH_RESULT);
        for(WebElement currentElement :  searchResultList){
            links.add(currentElement.getAttribute("href"));
        }
        for(int i=0; i<links.size(); i++){
            driver.get(links.get(i));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//head")));
            System.out.println(driver.getTitle());
         }
    }

    private static void postCondition() {
        if (driver != null)
            driver.quit();
    }


}
