package config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * This enum is the type of different browsers.
 *
 * @author basilguo@163.com
 * @date 21.2.23 21:56
 * @since version 1.0
 */
public enum DriverType implements DriverSetup{
  FIREFOX {
    @Override
    public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      String ffBinaryPath = "D:\\Program Files\\Mozilla Firefox\\firefox.exe";
      FirefoxOptions options = new FirefoxOptions();
      options.setBinary(ffBinaryPath);
      options.merge(capabilities);
      // 后台模式
      options.setHeadless(HEADLESS);
      return new FirefoxDriver(options);
    }
  },
  CHROME {
    @Override
    public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      Map<String, Object> chromePreferences = new HashMap<>();
      // 关闭了密码管理器，这样在每次执行有登录操作的测试时，就不会总询问是否要保存登录信息
      chromePreferences.put("profile.password_manager_enabled", false);
      ChromeOptions options = new ChromeOptions();
      options.merge(capabilities);
      // 后台模式
      options.setHeadless(HEADLESS);
      // 禁用每次询问设为默认浏览器
      options.addArguments("--no-default-browser-check");
      options.setExperimentalOption("prefs", chromePreferences);
      return new ChromeDriver(options);
    }
  },
  IE {
    @Override
    public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      InternetExplorerOptions options = new InternetExplorerOptions();
      options.merge(capabilities);

      // 代码中设定的这些选项用于确保在浏览器重新加载时正确清除会话（IE8在清除缓存方面做得特别差）
      options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
      // 修复鼠标悬停的一些问题
      options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
      options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
      return new InternetExplorerDriver(options);
    }
  },
  EDGE {
    @Override
    public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      EdgeOptions options = new EdgeOptions();
      options.merge(capabilities);
      return new EdgeDriver(options);
    }
  },
  SAFARI {
    @Override
    public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      SafariOptions options = new SafariOptions();
      options.merge(capabilities);
      return new SafariDriver(options);
    }
  },
  OPERA {
    @Override
    public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
      OperaOptions options = new OperaOptions();
      options.merge(capabilities);
      return new OperaDriver(options);
    }
  };

  public final static boolean HEADLESS = Boolean.getBoolean("headless");
}
