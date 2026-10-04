package com.bengkelotomasi.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page class untuk halaman "Secure Area" (muncul setelah login berhasil).
 *
 * Ingat aturan POM: Page class hanya MENYEDIAKAN informasi & aksi.
 * Assertion (lulus/gagal) tetap urusan step definition / test.
 */
public class SecureAreaPage extends BasePage {

    @FindBy(css = "h2")
    private WebElement judulHalaman;

    @FindBy(css = "a[href='/logout']")
    private WebElement tombolLogout;

    public SecureAreaPage(WebDriver driver) {
        super(driver);
    }

    /** apakah halaman Secure Area sudah tampil? */
    public boolean isTampil() {
        urlMengandung("/secure");
        tungguTerlihat(judulHalaman);
        return judulHalaman.getText().contains("Secure Area");
    }

    /** klik tombol Logout */
    public void logout() {
        klik(tombolLogout);
    }
}
