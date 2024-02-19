package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {
    private final WebDriverWait webDriverWait;

    @FindBy(id = "add-credential-btn")
    private WebElement addCredentialBtn;

    @FindBy(id = "save-credential-btn")
    private WebElement saveCredentialBtn;

    @FindBy(id = "credential-url")
    private WebElement credentialURLInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(className = "credential-url-content")
    private WebElement credentialURLContent;

    @FindBy(className = "credential-username-content")
    private WebElement credentialUsernameContent;

    @FindBy(className = "credential-password-content")
    private WebElement credentialPasswordContent;

    @FindBy(id = "edit-credential-btn")
    private WebElement editCredentialBtn;

    @FindBy(id = "delete-credential-btn")
    private WebElement deleteCredentialBtn;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver, 2);
    }

    public void createCredential(String url, String userName, String password) {

        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredentialBtn));
        addCredentialBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOf(credentialURLInput));
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialUsernameInput));
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialPasswordInput));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(saveCredentialBtn));

        credentialURLInput.sendKeys(url);
        credentialUsernameInput.sendKeys(userName);
        credentialPasswordInput.sendKeys(password);
        saveCredentialBtn.click();
    }

    public Credential getCredentialEncrypted() {

        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(credentialURLContent));
            webDriverWait.until(ExpectedConditions.visibilityOf(credentialUsernameContent));
            webDriverWait.until(ExpectedConditions.visibilityOf(credentialPasswordContent));
        } catch (TimeoutException timeoutException) {
            return null;
        }

        Credential credential = new Credential();
        credential.setUrl(credentialURLContent.getText());
        credential.setUsername(credentialUsernameContent.getText());
        credential.setPassword(credentialPasswordContent.getText());
        return credential;
    }

    public void editCredential(String newUrl, String newUserName, String newPassword) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(editCredentialBtn));
        editCredentialBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOf(credentialURLInput));
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialUsernameInput));
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialPasswordInput));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(saveCredentialBtn));

        credentialURLInput.click();
        credentialURLInput.clear();
        credentialURLInput.sendKeys(newUrl);

        credentialUsernameInput.click();
        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(newUserName);

        credentialPasswordInput.click();
        credentialPasswordInput.clear();
        credentialPasswordInput.sendKeys(newPassword);

        saveCredentialBtn.click();
    }

    public void deleteCredential() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteCredentialBtn));
        deleteCredentialBtn.click();
    }

}