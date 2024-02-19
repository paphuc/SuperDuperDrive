package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {
    private final WebDriverWait webDriverWait;
    @FindBy(id = "nav-notes-tab")
    private WebElement navigateToNotesTab;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "save-note-btn")
    private WebElement saveNoteBtn;

    @FindBy(className = "note-title-content")
    private WebElement noteTitleContent;

    @FindBy(className = "note-description-content")
    private WebElement noteDescriptionContent;

    @FindBy(id = "edit-note-btn")
    private WebElement editNoteBtn;

    @FindBy(id = "delete-note-btn")
    private WebElement deleteNoteBtn;

    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        webDriverWait = new WebDriverWait(driver, 2);
        PageFactory.initElements(driver,this);

    }

    public void saveNewNote(String title, String description) {

        webDriverWait.until(ExpectedConditions.elementToBeClickable(addNoteBtn));

        addNoteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDescriptionInput));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(saveNoteBtn));

        noteTitleInput.sendKeys(title);
        noteDescriptionInput.sendKeys(description);
        saveNoteBtn.click();
    }

    public Note getNote() {

        try {
            webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleContent));
            webDriverWait.until(ExpectedConditions.visibilityOf(noteDescriptionContent));
        } catch (TimeoutException timeoutException) {
            return null;
        }

        Note note = new Note();
        note.setNoteTitle(noteTitleContent.getText());
        note.setNoteDescription(noteDescriptionContent.getText());
        return note;
    }

    public void editNote(String newTitle, String newDescription) {

        webDriverWait.until(ExpectedConditions.elementToBeClickable(editNoteBtn));
        editNoteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOf(noteTitleInput));
        webDriverWait.until(ExpectedConditions.visibilityOf(noteDescriptionInput));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(saveNoteBtn));

        noteTitleInput.click();
        noteTitleInput.clear();
        noteTitleInput.sendKeys(newTitle);

        noteDescriptionInput.click();
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(newDescription);

        saveNoteBtn.click();
    }

    public void deleteNote() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteNoteBtn));
        deleteNoteBtn.click();
    }
}