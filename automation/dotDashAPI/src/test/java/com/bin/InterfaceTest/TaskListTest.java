package com.bin.InterfaceTest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.bin.basepackage.BaseAPIClass;
import com.bin.nssPageLayer.TaskMainPage;


public class TaskListTest extends BaseAPIClass{

	TaskMainPage taskPage;
	HashMap<String, String> categoryColors;
	SoftAssert sa;
	public TaskListTest() throws IOException{
		super();
		// TODO Auto-generated constructor stub
	}
	
	@BeforeMethod
	public void setUp() throws IOException {
		initiliazeWebDriver();
		sa = new SoftAssert();
		taskPage = new TaskMainPage(driver);
		categoryColors = taskPage.obtainStandardColors();
	}
	
	//Context1 - Add Tasks
	//1.1
	@Test(priority = 1, dataProvider="taskNames")
	public void createTaskWithOutAssigningCatergoryTest(String taskName) {
		taskPage.enterTask(taskName);
		taskPage.addTask();
		boolean taskAdded = taskPage.isTaskPresent(taskName, "(None)");
		sa.assertTrue(taskAdded, "Task is not added");
		taskPage.removeTask(taskName);
		sa.assertAll();
	}
	
	@Test(priority = 2, dataProvider="taskNames")
	public void createDuplicateTaskTest(String taskName) {
		
		taskPage.enterTask(taskName);
		taskPage.addTask();
		taskPage.enterTask(taskName);
		taskPage.addTask();
		boolean duplicatePageNav = taskPage.isDuplicateTodoPageNavigation();
		sa.assertTrue(duplicatePageNav, "Task already Exists");
		
		int numberOfTasks = taskPage.countOfTaskWithaName(taskName);
		sa.assertEquals(numberOfTasks, 1, "Duplicate task Created");
		taskPage.removeTask(taskName);
		sa.assertAll();
		
	}
	
	
	@Test(priority = 3, dataProvider= "categoryNames")
	public void createTaskWithCatergoryAndNoDueDateTest(String taskName, String categoryName) {
		taskPage.enterCategoryWithColor(categoryName, "Yellow", "#ffff00");
		taskPage.enterTask(taskName);
		taskPage.assignCategory(categoryName);
		taskPage.addTask();
		boolean caterogyAssgined = taskPage.isTaskAssignedCategoryWithNoDueDate(taskName, categoryName);
		sa.assertEquals(caterogyAssgined, true, "Not the catergory Assigned");
		taskPage.removeTask(taskName);
		taskPage.removeCategory(categoryName);
		sa.assertAll();
	}
	
	
	@Test(priority = 4, dataProvider= "categoryNames")
	public void createTaskWithCatergoryAndFutureDueDateTest(String taskName, String categoryName) {
		taskPage.enterCategoryWithColor(categoryName, "Yellow", "#ffff00");
		taskPage.enterTask(taskName);
		taskPage.assignCategory(categoryName);
		taskPage.assignDueDateForTask("30", "Dec", "2018");
		taskPage.addTask();
		boolean taskAdded = taskPage.isTaskPresentWithCategoryAndDate(taskName,categoryName, "(30/12/18)");
		sa.assertTrue(taskAdded, "Task is not added");
		taskPage.removeTask(taskName);
		taskPage.removeCategory(categoryName);
		sa.assertAll();
	}
	
	@Test(priority = 5, dataProvider= "taskNames")
	public void createTaskWithNoCatergoryAndFutureDueDateTest(String taskName) {
		taskPage.enterTask(taskName);
		taskPage.assignDueDateForTask("30", "Dec", "2018");
		taskPage.addTask();
		boolean taskAdded = taskPage.isTaskPresent(taskName, "(30/12/18)");
		sa.assertTrue(taskAdded, "Task is not added");
		taskPage.removeTask(taskName);
		sa.assertAll();
	}
	
	
	@Test(priority = 6, dataProvider= "categoryNames")
	public void createTaskWithCatergoryAndPreviousDueDateTest(String taskName, String categoryName) {
		taskPage.enterCategoryWithColor(categoryName, "Yellow", "#ffff00");
		taskPage.enterTask(taskName);
		taskPage.assignCategory(categoryName);
		taskPage.assignDueDateForTask("30", "Mar", "2018");
		taskPage.addTask();
		boolean taskAdded = taskPage.isTaskPresentWithCategoryAndDate(taskName,categoryName, "(30/03/18)");
		sa.assertTrue(taskAdded, "Task is not added");
		taskPage.removeTask(taskName);
		taskPage.removeCategory(categoryName);
		sa.assertAll();
	}
	
