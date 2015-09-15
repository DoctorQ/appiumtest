package org.gradle;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AppiumTest {
	private AndroidDriver<AndroidElement> driver;

	@BeforeClass
	public void setUp() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "ZX1G42C39V");
		// capabilities.setCapability(MobileCapabilityType.APP,
		// app.getAbsolutePath());
		capabilities.setCapability("platformVersion", "5.1");
		capabilities.setCapability("appPackage", "com.wuba");
		capabilities.setCapability("appActivity",
				"com.wuba.activity.launch.LaunchActivity");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
				120);
		driver = new AndroidDriver<AndroidElement>(new URL(
				"http://127.0.0.1:4723/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void webviewTest() throws InterruptedException {
		AndroidElement e1 = driver
				.findElementByAndroidUIAutomator("text(\"房产\")");
		e1.click();
		switchToWeb();

		System.out.println(driver.getCurrentUrl());
		AndroidElement e2 = (AndroidElement) driver
				.findElement(By.className("bd")).findElements(By.tagName("li"))
				.get(0);
		String str = e2.getText();
		Point point = e2.getCenter();
		System.out.println(str);
		System.out.println(point.toString());
		e2.click();
		switchToNative();
		AndroidElement back = driver
				.findElementByAndroidUIAutomator("resourceId(\"com.wuba.house:id/title_left_btn\")");

		back.click();
	}

	@Test
	public void postMessage() throws InterruptedException {
		AndroidElement e1 = driver
				.findElementByAndroidUIAutomator("text(\"房产\")");
		e1.click();
		Thread.sleep(5 * 1000);
		AndroidElement e2 = driver
				.findElementByAndroidUIAutomator("resourceId(\"com.wuba:id/title_right_btn\")");
		e2.click();
		Thread.sleep(5 * 1000);
		
		//switchToWeb();
//		System.out.println(driver.getCurrentUrl());
		AndroidElement e3 = driver.findElementByAndroidUIAutomator(
				"new UiSelector().descriptionContains(\"" + "整套出租" + "\")");
		e3.click();
		Thread.sleep(5 * 1000);
		
		
		driver.swipe(250, 1651, 250, 600, 0);
		Thread.sleep(5 * 1000);
		switchToWeb();
		Thread.sleep(5 * 1000);
		System.out.println(driver.getCurrentUrl());
		Thread.sleep(5 * 1000);
		AndroidElement e4 = (AndroidElement) driver
				.findElement(By.className("input")).findElements(By.tagName("input"))
				.get(0);
		e4.click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
//		AndroidElement e4 = driver.findElementByAndroidUIAutomator(
//				"new UiSelector().descriptionContains(\"" + "1-30字" + "\")");
//		e4.click();
		//e4.sendKeys("123");
		
//		driver.findElementByAndroidUIAutomator(
//				"new UiSelector().descriptionContains(\"" + "发布" + "\")")
//				.click();
//		driver.findElementByAndroidUIAutomator(
//				"resourceId(\"com.wuba:id/positiveButton\")").click();
//		driver.findElementByAndroidUIAutomator(
//				"resourceId(\"com.wuba:id/title_left_btn\")").click();
//		driver.findElementByAndroidUIAutomator(
//				"resourceId(\"com.wuba:id/positiveButton\")").click();
//		driver.findElementByAndroidUIAutomator(
//				"resourceId(\"com.wuba:id/title_left_btn\")").click();
		
		
		//Thread.sleep(15000);
		

	}

	private void switchToWeb() {
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextNames);
			if (contextName.toLowerCase().contains("webview_com.wuba")) {
				System.out.println(contextName);
				contextName = contextName
						.substring(0, contextName.indexOf("_"));
				driver.context(contextName);
				System.out.println("切换到webview");
				break;
			}
		}
	}

	private void switchToNative() {
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			if (contextName.toLowerCase().contains("native")) {
				// contextName = contextName
				// .substring(0, contextName.indexOf("_"));
				driver.context(contextName);
				System.out.println("切换到native");
				break;
			}
		}
	}

	@AfterClass
	public void tearDown() {
		System.out.println("结束");
		driver.quit();
	}

	/***
	 * 下滑1/4屏幕
	 */
	private void slideDown() {
		int x = driver.manage().window().getSize().width;
		int y = driver.manage().window().getSize().height;
		driver.swipe(x / 2, y / 3 * 1, x / 2, y / 3 * 2, 0);
	}

}
