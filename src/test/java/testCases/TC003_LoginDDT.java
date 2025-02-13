package testCases;
/* Data is valid--Login successful--Test Passed-Logout
  Data is valid--Login unsuccessful--Test Failed
  
  Data is invalid--Login successful--Test Failed--Logout
  Data is invalid--Login unsuccessful--Test Passed
 */

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT {
	
	public class TC002_LoginTest extends BaseClass {
		
		@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="DataDriven")//Getting Data from different class/package
		public void verify_logionDDT(String email, String pwd,String exp) {
			logger.info("*******Starting of TC003_LoginDDT ********");
			try {
			//HomePage
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//Login Page
			LoginPage lp=new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			
			//MyAccount
			MyAccountPage macc=new MyAccountPage(driver);
			boolean targetPage=macc.isMyAccountPagExists();
			
			if(exp.equalsIgnoreCase("Valid")) {
				
				if(targetPage == true) {
					macc.clickLogout();
					Assert.assertTrue(true);
				}
				else {
					Assert.assertTrue(false);
				}
			}
			if(exp.equalsIgnoreCase("Invalid")) {
				if(targetPage == true) {
					macc.clickLogout();
					Assert.assertTrue(false);
				}
				else {
					Assert.assertTrue(true);
				}
				
			}
			}catch(Exception e) {
				Assert.fail();
			}
			logger.info("*******Finished of TC003_LoginDDT ********");
			
}

	}
}
