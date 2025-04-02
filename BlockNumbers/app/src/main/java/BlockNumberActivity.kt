import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.blocknumbers.R

class BlockNumberActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var buttonAdd: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var blockedNumbers = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_number)

        editText = findViewById(R.id.editTextNumber)
        buttonAdd = findViewById(R.id.buttonAdd)
        listView = findViewById(R.id.listView)

        loadBlockedNumbers()

        buttonAdd.setOnClickListener {
            val number = editText.text.toString()
            if (number.isNotEmpty()) {
                BlockedNumbers.addNumber(this, number)
                blockedNumbers.add(number)
                adapter.notifyDataSetChanged()
                editText.text.clear()
            }
        }
    }

    private fun loadBlockedNumbers() {
        blockedNumbers = BlockedNumbers.getBlockedNumbers(this).toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, blockedNumbers)
        listView.adapter = adapter
    }
}
