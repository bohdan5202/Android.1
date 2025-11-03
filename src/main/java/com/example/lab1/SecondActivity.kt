package com.example.lab1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    private lateinit var myAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupButtonListener()
    }

    fun setupRecyclerView() {
        val count = intent.getIntExtra("NUM", 0)
        val allSubject : Array<String> = resources.getStringArray(R.array.subject_lessons)
        val subjects = allSubject.take(count)
        myAdapter = Adapter(subjects)
        binding.recyclerView.apply {
          layoutManager = LinearLayoutManager(this@SecondActivity)
            adapter = myAdapter
        }
    }

    fun setupButtonListener() {
        binding.button.setOnClickListener {
            val finalList: IntArray = myAdapter.getList().toIntArray()
            val resultsIntent = Intent().apply {
                putExtra("AVERAGE", finalList)
            }
            setResult(RESULT_OK, resultsIntent)
            finish()
        }
    }
}
