package com.bin.nssPageLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bin.basepackage.BaseAPIClass;

public class TaskMainPage extends BaseAPIClass{

	WebDriverWait eWait;
	HashMap<String, String> categoryColors;
	String overDueColorInHex= "";
	public TaskMainPage(WebDriver driver) throws IOException {
	   	 this.driver = driver;
	   	 eWait = new WebDriverWait(driver, 20);
	     categoryColors = new HashMap<String, String>();
		 PageFactory.initElements(driver, this);
	}

	@FindBy(how=How.XPATH, using = "//div[@id='todos-content']//ul//li")
	private List<WebElement> taskTableList;
	
	@FindBy(how=How.NAME, using = "data")
	private WebElement taskNameTxtBox;
	
	@FindBy(how=How.NAME, using = "category")
	private WebElement assignCategoryDropDown;
	
	@FindBy(how=How.NAME, using = "categorydata")
	private WebElement createCategoryTxtBox;
	
	@FindBy(how=How.NAME, using = "colour")
	private WebElement assignCategoryColourDropDown;
	
	@FindBy(how=How.CSS, using = "div[class='advance-controls'] input[type='submit'][value='Add category']")
	private WebElement categoryAddBtn;
	
	@FindBy(how=How.NAME, using = "due_day")
	private WebElement dueDateSelect;
	
	@FindBy(how=How.NAME, using = "due_month")
	private WebElement dueDateMonth;
	
	@FindBy(how=How.NAME, using = "due_year")
	private WebElement dueDateYear;
	
	@FindBy(how=How.CSS, using = "div[class='advance-controls'] input[type='submit'][value='Add']")
	private WebElement taskAddBtn;
	
	@FindBy(how=How.CSS, using = "div[class='controls'] input[type='submit'][value='Remove']")
	private WebElement taskRemoveBtn;
	
	@FindBy(how=How.CSS, using = "div[class='controls'] input[type='submit'][value='Complete']")
	private WebElement taskCompleteBtn;
	
	@FindBy(how=How.XPATH, using = "//div[@class='controls']//a//span")
	private List<WebElement> categoryList;
	
	@FindBy(how=How.XPATH, using = "//div[@class='controls']//span[contains(text(),'Overdue')]")
	private WebElement overdueCategory;
	
	@FindBy(how=How.XPATH, using = "//div[@class='controls']//a[@title='Remove this category']")
	private List<WebElement> listOfCategory;
	
	@FindBy(how=How.XPATH, using = "//a[text()='Yes']")
	private WebElement confirmYesRemoveCategory;
	
	@FindBy(how=How.XPATH, using = "//a[text()='Rename the category']")
	private WebElement renameTheCategory;
	
	@FindBy(how=How.XPATH, using = "//a[text()='Add the category with this colour']")
	private WebElement addCategoryWiththisColor;
	
	@FindBy(how=How.XPATH, using = "//a[text()='Nevermind']")
	private WebElement neverMindCatergorySet;
	
	@FindBy(how=How.NAME, using = "allbox")
	private WebElement toggleCheckBox;
	
	public WebElement getTaskUnStyledTaskWebElement(String taskName) {
		for(WebElement taskElement : taskTableList) {
			if(taskElement.getText().contains(taskName)) {
				return taskElement;
		}
      }
		return null;
	}
	
	public boolean clickTaskPresent(String taskName) {
		WebElement taskWebElement = getTaskUnStyledTaskWebElement(taskName);
		if(taskWebElement!=null) {
		   eWait.until(ExpectedConditions.elementToBeClickable(taskWebElement));
		   taskWebElement.findElement(By.cssSelector("input[type='checkbox']")).click();
		   return true;
		}else {
			return false;
		}
	}
	
	
	public boolean isTaskPresent(String taskName, String date) {
		WebElement taskWebElement = getTaskStyledTaskWebElement(taskName, date);
		if(taskWebElement!=null) {
		   return true;
		}else {
			return false;
		}
	}
	
	
	
	public boolean isTaskPresentWithCategoryAndDate(String taskName, String category, String date) {
		WebElement taskWebElement = getTaskStyledTaskWebElement(taskName, date);
		boolean taskForCategory = isTaskAssignedCategoryWithColor(taskWebElement, taskName);
		if(taskWebElement!=null && taskForCategory) {
		   return true;
		}else {
			return false;
		}
	}
	
