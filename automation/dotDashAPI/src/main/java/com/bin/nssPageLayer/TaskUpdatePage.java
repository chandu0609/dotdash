package com.bin.nssPageLayer;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.bin.basepackage.BaseAPIClass;

public class TaskUpdatePage extends BaseAPIClass{

	public TaskUpdatePage(WebDriver driver) throws IOException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how=How.NAME, using ="data")
	private WebElement taskNameTxt;
	
	@FindBy(how=How.CSS, using ="input[type='submit'][value='Update']")
	private WebElement taskUpdateBtn;
	
	@FindBy(how=How.NAME, using = "category")
	private WebElement assignCategoryDropDown;
		
	@FindBy(how=How.NAME, using = "due_day")
	private WebElement dueDateSelect;
	
	@FindBy(how=How.NAME, using = "due_month")
	private WebElement dueDateMonth;
	
	@FindBy(how=How.NAME, using = "due_year")
	private WebElement dueDateYear;

	public void enterUpdatedTask(String updatedTask) {
		// TODO Auto-generated method stub
		taskNameTxt.clear();
		taskNameTxt.sendKeys(updatedTask);
	}

	public void updateTask() {
		// TODO Auto-generated method stub
		taskUpdateBtn.click();
	}

	public void updateCategory(String category) {
		Select categoryDropDown = new Select(assignCategoryDropDown);
		categoryDropDown.selectByVisibleText(category);
	}
	
	public void updateDate(String dueDay, String dueMonth, String dueYear) {
		Select dueDaySelect = new Select(dueDateSelect);
		dueDaySelect.selectByVisibleText(dueDay);
		
		if(dueMonth != "") {
		 Select dueDateMon = new Select(dueDateMonth);
		 dueDateMon.selectByVisibleText(dueMonth);
		}
		
		Select dueDateYr = new Select(dueDateYear);
		dueDateYr.selectByVisibleText(dueYear);
	}

	
	public void navigateToTaskMainPage() {
		String completeURL = prop.getProperty("completeurl");
		driver.get(completeURL);		
	}

	
	public void naviagteBack() {
		driver.navigate().back();
	}
	
	public String getTaskName() {
		return taskNameTxt.getAttribute("value");
	}
	
	public String getCategory() {
		// TODO Auto-generated method stub
		Select categoryDropDown = new Select(assignCategoryDropDown);
		String taskCategory = categoryDropDown.getFirstSelectedOption().getText();
		return taskCategory;
	}
	
	public String getDueDay() {
		// TODO Auto-generated method stub
		Select dueDayDropDown = new Select(dueDateSelect);
		String dueDayTask = dueDayDropDown.getFirstSelectedOption().getText();
		return dueDayTask;
	}
	
	
	public String getDueMonth() {
		// TODO Auto-generated method stub
		Select dueMonthDropDown = new Select(dueDateMonth);
		String dueMonthTask = dueMonthDropDown.getFirstSelectedOption().getText();
		return dueMonthTask;
	}
	
	
	public String getDueYear() {
		// TODO Auto-generated method stub
		Select dueYearDropDown = new Select(dueDateYear);
		String dueYearTask = dueYearDropDown.getFirstSelectedOption().getText();
		return dueYearTask;
	}

}