package com.bengkelotomasi.hooks;

import com.bengkelotomasi.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Hooks: kode yang otomatis jalan SEBELUM dan SESUDAH setiap Scenario.
 *
 * Analogi: petugas panggung. Sebelum tiap "pertunjukan" (Scenario) ia
 * menyiapkan panggung (buka browser); setelah selesai ia membereskan
 * (tutup browser). Jadi step definition tinggal fokus ke cerita test.
 */
public class Hooks {

    @Before
    public void sebelumScenario() {
        DriverManager.bukaBrowser();
    }

    @After
    public void sesudahScenario(Scenario scenario) {
        // bonus: kalau scenario GAGAL, ambil screenshot & tempel ke laporan HTML
        if (scenario.isFailed() && DriverManager.getDriver() != null) {
            byte[] gambar = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(gambar, "image/png", "screenshot-saat-gagal");
        }
        DriverManager.tutupBrowser();
    }
}
