package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@Epic("DemoQA")
@Feature("Login")
@Owner("Andrei Dovidovich")
@Tag("login")
public class LoginTest extends BaseTest {

    private static final String VALID_USERNAME = "testuser";
    private static final String VALID_PASSWORD = "Test@1234!";
    private static final String INVALID_PASSWORD = "wrongpass";
    private static final String INVALID_USERNAME = "wronguser";
    private static final String INVALID_CREDENTIALS_MESSAGE =
            "Invalid username or password";
    private LoginPage loginPage;

    @BeforeEach
    void openLoginPage() {
        loginPage = new LoginPage(driver).open();
    }

    @Test
    @Tag("smoke")
    @DisplayName("Страница Login открывается и форма видна")
    @Story("Basic UI")
    @Severity(SeverityLevel.BLOCKER)
    @TmsLink("DEMOQA-LOGIN-1")
    void loginPageIsOpenedTest() {
        assertAll(
                () -> assertTrue(loginPage.getCurrentUrl().contains("/login"),
                        "URL должен содержать /login, но был: " + loginPage.getCurrentUrl()),
                () -> assertTrue(loginPage.isLoginFormVisible(),
                        "Форма логина должна быть видна")
        );
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Story("Negative login")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("DEMOQA-LOGIN-3")
    void invalidPasswordLoginTest() {
        loginPage.login(VALID_USERNAME, INVALID_PASSWORD);

        assertAll(
                () -> assertFalse(loginPage.isLogoutButtonVisible(),
                        "Logout не должен появиться при неверном пароле"),
                () -> assertTrue(loginPage.isErrorMessageVisible(),
                        "Сообщение об ошибке должно быть видно"),
                () -> assertTrue(
                        loginPage.getErrorMessage().contains(INVALID_CREDENTIALS_MESSAGE),
                        "Ожидали '" + INVALID_CREDENTIALS_MESSAGE + "', "
                                + "получили: " + loginPage.getErrorMessage())
        );
    }

    @Test
    @DisplayName("Логин с неверным username")
    @Story("Negative login")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("DEMOQA-LOGIN-4")
    void invalidUsernameLoginTest() {
        loginPage.login(INVALID_USERNAME, VALID_PASSWORD);

        assertAll(
                () -> assertFalse(loginPage.isLogoutButtonVisible(),
                        "Logout не должен появиться при неверном username"),
                () -> assertTrue(loginPage.isErrorMessageVisible(),
                        "Сообщение об ошибке должно быть видно"),
                () -> assertTrue(
                        loginPage.getErrorMessage().contains(INVALID_CREDENTIALS_MESSAGE),
                        "Ожидали '" + INVALID_CREDENTIALS_MESSAGE + "', "
                                + "получили: " + loginPage.getErrorMessage())
        );
    }
}