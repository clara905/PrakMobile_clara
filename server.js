const express    = require("express");
const bodyParser = require("body-parser");
const cors       = require("cors");
const db         = require("./db");

const app = express();
app.use(cors());
app.use(bodyParser.json());

/* ===================== MAHASISWA ===================== */

// GET semua mahasiswa
app.get("/mahasiswa", (req, res) => {
  db.query("SELECT * FROM mahasiswa ORDER BY id DESC", (err, result) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal mengambil data" });
    res.json({ success: true, data: result });
  });
});

// GET mahasiswa by NIM (untuk login)
app.get("/mahasiswa/:nim", (req, res) => {
  const nim = req.params.nim;
  db.query("SELECT * FROM mahasiswa WHERE nim = ?", [nim], (err, result) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal mengambil data" });
    if (result.length === 0) return res.status(404).json({ success: false, message: "NIM tidak ditemukan" });
    res.json({ success: true, data: result[0] });
  });
});

// POST tambah mahasiswa
app.post("/mahasiswa", (req, res) => {
  const { nim, nama, prodi, semester, email, password } = req.body;
  const sql = "INSERT INTO mahasiswa (nim, nama, prodi, semester, email, password) VALUES (?, ?, ?, ?, ?, ?)";
  db.query(sql, [nim, nama, prodi, semester, email, password ?? "1234"], (err) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal menambah mahasiswa" });
    res.json({ success: true, message: "Mahasiswa berhasil ditambahkan" });
  });
});

// PUT update mahasiswa
app.put("/mahasiswa/:id", (req, res) => {
  const { nim, nama, prodi, semester, email } = req.body;
  const sql = "UPDATE mahasiswa SET nim=?, nama=?, prodi=?, semester=?, email=? WHERE id=?";
  db.query(sql, [nim, nama, prodi, semester, email, req.params.id], (err) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal update mahasiswa" });
    res.json({ success: true, message: "Mahasiswa berhasil diupdate" });
  });
});

// DELETE mahasiswa
app.delete("/mahasiswa/:id", (req, res) => {
  db.query("DELETE FROM mahasiswa WHERE id=?", [req.params.id], (err) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal menghapus data" });
    res.json({ success: true, message: "Data berhasil dihapus" });
  });
});

/* ===================== DOSEN ===================== */

// GET semua dosen
app.get("/dosen", (req, res) => {
  db.query("SELECT * FROM dosen ORDER BY id DESC", (err, result) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal mengambil data" });
    res.json({ success: true, data: result });
  });
});

// POST tambah dosen
app.post("/dosen", (req, res) => {
  const { nidn, nama, prodi, email } = req.body;
  const sql = "INSERT INTO dosen (nidn, nama, prodi, email) VALUES (?, ?, ?, ?)";
  db.query(sql, [nidn, nama, prodi, email], (err) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal menambah dosen" });
    res.json({ success: true, message: "Dosen berhasil ditambahkan" });
  });
});

// DELETE dosen
app.delete("/dosen/:id", (req, res) => {
  db.query("DELETE FROM dosen WHERE id=?", [req.params.id], (err) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal menghapus data" });
    res.json({ success: true, message: "Data berhasil dihapus" });
  });
});

/* ===================== MATA KULIAH ===================== */

// GET semua mata kuliah
app.get("/matkul", (req, res) => {
  db.query("SELECT * FROM mata_kuliah ORDER BY id DESC", (err, result) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal mengambil data" });
    res.json({ success: true, data: result });
  });
});

// POST tambah mata kuliah
app.post("/matkul", (req, res) => {
  const { kode, nama, sks, semester } = req.body;
  const sql = "INSERT INTO mata_kuliah (kode, nama, sks, semester) VALUES (?, ?, ?, ?)";
  db.query(sql, [kode, nama, sks, semester], (err) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal menambah mata kuliah" });
    res.json({ success: true, message: "Mata kuliah berhasil ditambahkan" });
  });
});

// DELETE mata kuliah
app.delete("/matkul/:id", (req, res) => {
  db.query("DELETE FROM mata_kuliah WHERE id=?", [req.params.id], (err) => {
    if (err) return res.status(500).json({ success: false, message: "Gagal menghapus data" });
    res.json({ success: true, message: "Data berhasil dihapus" });
  });
});

app.listen(3000, () => {
  console.log("Server SIAKAD berjalan di port 3000");
});