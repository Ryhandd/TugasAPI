# Tugas Pemrograman Mobile: API & Integrasi Supabase (Skema Akademik)

Proyek ini adalah implementasi API untuk mengelola data profil mahasiswa/pegawai yang terintegrasi dengan database **Supabase**. Dibuat untuk memenuhi tugas mata kuliah Pemrograman Mobile/Web di Semester 4.

## 🛠️ Konfigurasi Database
Aplikasi ini terhubung ke backend Supabase dengan spesifikasi sebagai berikut:
* **Base URL:** `https://smnrecvckiaoynhdqzbl.supabase.co/`
* **Database Schema:** `akademik`
* **Authentication:** Menggunakan API Key (Service Role/Anon Key) yang sudah terkonfigurasi di dalam proyek.

## 📊 Struktur Data (Fields)
Data yang dikelola dalam API ini mencakup:
1. **NIP**: Nomor Induk Pegawai / Identitas Unik.
2. **Nama**: Nama lengkap pengguna.
3. **Alamat**: Alamat tempat tinggal.
4. **Link Foto**: URL string untuk menampilkan foto profil.

## 🚀 Fitur Utama
* **Fetch Data:** Mengambil data dari tabel dalam skema `akademik`.
* **Update Profil:** Memperbarui informasi NIP, Nama, Alamat, dan Link Foto.
* **Integrasi Supabase Client:** Penggunaan library client untuk koneksi database *real-time*.

## 📂 Cara Penggunaan
1. Pastikan koneksi internet aktif untuk mengakses server Supabase.
2. Clone repositori ini.
3. Jalankan proyek melalui **Android Studio** (atau IDE pilihan kamu).
4. Aplikasi akan otomatis melakukan *handshake* ke URL Supabase menggunakan API Key yang tersedia untuk mengambil data dari skema `akademik`.

---

## 👤 Identitas Mahasiswa
* **Nama:** Rayhand N. U. Wumu
* **NIM:** 24024010
* **Kelas:** 4TI1
* **Program Studi:** Teknik Informatika

---
*Dokumentasi ini dibuat untuk keperluan akademik - 2026*