	@Test(priority=7, dataProvider="categoryNames")
	public void removeTaskTest(String taskName, String categoryName) {
		taskPage.enterCategoryWithColor(categoryName, "Yellow", "#ffff00");
		taskPage.enterTask(taskName);
		taskPage.assignCategory(categoryName);
		taskPage.addTask();
		taskPage.removeTask(taskName);
		boolean removedTask = taskPage.isTaskPresent(taskName,"(None)");
		sa.assertEquals(removedTask, false, "Task 1 not deleted");
		taskPage.removeCategory(categoryName);
		sa.assertAll();
	}
	
	
	@Test(priority=8, dataProvider="multipleTaskNames")
	public void removeMultipleTasksTest(String taskName1, String taskName2, String taskName3, String taskName4) {
		taskPage.enterTask(taskName1);
		taskPage.addTask();
		taskPage.enterTask(taskName2);
		taskPage.addTask();
		taskPage.enterTask(taskName3);
		taskPage.addTask();
		taskPage.enterTask(taskName4);
		taskPage.addTask();
		ArrayList<String> taskNames = new ArrayList<String>();
		taskNames.add(taskName1);
		taskNames.add(taskName2);
		taskNames.add(taskName3);
		taskNames.add(taskName4);
		
		taskPage.removeMultipleTasks(taskNames);
		
		
		boolean removedTask = taskPage.isTaskPresent(taskName1,"(None)");
		sa.assertEquals(removedTask, false, "Task 1 not deleted");
		removedTask = taskPage.isTaskPresent(taskName2,"(None)");
		sa.assertEquals(removedTask, false, "Task 2 not deleted");
		removedTask = taskPage.isTaskPresent(taskName3,"(None)");
		sa.assertEquals(removedTask, false, "Task 3 not deleted");
		removedTask = taskPage.isTaskPresent(taskName4,"(None)");
		sa.assertEquals(removedTask, false, "Task 4 not deleted");
		
		sa.assertAll();
	}
	
	
	@Test(priority = 9, dataProvider= "taskNames")
	public void createTaskWithNoCatergoryAndInvalidDueDateTest(String taskName) {
		taskPage.enterTask(taskName);
		taskPage.assignDueDateForTask("30", "", "2018");
		taskPage.addTask();
		boolean taskAdded = taskPage.isTaskPresent(taskName,"(None)");
		sa.assertTrue(taskAdded, "Task is not added");
		taskPage.removeTask(taskName);
		sa.assertAll();
	}
	
	
	@Test(priority = 10, dataProvider= "taskNames")
	public void inspectCompletedTaskWithNoCategoryWithDateTest(String taskName) {
		taskPage.enterTask(taskName);
		taskPage.addTask();
		taskPage.completeTask(taskName);
        boolean taskCompleted = taskPage.isTaskCompleted(taskName, "#000000", "(None)");
        sa.assertTrue(taskCompleted, "Task complete operation not successfull");
		taskPage.removeTask(taskName);
		sa.assertAll();
	}
	
	
	@Test(priority = 11, dataProvider= "categoryNames")
	public void inspectCompletedTaskWithCategoryWithDateTest(String taskName, String categoryName) {
		taskPage.enterCategoryWithColor(categoryName, "Yellow", "#ffff00");
		taskPage.enterTask(taskName);
		taskPage.assignCategory(categoryName);
		taskPage.assignDueDateForTask("30", "Mar", "2018");
		taskPage.addTask();
		taskPage.completeTask(taskName);
        boolean taskCompleted = taskPage.isTaskCompleted(taskName, "#ffff00", "(30/03/18)");
        sa.assertTrue(taskCompleted, "Task complete operation not successful");
		taskPage.removeTask(taskName);
		taskPage.removeCategory(categoryName);
		sa.assertAll();
	}
	
	@Test(priority = 12)
	public void inspectToggleAllTest() {
		taskPage.toggleAll(true);
		int toggledTasks = taskPage.countOfToggleTasks();
		Assert.assertEquals(toggledTasks, 10,"Toggle tasks count didnot match after check!");
	}
	
