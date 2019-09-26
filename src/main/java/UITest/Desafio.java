package UITest;

import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Desafio {
	
WebDriver driver;
	
	@Parameters("browserSelect")	

	@BeforeTest
	public void setUp(String browserSelect) {
		
		//Preparação para múltiplos browsers em paralelo
		
		try {
		
			if(browserSelect.equalsIgnoreCase("firefox")) {
			 
			System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize(); 
			
			  }else if (browserSelect.equalsIgnoreCase("chrome")) { 
			 
				  System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");  
				  driver = new ChromeDriver();
				  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				  driver.manage().window().maximize();
				  
			  }
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test(description="teste do Desafio 1")
	public void desafio1() {
		
		driver.get("https://shopcart-challenge.4all.com");
		
		driver.findElement(By.id("open-categories-btn")).click();
	    
		driver.findElement(By.id("category-1")).click();
	    
		
		WebDriverWait wait = new WebDriverWait(driver,3);
		
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-product-4-btn")));
		driver.findElement(By.id("add-product-4-btn")).click();
		
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-product-5-btn")));
	    driver.findElement(By.id("add-product-5-btn")).click();
		
	    
		driver.findElement(By.id("open-categories-btn")).click();
	    
		driver.findElement(By.id("category-all")).click();
	    
	    driver.findElement(By.id("cart-btn")).click();
	    
	    for (int i=0; i<4; i++) {	
			driver.findElement(By.id("add-product-4-qtd")).click();	
		}
		
	    driver.findElement(By.id("finish-checkout-button")).click();	    
	    
	    //Validação
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ReactModalPortal")));
	  	    
	  	WebElement Success = driver.findElement(By.className("ReactModalPortal"));
	  	String SuccessMessage = Success.getText();
	  	Assert.assertTrue(SuccessMessage.contains("Pedido realizado com sucesso!"));
	  	System.out.print("\n" + SuccessMessage + "\n");
	  	    
	  	//Finalização
	  	driver.findElement(By.className("close-modal")).click();
	    
	}
	
	
	@Test(description="teste do Desafio 2")
	public void desafio2() {
		
		List<Double> price = new ArrayList<Double>();
		price.add(3.5);
		price.add(3.0);	
		price.add(2.0);
		price.add(5.5);
		price.add(5.0);
		price.add(4.0);
		
		driver.get("https://shopcart-challenge.4all.com");
		double Total = 0;
		
			
		driver.findElement(By.id("open-categories-btn")).click();
	    
		driver.findElement(By.id("category-0")).click();
		
		
		WebDriverWait wait = new WebDriverWait(driver,3);
		
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-product-0-btn")));
		driver.findElement(By.id("add-product-0-btn")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-product-1-btn")));
	    driver.findElement(By.id("add-product-1-btn")).click();
	    
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-product-2-btn")));
	    driver.findElement(By.id("add-product-2-btn")).click();
		
		driver.findElement(By.id("open-categories-btn")).click();
	    
		driver.findElement(By.id("category-all")).click();

		for (int i=0; i<3; i++)	{
		Total = Total + price.get(i);
			}
		
			
		driver.findElement(By.id("add-product-3-btn")).click();
		Total = Total + price.get(3);
			 
		driver.findElement(By.id("cart-btn")).click();		
			
		for (int i=0; i<9; i++) {
				
			driver.findElement(By.id("add-product-3-qtd")).click();
			Total = Total + price.get(3);
				
			}
					
			
		for (int i=0; i<5; i++) {
				
			driver.findElement(By.id("remove-product-3-qtd")).click();
			Total = Total - price.get(3);
				
			}
		
		//Validação do preço dos produtos (vide nota)
		
		SoftAssert softAssert = new SoftAssert();

		NumberFormat Reais = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));	
		String totalValue = Reais.format(Total);
		softAssert.assertEquals((driver.findElement(By.id("price-total-checkout"))).getText(),totalValue);
		System.out.print("\n" + totalValue + "\n");
			

					 
		driver.findElement(By.id("finish-checkout-button")).click();
	    
	    //Validação
	  	wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ReactModalPortal")));
	  	    
	  	WebElement Success = driver.findElement(By.className("ReactModalPortal"));
	  	String SuccessMessage = Success.getText();
	  	Assert.assertTrue(SuccessMessage.contains("Pedido realizado com sucesso!"));
	  	System.out.print("\n" + SuccessMessage + "\n");
	  	    
	  	//Finalização
	  	driver.findElement(By.className("close-modal")).click();
		
	}
	
	@AfterTest
	public void tearDown() {
		
		driver.quit();
	}
}