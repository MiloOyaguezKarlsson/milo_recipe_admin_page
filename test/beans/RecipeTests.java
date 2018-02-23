/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import utilities.ConnectionFactory;
import utilities.Recipe;

/**
 *
 * @author milooyaguez karlsson
 */
public class RecipeTests {
    
    public WebDriver webDriver;
    public Connection connection;
    
    public RecipeTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        try {
            connection.close();
            webDriver.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of deleteRecipe method, of class RecipeBean.
     */
    @Test
    public void testDeleteRecipe() {
        try {
            // lägg till ett test-recept som sedan ska tas bort
            connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO recipes VALUES (?,?,?,?, CURRENT_DATE(),?,?,?)");
            stmt.setInt(1, 1); //sätter id till 1 så att den hamnar högst upp i tabellen
            stmt.setString(2, "test");
            stmt.setString(3, "milo");
            stmt.setString(4, "test");
            stmt.setString(5, "test");
            stmt.setString(6, "test");
            stmt.setInt(7, 1);
            stmt.executeUpdate();

            // selenium gå in och tryck på knappen för att ta bort test-receptet
            // alla id:n är aoutogenererade av jsf så jag går in i html källkoden och letar upp de jag vill ha
            System.setProperty("webdriver.chrome.driver", "C:\\Java\\chromedriver\\chromedriver.exe");
            webDriver = new ChromeDriver();
            webDriver.get("http://localhost:8080/milo_recipe_admin_page/faces/index.xhtml");
            //logga in
            webDriver.findElement(By.id("j_idt5:username")).sendKeys("milo");
            webDriver.findElement(By.id("j_idt5:password")).sendKeys("Milo123!");
            webDriver.findElement(By.id("j_idt5:login-btn")).click();

            //ta bort första receptet
            WebElement deleteBtn = fluentWait(By.id("j_idt9:recipeForm"))
                    .findElement(By.tagName("button"));
            deleteBtn.click();

            // kolla så att receptet togs bort i databasen
            //vänta för att receptet ska hinna tas bort
            Thread.sleep(10000);
            //hämta alla recept
            stmt = connection.prepareStatement("SELECT * FROM recipes");
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                //kolla så att recept med id 1 inte finns kvar
                assertNotEquals(1, result.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public WebElement fluentWait(final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);
        
        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });
        
        return foo;
    }
    
}
