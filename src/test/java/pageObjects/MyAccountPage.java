package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h2[text()='My Account']") 
	WebElement msgHeading;      //My Account Page heading
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Logout']")
	WebElement lnkLogout;
	
	public boolean isMyAccountPagExists() {
		try {
		return (msgHeading.isDisplayed());
		}catch(Exception e) {
			return false;
		}
	}
	
	public void clickLogout() {
		lnkLogout.click();
	}
}