	private WebElement getTaskStyledTaskWebElement(String taskName, String date) {
		for(WebElement taskElement : taskTableList) {
			if(taskElement.getText().contains(taskName) && taskElement.getText().contains(date)) {
				return taskElement;
		      }
		    }
			return null;
	}
	
	

	
	///Future Date the color is expected to category color
	public boolean isTaskAssignedCategoryWithColor(WebElement taskElement, String taskName) {
		// TODO Auto-generated method stub
		//WebElement taskElement = GetTaskUnStyledTaskWebElement(taskName);
		WebElement taskElementSpan = taskElement.findElement(By.cssSelector("span"));
		String color = taskElementSpan.getCssValue("color");
		String hex = Color.fromString(color).asHex();
		for(Entry<String, String> entry : categoryColors.entrySet()) {
		   if(entry.getValue().equals(hex))	{
			   System.out.println( entry.getKey());
			   return true;
		   }
		}
		return false;
	}
	
	
	
	public boolean isTaskAssignedCategoryWithNoDueDate(String taskName, String category) {
		// TODO Auto-generated method stub
		WebElement taskElement = getTaskUnStyledTaskWebElement(taskName);
		WebElement taskElementSpan = taskElement.findElement(By.cssSelector("span"));
		String color = taskElementSpan.getCssValue("color");
		String hex = Color.fromString(color).asHex();
		for(Entry<String, String> entry : categoryColors.entrySet()) {
		   if(entry.getValue().equals(hex))	{
			   System.out.println( entry.getKey());
			   return true;
		   }
		}
		return false;
	}
	
	
	/*public boolean isTaskPresentWithCategoryAndPreviousDueDate(String taskName, String category, String date) {
		WebElement taskWebElement = getTaskStyledTaskWebElement(taskName, date);
		boolean taskForCategory = isTaskAssignedCategoryWithColor(taskWebElement, taskName);
		if(taskWebElement!=null && taskForCategory) {
		   return true;
		}else {
			return false;
		}
	}*/
	
	
	
	
	
	public String isTaskTaggedWithCategoryAndColor(String taskName) {
		// TODO Auto-generated method stub
				WebElement taskElement = getTaskUnStyledTaskWebElement(taskName);
				WebElement taskElementSpan = taskElement.findElement(By.xpath("//span[2]"));
				String color = taskElementSpan.getCssValue("color");
				String hex = Color.fromString(color).asHex();
				for(Entry<String, String> entry : categoryColors.entrySet()) {
				   if(entry.getValue().equals(hex))	{
					   return entry.getKey();
				   }
				}
				return "";
	}
	
	
	public boolean isTaskCompleted(String taskName, String color, String date) {
		WebElement taskWebElement = getTaskStyledTaskWebElement(taskName, date);
		if(taskWebElement == null)
		{
			return false;
		}
		WebElement taskElementStriked = taskWebElement.findElement(By.cssSelector("strike"));
		if(taskElementStriked==null) {
			return false;
		}
		String colorElement =taskElementStriked.getCssValue("color");
		String hex = Color.fromString(colorElement).asHex();
		if(taskElementStriked.getText().equals(taskName) && hex.equals(color)) {
			return true;
		}
		return false;
	}

	
	
	
	
	public void enterTask(String taskName) {
		taskNameTxtBox.sendKeys(taskName.trim());
	}
	
	public void assignCategory(String categoryName) {
		Select categorySelect = new Select(assignCategoryDropDown);
		categorySelect.selectByVisibleText(categoryName);
	}
	
	public void enterCategory(String categoryName) {
		createCategoryTxtBox.sendKeys(categoryName.trim());
	}
	
	public void clickAddCategoryBtn() {
		categoryAddBtn.click();
	}
	

	
	public void enterCategoryWithColor(String categoryName, String categoryColor, String hex) {
		enterCategory(categoryName);
		Select categoryColourSelect = new Select(assignCategoryColourDropDown);
		categoryColourSelect.selectByVisibleText(categoryColor);
		clickAddCategoryBtn();
		
		if(driver.getCurrentUrl().contains("index.php")) {
			categoryColors.put(categoryName, hex);
		}
		
	}
	
	
	public void assignDueDateForTask(String dueDay, String dueMonth, String dueYear) {
		Select dueDaySelect = new Select(dueDateSelect);
		dueDaySelect.selectByVisibleText(dueDay);
		
		if(dueMonth != "") {
		 Select dueDateMon = new Select(dueDateMonth);
		 dueDateMon.selectByVisibleText(dueMonth);
		}
		
		Select dueDateYr = new Select(dueDateYear);
		dueDateYr.selectByVisibleText(dueYear);
	}
	
