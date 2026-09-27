package elements;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoadPropertiesUtils;

import java.time.Duration;

/**
 * Базовый класс для всех кастомных элементов.
 */
public abstract class BaseElement {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final By locator;
    protected final String name;
    protected static final Logger log = LogManager.getLogger(BaseElement.class);

    public BaseElement(WebDriver driver, By locator, String name) {
        this.driver = driver;
        this.locator = locator;
        this.name = name;
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(LoadPropertiesUtils.getInt("timeout")));
    }

    public BaseElement(WebDriver driver, By locator) {
        this(driver, locator, locator.toString());
    }

    protected WebElement get() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement getClickable() {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    @Step("Клик по элементу '{this.name}'")
    public void click() {
        log.info("[{}] click", name);
        scrollToView();
        getClickable().click();
    }

    @Step("Получить текст элемента '{this.name}'")
    public String getText() {
        String text = get().getText();
        log.info("[{}] getText -> '{}'", name, text);
        return text;
    }

    @Step("Проверить видимость элемента '{this.name}'")
    public boolean isVisible() {
        try {
            return get().isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    protected void scrollToView() {
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", get());
        } catch (Exception ignored) {
        }
    }
}