package config;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The factory to create RemoteWebDriver
 *
 * @author basilguo@163.com
 * @date 21.2.23 20:59
 * @since version 1.0
 */
public class DriverFactory {
  private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);

  private RemoteWebDriver webDriver;
  private DriverType selectedDriverType;

  private final String operationSystem = System.getProperty("os.name");
  private final String systemArch = System.getProperty("os.arch");

  public DriverFactory() {
    DriverType driverType = DriverType.FIREFOX;
    String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
    try {
      driverType = DriverType.valueOf(browser);
    } catch (IllegalArgumentException ignored) {
      log.info("Unknown driver specified, defaulting to'" + driverType + "'...");
    } catch (NullPointerException ignored) {
      log.info("No driver specified, defaulting to'" + driverType + "'...");
    }
    selectedDriverType = driverType;
  }

  private void printOSInfo(String browser) {
    log.info("Current Operation System: " + operationSystem);
    log.info("Current System Architecture: " + systemArch);
    log.info("Current Browser choice: " + browser);
    log.info("Current thread: "+ Thread.currentThread().getId());
  }

  public RemoteWebDriver getDriver() {
    if (null == webDriver) {
      instantiateWebDriver(selectedDriverType);
    }
    return webDriver;
  }

  private void instantiateWebDriver(DriverType driverType) {
    printOSInfo(driverType.toString());
    DesiredCapabilities capabilities = new DesiredCapabilities();
    webDriver = driverType.getWebDriverObject(capabilities);
  }

//  public RemoteWebDriver getFirefoxDriver() {
//    if (null == webDriver || webDriver instanceof ChromeDriver) {
//      printOSInfo("Firefox");
//      String ffBinaryPath = "D:\\Program Files\\Mozilla Firefox\\firefox.exe";
//      FirefoxOptions options = new FirefoxOptions();
//      options.setBinary(ffBinaryPath);
//      webDriver = new FirefoxDriver(options);
//    }
//    return webDriver;
//  }

  public void quitDriver() {
    if (null != webDriver) {
      webDriver.quit();
      // 防止误用，这是一个好习惯
      webDriver = null;
    }
  }

}
