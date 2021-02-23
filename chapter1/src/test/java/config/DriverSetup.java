package config;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * This interface is a functional interface,
 * associated with the enum DriverType, it could create
 * a driver with capabilities.
 *
 * @author basilguo@163.com
 * @date 21.2.23 21:55
 * @since version 1.0
 */
public interface DriverSetup {
  RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities);
}
