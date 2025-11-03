package com.example.lab1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.Adapter.ViewHolder
import com.example.lab1.databinding.ItemBinding
import javax.security.auth.Subject

class Adapter(private val subject: List<String>) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemCount(): Int {
        return subject.size
    }

    private val myList: MutableList<Int> = MutableList(subject.size) { -1 }

    fun bind(position: Int){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }


    inner class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.textView.text = subject[position]

            val grade = myList[position]
            binding.radioGroup.setOnCheckedChangeListener(null)

            when (grade) {
                1 -> binding.radioGroup.check(R.id.radioButton)
                2 -> binding.radioGroup.check(R.id.radioButton2)
                3 -> binding.radioGroup.check(R.id.radioButton3)
                4 -> binding.radioGroup.check(R.id.radioButton4)
                5 -> binding.radioGroup.check(R.id.radioButton5)
                else -> binding.radioGroup.clearCheck()
            }

            binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val selected = when (checkedId) {
                    R.id.radioButton -> 1
                    R.id.radioButton2 -> 2
                    R.id.radioButton3 -> 3
                    R.id.radioButton4 -> 4
                    R.id.radioButton5 -> 5
                    else -> 0
                }
                val currentpos = bindingAdapterPosition
                if (currentpos != RecyclerView.NO_POSITION) {
                    myList[currentpos] = selected
                }
            }
        }
    }
    fun getList(): List<Int> {
        return myList
    }
}