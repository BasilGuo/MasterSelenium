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
 * Usage: mvn clean verify -Dwebdriver.gecko.driver=<PATH_TO_GECKODRVIER_BINARY>
 *   如果geckodriver的可执行文件位于PATH环境变量中，那么可以不加后面的参数。
 *
 * @author basilguo@163.com
 * @date 21.2.23 19:09
 * @since version 1.0
 */

public class BasicIT extends DriverBase{
  private static final Logger log = LoggerFactory.getLogger(BasicIT.class);

  private ExpectedCondition<Boolean> pageTitleStartWith(
      final String searchStr) {
    return driver -> driver.getTitle().toLowerCase()
        .startsWith(searchStr.toLowerCase());
  }

  private void baiduExampleSearchesFor(final String searchStr) {
//    // 如果firefox安装在了默认位置，就可以不指定其位置
//    // 否则就需要像这样指定其所在的位置
//    FirefoxOptions options = new FirefoxOptions();
//    options.setBinary("D:\\Program Files\\Mozilla Firefox\\firefox.exe");
//    // 控制firefox自己的错误输出
//    System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
//    WebDriver driver = new FirefoxDriver(options);
    WebDriver driver = DriverBase.getDriver();
    // 打开网页
    driver.get("http://www.baidu.com");

    // 获取元素，这是获取的百度的搜索框，未来可能wd这个名字会变化
    WebElement searchElem = driver.findElement(By.name("wd"));

    // 清空input中内容
    searchElem.clear();
    // 填写要查找的内容
    searchElem.sendKeys(searchStr);
    // 查询
    searchElem.submit();
    // 打印日志：网页名称
    log.info("Title: " + driver.getTitle());

    // selenium等待
    WebDriverWait wait = new WebDriverWait(driver, 10, 1000);
    wait.until(pageTitleStartWith(searchStr));
    log.info("Page title is: " + driver.getTitle());

//    driver.quit();
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
