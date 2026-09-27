package elements;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputElement extends BaseElement {

    private static final Logger log = LogManager.getLogger(InputElement.class);

    public InputElement(WebDriver driver, By locator, String name) {
        super(driver, locator, name);
    }

    public InputElement(WebDriver driver, By locator) {
        super(driver, locator);
    }

    @Step("Ввести '{text}' в поле '{this.name}'")
    public InputElement type(String text) {
        log.info("[{}] type '{}'", name, text);
        WebElement el = get();
        el.sendKeys(text);
        return this;
    }
}