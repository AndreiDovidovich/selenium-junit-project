package elements;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ButtonElement extends BaseElement {

    private static final Logger log = LogManager.getLogger(ButtonElement.class);

    public ButtonElement(WebDriver driver, By locator, String name) {
        super(driver, locator, name);
    }

    public ButtonElement(WebDriver driver, By locator) {
        super(driver, locator);
    }

    @Step("Клик по кнопке '{this.name}'")
    public ButtonElement clickButton() {
        log.info("[{}] clickButton", name);
        scrollToView();
        getClickable().click();
        return this;
    }
}