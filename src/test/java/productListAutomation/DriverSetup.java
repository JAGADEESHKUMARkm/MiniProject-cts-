package productListAutomation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverSetup {

	static WebDriver driver;
	public void getDriver() throws InterruptedException, IOException {

		//Title validation
		String expectedTitle=driver.getTitle();
		if(expectedTitle.contains("Online Furniture Shopping Store")) {
			System.out.println("Title Matched");
		}	
		else {
			System.out.println("Title Mismatched");
		}	
		Thread.sleep(10000);
	}	
		//if any ad popups this code works
		public void addblock() {
		try {
			driver.findElement(By.xpath("/html/body")).click();
			Set <String> tab1=driver.getWindowHandles();
			List<String>tab2=new ArrayList<String>(tab1);
			String tab3=tab2.get(0);
			driver.switchTo().window(tab3);
		}
		catch (Exception e) {
			System.out.println("the ad is not displayed");
		}
		}
	
		// hovering the furniture
		public void hover() {
		WebElement furniture = driver.findElement(By.name("Furniture"));
		Actions actions = new Actions(driver);
		actions.moveToElement(furniture).build().perform();
		driver.findElement(By.xpath("//a[@class='hd-menu-category-link ng-star-inserted'][normalize-space()='Settees & Benches']")).click();
		}
		
		//getting the total
		public void total() throws InterruptedException, IOException {
		String settees=driver.findElement(By.xpath("//*[@id=\"clip-heder-desktop\"]/div/owl-carousel-o/div/div[1]/owl-stage/div/div/div[2]/pf-clip-category-list/div/a/div/div[2]")).getText();
		System.out.println("count of settees :"+settees);
		String benches=driver.findElement(By.xpath("//*[@id=\"clip-heder-desktop\"]/div/owl-carousel-o/div/div[1]/owl-stage/div/div/div[3]/pf-clip-category-list/div/a/div/div[2]")).getText();
		System.out.println("count of benches :"+benches);
		String Recamiers=driver.findElement(By.xpath("//*[@id=\"clip-heder-desktop\"]/div/owl-carousel-o/div/div[1]/owl-stage/div/div/div[4]/pf-clip-category-list/div/a/div/div[2]")).getText();
		System.out.println("count of Recamiers :"+Recamiers);
		
		//click material,checkbox and apply button
		WebDriverWait mywait = new WebDriverWait(driver,Duration.ofSeconds(10));
		JavascriptExecutor js = (JavascriptExecutor)driver; 

		WebElement material = mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='Material']")));
		js.executeScript("arguments[0].click();", material);
		//Thread.sleep(5000);
		WebElement metal = mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"filter-checkList-checkbox ng-star-inserted\"][3]//label")));
		js.executeScript("arguments[0].click();",metal);
		WebElement apply =  mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),\"APPLY\")]")));
		js.executeScript("arguments[0].click();", apply);
		
		//count of metal benches
		String metalBench=driver.findElement(By.cssSelector("div[class='color-secondary listing-count font-medium text-sm ng-star-inserted'] span:nth-child(2)")).getText();
		System.out.println(metalBench+" Metal Benches");
		
		//sheet
		FileOutputStream file = new FileOutputStream(System.getProperty("user.dir")+"\\Excel\\data.xlsx");
		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet("data");
		XSSFRow row = sheet.createRow(1);
		XSSFCell cell = row.createCell(0);
		XSSFCell cell1 = row.createCell(1);
		XSSFCell cell2 = row.createCell(2);
		XSSFCell cell3 = row.createCell(3);
		cell.setCellValue(settees);
		cell1.setCellValue(benches);
		cell2.setCellValue(Recamiers);
		cell3.setCellValue(metalBench);
		book.write(file);
		book.close();
		file.close();
}
public void screenshot() throws IOException {
		//screenshot
		File FirstScr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destination = new File("../MiniProject/Screenshot/PageScreenshot.png");				
		FileHandler.copy(FirstScr, destination);

		//all tabs has closed
		driver.quit();					
}	

	public static void main(String[] args) throws InterruptedException, IOException 
	{
		System.out.println("In which Browser you want to load the website..?\n 1.Chrome \n 2.Microsoft Edge");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your Choice (1 or 2 ): ");
		int choice=sc.nextInt();
		System.out.println("Loading website...");
		switch(choice)
		{
		case 1:
			driver=LoadDriver.LoadChrome();
			break;
		case 2:
			driver=LoadDriver.LoadEdge();
			break;
		default:
			driver=LoadDriver.LoadChrome();
		}
		DriverSetup load = new DriverSetup();
		load.getDriver();
		load.addblock();
		load.hover();
		load.total();
		load.screenshot();
		
		sc.close();
	}
}
