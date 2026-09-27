package elements;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TextElement extends BaseElement {

    private static final Logger log = LogManager.getLogger(TextElement.class);

    public TextElement(WebDriver driver, By locator, String name) {
        super(driver, locator, name);
    }

    @Step("Прочитать текст элемента '{name}'")
    public String read() {
        String text = getText();
        log.info("[{}] read -> '{}'", name, text);
        return text;
    }
}