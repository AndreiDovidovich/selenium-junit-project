package pages;

import elements.InputElement;
import elements.TableElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class BooksPage extends BasePage {

    private final InputElement searchBox =
            new InputElement(driver, By.id("searchBox"), "Search box");

    private final TableElement booksTable =
            new TableElement(driver, By.xpath("//table"), "Books table");

    public BooksPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPath() {
        return "/books";
    }

    @Step("Открыть страницу Books")
    public BooksPage open() {
        driver.get(getUrl());
        return this;
    }

    @Step("Ввести '{query}' в поиск")
    public BooksPage search(String query) {
        searchBox.type(query);
        return this;
    }

    public int getRowCount() {
        return booksTable.getRowCount();
    }

    public List<String> getAllRows() {
        return booksTable.getRowTexts();
    }

    @Step("Получить список названий книг")
    public List<String> getBookTitles() {
        return booksTable.getColumnTexts(1);
    }

    @Step("Получить список авторов")
    public List<String> getAuthors() {
        return booksTable.getColumnTexts(2);
    }

    @Step("Получить список издателей")
    public List<String> getPublishers() {
        return booksTable.getColumnTexts(3);
    }

    public boolean hasBookWithTitle(String title) {
        return getBookTitles().stream()
                .anyMatch(t -> t.contains(title));
    }

    public boolean hasAuthor(String author) {
        return getAuthors().stream()
                .anyMatch(a -> a.contains(author));
    }

    public boolean isTableEmpty() {
        return booksTable.isEmpty();
    }
}