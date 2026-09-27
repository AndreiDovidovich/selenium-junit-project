package pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoadPropertiesUtils;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected static final Logger log = LogManager.getLogger(BasePage.class);

    protected abstract String getPath();

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(LoadPropertiesUtils.getInt("timeout")));
    }

    public String getUrl() {
        return LoadPropertiesUtils.getBaseUrl() + getPath();
    }

    @Step("Открыть страницу '{this.getClass().getSimpleName}'")
    public BasePage openPage() {
        String url = getUrl();
        log.info("Open URL: {}", url);
        driver.get(url);
        return this;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}