package com.example.a5046ass2_project.RecycleView.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a5046ass2_project.RecycleView.model.CourseResult
import com.example.a5046ass2_project.databinding.RvLayoutBinding

class RecyclerViewAdapter(results: MutableList<CourseResult>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private var courseResults: MutableList<CourseResult>
    init {
        courseResults = results
    }
    //creates a new viewholder constructed with a new View, inflated from a layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        // Inflate the view from an XML layout file
        val binding: RvLayoutBinding =
            RvLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // construct the viewholder with the view binding
        return ViewHolder(binding)
    }
    // binds the view holder created with data that will be displayed
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val result: CourseResult = courseResults[position]
        // viewholder binding with its data at the specified position
        viewHolder.binding.tvRvunit.setText(result.unit)
        viewHolder.binding.tvRvmark.setText(Integer.toString(result.mark))
        viewHolder.binding.ivItemDelete.setOnClickListener(
            View.OnClickListener
        {
            courseResults.remove(result)
            notifyDataSetChanged();
        })
    }

    override fun getItemCount() = courseResults.size


    fun addResults(results: MutableList<CourseResult>) {
        courseResults = results
        notifyDataSetChanged()
    }

    class ViewHolder(binding: RvLayoutBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        val binding: RvLayoutBinding
        init {
            this.binding = binding
        }
    }
}
