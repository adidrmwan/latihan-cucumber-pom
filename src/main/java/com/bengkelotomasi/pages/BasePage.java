package com.bengkelotomasi.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage: kode umum yang dipakai SEMUA Page class.
 *
 * Di sini kita gabungkan 3 pelajaran sekaligus:
 *   - Sesi 4 (Waits)  : semua aksi memakai explicit wait, TANPA Thread.sleep.
 *   - Sesi 5 (POM)    : driver & initElements cukup ditulis sekali di sini.
 *   - Hindari duplikasi: pesan "flash" muncul di banyak halaman, jadi
 *                        locator-nya cukup ditaruh SATU kali di sini.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // komponen yang muncul di BANYAK halaman -> cukup didefinisikan sekali
    @FindBy(id = "flash")
    private WebElement pesanFlash;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ---------- helper aksi yang sudah "sabar" (pakai explicit wait) ----------

    protected void tungguTerlihat(WebElement elemen) {
        wait.until(ExpectedConditions.visibilityOf(elemen));
    }

    protected void ketik(WebElement elemen, String teks) {
        tungguTerlihat(elemen);
        elemen.clear();
        elemen.sendKeys(teks);
    }

    protected void klik(WebElement elemen) {
        wait.until(ExpectedConditions.elementToBeClickable(elemen)).click();
    }

    // ---------- method umum yang bisa dipakai lewat page mana pun ----------

    /** teks pesan notifikasi, mis. "You logged into a secure area!" */
    public String getPesanFlash() {
        tungguTerlihat(pesanFlash);
        return pesanFlash.getText();
    }

    /** tunggu sampai URL mengandung potongan teks tertentu */
    public boolean urlMengandung(String potonganUrl) {
        return wait.until(ExpectedConditions.urlContains(potonganUrl));
    }
}
