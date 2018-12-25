package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GasCalculatorPage {	
	
	public GasCalculatorPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="uscodreading")
    public WebElement currentOdometer;
    
    @FindBy(id="uspodreading")
    public WebElement previousOdometer;
    
    @FindBy(id="usgasputin")
    public WebElement gas;
    
    @FindBy(xpath="//*[@id='standard']//input[@alt='Calculate']")
    public WebElement calculate;
    
    @FindBy(xpath="//b[contains(.,'miles per gallon')]")
    public WebElement result;
}
