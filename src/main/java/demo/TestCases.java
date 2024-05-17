package demo;
import java.time.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//import io.github.bonigarcia.wdm.WebDriverManager;


public class TestCases {
    ChromeDriver driver;
    String url = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";
    WebDriverWait wait;
    JavascriptExecutor js;
    public TestCases()
    {
        System.out.println("Constructor: TestCases");
        //WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        js = (JavascriptExecutor) driver;
    }

    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    
    public  void testCase01(){
        System.out.println("Start Test case: testCase01");
        try{
        openURL(url);
        entername("asha");
        entercourse("yes");
        enterexperience("3 - 5");
        enterlearnings("Java");
        enterlearnings("Selenium");
        enterlearnings("TestNG");
        enterhowtoaddress("Mrs");
        enter7daysagodate();
        entertime();
        clicksubmit();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("end Test case: testCase01");
    }

    public void openURL(String URL) {
        driver.get(URL);
    }

    public void click(WebElement ele) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendKeys(WebElement ele, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(ele));
            ele.sendKeys(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void entercourse(String yesorno) {
        WebElement courseele = driver.findElement(By.xpath("//textarea[@aria-label='Your answer']"));
        sendKeys(courseele,yesorno);
    }

    public void entername(String nametext) {
        WebElement nameele = driver.findElement(By.xpath("//div/div[text()='Your answer']/preceding-sibling::input"));
        sendKeys(nameele, nametext);
    }

    private void enterlearnings(String subject) {
        WebElement subjectele = driver.findElement(By.xpath("//div[@role='listitem']//span[text()='" + subject + "']"));
        click(subjectele);
    }

    private void enterexperience(String exp) {
        WebElement expele = driver.findElement(By.xpath("//div[@role='listitem']//span[text()='" + exp + "']"));
        click(expele);
    }

    private void clicksubmit() {
        WebElement submitbutton = driver.findElement(By.xpath("//span[text()='Submit']"));
        click(submitbutton);
    }

    private void entertime() {
        LocalTime presenttime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String time = presenttime.format(formatter);
        WebElement hour = driver.findElement(By.xpath("//input[@aria-label='Hour']"));
        WebElement min = driver.findElement(By.xpath("//input[@aria-label='Minute']"));
        WebElement ampmdrpdown = driver.findElement(By.xpath("//div[@data-value='AM']/.."));
        sendKeys(hour, time.split(":")[0]);
        sendKeys(min, time.split(":")[1]);
        click(ampmdrpdown);
        String ampmdata = time.split(" ")[1];
        WebElement ampm = driver.findElement(By.xpath("//div[@data-value='" + ampmdata + "']"));
        click(ampm);
    }

    private void enter7daysagodate() {
    WebElement dateele = driver.findElement(By.xpath("//input[@type='date']"));
    LocalDate date = LocalDate.now();
    LocalDate sevendayagodate = date.minusDays(7);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formatteddate = sevendayagodate.format(formatter);
    sendKeys(dateele, formatteddate);
    }

    private void enterhowtoaddress(String text) throws InterruptedException {
        WebElement drpele = driver.findElement(By.xpath("//div[@role='presentation']//span[text()='Choose']"));
        click(drpele);
        WebElement textele = driver.findElement(By.xpath("//div[@role='presentation']//span[text()='" + text + "']"));
        js.executeScript("arguments[0].scrollIntoView(true);", textele);
        Thread.sleep(5000);
        click(textele);
    }
}
