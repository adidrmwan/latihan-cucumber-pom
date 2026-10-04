# Latihan Cucumber + Gherkin + POM (Bengkel Otomasi)

Lanjutan **Sesi 5 (Page Object Model)**: test case ditulis dalam **Gherkin**
(bahasa yang bisa dibaca manusia), dijalankan oleh **Cucumber**, dan tetap
memakai **POM** untuk mengurus locator & aksi.

Semua dijalankan di situs latihan publik
[the-internet.herokuapp.com](https://the-internet.herokuapp.com).

## Kenapa Cucumber + Gherkin?

Sejauh ini test ditulis dalam Java — hanya programmer yang bisa membacanya.
Dengan Gherkin, test case ditulis seperti kalimat biasa:

```gherkin
Scenario: Login berhasil dengan kredensial yang valid
  Given saya berada di halaman login
  When saya login dengan username "tomsmith" dan password "SuperSecretPassword!"
  Then saya diarahkan ke halaman Secure Area
```

QA, product manager, bahkan klien bisa membaca & ikut menyusun skenario ini.
Pendekatan ini disebut **BDD (Behavior-Driven Development)**.

> **Analogi:** file `.feature` adalah *naskah drama* yang bisa dibaca siapa saja.
> Step definition adalah *sutradara* yang menerjemahkan tiap kalimat naskah
> menjadi instruksi. Page Object adalah *aktor* yang benar-benar beraksi di
> panggung (browser). Penulis naskah tidak perlu tahu cara aktor bergerak.

## Alur kerjanya

```
login.feature  ──►  LoginSteps.java  ──►  LoginPage / SecureAreaPage  ──►  Browser
 (Gherkin)          (step definition)      (Page Object / POM)
 "APA" diuji        penerjemah kalimat      "BAGAIMANA" berinteraksi
```

- **Tidak ada locator** di file `.feature` maupun di step definition.
  Semua locator tetap terkunci di Page class (aturan POM dari Sesi 5).
- Assertion (lulus/gagal) ada di step definition bagian `@Then`, **bukan** di Page class.

## Struktur folder

```
latihan-cucumber-pom/
├── pom.xml                                    <- Selenium + Cucumber + JUnit
├── README.md
├── .gitignore
└── src/
    ├── main/java/com/bengkelotomasi/pages/    <- PAGE OBJECT (POM)
    │   ├── BasePage.java                       (wait helper + pesan flash bersama)
    │   ├── LoginPage.java
    │   └── SecureAreaPage.java
    └── test/
        ├── java/com/bengkelotomasi/
        │   ├── driver/DriverManager.java       <- tempat menyimpan browser bersama
        │   ├── hooks/Hooks.java                <- buka/tutup browser tiap scenario
        │   ├── steps/LoginSteps.java           <- STEP DEFINITION (kalimat -> kode)
        │   └── runner/RunCucumberTest.java     <- "tombol start" Cucumber
        └── resources/
            ├── features/login.feature          <- TEST CASE dalam GHERKIN
            └── cucumber.properties
```

## Sintaks Gherkin yang dipakai di `login.feature`

| Kata kunci | Arti | Contoh di proyek ini |
|---|---|---|
| `Feature` | fitur yang diuji | Login ke Secure Area |
| `Background` | langkah yang jalan sebelum SETIAP scenario | buka halaman login |
| `Scenario` | satu kasus uji | Login berhasil |
| `Scenario Outline` + `Examples` | kasus uji yang sama, diulang dengan data berbeda | 3 variasi login gagal |
| `Given` | kondisi awal | saya berada di halaman login |
| `When` | aksi pengguna | saya login dengan ... |
| `Then` | hasil yang diharapkan | saya melihat pesan ... |
| `And` | lanjutan dari langkah sebelumnya | saya menekan tombol logout |
| `@smoke`, `@negative` | tag untuk mengelompokkan scenario | |

Total: **5 kasus uji** (2 scenario + 1 scenario outline × 3 baris data).

## Prasyarat

- JDK 17+ (`java -version` harus muncul nomor versi).
- Google Chrome terpasang (Selenium Manager mengurus driver otomatis).
- Koneksi internet.
- *(Opsional tapi sangat membantu)* di IntelliJ, instal plugin **Cucumber for Java**
  dan **Gherkin** (Settings → Plugins) supaya file `.feature` berwarna dan bisa
  di-klik langsung ke step definition-nya.

## Cara menjalankan

### Lewat terminal
Dari dalam folder `latihan-cucumber-pom/`:

```bash
# jalankan SEMUA scenario
mvn test

# jalankan hanya scenario bertag @smoke
mvn test -Dcucumber.filter.tags="@smoke"

# jalankan hanya scenario bertag @negative
mvn test -Dcucumber.filter.tags="@negative"
```

### Lewat IntelliJ IDEA
- Klik kanan `RunCucumberTest.java` → **Run 'RunCucumberTest'**, atau
- Klik kanan file `login.feature` → **Run** (butuh plugin Cucumber for Java).

### Melihat laporan
Setelah dijalankan, buka file `target/cucumber-report.html` di browser.
Kalau ada scenario yang gagal, screenshot saat gagal otomatis ikut tertempel
di laporan (diatur di `Hooks.java`).

## Contoh output di console

```
Scenario: Login berhasil dengan kredensial yang valid
  Given saya berada di halaman login                                       # LoginSteps...
  When saya login dengan username "tomsmith" dan password "SuperSecretPassword!"
  Then saya diarahkan ke halaman Secure Area
  And saya melihat pesan "You logged into a secure area!"
...
5 Scenarios (5 passed)
20 Steps (20 passed)
```

## Tugas untuk mentee

Tambahkan fitur baru **tanpa menyentuh** file yang sudah ada — ini bukti bahwa
struktur Cucumber + POM gampang dikembangkan.

1. Buat file `src/test/resources/features/dropdown.feature`, misalnya:
   ```gherkin
   Feature: Memilih opsi dropdown

     Scenario: Memilih opsi dari dropdown
       Given saya berada di halaman dropdown
       When saya memilih opsi "Option 2"
       Then opsi yang terpilih adalah "Option 2"
   ```
2. Buat Page class `DropdownPage.java` di folder `pages/` (extend `BasePage`,
   pakai `@FindBy(id = "dropdown")` dan class `Select` dari Sesi 3).
3. Buat step definition `DropdownSteps.java` di folder `steps/` yang memanggil
   method `DropdownPage` — ingat: tidak boleh ada locator di step definition.
4. Jalankan `mvn test`. Kalau ada langkah yang belum punya step definition,
   Cucumber akan menampilkan **potongan kode siap-tempel** di console — salin itu
   sebagai titik awal.

## Cara push ke GitHub

Dari dalam folder `latihan-cucumber-pom/`:

```bash
git init
git add .
git commit -m "Cucumber + Gherkin dengan Page Object Model"
git branch -M main
git remote add origin https://github.com/<username-kamu>/latihan-cucumber-pom.git
git push -u origin main
```

Folder `target/` (termasuk laporan) dan file IDE tidak ikut ter-push karena
sudah diatur di `.gitignore`.
