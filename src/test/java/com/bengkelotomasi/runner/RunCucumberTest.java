package com.bengkelotomasi.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * RUNNER: "tombol start" untuk menjalankan semua file .feature.
 *
 * - features : di folder mana file .feature disimpan
 * - glue     : di package mana step definition & hooks berada
 * - plugin   : format laporan ("pretty" = log rapi di console,
 *              "html:..." = laporan HTML yang bisa dibuka di browser)
 *
 * Nama class diakhiri "Test" supaya otomatis dijalankan oleh "mvn test".
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.bengkelotomasi.steps", "com.bengkelotomasi.hooks"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)
public class RunCucumberTest {
    // sengaja kosong: semua pengaturan ada di anotasi di atas
}
