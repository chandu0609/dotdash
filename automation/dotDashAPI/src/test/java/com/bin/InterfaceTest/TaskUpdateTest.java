package com.bin.InterfaceTest;

import java.io.IOException;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.bin.basepackage.BaseAPIClass;
import com.bin.nssPageLayer.TaskMainPage;
import com.bin.nssPageLayer.TaskUpdatePage;


public class TaskUpdateTest extends BaseAPIClass{

    TaskMainPage taskPage;
    TaskUpdatePage taskUpdatePage;
    HashMap<String, String> categoryColors;
   SoftAssert sa;
	
	public TaskUpdateTest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	@BeforeMethod
	public void setUpMethod() throws IOException {
		initiliazeWebDriver();
		taskPage = new TaskMainPage(driver);
		taskUpdatePage = new TaskUpdatePage(driver);
		sa = new SoftAssert();
		categoryColors = taskPage.obtainStandardColors();
	}
	
	
	@Test(priority = 1, dataProvider="categoryNames",dataProviderClass=TaskListTest.class )
	public void inspectTaskDetailsInUpdatePageTest(String taskName, String categoryName) {
		taskPage.enterCategoryWithColor(categoryName, "Yellow", "#ffff00");
		taskPage.enterTask(taskName);
		taskPage.assignCategory(categoryName);
		taskPage.assignDueDateForTask("30", "Mar", "2018");
		taskPage.addTask();
		taskPage.navigateToTaskPage(taskName);
        
		String addedTaskName = taskUpdatePage.getTaskName();
		sa.assertEquals(addedTaskName, taskName);
		
		String addedCategory = taskUpdatePage.getCategory();
		sa.assertEquals(addedCategory, categoryName);
		
		String addedDay = taskUpdatePage.getDueDay();
		sa.assertEquals(addedDay, "30");
		
		String addedMonth = taskUpdatePage.getDueMonth();
		sa.assertEquals(addedMonth, "Mar");
		
		String addedYear = taskUpdatePage.getDueYear();
		sa.assertEquals(addedYear, "2018");
		taskUpdatePage.naviagteBack();
		taskPage.removeTask(taskName);
		taskPage.removeCategory(categoryName);
		
		sa.assertAll();
		
		
	}
	
	
	
	
	@Test(priority = 2, dataProvider="taskNames",dataProviderClass=TaskListTest.class )
	public void inspectTaskDetailsInUpdatePageWithNoCategoryTest(String taskName) {
		taskPage.enterTask(taskName);
		taskPage.assignDueDateForTask("30", "Dec", "2018");
		taskPage.addTask();
		taskPage.navigateToTaskPage(taskName);
        
		String addedTaskName = taskUpdatePage.getTaskName();
		sa.assertEquals(addedTaskName, taskName);
		
		String addedCategory = taskUpdatePage.getCategory();
		sa.assertEquals(addedCategory, "None");
		
		String addedDay = taskUpdatePage.getDueDay();
		sa.assertEquals(addedDay, "30");
		
		String addedMonth = taskUpdatePage.getDueMonth();
		sa.assertEquals(addedMonth, "Dec");
		
		String addedYear = taskUpdatePage.getDueYear();
		sa.assertEquals(addedYear, "2018");
		taskUpdatePage.naviagteBack();
		taskPage.removeTask(taskName);
		
		sa.assertAll();
	}
	