	@Test(priority = 13)
	public void inspectUnToggleAllTest() {
		taskPage.toggleAll(true);
		int toggledTasks = taskPage.countOfToggleTasks();
		sa.assertEquals(toggledTasks, 10,"Toggle tasks count didnot match after check!");
		taskPage.toggleAll(false);
		toggledTasks = taskPage.countOfToggleTasks();
		Assert.assertEquals(toggledTasks, 0,"Toggle tasks count didnot match after uncheck!");
	}
	
	
	@Test(priority = 14, dataProvider="singleCategory")
	public void addSimpleCategoryTest(String categoryName, String color, String hexValue) {
		taskPage.enterCategoryWithColor(categoryName, color, hexValue);
		boolean isSimpleCategoryCreated = taskPage.checkCategoryAndColor(categoryName,hexValue);
		sa.assertTrue(isSimpleCategoryCreated,"Simple Category is not created!");
		taskPage.removeCategory(categoryName);
		sa.assertAll();
	}
	
	
	@Test(priority = 15, dataProvider="addAnotherWithSameColor")
	public void addCategoryWithColorToAnotherCategoryRenameTest(String existingCategory, String proposedCategory, 
			                                      String color, String hexValue) {
		taskPage.enterCategoryWithColor(existingCategory, color, hexValue);
		taskPage.enterCategoryWithColor(proposedCategory, color, hexValue);
		taskPage.renameExistingCategory(existingCategory, proposedCategory, hexValue);
		boolean isCategoryCreatedWithExistingColor = taskPage.checkCategoryAndColor(proposedCategory,hexValue);
		
		sa.assertTrue(isCategoryCreatedWithExistingColor,"Category is not assigned with color");
		taskPage.removeCategory(proposedCategory);
		sa.assertAll();
	}
	
	
	@Test(priority = 16, dataProvider="addAnotherWithSameColor")
	public void addCategoryWithColorToAnotherCategoryReplaceTest(String existingCategory, String proposedCategory, 
			                                      String color, String hexValue) {
		taskPage.enterCategoryWithColor(existingCategory, color, hexValue);
		taskPage.enterCategoryWithColor(proposedCategory, color, hexValue);
		taskPage.addCategoryWithThisColor(existingCategory, proposedCategory, hexValue);
		boolean isnewCategoryCreatedWithExistingColor = taskPage.checkCategoryAndColor(proposedCategory,hexValue);
		boolean isOldCategoryAssignedBlackColor = taskPage.checkCategoryAndColor(existingCategory,"#000000");//Black color
		
		sa.assertTrue(isnewCategoryCreatedWithExistingColor,"New Category is not assigned with color");
		sa.assertTrue(isOldCategoryAssignedBlackColor,"Old Category is not assigned with black color");
		taskPage.removeCategory(proposedCategory);
		taskPage.removeCategory(existingCategory);
		sa.assertAll();
	}
	
	
	@Test(priority = 17, dataProvider="addAnotherWithSameColor")
	public void addCategoryWithColorToAnotherCategoryNeverMindTest(String existingCategory, String proposedCategory, 
			                                      String color, String hexValue) {
		taskPage.enterCategoryWithColor(existingCategory, color, hexValue);
		taskPage.enterCategoryWithColor(proposedCategory, color, hexValue);
		taskPage.neverMindCancelCategory();
		boolean isCategoryCreatedWithExistingColor = taskPage.checkCategoryAndColor(existingCategory,hexValue);
		boolean isCategoryPresent = taskPage.isCatergoryPresent(proposedCategory);
		sa.assertTrue(isCategoryCreatedWithExistingColor,"Category is not assigned with color");
		sa.assertFalse(isCategoryPresent,"Category is Created which it should not");
		taskPage.removeCategory(existingCategory);
		sa.assertAll();
	}
	
	
	@Test(priority = 18, dataProvider="onlyCategory")
	public void removeCategoryTest(String proposedCategory) {
		taskPage.enterCategory(proposedCategory);
		taskPage.clickAddCategoryBtn();
		taskPage.removeCategory(proposedCategory);
		boolean isCategoryPresent = taskPage.isCatergoryPresent(proposedCategory);
		sa.assertFalse(isCategoryPresent,"Category is Created which it should not");
		sa.assertAll();
	}
	
