package tests.books;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.BooksPage;
import tests.BaseTest;

import static org.junit.jupiter.api.Assertions.*;

@Epic("DemoQA")
@Feature("Book Store — Search")
@Owner("Andrei Dovidovich")
@Tag("books")
class SearchBooksTest extends BaseTest {

    private BooksPage booksPage;

    @BeforeEach
    void openBooksPage() {
        booksPage = new BooksPage(driver).open();
    }

    @Test
    @Tag("smoke")
    @DisplayName("Таблица книг отображается при открытии страницы")
    @Story("Basic UI")
    @Severity(SeverityLevel.BLOCKER)
    @TmsLink("DEMOQA-1")
    void booksTableIsVisibleTest() {
        int rowCount = booksPage.getRowCount();

        assertTrue(rowCount > 0,
                "Таблица должна содержать хотя бы одну книгу, но найдено: " + rowCount);
    }

    @Test
    @Tag("smoke")
    @DisplayName("Все 8 книг отображаются на странице")
    @Story("Basic UI")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("DEMOQA-2")
    void allBooksAreDisplayedTest() {
        assertEquals(8, booksPage.getRowCount(),
                "На demoqa.com/books должно быть ровно 8 книг");
    }

    @Test
    @DisplayName("Поиск по части названия возвращает подходящие книги")
    @Story("Поиск по названию")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("DEMOQA-3")
    void searchByPartialTitleTest() {
        booksPage.search("Design");

        int rowCount = booksPage.getRowCount();
        assertTrue(rowCount > 0, "Должны найтись книги, но найдено: " + rowCount);
        assertTrue(booksPage.getBookTitles().stream().allMatch(t -> t.contains("Design")),
                "Все результаты должны содержать 'Design', но получено: "
                        + booksPage.getBookTitles());
    }

    @Test
    @DisplayName("Поиск по автору возвращает его книги")
    @Story("Поиск по автору")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("DEMOQA-4")
    void searchByAuthorTest() {
        booksPage.search("Addy Osmani");

        assertTrue(booksPage.getRowCount() > 0, "Должны найтись книги");
        assertTrue(booksPage.hasAuthor("Addy Osmani"),
                "Должен быть автор 'Addy Osmani'");
    }

    @Test
    @DisplayName("Поиск несуществующей книги — таблица пуста")
    @Story("Негативный поиск")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("DEMOQA-5")
    void searchNonExistingBookTest() {
        booksPage.search("ZZZNOEXISTZZZ");

        assertAll(
                () -> assertTrue(booksPage.isTableEmpty(),
                        "Таблица должна быть пуста при поиске несуществующей книги"),
                () -> assertEquals(0, booksPage.getRowCount(),
                        "Количество строк должно быть 0")
        );
    }

    @ParameterizedTest(name = "Поиск по названию книги #{index}: {0}")
    @ValueSource(strings = {
            "Git Pocket Guide",
            "Speaking JavaScript",
            "You Don't Know JS"
    })
    @Story("Data-driven: каждая книга находится по названию")
    @Severity(SeverityLevel.MINOR)
    @TmsLink("DEMOQA-6")
    void searchEachBookByTitleTest(String title) {
        booksPage.search(title);

        assertAll(
                () -> assertEquals(1, booksPage.getRowCount(),
                        "Должна найтись ровно одна книга по запросу '" + title + "'"),
                () -> assertTrue(booksPage.hasBookWithTitle(title),
                        "Книга '" + title + "' должна найтись")
        );
    }
}