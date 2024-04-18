import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//import org.testng.annotations.Test;
//xidwgumwwowibzwt
public class FPL_Bot {

	WebDriver driver;
	JavascriptExecutor Jscript;
	ScrollPage scroll;
	Statement stmt;
	ResultSet rs;
	Statement stmt2;
	ResultSet rs2;
	SimpleDateFormat dateFormat,dateFormat2;
	boolean closed=false;
	File f;
	boolean updateComplete; 
	
	public FPL_Bot(){
		
		
		setDataBaseConnection();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
		f = new File("C:\\FPL_Downloads\\Document.pdf");
		updateComplete=false;
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\Jars\\chromedriver.exe");
		
		
			HashMap<String,Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("plugins.always_open_pdf_externally", true);
			chromePrefs.put("download.default_directory", "C:"+File.separator+"FPL_Downloads");
			chromePrefs.put("excludeSwitches", "enable-popup-blocking");
					
			
			
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			
			
			
			driver = new ChromeDriver(options);
						
		   
			
			
			Jscript = (JavascriptExecutor) driver;
			//driver.get("http://localhost/GDrive/FPLServer.php?insert_file");
			//Thread.sleep(1000000);
			
			
			driver.get("https://www.fpl.com/my-account/multi-dashboard.html");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Thread.sleep(3000);
			
			Monitor monitor=new Monitor();
			monitor.start();
		
			
		}catch(NoSuchElementException e) {System.out.println("Page refresh");driver.navigate().refresh();}
		catch(Exception ee) {ee.printStackTrace();}
	}
	class Monitor extends Thread
	{		
		public void run() {
			try {
				
				driver.findElement(By.id("core_view_form_ValidationTextBox_0")).sendKeys("bookkeeper@nbvresorts.com");
				driver.findElement(By.id("core_view_form_ValidationTextBox_1")).sendKeys("2019SandaPar");
				//driver.findElement(By.xpath("//input[@id='core_view_form_ValidationTextBox_1']")).sendKeys("***");			
				onClickId("Login","core_view_form_Button_0_label");
									
				rs=stmt.executeQuery("select * from fpl_accounts where Update_Date IS null OR NOT Update_Date = CURDATE()");
				Date checkDate;
				String account_No;
				
				while(rs.next()) {
					
					
					checkDate = rs.getDate(4);
					account_No = rs.getString(1);
					
						System.out.println("Updating account: "+account_No);
						//driver.findElement(By.id("core_view_form_ValidationTextBox_4")).sendKeys(account_No);
						
						typeAccount("Sending Key Account Textfield",account_No);
						onClickXpath("Click Account No.","//a[@class='account-number-link']",false);					
						
								if(!closed) {
												//onClickLink("Click Close","Close");
												closed=true;
											}	
								
						onClickLink("Click View Bill","VIEW BILL");		
						Download DL = new Download(account_No,this);
						DL.start();
					
						onClickXpath("Click Download","//span[@id='core_view_form_Button_2_label']",true);
						this.suspend();
						System.out.println("fffffffff");
								String oldTab = driver.getWindowHandle();
								ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
							    		//newTab.remove(oldTab);
							    		// change focus to new tab
							    driver.switchTo().window(newTab.get(0));
								
								
						//stmt2.execute("UPDATE fpl_accounts SET Update_Date = '"+dateFormat.format(new Date())+"' where account_no ='"+account_No+"'");
						driver.get("https://www.fpl.com/my-account/multi-dashboard.html");
						System.out.println(account_No+" updated");
						
										
				}
				
				
		
				
			}catch(NoSuchElementException e) {System.out.println("Page refreshmonitor");driver.navigate().refresh();}
			catch(Exception ee) {System.out.println("Monitor exception");ee.printStackTrace();}
				
			}
	
			public void typeAccount(String title,String input) throws Exception
			{
				int timeouts = 0;
				
				while(true) {
					try {
						 if(timeouts == 0) 
							System.out.println("Starting "+title);
						 else
							 System.out.println("Timeout "+timeouts);				 
						 	 driver.findElement(By.id("core_view_form_ValidationTextBox_4")).sendKeys(input);
							
						System.out.println(title+" complete:");
						break;
					}catch(NoSuchElementException e) {System.out.println("Page refresh4");driver.navigate().refresh();}
					catch(Exception ee) {scrollDown();}
				}
			}
			
			public void onClickXpath(String title,String input,boolean forEmail) throws Exception
			{
				int timeouts = 0;
				
				while(true) {
					try {
						 if(timeouts == 0) 
							System.out.println("Starting "+title);
						 else
							 System.out.println("Timeout "+timeouts);				 
							driver.findElement(By.xpath(input)).click();
						
							if(forEmail == true) {
								//new SendEmail();
							}
							
						System.out.println(title+" complete:");
						break;
					}catch(NoSuchElementException e) {System.out.println("Page refresh1");driver.navigate().refresh();}
					catch(Exception ee) {scrollDown();}
				}
			}
			
			public void onClickLink(String title,String input) throws Exception
			{
				int timeouts = 0;
				
				while(true) {
					try {
						 if(timeouts == 0) 
							System.out.println("Starting "+title);
						 else
							 System.out.println("Timeout "+timeouts);
						 
						 driver.findElement(By.linkText(input)).click();
						
						System.out.println(title+" complete:");
						break;
					}catch(NoSuchElementException e) {System.out.println("Page refresh2");driver.navigate().refresh();}
					 catch(Exception ee) {scrollDown();}
				}
			}
			public void onClickId(String title,String input) throws Exception
			{
				int timeouts = 0;
				
				while(true) {
					try {
						 if(timeouts == 0) 
							System.out.println("Starting "+title);
						 else
							 System.out.println("Timeout "+timeouts);
						 
						 driver.findElement(By.id(input)).click();
						
						System.out.println(title+" complete:");
						break;
					}catch(NoSuchElementException e) {System.out.println("Page refresh3");driver.navigate().refresh();}
					catch(Exception ee) {scrollDown();}
				}
			}
			public void scrollDown() {
				 Jscript.executeScript("window.scrollBy(0,100)", "");
			}
	}
	public void setDataBaseConnection() {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/fpldata","root","");  	
			stmt=con.createStatement();
			stmt2=con.createStatement();
			
		   }catch(Exception e){ e.printStackTrace();}  
	}
	
	class ScrollPage extends Thread {
		public void run() {
			try {
				 while(true) {
			            Jscript.executeScript("window.scrollBy(0,10)", "");
			            Thread.sleep(100);}
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}catch(Exception ee) {ee.printStackTrace();}
		}
		
	}
	class Download extends Thread {
		String acc_no;
		Monitor mon;
		String fileName;
		public Download(String in,Monitor monitor) {
			acc_no = in;
			mon=monitor;
		}
		public void run() {
			try {
				while(true){
					System.out.println("Download thread waiting");
					Thread.sleep(500);
					
					if(!f.exists()) {
						
					}else {
						fileName = dateFormat.format(new Date())+"_"+acc_no+".pdf";
						f.renameTo(new File("C:\\FPL_Downloads\\"+fileName));
						driver.get("http://localhost/gdrive/FPLServer.php?insert_onefile&Ac_no="+fileName);
						System.out.println("Download thread complete");
						mon.resume();
						break;
					}
					
				}
			}catch(Exception ee) {ee.printStackTrace();}
		}		
	}
	
	public static void main(String args[]) {new FPL_Bot();}
}