	@Test(priority = 3, dataProvider="categoryNames",dataProviderClass=TaskListTest.class )
	public void inspectTaskDetailsInUpdatePageWithNoDueDateTest(String taskName, String categoryName) {
		taskPage.enterCategoryWithColor(categoryName, "Yellow", "#ffff00");
		taskPage.enterTask(taskName);
		taskPage.assignCategory(categoryName);
		taskPage.addTask();
		taskPage.navigateToTaskPage(taskName);
        
		String addedTaskName = taskUpdatePage.getTaskName();
		sa.assertEquals(addedTaskName, taskName);
		
		String addedCategory = taskUpdatePage.getCategory();
		sa.assertEquals(addedCategory, categoryName);
		
		String addedDay = taskUpdatePage.getDueDay();
		sa.assertEquals(addedDay, "None");
		
		String addedMonth = taskUpdatePage.getDueMonth();
		sa.assertEquals(addedMonth, "None");
		
		String addedYear = taskUpdatePage.getDueYear();
		sa.assertEquals(addedYear, "2018");
		taskUpdatePage.naviagteBack();
		taskPage.removeTask(taskName);
		taskPage.removeCategory(categoryName);
		
		sa.assertAll();
		
		
	}
	
	
	@Test(priority = 4, dataProvider="taskNames",dataProviderClass=TaskListTest.class )
	public void inspectTaskDetailsInUpdatePageNoCategoryAndNoDueDateTest(String taskName) {
		taskPage.enterTask(taskName);
		taskPage.addTask();
		taskPage.navigateToTaskPage(taskName);
        
		String addedTaskName = taskUpdatePage.getTaskName();
		sa.assertEquals(addedTaskName, taskName);
		
		String addedCategory = taskUpdatePage.getCategory();
		sa.assertEquals(addedCategory, "None");
		
		String addedDay = taskUpdatePage.getDueDay();
		sa.assertEquals(addedDay, "None");
		
		String addedMonth = taskUpdatePage.getDueMonth();
		sa.assertEquals(addedMonth, "None");
		
		String addedYear = taskUpdatePage.getDueYear();
		sa.assertEquals(addedYear, "2018");
		taskUpdatePage.naviagteBack();
		taskPage.removeTask(taskName);
		
		sa.assertAll();
		
		
	}
	
	
	@Test(priority = 5, dataProvider="taskNames")
	public void updateTaskNameTest(String taskName, String updatedTaskName) {
		taskPage.enterTask(taskName);
		taskPage.addTask();
		taskPage.navigateToTaskPage(taskName);
		taskUpdatePage.enterUpdatedTask(updatedTaskName);
		taskUpdatePage.updateTask();
		boolean isUpdatedTaskPresent = taskPage.isTaskPresent(updatedTaskName,"(None)");
		sa.assertEquals(isUpdatedTaskPresent, true, "Task is not updated");
		taskPage.removeTask(updatedTaskName);
		sa.assertAll();
		
	}
	
	
	@Test(priority = 6, dataProvider="taskUpdateCategoryNames")
	public void updateTaskByCategoryTest(String addTask, String addCategory, String newCategory) {
		taskPage.enterCategoryWithColor(addCategory, "Yellow", "#ffff00");
		taskPage.enterTask(addTask);
		taskPage.assignCategory(addCategory);
		taskPage.addTask();
		taskPage.navigateToTaskPage(addTask);
		taskUpdatePage.updateCategory(newCategory);
		taskUpdatePage.updateTask();
		boolean taskAddedWithCategory = taskPage.isTaskPresentWithCategoryAndDate(addTask,newCategory,"(None)");
		
		sa.assertTrue(taskAddedWithCategory, "Expected category not assigned.");
		
		taskPage.removeTask(addTask);
		taskPage.removeCategory(addCategory);
		sa.assertAll();
	}
	
	
	@Test(priority = 7, dataProvider="taskNames",dataProviderClass=TaskListTest.class )
	public void updateTaskByPreviousDueDateTest(String addTask) {
		taskPage.enterTask(addTask);
		taskPage.addTask();
		taskPage.navigateToTaskPage(addTask);
		taskUpdatePage.updateDate("30", "Mar", "2018");
		taskUpdatePage.updateTask();
		boolean taskAddedWithCategory = taskPage.isTaskPresentWithCategoryAndDate(addTask,"Overdue","(30/03/18)");
		
		sa.assertTrue(taskAddedWithCategory, "Expected category not assigned.");
		
		taskPage.removeTask(addTask);
		sa.assertAll();
	}
	
	
	@Test(priority = 8, dataProvider="categoryNames",dataProviderClass=TaskListTest.class )
	public void updateTaskWithCategoryByPreviousDueDateTest(String addTask, String category) {
		taskPage.enterCategoryWithColor(category, "Yellow", "#ffff00");
		taskPage.enterTask(addTask);
		taskPage.addTask();
		taskPage.navigateToTaskPage(addTask);
		taskUpdatePage.updateDate("30", "Mar", "2018");
		taskUpdatePage.updateCategory(category);
		taskUpdatePage.updateTask();
		boolean taskOverdue = taskPage.isTaskPresentWithCategoryAndDate(addTask,"Overdue","(30/03/18)");
		sa.assertTrue(taskOverdue, "Task is not shown as overdue");
		String taskCategory = taskPage.isTaskTaggedWithCategoryAndColor(addTask);
		sa.assertEquals(taskCategory, category, "Task is not tagged as per catergory");
		
		taskPage.removeTask(addTask);
		taskPage.removeCategory(category);
		sa.assertAll();
	}
	
	
	@AfterMethod
	public void tearDown() {
		if(driver!=null) {
			driver.close();
			driver = null;
		}
	}
	
	
	
	@DataProvider(name="taskNames")
	private Object[][] singleTask() {
		Object[][] taskList = new Object[][] { {"testTask1", "testTask2"}
			                                  };
	    return taskList;                               
	}
	
	
	@DataProvider(name="taskCategoryNames")
	private Object[][] singleTaskWithCategories() {
		Object[][] categoryList = new Object[][] { {"testTask1","College"}
			                                  };
	    return categoryList;                               
	}
	
	
	@DataProvider(name="taskUpdateCategoryNames")
	private Object[][] singleUpdateTaskWithCategories() {
		Object[][] categoryList = new Object[][] { {"testTask1","College", "Play"}
			                                  };
	    return categoryList;                               
	}
	
	
	
}