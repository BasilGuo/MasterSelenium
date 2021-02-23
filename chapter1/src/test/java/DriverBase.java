import config.DriverFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The base of Driver, the common settings.
 * It uses TestNG's annotations.
 *
 * @author basilguo@163.com
 * @date 21.2.23 20:59
 * @since version 1.0
 */
public class DriverBase {
  private static final Logger log = LoggerFactory.getLogger(DriverBase.class);
  private static List<DriverFactory> webDriverThreadPool
      = Collections.synchronizedList(new ArrayList<>());
  private static ThreadLocal<DriverFactory> driverThread;

  @BeforeSuite(alwaysRun = true)
  public static void instantiateDriverObject() {
    driverThread = new ThreadLocal<DriverFactory>() {
      @Override
      protected DriverFactory initialValue() {
        DriverFactory webDriverThread = new DriverFactory();
        webDriverThreadPool.add(webDriverThread);
        return webDriverThread;
      }
    };
  }

  public static RemoteWebDriver getDriver() {
    return driverThread.get().getDriver();
  }

  @AfterMethod(alwaysRun = true)
  public static void clearCookie() {
    try {
      getDriver().manage().deleteAllCookies();
    } catch (Exception e) {
      log.info("Cannot delete this cookies: " + e);
    }
  }

  @AfterSuite(alwaysRun = true)
  public static void closeDriverObjects() {
    for (DriverFactory webDriver : webDriverThreadPool) {
      webDriver.quitDriver();
    }
  }
}
