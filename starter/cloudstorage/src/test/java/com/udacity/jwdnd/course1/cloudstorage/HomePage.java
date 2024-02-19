package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriverWait webDriverWait;

    @FindBy(css="#logoutDiv button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement navigateToNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navigateToCredentialsTab;
    
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    public void clickNotesTab(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(navigateToNotesTab));
        navigateToNotesTab.click();
    }

    public void clickCredentialsTab() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(navigateToCredentialsTab));
        navigateToCredentialsTab.click();
    }
}