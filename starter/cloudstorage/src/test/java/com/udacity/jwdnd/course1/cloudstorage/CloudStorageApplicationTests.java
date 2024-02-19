package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import java.io.File;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depending on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.titleContains("Login"));

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertTrue(driver.getPageSource().contains("back to home page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertTrue(driver.getPageSource().contains("403"));

	}

	public void mockLogin() {
		doMockSignUp("firstName", "lastName", "userName", "password");
		doLogIn("userName", "password");
	}

	// Testing
	// 1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
	/**
	 * test Unauthorized User Can Access Only Login And SignUp Pages
	 */
	@Test
	public void testUnauthorizedUserCanAccessLoginAndSignUpPages() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());
	}

	/**
	 * test Unauthorized User Can Not Access Only HomePage And SignUp Pages
	 */
	@Test
	public void testUnauthorizedUserCannotAccessHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}

	/**
	 * test Access HomePage Page Successfully
	 */
	@Test
	public void testAccessHomePageSuccessfully() {
		mockLogin();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		driver.findElement(By.id("logout-btn")).click();

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}

	// 2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
	/**
	 * test Create Note Successfully
	 */
	@Test
	public void testCreateNoteSuccessfully() {
		mockLogin();

		HomePage homePage = new HomePage(driver);
		homePage.clickNotesTab();
		NotePage notePage = new NotePage(driver);
		notePage.saveNewNote("title", "description");

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickNotesTab();
		notePage = new NotePage(driver);
		Note note = notePage.getNote();

		Assertions.assertEquals("title", note.getNoteTitle());
		Assertions.assertEquals("description", note.getNoteDescription());
	}

	/**
	 * test Edit Note Successfully
	 */
	@Test
	public void testEditNoteSuccessfully() {
		mockLogin();

		HomePage homePage = new HomePage(driver);
		homePage.clickNotesTab();
		NotePage notePage = new NotePage(driver);
		notePage.saveNewNote("title", "description");

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickNotesTab();
		notePage = new NotePage(driver);
		notePage.editNote("updated title", "updated description");

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickNotesTab();
		notePage = new NotePage(driver);
		Note newNote = notePage.getNote();

		Assertions.assertEquals("updated title", newNote.getNoteTitle());
		Assertions.assertEquals("updated description", newNote.getNoteDescription());

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickNotesTab();
		notePage = new NotePage(driver);
		notePage.deleteNote();
	}

	/**
	 * test Delete Note Successfully
	 */
	@Test
	public void testDeleteNoteSuccessfully() {
		mockLogin();

		HomePage homePage = new HomePage(driver);
		homePage.clickNotesTab();
		NotePage notePage = new NotePage(driver);
		notePage.saveNewNote("title", "description");

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickNotesTab();
		notePage = new NotePage(driver);
		notePage.deleteNote();

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickNotesTab();
		notePage = new NotePage(driver);
		Note note = notePage.getNote();

		Assertions.assertNull(note);
	}

	// 3. Write Tests for Credential Creation, Viewing, Editing, and Deletion
	/**
	 * test Create Credential Successfully
	 */
	@Test
	public void testCreateCredentialSuccessfully() {
		mockLogin();

		HomePage homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.createCredential("url", "userName", "password");

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		credentialPage = new CredentialPage(driver);
		Credential credential = credentialPage.getCredentialEncrypted();

		Assertions.assertEquals("url", credential.getUrl());
		Assertions.assertEquals("userName", credential.getUsername());
		Assertions.assertNotEquals("password", credential.getPassword());
	}

	/**
	 * test Edit Credential Successfully
	 */
	@Test
	public void testEditCredentialSuccessfully() {
		mockLogin();

		HomePage homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.createCredential("url", "userName", "password");

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		credentialPage = new CredentialPage(driver);
		Credential credential = credentialPage.getCredentialEncrypted();

		Assertions.assertEquals("url", credential.getUrl());
		Assertions.assertEquals("userName", credential.getUsername());
		Assertions.assertNotEquals("password", credential.getPassword());

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		credentialPage = new CredentialPage(driver);
		credentialPage.editCredential("updated url", "updated userName", "updated password");

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		credentialPage = new CredentialPage(driver);
		Credential updatedCredential = credentialPage.getCredentialEncrypted();
		Assertions.assertEquals("updated url", updatedCredential.getUrl());
		Assertions.assertEquals("updated userName", updatedCredential.getUsername());
		Assertions.assertNotEquals(credential.getPassword(), updatedCredential.getPassword());
	}

	/**
	 * test Delete Credential Successfully
	 */
	@Test
	public void testDeleteCredentialSuccessfully() {
		mockLogin();

		HomePage homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.createCredential("url", "userName", "password");

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		credentialPage = new CredentialPage(driver);
		Credential credential = credentialPage.getCredentialEncrypted();

		Assertions.assertEquals("url", credential.getUrl());
		Assertions.assertEquals("userName", credential.getUsername());
		Assertions.assertNotEquals("password", credential.getPassword());

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		credentialPage = new CredentialPage(driver);
		credentialPage.deleteCredential();

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.clickCredentialsTab();
		credentialPage = new CredentialPage(driver);
		credential = credentialPage.getCredentialEncrypted();

		Assertions.assertNull(credential);
	}
}
