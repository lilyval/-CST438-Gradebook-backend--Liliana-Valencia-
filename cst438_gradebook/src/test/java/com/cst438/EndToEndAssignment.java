package com.cst438;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;

//as an Instructor I can create a new assignment for a course that I teach
//

public class EndToEndAssignment {
	public static final String CHROME_DRIVER_FILE_LOCATION = "/Users/lily/Downloads";

	public static final String URL = "http://localhost:3000";
	public static final String TEST_USER_EMAIL = "test@csumb.edu";
	public static final String TEST_INSTRUCTOR_EMAIL = "dwisneski@csumb.edu";
	public static final int SLEEP_DURATION = 1000; // 1 second.
	public static final String TEST_ASSIGNMENT_NAME = "Test Assignment #5";
	public static final String TEST_ASSIGNMENT_DUEDATE = "2022-10-10";
	public static final int COURSE_ID = 0001;
	

	@Autowired
	EnrollmentRepository enrollmentRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	AssignmentGradeRepository assignnmentGradeRepository;

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Test 
	public void addAssignmentHW() throws Exception {

				Assignment ag = new Assignment();
				do {
					ag = assignmentRepository.findByCourseIdAndName(COURSE_ID, TEST_ASSIGNMENT_NAME);
					if(ag != null)
						assignmentRepository.delete(ag);
				} while (ag != null);
				
				
				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
				WebDriver driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);



				try {
					
					driver.get(URL);
					Thread.sleep(SLEEP_DURATION);
					
					driver.findElement(By.xpath("//button")).click();
					Thread.sleep(SLEEP_DURATION);
					
					driver.findElement(By.xpath("//input[@name='courseId']")).sendKeys(Integer.toString(COURSE_ID));
			
					driver.findElement(By.xpath("//input[@name='assignmentName']")).sendKeys(TEST_ASSIGNMENT_NAME);
					
					driver.findElement(By.xpath("//input[@name='dueDate']")).sendKeys("1999-10-11");
					
					driver.findElement(By.xpath("//button[span='Add']")).click();
					Thread.sleep(SLEEP_DURATION);
					
		
					WebElement we = driver.findElement(By.xpath("(//div[@role='row'])[last()]"));
					assertNotNull(we, "Added assignment N/A");
					
					Assignment as = assignmentRepository.findByCourseIdAndName(COURSE_ID, TEST_ASSIGNMENT_NAME);
					assertNotNull(as, "N/A");
					

				} catch (Exception ex) {
					throw ex;
				} finally {
					Assignment as = assignmentRepository.findByCourseIdAndName(COURSE_ID, TEST_ASSIGNMENT_NAME);
					if (as != null) 
						assignmentRepository.delete(as);

					driver.quit();
				}
				
}//end class
			
		
}
