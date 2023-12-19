package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequiredTests {
    @LocalServerPort
    private int port;

    private WebDriver driver;

    private String urlPart;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.urlPart = "http://localhost:" + this.port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    private void doMockSignUp(String firstName, String lastName, String userName, String password) throws InterruptedException {

        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get("http://localhost:" + this.port + "/signup");
        Thread.sleep(1000);
        driver.findElement(By.id("inputFirstName")).click();
        driver.findElement(By.id("inputFirstName")).sendKeys(firstName);
        driver.findElement(By.id("inputLastName")).click();
        driver.findElement(By.id("inputLastName")).sendKeys(lastName);
        driver.findElement(By.id("inputUsername")).click();
        driver.findElement(By.id("inputUsername")).sendKeys(userName);
        driver.findElement(By.id("inputPassword")).click();
        driver.findElement(By.id("inputPassword")).sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

        Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
        Assertions.assertTrue(driver.findElement(By.id("signupsuccess")).isDisplayed());
    }

    private void doLogIn(String userName, String password) {
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginBtn")));
        WebElement loginButton = driver.findElement(By.id("loginBtn"));
        loginButton.click();
        try {
            webDriverWait.until(ExpectedConditions.titleContains("Home"));
        }catch(Exception ignored){}
    }

    private void doLogOut() throws InterruptedException {
        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get(urlPart + "/home");
        Thread.sleep(1000);
        Thread.sleep(1000);
        driver.findElement(By.id("logoutBtn")).click();
        Thread.sleep(1000);
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
        driver.get(urlPart + "/home");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());


    }

    @Test
    @Order(1)
    void LoginLogout() throws InterruptedException {
        doMockSignUp("Test","test","ReqTest","password");
        doLogIn("ReqTest","password");
        doLogOut();
    }

    @Test
    void securityTest() throws InterruptedException {
        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get(urlPart + "/home");
        Thread.sleep(1000);
        Assertions.assertEquals(urlPart + "/login", driver.getCurrentUrl());
    }
    @Test
    @Order(2)
    void noteCreationTest() throws InterruptedException {
        doLogIn("ReqTest","password");

        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get(urlPart + "/home");
        Thread.sleep(1000);
        driver.findElement(By.id("noteTab")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("addNote")).click();
        Thread.sleep(1000);
        driver.findElement(By.name("noteTitle")).sendKeys("Testing title");
        Thread.sleep(1000);
        driver.findElement(By.name("noteDescription")).sendKeys("Testing Description");
        driver.findElement(By.id("saveNote")).click();
        Assertions.assertEquals(urlPart + "/result?success=true", driver.getCurrentUrl());
        driver.get(urlPart + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Thread.sleep(2000);
        driver.findElement(By.id("noteTab")).click();
        Thread.sleep(2000);
        Assertions.assertEquals("Testing title", driver.findElement(By.id("note-title-checker")).getText());
        Assertions.assertEquals("Testing Description", driver.findElement(By.id("note-description-checker")).getText());

    }

    @Test
    @Order(3)
    void changingNotetest() throws InterruptedException {
        doLogIn("ReqTest","password");

        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get(urlPart + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        driver.findElement(By.id("noteTab")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("note-edit")).click();
        Thread.sleep(1000);
        driver.findElement(By.name("noteTitle")).clear();
        Thread.sleep(1000);
        driver.findElement(By.name("noteTitle")).sendKeys("Testing title edit");
        Thread.sleep(1000);
        driver.findElement(By.id("note-description")).clear();
        Thread.sleep(1000);
        driver.findElement(By.id("note-description")).sendKeys("Testing Description edit");
        driver.findElement(By.id("saveNote")).click();
        Thread.sleep(2000);

        driver.get(urlPart + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Thread.sleep(1000);
        driver.findElement(By.id("noteTab")).click();
        Thread.sleep(2000);

        Assertions.assertEquals("Testing title edit", driver.findElement(By.id("note-title-checker")).getText());
        Assertions.assertEquals("Testing Description edit", driver.findElement(By.id("note-description-checker")).getText());
    }

    @Test
    @Order(4)
    void deletingNotetest() throws InterruptedException {
        doLogIn("ReqTest","password");

        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get(urlPart + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Thread.sleep(1000);
        driver.findElement(By.id("noteTab")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("note-delete")).click();
        Thread.sleep(1000);
        Assertions.assertEquals(urlPart + "/result?success=true", driver.getCurrentUrl());
        driver.get(urlPart + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Thread.sleep(1000);
        driver.findElement(By.id("noteTab")).click();

        Assertions.assertTrue(driver.findElements(By.id("note-title-checker")).isEmpty());
    }

    @Test
    @Order(5)
    void creatingCredtest() throws InterruptedException {
        doLogIn("ReqTest","password");

        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get(urlPart + "/home");
        Thread.sleep(1000);
        driver.findElement(By.id("credentialsTab")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("addCredential")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("credential-url")).sendKeys("https://google.com");
        Thread.sleep(1000);
        driver.findElement(By.id("credential-username")).sendKeys("Testing username");
        Thread.sleep(1000);
        driver.findElement(By.id("credential-password")).sendKeys("Testing password");
        Thread.sleep(1000);
        driver.findElement(By.id("saveCredential")).click();
        Thread.sleep(1000);
        driver.get(urlPart + "/home");
        Thread.sleep(1000);
        driver.findElement(By.id("credentialsTab")).click();
        Thread.sleep(1000);
        Assertions.assertEquals("https://google.com", driver.findElement(By.id("Credential-checker")).getText());
        Assertions.assertEquals("Testing username", driver.findElement(By.id("username-checker")).getText());
        Thread.sleep(1000);

    }

    @Test
    @Order(6)
    void editingCredtest() throws InterruptedException {
        doLogIn("ReqTest","password");

        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get(urlPart + "/home");
        Thread.sleep(1000);
        driver.findElement(By.id("credentialsTab")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("credential-edit")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("credential-url")).clear();
        driver.findElement(By.id("credential-url")).sendKeys("http://udacity.com");
        Thread.sleep(1000);
        driver.findElement(By.id("credential-username")).clear();
        driver.findElement(By.id("credential-username")).sendKeys("Testing username edit");
        Thread.sleep(1000);
        driver.findElement(By.id("credential-password")).clear();
        driver.findElement(By.id("credential-password")).sendKeys("testing password edit");
        Thread.sleep(1000);
        driver.findElement(By.id("saveCredential")).click();
        Assertions.assertEquals(urlPart + "/result?success=true", driver.getCurrentUrl());
        driver.get(urlPart + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Thread.sleep(1000);
        driver.findElement(By.id("credentialsTab")).click();
        Thread.sleep(1000);

        Assertions.assertEquals("http://udacity.com",driver.findElement(By.id("Credential-checker")).getText());
        Assertions.assertEquals("Testing username edit", driver.findElement(By.id("username-checker")).getText());
    }

    @Test
    @Order(7)
    void deletingCredtest() throws InterruptedException {
        doLogIn("ReqTest","password");

        WebDriverWait webDriverWait = new WebDriverWait(driver,2L);
        driver.get(urlPart + "/home");
        Thread.sleep(1000);
        driver.findElement(By.id("credentialsTab")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("credential-delete")).click();
        Thread.sleep(1000);
        Assertions.assertEquals(urlPart + "/result?success=true", driver.getCurrentUrl());
        driver.get(urlPart + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Thread.sleep(1000);
        driver.findElement(By.id("credentialsTab")).click();
        Assertions.assertTrue(driver.findElements(By.id("Credential-checker")).isEmpty());
    }
}
