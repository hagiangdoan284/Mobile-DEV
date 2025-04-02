package com.example.blocknumbers
import BlockNumberActivity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.ArrayAdapter
import com.example.blocknumbers.R

class MainActivity : AppCompatActivity() {

    private lateinit var buttonManageBlockList: Button
    private lateinit var listViewBlockedNumbers: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var blockedNumbers = mutableListOf<String>()

    companion object {
        private const val REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        buttonManageBlockList = findViewById(R.id.buttonManageBlockList)
        listViewBlockedNumbers = findViewById(R.id.listViewBlockedNumbers)

        // Yêu cầu quyền truy cập
        requestPermissions()

        // Tải danh sách số bị chặn
        loadBlockedNumbers()

        // Chuyển đến màn hình quản lý số bị chặn
        buttonManageBlockList.setOnClickListener {
            val intent = Intent(this, BlockNumberActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Cập nhật danh sách khi quay lại MainActivity
        loadBlockedNumbers()
    }

    private fun loadBlockedNumbers() {
        blockedNumbers = BlockedNumbers.getBlockedNumbers(this).toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, blockedNumbers)
        listViewBlockedNumbers.adapter = adapter
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ANSWER_PHONE_CALLS
        )

        if (permissions.any {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            }) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Quyền đã được cấp", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Cần cấp quyền để ứng dụng hoạt động", Toast.LENGTH_LONG).show()
            }
        }
    }
}
