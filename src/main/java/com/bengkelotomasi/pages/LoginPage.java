package com.bengkelotomasi.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page class untuk halaman login.
 * SEMUA locator halaman ini hidup di sini (sebagai @FindBy), begitu juga
 * aksinya. Step definition (Cucumber) tidak perlu tahu locator mentahnya.
 */
public class LoginPage extends BasePage {

    private static final String URL = "https://the-internet.herokuapp.com/login";

    @FindBy(id = "username")
    private WebElement inputUsername;

    @FindBy(id = "password")
    private WebElement inputPassword;

    @FindBy(css = "button[type='submit']")
    private WebElement tombolLogin;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /** buka halaman login */
    public void buka() {
        driver.get(URL);
    }

    /** isi username & password lalu klik Login */
    public void login(String username, String password) {
        ketik(inputUsername, username);
        ketik(inputPassword, password);
        klik(tombolLogin);
    }

    /** apakah sekarang sedang berada di halaman login? */
    public boolean isTampil() {
        return urlMengandung("/login");
    }
}
