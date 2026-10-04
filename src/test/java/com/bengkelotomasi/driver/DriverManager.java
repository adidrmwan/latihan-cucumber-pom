package com.bengkelotomasi.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * DriverManager: "lemari" tempat menyimpan browser yang sedang dipakai.
 *
 * Kenapa perlu? Di Cucumber, kode tersebar di beberapa class (Hooks yang
 * membuka/menutup browser, LoginSteps yang memakai browser). Semuanya butuh
 * browser YANG SAMA. DriverManager adalah tempat bersama untuk mengambilnya.
 *
 * ThreadLocal = tiap "jalur eksekusi" punya browser sendiri, sehingga aman
 * kalau nanti test dijalankan paralel (dibahas di Sesi 6 & 8).
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
        // class utilitas, tidak perlu dibuat objeknya
    }

    public static void bukaBrowser() {
        DRIVER.set(new ChromeDriver()); // Selenium Manager mengurus driver otomatis
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void tutupBrowser() {
        if (DRIVER.get() != null) {
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}
