package com.example.firebaseauthentiaction

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseauthentication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class User(val id: String, val email: String, val name: String = "")

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.length >= 6) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                saveUserToDatabase(userId, email)
                            }
                            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Vui lòng nhập email hợp lệ và mật khẩu tối thiểu 6 ký tự!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserToDatabase(userId: String, email: String) {
        val user = User(userId, email)
        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Lưu dữ liệu thành công!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Lưu dữ liệu thất bại!", Toast.LENGTH_SHORT).show()
            }
    }
}
