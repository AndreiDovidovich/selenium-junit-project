package pages;

import elements.ButtonElement;
import elements.InputElement;
import elements.TextElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final InputElement username =
            new InputElement(driver, By.id("userName"), "Username");
    private final InputElement password =
            new InputElement(driver, By.id("password"), "Password");
    private final ButtonElement loginBtn =
            new ButtonElement(driver, By.id("login"), "Login button");
    private final ButtonElement logoutBtn =
            new ButtonElement(driver, By.xpath("//button[text()='Log out']"), "Logout button");
    private final TextElement errorMessage =
            new TextElement(driver, By.id("name"), "Error message");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPath() {
        return "/login";
    }

    @Step("Открыть страницу Login")
    public LoginPage open() {
        driver.get(getUrl());
        return this;
    }

    @Step("Войти пользователем '{user}'")
    public LoginPage login(String user, String pass) {
        username.type(user);
        password.type(pass);
        loginBtn.clickButton();
        return this;
    }

    public boolean isLogoutButtonVisible() {
        return logoutBtn.isVisible();
    }

    public String getErrorMessage() {
        return errorMessage.read();
    }

    public boolean isErrorMessageVisible() {
        return errorMessage.isVisible();
    }

    public boolean isLoginFormVisible() {
        return username.isVisible() && password.isVisible() && loginBtn.isVisible();
    }
}