	public boolean navigateToTaskPage(String taskName) {
		WebElement taskWebElement = getTaskUnStyledTaskWebElement(taskName);
		if(taskWebElement!=null) {
		   taskWebElement.findElement(By.cssSelector("a[href*='edit.php']")).click();
		   eWait.until(ExpectedConditions.urlContains("edit.php?id")); //In case url is slow in loading
		    return true;
		}else {
			return false;
		}
	}
	
	public void addTask() {
		taskAddBtn.click();
	}

	public boolean removeTask(String taskName) {
		if(clickTaskPresent(taskName))
		{
		  taskRemoveBtn.click();
		  return true;
	    }
		return false;
	}
	
	
	public boolean completeTask(String taskName) {
		if(clickTaskPresent(taskName))
		{
			taskCompleteBtn.click();
		  return true;
	    }
		return false;
		
	}
	
	public boolean removeMultipleTasks(ArrayList<String> taskNames) {
		boolean allSelected = false;
		for(String taskName : taskNames) {
			allSelected = clickTaskPresent(taskName);
			if(!allSelected) {
				return false;
			}
	     }
		 taskRemoveBtn.click();
		 return true;
	}

	public boolean isDuplicateTodoPageNavigation() {
		String todoExistUrl = driver.getCurrentUrl();
		if(todoExistUrl.contains("todo.php")) {
			driver.navigate().back();
			return true;
		}
		return false;
		
	}

	public int countOfTaskWithaName(String taskName) {
		int taskCount = 0;
		for(WebElement taskElement : taskTableList) {
			if(taskElement.getText().contains(taskName)) {
				taskCount++;
		}
	  }
		return taskCount;
	}

	public HashMap<String, String> obtainStandardColors() {
		// TODO Auto-generated method stub
		String overDueColor = overdueCategory.getCssValue("color");
		overDueColorInHex = Color.fromString(overDueColor).asHex();
		categoryColors.put(overdueCategory.getText(), overDueColorInHex);
		for(WebElement categoryElement : categoryList) {
			String color = categoryElement.getCssValue("color");
			String hex = Color.fromString(color).asHex();
			categoryColors.put(categoryElement.getText(), hex);
		}
		return categoryColors;
	}

	public void removeCategory(String categoryName) {
		// TODO Auto-generated method stub
		for(WebElement category:listOfCategory) {
			if(category.getText().contains(categoryName)) {
				category.click();
				eWait.until(ExpectedConditions.elementToBeClickable(confirmYesRemoveCategory));
				confirmYesRemoveCategory.click();
				categoryColors.remove(categoryName);
				break;
			}
		}
	}

	public void toggleAll(boolean toggle) {
		toggleCheckBox.click();
		
	}

	public int countOfToggleTasks() {
		int toggleTasks = 0;
		for(WebElement taskElement : taskTableList) {
			 eWait.until(ExpectedConditions.elementToBeClickable(taskElement));
			 boolean checked = taskElement.findElement(By.cssSelector("input[type='checkbox']")).isSelected();
			 if(checked) {
				 toggleTasks++;
			 }
		}
		return toggleTasks;
	}

	public boolean checkCategoryAndColor(String categoryName, String hexValue) {
		for(WebElement category:listOfCategory) {
			if(category.getText().equals(categoryName)) {
				String xPathOfCategory= String.format("//span[text()='%s']",categoryName);
				WebElement taskElementSpan = category.findElement(By.xpath(xPathOfCategory));
				String colorElement = taskElementSpan.getCssValue("color");
				String hex = Color.fromString(colorElement).asHex();
				for(Entry<String, String> entry : categoryColors.entrySet()) {
				   if(entry.getValue().equals(hex) && hex.equals(hexValue)&& categoryName.equals(entry.getKey()))	{
					   System.out.println(entry.getValue());
					   return true;
				   }
				}
			}
		}
		return false;
		
	}

	public void renameExistingCategory(String existingCategory, String proposedCategory, String hexValue) {
		// TODO Auto-generated method stub
		renameTheCategory.click();
		categoryColors.remove(existingCategory);
		categoryColors.put(proposedCategory, hexValue);
	}

	public void addCategoryWithThisColor(String existingCategory, String proposedCategory, String hexValue) {
		addCategoryWiththisColor.click();
		categoryColors.remove(existingCategory);
		categoryColors.put(existingCategory,"#000000");
		categoryColors.put(proposedCategory, hexValue);
		
	}

	public void neverMindCancelCategory() {
		// TODO Auto-generated method stub
		neverMindCatergorySet.click();
	}

	public boolean isCatergoryPresent(String categoryName) {
		for(WebElement category:listOfCategory) {
			if(category.getText().equals(categoryName)) {
					   return true;
			}
		}
		return false;
	}

			
}
