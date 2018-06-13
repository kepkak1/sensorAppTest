
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSensorApp {

    ChromeDriver driver;


    @Before
    public void initializeTest(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Karolina\\Documents\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @After
    public void closeDriver(){
        driver.close();
    }

    @Test
    public void T1_FirstRunWithoutLogScreen(){
        driver.navigate().to("http://localhost:8100/");
        driver.manage().window().maximize();
        WebElement element= driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-0"+"\""+"]/page-record/ion-header/ion-navbar/div[2]"));
        Assert.assertEquals(element.getText(),"Record new gesture");
    }

    @Test
    public void T2_CheckMovesBetweenTabs(){
        driver.navigate().to("http://localhost:8100/");
        driver.manage().window().maximize();
        WebElement recordTab = driver.findElement(By.id("tab-t0-0"));
        WebElement recogniseTab = driver.findElement(By.id("tab-t0-1"));
        WebElement settingsTab = driver.findElement(By.id("tab-t0-2"));

        recogniseTab.click();
        WebElement recognisingTitle = driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-1"+"\""+"]/page-recognise/ion-header/ion-navbar/div[2]/ion-title"));
        Assert.assertEquals(recognisingTitle.getText(),"Recognising gesture");

        settingsTab.click();
        WebElement settingsTitle = driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-2"+"\""+"]/page-settings/ion-header/ion-navbar/div[2]/ion-title"));
        Assert.assertEquals(settingsTitle.getText(), "Your gestures");

        recordTab.click();
        WebElement recordTitle= driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-0"+"\""+"]/page-record/ion-header/ion-navbar/div[2]/ion-title"));
        Assert.assertEquals(recordTitle.getText(),"Record new gesture");
    }


    @Test
    public void T3_RecogniseWithoutMyo(){
        driver.navigate().to("http://localhost:8100/");
        driver.manage().window().maximize();
        WebElement recogniseTab = driver.findElement(By.id("tab-t0-1"));

        recogniseTab.click();
        WebElement element= driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-1"+"\""+"]/page-recognise/ion-content/div[2]/button/span"));
        element.click();

        if(recogniseTab.isDisplayed() && recogniseTab.isEnabled()){
            WebDriverWait wait = new WebDriverWait(driver, 6);
            wait.until(ExpectedConditions.elementToBeClickable(recogniseTab));
            System.out.println("Myo is disconnected");
        }
    }

    @Test
    public void T4_CheckMinimumLengthOfWord(){
        driver.navigate().to("http://localhost:8100/");
        driver.manage().window().maximize();
        WebElement myoIcon= driver.findElement(By.cssSelector("#tabpanel-t0-0 > page-record > ion-header > ion-navbar > ion-buttons > button:nth-child(1)"));
        WebElement wordInput = driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-0"+"\""+"]/page-record/ion-content/div[2]/form/ion-item[2]/div[1]/div/ion-input/input"));

        wordInput.sendKeys("el");
        myoIcon.click();
        WebElement message = driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-0"+"\""+"]/page-record/ion-content/div[2]/form/ion-item[3]/div[1]/div/ion-label/p"));
        Assert.assertEquals(message.getText(), "Sorry, word should have from 3 to 30 characters\n" +
                "and can only contains letters a-z!");
    }

    @Test
    public void T5_CheckMaximumLengthOfWord(){
        driver.navigate().to("http://localhost:8100/");
        driver.manage().window().maximize();
        WebElement myoIcon= driver.findElement(By.cssSelector("#tabpanel-t0-0 > page-record > ion-header > ion-navbar > ion-buttons > button:nth-child(1)"));
        WebElement wordInput = driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-0"+"\""+"]/page-record/ion-content/div[2]/form/ion-item[2]/div[1]/div/ion-input/input"));

        wordInput.sendKeys("aaaaaaaaaassssssssssddddddddddd");
        myoIcon.click();
        WebElement message = driver.findElement(By.xpath("//*[@id="+"\""+"tabpanel-t0-0"+"\""+"]/page-record/ion-content/div[2]/form/ion-item[3]/div[1]/div/ion-label/p"));
        Assert.assertEquals(message.getText(), "Sorry, word should have from 3 to 30 characters\n" +
                "and can only contains letters a-z!");
    }
}
