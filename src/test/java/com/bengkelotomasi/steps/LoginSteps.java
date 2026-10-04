package com.bengkelotomasi.steps;

import com.bengkelotomasi.driver.DriverManager;
import com.bengkelotomasi.pages.LoginPage;
import com.bengkelotomasi.pages.SecureAreaPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

/**
 * STEP DEFINITIONS: "penerjemah" dari kalimat Gherkin ke kode Java.
 *
 * Tiap method di sini menempel ke SATU kalimat di file .feature lewat
 * anotasi @Given / @When / @Then. {string} = bagian kalimat yang di dalam
 * tanda kutip, otomatis dikirim sebagai parameter method.
 *
 * Perhatikan: di sini TIDAK ADA locator sama sekali. Step definition hanya
 * memanggil method Page Object (POM). Locator tetap terkunci di Page class.
 *
 *   login.feature  ->  LoginSteps (kalimat -> aksi)  ->  Page Object  ->  Browser
 */
public class LoginSteps {

    private LoginPage loginPage;

    // ---------------- GIVEN: kondisi awal ----------------

    @Given("saya berada di halaman login")
    public void sayaBeradaDiHalamanLogin() {
        loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.buka();
    }

    // ---------------- WHEN: aksi pengguna ----------------

    @When("saya login dengan username {string} dan password {string}")
    public void sayaLoginDengan(String username, String password) {
        loginPage.login(username, password);
    }

    @When("saya menekan tombol logout")
    public void sayaMenekanTombolLogout() {
        new SecureAreaPage(DriverManager.getDriver()).logout();
    }

    // ---------------- THEN: hasil yang diharapkan (assertion) ----------------

    @Then("saya diarahkan ke halaman Secure Area")
    public void sayaDiarahkanKeSecureArea() {
        SecureAreaPage secureArea = new SecureAreaPage(DriverManager.getDriver());
        assertTrue("Halaman Secure Area tidak tampil", secureArea.isTampil());
    }

    @Then("saya kembali ke halaman login")
    public void sayaKembaliKeHalamanLogin() {
        assertTrue("Tidak kembali ke halaman login", loginPage.isTampil());
    }

    @Then("saya tetap berada di halaman login")
    public void sayaTetapDiHalamanLogin() {
        assertTrue("Seharusnya tetap di halaman login", loginPage.isTampil());
    }

    @Then("saya melihat pesan {string}")
    public void sayaMelihatPesan(String pesanDiharapkan) {
        // pesan flash ada di BasePage, jadi bisa dibaca lewat page mana pun.
        // Pakai contains() karena teks aslinya diakhiri tanda "x" (tombol tutup).
        String pesanAktual = loginPage.getPesanFlash();
        assertTrue(
                "Pesan tidak sesuai. Diharapkan mengandung: '" + pesanDiharapkan
                        + "' tapi tampil: '" + pesanAktual + "'",
                pesanAktual.contains(pesanDiharapkan));
    }
}
