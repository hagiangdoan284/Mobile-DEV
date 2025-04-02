package com.example.bt1sharereference

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bt1sharereference.ui.theme.BT1ShareReferenceTheme

class MainActivity : ComponentActivity() {
    private lateinit var btsave: Button
    private lateinit var btget: Button
    private lateinit var btclear: Button
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Khởi tạo SharedPreferences
        preferenceHelper = PreferenceHelper(this)

        // Ánh xạ view
        btsave = findViewById(R.id.btn_Luu)
        btget = findViewById(R.id.btn_Hienthi)
        btclear = findViewById(R.id.btn_Xoa)
        usernameEditText = findViewById(R.id.edt_ten)
        passwordEditText = findViewById(R.id.edt_matkhau)

        // Xử lý sự kiện nút "Lưu"
        btsave.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            preferenceHelper.saveCredentials(username, password)
        }

        // Xử lý sự kiện nút "Hiển thị"
        btget.setOnClickListener {
            val (storedUsername, storedPassword) = preferenceHelper.getCredentials()
            usernameEditText.setText(storedUsername ?: "")
            passwordEditText.setText(storedPassword ?: "")
        }

        // Xử lý sự kiện nút "Xóa"
        btclear.setOnClickListener {
            preferenceHelper.clearCredentials()
            usernameEditText.text.clear()
            passwordEditText.text.clear()
        }
    }
}
