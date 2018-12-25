package TestCases;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.internal.Locatable;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestCases.Xls_Reader;

import Pages.GasCalculatorPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GasCalculatorTest {

	WebDriver driver = null;
	Xls_Reader x1 = new Xls_Reader("C:\\Users\\msbj1\\Desktop\\Selenium\\testing-maven\\selenium-maven-calculator1\\testdata.xlsx");
	
	
	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://www.calculator.net/gas-mileage-calculator.html");
		
	}
	
	@AfterTest
	public void closeup() {
		driver.quit();
	}
	
	@Test
	public void calculateTest() {
		GasCalculatorPage calculatorPage = new GasCalculatorPage(driver);
		
		int rowCount = x1.getRowCount("Data");
		System.out.println("Number of rows: "+rowCount);
		
		for (int i=2; i<=rowCount; i++) {			
			String co = x1.getCellData("Data", "CurrentOR", i);
			String po = x1.getCellData("Data", "PreviousOR", i);
			String g = x1.getCellData("Data", "Gas", i);
			
			
			calculatorPage.currentOdometer.clear();
			calculatorPage.currentOdometer.sendKeys(co);
			calculatorPage.previousOdometer.clear();
			calculatorPage.previousOdometer.sendKeys(po);
			calculatorPage.gas.clear();
			calculatorPage.gas.sendKeys(g);
			calculatorPage.calculate.click();
			
			String wholeText = calculatorPage.result.getText();
			String[] wholeTextArr = wholeText.split(" ");
			String actualResult = wholeTextArr[0];
			System.out.println(i+". Result - "+actualResult);
			x1.setCellData("Data", "Actual", i, actualResult);
			
			double co1 = Double.parseDouble(co);
			double po1 = Double.parseDouble(po);
			double g1 = Double.parseDouble(g);
			
			double expectedResult1 = (co1-po1)/g1;
			DecimalFormat df = new DecimalFormat("0.00");
			String expectedResultRounded = df.format(expectedResult1);
			System.out.println(expectedResultRounded);
			x1.setCellData("Data", "Expected", i, expectedResultRounded);
			
			String result;
			if (expectedResultRounded.equals(actualResult)) {
				result = "Pass";
			} else {
				result = "Fail";
			}
			x1.setCellData("Data", "Status", i, result);
			
			x1.setCellData("Data", "Time", i, LocalTime.now().toString());
		}
		
		
	}
}
