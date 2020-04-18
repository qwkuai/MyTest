import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumCase {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		System.out.println(getCongigValue("VISIT_RESULT"));
		//server方式使用浏览器
		//WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"),DesiredCapabilities.chrome());
		
		//使用驱动目录方式，使用浏览器
		System.setProperty("webdriver.chrome.driver",".\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//打开浏览器
		options.addArguments("--disable-gpu","window-size=1024,768","--no-sandbox");
		
		//命令行模式打开浏览器，桌面不打开
		//options.addArguments("--headless","--disable-gpu","window-size=1024,768","--no-sandbox");
		RemoteWebDriver driver = new ChromeDriver(options);
		driver.get("http://www.baidu.com");
		driver.findElementById("kw").sendKeys("蒯全伟");
		//driver.findElement(By.id("kw")).sendKeys(("蒯全伟"));
		new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.className("c-container")));
        File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        List<WebElement> elesList = driver.findElementsByClassName("c-container");
        for (int i = 0; i < elesList.size(); i++) {
			WebElement tElement = elesList.get(i);
			System.out.println(tElement.findElement(By.tagName("h3")).getText());
			if(tElement.findElement(By.tagName("h3")).getText().contains("传通科技")) {
				System.out.println("found it");
				//break;
			}
		}
        try {
            // 拷贝截图文件到我们项目./Screenshots
        	FileUtils.copyFile(src, new File(".\\Screenshots\\screen.png"));
        }
         
        catch (IOException e)
         {
        	System.out.println(e.getMessage());
        }
		System.out.println("Page title is: " + driver.getTitle());
		driver.quit();
		System.out.println("hello world");

	}
	
	public static int getVisitNo() {
		try {
			return Integer.parseInt(getCongigValue("VISIT_RESULT"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}
	public static String getCongigValue(String key) throws FileNotFoundException {
		Properties properties = new Properties();
		InputStream inputStream = new FileInputStream(".\\test.properties");
		try {
			properties.load(inputStream);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return properties.getProperty(key);
	}

}
