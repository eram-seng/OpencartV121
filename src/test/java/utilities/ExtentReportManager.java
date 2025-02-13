package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.observer.ExtentObserver;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
   public ExtentSparkReporter sparkReporter;
   public ExtentReports extent;
   public ExtentTest test;
   String repName;

   public void onStart(ITestContext testContext) {
	   
	   /*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	   Date dt=new Date();
	   String currentdatetimestamp=df.format(dt);*/
	   
	   
      String timeStamp = (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")).format(new Date());//Time stamp
      this.repName = "Test-Report-" + timeStamp + ".html";
      this.sparkReporter = new ExtentSparkReporter(".//reports/" + this.repName); //specify the location of the report
      this.sparkReporter.config().setDocumentTitle("opencart Automation Report"); //Title of the report
      this.sparkReporter.config().setReportName("opencart Functional Testing"); //Name of the report
      this.sparkReporter.config().setTheme(Theme.DARK);
      this.extent = new ExtentReports();
      this.extent.attachReporter(new ExtentObserver[]{this.sparkReporter});
      this.extent.setSystemInfo("Application", "opencart");
      this.extent.setSystemInfo("Module", "Admin");
      this.extent.setSystemInfo("Sub Module", "Customers");
      this.extent.setSystemInfo("User Name", System.getProperty("user.name"));
      this.extent.setSystemInfo("Environemnt", "QA");
      String os = testContext.getCurrentXmlTest().getParameter("os");
      this.extent.setSystemInfo("Operating System", os);
      String browser = testContext.getCurrentXmlTest().getParameter("browser");
      this.extent.setSystemInfo("Browser", browser);
      List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
      if (!includedGroups.isEmpty()) {
         this.extent.setSystemInfo("Groups", includedGroups.toString());
      }

   }

   public void onTestSuccess(ITestResult result) {
      this.test = this.extent.createTest(result.getTestClass().getName());
      this.test.assignCategory(result.getMethod().getGroups());
      this.test.log(Status.PASS, result.getName() + " got successfully executed");
   }

   public void onTestFailure(ITestResult result) {
      this.test = this.extent.createTest(result.getTestClass().getName());
      this.test.assignCategory(result.getMethod().getGroups());
      this.test.log(Status.FAIL, result.getName() + " got failed");
      this.test.log(Status.INFO, result.getThrowable().getMessage());

      try {
         String imgPath = (new BaseClass()).captureScreen(result.getName());
         this.test.addScreenCaptureFromPath(imgPath);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void onTestSkipped(ITestResult result) {
      this.test = this.extent.createTest(result.getTestClass().getName());
      this.test.assignCategory(result.getMethod().getGroups());
      this.test.log(Status.SKIP, result.getName() + " got skipped");
      this.test.log(Status.INFO, result.getThrowable().getMessage());
   }

   public void onFinish(ITestContext testContext) {
      this.extent.flush();
      String pathOfExtentReport = System.getProperty("user.dir") + "/reports/" + this.repName;
      File extentReport = new File(pathOfExtentReport);

      try {
    	  Desktop.getDesktop().open(extentReport);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }
}