	@Test(priority = 19, dataProvider="listOfCategoryAndTasks")
	public void inspectTasksIfCategoryIsRenamedTest(String taskName, String existingCategory,
			                  String proposedCategory, String color, String hexValue) {
		taskPage.enterCategoryWithColor(existingCategory, color, hexValue);
		taskPage.enterTask(taskName);
		taskPage.assignCategory(existingCategory);
		taskPage.assignDueDateForTask("30", "Mar", "2018");
		taskPage.addTask();
		
		taskPage.enterCategoryWithColor(proposedCategory, color, hexValue);
		taskPage.renameExistingCategory(existingCategory, proposedCategory, hexValue);
		String isTaskReassginedToNewCategory = taskPage.isTaskTaggedWithCategoryAndColor(taskName);
		sa.assertEquals(isTaskReassginedToNewCategory, proposedCategory,"The task is not assigned to new proposed category");
		taskPage.removeTask(taskName);
		taskPage.removeCategory(proposedCategory);
		sa.assertAll();
	}
	
	
	@Test(priority = 20, dataProvider="listOfCategoryAndTasks")
	public void inspectTasksIfCategoryIsReplacedTest(String taskName, String existingCategory,
			                  String proposedCategory, String color, String hexValue) {
		taskPage.enterCategoryWithColor(existingCategory, color, hexValue);
		taskPage.enterTask(taskName);
		taskPage.assignCategory(existingCategory);
		taskPage.assignDueDateForTask("30", "Mar", "2018");
		taskPage.addTask();
		
		taskPage.enterCategoryWithColor(proposedCategory, color, hexValue);
		taskPage.addCategoryWithThisColor(existingCategory, proposedCategory, hexValue);
		String isTaskReassginedToNewCategory = taskPage.isTaskTaggedWithCategoryAndColor(taskName);
		sa.assertEquals(isTaskReassginedToNewCategory, existingCategory,"The task is not assigned to new proposed category");
		taskPage.removeTask(taskName);
		taskPage.removeCategory(proposedCategory);
		taskPage.removeCategory(existingCategory);
		sa.assertAll();
	}
	
	
			
	
	@DataProvider(name="taskNames")
	private Object[][] singleTask() {
		Object[][] taskList = new Object[][] { {"testTask1"}
			                                  };
	    return taskList;                               
	}
	
	@DataProvider(name="smallAndCapssingleTask")
	private Object[][] smallAndCapssingleTask() {
		Object[][] taskList = new Object[][] { {"testTask1", "TESTTASK1"}
			                                  };
	    return taskList;                               
	}
	
	
	@DataProvider(name="singleCategory")
	private Object[][] singleCategory() {
		Object[][] taskList = new Object[][] { {"College", "Yellow", "#ffff00"}
			                                  };
	    return taskList;                               
	}
	
	@DataProvider(name="addAnotherWithSameColor")
	private Object[][] addAnotherWithSameColor() {
		Object[][] taskList = new Object[][] { {"ExistingCategory", "ProposedCategory", "Blue", "#0000ff"}
			                                  };
	    return taskList;                               
	}
	
	
	@DataProvider(name="categoryNames")
	private Object[][] singleTaskWithCategories() {
		Object[][] categoryList = new Object[][] { {"testTask1","College"}
			                                  };
	    return categoryList;                               
	}
	
	@DataProvider(name="onlyCategory")
	private Object[][] onlyCategory() {
		Object[][] categoryList = new Object[][] { {"College"}
			                                  };
	    return categoryList;                               
	}
	
	@DataProvider(name="multipleTaskNames")
	private Object[][] listOfTasksWithCategory() {
		Object[][] categoryList = new Object[][] { {"testTask1","testTask2", "testTask3","testTask4"},
			                                  };
	    return categoryList;                               
	}
	
	@DataProvider(name="listOfCategoryAndTasks")
	private Object[][] listOfCategoryAndTasks() {
		Object[][] categoryList = new Object[][] { {"testTask1", "ExistingCategory", "ProposedCategory", "Blue", "#0000ff"},
			                                  };
	    return categoryList;                               
	}
	
	@AfterMethod
	public void tearDown() {
		if(driver!=null) {
			driver.close();
			driver = null;
		}
	}
	
}