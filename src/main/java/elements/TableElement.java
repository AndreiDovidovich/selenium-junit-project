package elements;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Универсальный элемент для таблиц.
 */
public class TableElement extends BaseElement {

    private static final Logger log = LogManager.getLogger(TableElement.class);

    private static final By ROWS = By.cssSelector("tbody tr");
    private static final By CELLS = By.tagName("td");

    public TableElement(WebDriver driver, By locator, String name) {
        super(driver, locator, name);
    }

    private List<WebElement> getRows() {
        WebElement table = get();
        List<WebElement> rows = table.findElements(ROWS);
        return rows;
    }

    private List<WebElement> getCells(WebElement row) {
        return row.findElements(CELLS);
    }

    @Step("Получить количество строк в '{this.name}'")
    public int getRowCount() {
        List<WebElement> rows = getRows();
        log.info("[{}] row count = {}", name, rows.size());
        return rows.size();
    }

    @Step("Прочитать все строки '{this.name}'")
    public List<String> getRowTexts() {
        return getRows().stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Step("Прочитать колонку #{columnIndex} в '{this.name}'")
    public List<String> getColumnTexts(int columnIndex) {
        List<String> result = new ArrayList<>();
        for (WebElement row : getRows()) {
            List<WebElement> cells = getCells(row);
            if (cells.size() > columnIndex) {
                String text = cells.get(columnIndex).getText().trim();
                if (!text.isEmpty()) {
                    result.add(text);
                }
            }
        }
        return result;
    }

    public boolean isEmpty() {
        return getRowCount() == 0;
    }
}