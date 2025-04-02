package com.example.bt2sqlite

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.bt2sqlite.utils.DatabaseHelper
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bt2sqlite.ui.theme.BT2SQLiteTheme
import com.example.bt2sqlite.model.Nguoidung

class MainActivity : ComponentActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var etSdt: EditText
    private lateinit var etTen: EditText
    private lateinit var btnThem: Button
    private lateinit var btnSua: Button
    private lateinit var btnXoa: Button
    private lateinit var btnHienthi: Button
    private lateinit var edID: EditText
    private lateinit var txtKetqua: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        etSdt = findViewById(R.id.etSDT)
        etTen = findViewById(R.id.etTen)

        btnThem = findViewById(R.id.btnThem)
        btnSua = findViewById(R.id.btnSua)
        btnXoa = findViewById(R.id.btnXoa)
        btnHienthi = findViewById(R.id.btnHienthi)
        edID = findViewById(R.id.etID)

        txtKetqua = findViewById(R.id.txtKetqua)


        btnThem.setOnClickListener {
            val ten = etTen.text.toString()
            val sdt = etSdt.text.toString()
            // Thêm người dùng vào cơ sở dữ liệu
            if (ten.isNotEmpty() && sdt.isNotEmpty()) {
                val nguoidung = Nguoidung(name = ten, phone = sdt)
                val result = databaseHelper.insertContact(nguoidung)
                if (result) {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
        btnSua.setOnClickListener {
            val id = edID.text.toString().toIntOrNull()
            val ten = etTen.text.toString()
            val sdt = etSdt.text.toString()
            if (ten.isNotEmpty() && sdt.isNotEmpty()) {
                val nguoidung = Nguoidung(id = id ?: 0, name = ten, phone = sdt)
                val result = databaseHelper.updateContact(nguoidung)
                if (result) {
                    Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Sửa thất bại", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
        btnXoa.setOnClickListener {
            val id = edID.text.toString().toIntOrNull()
            val result = databaseHelper.deleteContact(id ?: 0)
            if (result) {
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show()
            }
        }
        btnHienthi.setOnClickListener {
            val nguoidung= databaseHelper.getAllContacts()
            if (nguoidung.isNotEmpty()) {
                txtKetqua.text = nguoidung.joinToString("\n") { "ID: ${it.id}, Tên: ${it.name}, SĐT: ${it.phone}" }
            } else {
                txtKetqua.text = "Danh sách trống!"
            }
        }
    }
}

