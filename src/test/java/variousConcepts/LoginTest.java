package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {

	WebDriver driver;
	String browser;
	String url;

	@BeforeTest
	public void readConfig() {

//		FileReader//BufferReader//InputStream//Scanner

		try {
			InputStream input = new FileInputStream(
					"C:\\Users\\Admin\\Selenium\\testNG\\src\\main\\java\\config\\Config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("browser used:  " + browser);
			url = prop.getProperty("url");

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

// Elements
	By userNameField = By.xpath("//input[@id='username']");
	By passwordField = By.xpath("//input[@id='password']");
	By signInButtonField = By.xpath("//button[@name='login']");
	By dashboardHeaderField = By.xpath("//h2[text()=' Dashboard ']");
	By customerField = By.xpath("//span[text()='Customers']");
	By addCustomerField = By.xpath("//a[text()='Add Customer']");
	By addCustomerHeaderField = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By fullNameField = By.xpath("//input[@id='account']");
	By companyDropdownField = By.xpath("//select[@id='cid']");
	By selectField = By.xpath("//option[text()='Techfios']");
	By emailField = By.xpath("//input[@id='email']");
	By phoneField = By.xpath("//input[@id='phone']");
	By addressField = By.xpath("//input[@id='address']");
	By cityField = By.xpath("//input[@id='city']");
	By stateregionField = By.xpath("//input[@id='state']");
	By zipPostalField = By.xpath("//input[@id='zip']");
	By countryDropDownField = By.xpath("//select[@id='country']");
	By currencyDropDownField = By.xpath("//select[@id='currency']");
	By groupDropDownField = By.xpath("//select[@id='group']");
	By password2Field = By.xpath("//input[@id='password']");
	By confirmpasswordField = By.xpath("//input[@id='cpassword']");
	By saveButtonField = By.xpath("//button[@id='submit']");

// login data
	String User_Name = "demo@techfios.com";
	String Password = "abc123";
//	testData/mockData
	String Dashboard_Header_Text = "Dashboard";
	String AddCustomer_Header_Text = "Add Contact";
	String Full_Name = "Selenium Feb2023";
	String Company = "Techfios";
	String Email = "demo@techfios.com";
	String Phone = "123456";
	String Country = "United States";

	@BeforeMethod
	public void init() {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "driver\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else {
			System.out.println("select proper browser!!");
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

//	@Test
	public void login() {

		driver.findElement(userNameField).sendKeys(User_Name);
		driver.findElement(passwordField).sendKeys(Password);
		driver.findElement(signInButtonField).click();
		Assert.assertEquals(driver.findElement(dashboardHeaderField).getText(), Dashboard_Header_Text,
				"Dashboard page not found");

	}

	@Test
	public void addCustomer() throws InterruptedException {

		login();
		Thread.sleep(2000);
		driver.findElement(customerField).click();
		driver.findElement(addCustomerField).click();
		Assert.assertEquals(driver.findElement(addCustomerHeaderField).getText(), AddCustomer_Header_Text,
				"Add Customer page not available");

		driver.findElement(fullNameField).sendKeys(Full_Name + randomNumGenerator(999));
		Select sel = new Select(driver.findElement(companyDropdownField));
		sel.selectByVisibleText(Company);

		driver.findElement(emailField).sendKeys(randomNumGenerator(999) + Email);
		driver.findElement(phoneField).sendKeys(Phone + randomNumGenerator(99));

		Select sel1 = new Select(driver.findElement(countryDropDownField));
		sel.selectByVisibleText(Country);

	}

	private int randomNumGenerator(int bound) {

		Random rnd = new Random();
		int generatedNum = rnd.nextInt(bound);
		return generatedNum;

	}

//	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();

	}
}
