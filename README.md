# SIAKAD — Sistem Informasi Akademik

Aplikasi Android sederhana untuk pengelolaan data akademik (mahasiswa, dosen, mata kuliah) yang dibangun sebagai bahan praktikum **Mobile Programming**. Project ini mendukung dua mode penyimpanan data: **SQLite (lokal)** dan **MySQL melalui REST API**.


---

## Tentang Project

SIAKAD dibuat secara bertahap mengikuti modul praktikum:

| Pertemuan | Materi | Fokus |
|---|---|---|
| 1–9 | Dasar Android (UI, RecyclerView, Fragment, Navigasi) | Tampilan & komponen dasar |
| 10 | Koneksi Database SQLite (Local) | CRUD data mahasiswa secara lokal di perangkat |
| 11 | Koneksi Database MySQL via API | CRUD data mahasiswa & dosen melalui REST API (Node.js + Express + MySQL) |

Project ini cocok digunakan sebagai bahan belajar perbandingan antara penyimpanan data lokal (offline) dan penyimpanan data terpusat (online via API).

---

## Fitur

### Autentikasi
- Login dengan dua role: **Admin** dan **Mahasiswa**.
- Admin masuk ke dashboard pengelolaan data.
- Mahasiswa masuk ke dashboard profil pribadi (data diambil dari SQLite berdasarkan NIM).

### Mode SQLite (Lokal)
- Tambah data mahasiswa.
- Lihat seluruh data mahasiswa dalam RecyclerView (list, grid, atau card).
- Hapus data mahasiswa.
- Data tersimpan permanen di perangkat, tidak membutuhkan koneksi internet.

### Mode MySQL API (Server)
- Tambah, lihat, edit, dan hapus data **mahasiswa** melalui REST API.
- Tambah, lihat, dan hapus data **dosen** melalui REST API.
- Komunikasi data menggunakan format JSON dengan Retrofit + Gson.
- Mendukung banyak pengguna (multi-device) karena data tersimpan terpusat di MySQL.

---

## Teknologi yang Digunakan

**Android (Client)**
- Kotlin
- Android SQLite (`SQLiteOpenHelper`)
- RecyclerView + ViewBinding
- Retrofit2 + Gson Converter
- OkHttp Logging Interceptor
- Material Components

**Backend (Server)**
- Node.js
- Express.js
- MySQL2
- CORS, Body-Parser
- Nodemon (development)

**Database**
- SQLite (lokal, on-device)
- MySQL (server, via XAMPP/standalone)

---

## Struktur Project

```
SIAKAD/
├── backend-siakad/                      ← Folder backend (di luar project Android)
│   ├── db.js                            ← Koneksi ke MySQL
│   ├── server.js                        ← Express server & endpoint API
│   └── package.json
│
└── app/src/main/
    ├── java/com/app/SIAKAD/
    │   ├── MainActivity.kt
    │   ├── data/
    │   │   ├── model/
    │   │   │   ├── Mahasiswa.kt
    │   │   │   ├── Dosen.kt
    │   │   │   ├── MataKuliah.kt
    │   │   │   └── ApiResponse.kt
    │   │   ├── local/
    │   │   │   └── MahasiswaDbHelper.kt          ← SQLite helper (CRUD lokal)
    │   │   └── remote/
    │   │       ├── ApiService.kt                  ← Daftar endpoint API
    │   │       └── ApiClient.kt                    ← Konfigurasi Retrofit
    │   └── ui/
    │       ├── adapter/
    │       │   ├── MahasiswaAdapter.kt             ← Adapter SQLite
    │       │   ├── MahasiswaApiAdapter.kt          ← Adapter API
    │       │   └── DosenApiAdapter.kt
    │       └── main/
    │           ├── LoginFragment.kt
    │           ├── AdminFragment.kt
    │           ├── DashboardMahasiswaFragment.kt
    │           ├── DataMahasiswaFragment.kt        ← Versi SQLite
    │           ├── TambahMahasiswaFragment.kt      ← Versi SQLite
    │           ├── DataMahasiswaApiFragment.kt     ← Versi API
    │           ├── FormMahasiswaApiFragment.kt     ← Versi API (tambah & edit)
    │           ├── DataDosenApiFragment.kt
    │           └── TambahDosenApiFragment.kt
    │
    └── res/layout/
        ├── activity_main.xml
        ├── fragment_login.xml
        ├── fragment_admin.xml
        ├── fragment_dashboard_mahasiswa.xml
        ├── fragment_data_mahasiswa.xml
        ├── fragment_tambah_mahasiswa.xml
        ├── fragment_data_mahasiswa_api.xml
        ├── fragment_form_mahasiswa_api.xml
        ├── fragment_data_dosen_api.xml
        ├── fragment_tambah_dosen_api.xml
        ├── item_mahasiswa.xml
        ├── item_mahasiswa_api.xml
        └── item_dosen_api.xml
```

