import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

/**
 * Selenium的基本使用
 *
 * @author basilguo@163.com
 * @date 21.2.23 19:09
 * @since version 1.0
 */

public class BasicTest {
  private static final Logger log = LoggerFactory.getLogger(BasicTest.class);

  private ExpectedCondition<Boolean> pageTitleStarteWith(
      final String searchStr) {
    return driver -> driver.getTitle().toLowerCase()
        .startsWith(searchStr.toLowerCase());
  }

  private void baiduExampleSearchesFor(final String searchStr) {
    FirefoxOptions options = new FirefoxOptions();
    options.setBinary("D:\\Program Files\\Mozilla Firefox\\firefox.exe");
    System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
    WebDriver driver = new FirefoxDriver(options);
    driver.get("http://www.baidu.com");

    WebElement searchElem = driver.findElement(By.name("wd"));

    searchElem.clear();
    searchElem.sendKeys(searchStr);
    searchElem.submit();
    log.info("Title: " + driver.getTitle());

    WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
    wait.until(pageTitleStarteWith(searchStr));
    log.info("Page title is: " + driver.getTitle());

    driver.quit();
  }

  @Test
  private void baiduCheeseExample() {
    baiduExampleSearchesFor("Cheese");
  }

  @Test
  private void baiduMilkExample() {
    baiduExampleSearchesFor("Milk");
  }
}
