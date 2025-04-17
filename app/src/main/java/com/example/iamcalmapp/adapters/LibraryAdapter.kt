package com.example.iamcalmapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.iamcalmapp.R
import com.example.iamcalmapp.data.LibraryClass
import com.example.iamcalmapp.databinding.LibraryCardItemBinding
import com.example.iamcalmapp.fragments.CategoryFragment

class LibraryAdapter(private val libraryList: List<LibraryClass>) : RecyclerView.Adapter<LibraryAdapter.CardViewHolder>() {

    inner class CardViewHolder(val binding: LibraryCardItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val binding= LibraryCardItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
        val item=libraryList[position]
        holder.binding.textView.text=item.title
        holder.binding.root.setOnClickListener {
            val fragment = CategoryFragment()
            val bundle = Bundle().apply {
                putParcelableArrayList("category", ArrayList(item.category))
            }
            fragment.arguments = bundle
            val activity = it.context as AppCompatActivity

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return libraryList.size
    }
}