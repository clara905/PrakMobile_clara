const mysql = require("mysql2");

const connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",           // sesuaikan dengan password MySQL kamu
  database: "siakad_db"
});

connection.connect((err) => {
  if (err) {
    console.error("Koneksi database gagal:", err.message);
  } else {
    console.log("Koneksi database berhasil");
  }
});

module.exports = connection;