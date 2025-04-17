package com.example.iamcalmapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.iamcalmapp.R
import com.example.iamcalmapp.data.Category
import com.example.iamcalmapp.databinding.CategoryCardItemBinding
import com.example.iamcalmapp.fragments.VideoLibraryFragment

class CategoryAdapter(private val categoryList: List<Category>): RecyclerView.Adapter<CategoryAdapter.CardViewHolder>() {
    inner class CardViewHolder(val binding: CategoryCardItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val binding= CategoryCardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
        val item=categoryList[position]
        holder.binding.titleText.text=item.name
        holder.binding.root.setOnClickListener {
            val fragment = VideoLibraryFragment()
            val bundle = Bundle().apply {
                putString("video", item.video)
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
        return categoryList.size
    }


}