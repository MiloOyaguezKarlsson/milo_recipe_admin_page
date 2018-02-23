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
import resources.UserResource;
import utilities.BCrypt;
import utilities.ConnectionFactory;
import utilities.User;

/**
 *
 * @author milooyaguez karlsson
 */
public class SetPwTest {

    Connection connection;
    WebDriver webDriver;

    public SetPwTest() {
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
            webDriver.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(SetPwTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of setPassword method, of class UserBean.
     */
    @Test
    public void testSetPassword() {
        try {
            // lägga till en test användare att ändra lösenordet på
            connection = ConnectionFactory.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users VALUES (?,?,?)");
            stmt.setString(1, "aaaaa");
            stmt.setString(2, "test");
            stmt.setInt(3, 1);
            stmt.executeUpdate();

            System.setProperty("webdriver.chrome.driver", "C:\\Java\\chromedriver\\chromedriver.exe");
            webDriver = new ChromeDriver();
            webDriver.get("http://localhost:8080/milo_recipe_admin_page/faces/index.xhtml");

            //logga in
            webDriver.findElement(By.id("j_idt5:username")).sendKeys("milo");
            webDriver.findElement(By.id("j_idt5:password")).sendKeys("Milo123!");
            webDriver.findElement(By.id("j_idt5:login-btn")).click();

            //gå in på users tabben
            WebElement element = fluentWait(By.id("j_idt9")).findElement(By.
                    tagName("ul")).findElements(By.tagName("li")).get(1);
            element.click();

            //skicka in nytt lösen
            webDriver.findElement(By.id("j_idt9:userForm"))
                    .findElements(By.tagName("input")).get(1).sendKeys("testpw");

            //klicka på ändra knappen
            webDriver.findElement(By.id("j_idt9:userForm"))
                    .findElements(By.tagName("button"))
                    .get(0).click();

            //vänta för att låta ändringen göras
            Thread.sleep(10000);

            //kolla så att lösenordet ändrats
            stmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, "aaaaa");
            ResultSet result = stmt.executeQuery();

            String changedPw = "";
            if (result.next()) {
                changedPw = result.getString("password");
            }
            boolean pwChanged = BCrypt.checkpw("testpw", changedPw);

            // ta bort användaren
            UserResource.deleteUser("aaaaa");

            assertEquals(true, pwChanged);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserDeleteTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserDeleteTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserDeleteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // metod för att vänta medans drivern letar efter element som inte finns ännu
    // för att undvika krasch
    public WebElement fluentWait(final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(locator);
            }
        });

        return element;
    }

}
