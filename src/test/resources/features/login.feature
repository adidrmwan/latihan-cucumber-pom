# ==========================================================================
#  FILE GHERKIN -- test case ditulis dalam "bahasa manusia"
#  Siapa pun (QA, PM, bahkan klien) bisa membaca & memahami file ini
#  tanpa perlu mengerti Java.
#
#  Kata kunci Gherkin:
#    Feature          -> fitur yang diuji
#    Background       -> langkah yang dijalankan SEBELUM setiap Scenario
#    Scenario         -> satu kasus uji
#    Scenario Outline -> kasus uji yang sama, diulang dengan data berbeda
#    Examples         -> tabel data untuk Scenario Outline
#    Given / When / Then / And -> kondisi awal / aksi / hasil yang diharapkan
#    @tag             -> label untuk mengelompokkan & memilih scenario
# ==========================================================================

Feature: Login ke Secure Area
  Sebagai pengguna terdaftar
  Saya ingin bisa login dan logout
  Agar bisa mengakses halaman yang dilindungi dengan aman

  Background:
    Given saya berada di halaman login

  @smoke
  Scenario: Login berhasil dengan kredensial yang valid
    When saya login dengan username "tomsmith" dan password "SuperSecretPassword!"
    Then saya diarahkan ke halaman Secure Area
    And saya melihat pesan "You logged into a secure area!"

  @smoke
  Scenario: Logout setelah berhasil login
    When saya login dengan username "tomsmith" dan password "SuperSecretPassword!"
    And saya menekan tombol logout
    Then saya kembali ke halaman login
    And saya melihat pesan "You logged out of the secure area!"

  @negative
  Scenario Outline: Login gagal dengan kredensial yang salah
    When saya login dengan username "<username>" dan password "<password>"
    Then saya tetap berada di halaman login
    And saya melihat pesan "<pesan>"

    Examples:
      | username | password             | pesan                     |
      | salah    | SuperSecretPassword! | Your username is invalid! |
      | tomsmith | passwordSalah        | Your password is invalid! |
      |          |                      | Your username is invalid! |
