package com.example.a5046ass2_project.RecycleView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a5046ass2_project.RecycleView.adapter.RecyclerViewAdapter
import com.example.a5046ass2_project.RecycleView.model.CourseResult
import com.example.a5046ass2_project.databinding.ActivityRecycleViewBinding

class RecycleView : AppCompatActivity() {
    private lateinit var binding: ActivityRecycleViewBinding
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var units: MutableList<CourseResult>
    private lateinit var adapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycleViewBinding.inflate(getLayoutInflater())
        val view = binding.root
        setContentView(view)
        units= CourseResult.initializeResultList()
        adapter = RecyclerViewAdapter(units)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.recyclerView.adapter = adapter
        layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.addButton.setOnClickListener {
            val unit = binding.etUnit.text.toString().trim()
            val mark = binding.etMark.text.toString().trim()
            if (!unit.isEmpty() || !mark.isEmpty()) {
                val markInt = mark.toInt()
                saveData(unit, markInt)
            }
        }
    }
    private fun saveData(unit: String, mark: Int) {
        val courseResult = CourseResult(unit, mark)
        units.add(courseResult)
        adapter.addResults(units)
    }
}