---

## Cara Setup

### 1. Setup Android (SQLite)

Mode ini tidak membutuhkan server tambahan, langsung jalan setelah project dibuka.

1. Clone atau buka project di Android Studio.
2. Pastikan dependency dasar (RecyclerView, Material Components) sudah tersedia di `build.gradle`.
3. Jalankan aplikasi di emulator atau perangkat fisik.
4. Login sebagai admin untuk mengelola data mahasiswa secara lokal.

### 2. Setup Backend (Node.js + MySQL)

Mode ini dibutuhkan jika ingin menggunakan fitur berbasis API (mahasiswa via MySQL, dosen).

**a. Buat folder backend**

```bash
mkdir backend-siakad
cd backend-siakad
npm init -y
npm install express mysql2 cors body-parser nodemon
```

**b. Buat database MySQL**

Jalankan via phpMyAdmin atau MySQL CLI:

```sql
CREATE DATABASE siakad_db;
USE siakad_db;

CREATE TABLE mahasiswa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nim VARCHAR(20) UNIQUE NOT NULL,
    nama VARCHAR(100) NOT NULL,
    prodi VARCHAR(100) NOT NULL,
    semester INT NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE dosen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nidn VARCHAR(20) UNIQUE NOT NULL,
    nama VARCHAR(100) NOT NULL,
    prodi VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE mata_kuliah (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kode VARCHAR(20) UNIQUE NOT NULL,
    nama VARCHAR(100) NOT NULL,
    sks INT NOT NULL,
    semester INT NOT NULL
);
```

**c. Sesuaikan koneksi database**

Buka `db.js`, sesuaikan `host`, `user`, `password` dengan konfigurasi MySQL lokal kamu.

**d. Jalankan server**

```bash
npm start
```

Jika berhasil, terminal akan menampilkan `Server SIAKAD berjalan di port 3000`.

**e. Uji API**

Buka browser dan akses:
```
http://localhost:3000/mahasiswa
http://localhost:3000/dosen
```

### 3. Setup Android (Koneksi API)

1. Tambahkan dependency Retrofit di `build.gradle (app)`:
   ```gradle
   implementation("com.squareup.retrofit2:retrofit:2.9.0")
   implementation("com.squareup.retrofit2:converter-gson:2.9.0")
   implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
   ```
2. Tambahkan permission internet di `AndroidManifest.xml`:
   ```xml
   <uses-permission android:name="android.permission.INTERNET"/>
   ```
   Dan atribut berikut di tag `<application>`:
   ```xml
   android:usesCleartextTraffic="true"
   ```
3. Sesuaikan `BASE_URL` di `ApiClient.kt`:
   - Emulator Android Studio → `http://10.0.2.2:3000/`
   - Perangkat fisik (HP) → `http://<IP-komputer-di-jaringan-yang-sama>:3000/`
   - Jangan gunakan `http://localhost:3000/` dari sisi Android.
4. Pastikan backend (`npm start`) sedang berjalan sebelum menjalankan aplikasi Android.

---

## Alur Aplikasi

```
App Dibuka
   └── LoginFragment
         ├── Login Admin (admin/admin)
         │     └── AdminFragment
         │           ├── Kelola Mahasiswa (SQLite atau API, sesuai konfigurasi)
         │           ├── Kelola Dosen (API)
         │           └── Kelola Mata Kuliah (Segera Hadir)
         │
         └── Login Mahasiswa (NIM/1234)
               └── DashboardMahasiswaFragment
                     ├── Lihat profil (nama, NIM, prodi, semester, email)
                     ├── Jadwal Kuliah (Segera Hadir)
                     ├── Nilai Akademik (Segera Hadir)
                     └── Logout → kembali ke LoginFragment
```

---

## Kredensial Demo

| Role | Username / NIM | Password | Catatan |
|---|---|---|---|
| Admin | `admin` | `admin` | Hardcoded untuk keperluan demo |
| Mahasiswa | NIM yang sudah terdaftar di SQLite | `1234` | Password sama untuk semua mahasiswa (demo) |

> Kredensial ini hanya untuk keperluan pembelajaran. Pada implementasi produksi, autentikasi harus melalui sistem hashing password dan validasi server, bukan hardcoded di sisi client.

---
