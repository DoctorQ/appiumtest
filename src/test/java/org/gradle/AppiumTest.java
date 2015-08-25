package org.gradle;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.junit.AfterClass;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AppiumTest {
	private AndroidDriver<AndroidElement> driver;

	@BeforeClass
	public void setUp() throws MalformedURLException {
		File appDir = new File("src/test/java/io/appium/java_client");
		File app = new File(appDir, "ApiDemos-debug.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "ZX1G42C39V");
		// capabilities.setCapability(MobileCapabilityType.APP,
		// app.getAbsolutePath());
		capabilities.setCapability("platformVersion", "4.3");
		capabilities.setCapability("appPackage", "com.wuba");
		capabilities.setCapability("appActivity",
				"com.wuba.activity.launch.LaunchActivity");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,
				120);
		driver = new AndroidDriver<AndroidElement>(new URL(
				"http://127.0.0.1:4723/wd/hub"), capabilities);
	}

	@Test
	public void webviewTest() {
		AndroidElement e1 = driver
				.findElementByAndroidUIAutomator("resourceId(\"com.wuba:id/cate_icon2\")");
		// AndroidElement e1 =
		// driver.findElementByAndroidUIAutomator("text(\"房产\")");
		e1.click();
		switchToWeb();

	}

	private void switchToWeb(){
		Set<String> contextNames = driver.getContextHandles();
		for(String contextName : contextNames){
			contextName = contextName.substring(0, contextName.indexOf("_"));
			driver.context(contextName);
			System.out.println("切换到webview");
			break;
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
