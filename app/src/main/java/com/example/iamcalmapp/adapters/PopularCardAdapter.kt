package com.example.iamcalmapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.iamcalmapp.R
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.iamcalmapp.data.PopularPractices
import com.example.iamcalmapp.databinding.PopularCardItemBinding
import com.example.iamcalmapp.fragments.CategoryFragment
import com.example.iamcalmapp.fragments.PopularFragment

class PopularCardAdapter(private val popularPractices: List<PopularPractices>): RecyclerView.Adapter<PopularCardAdapter.CardViewHolder>() {

    inner class CardViewHolder(val binding: PopularCardItemBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        val binding= PopularCardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val itemCard=popularPractices[position]
        holder.binding.title.text=itemCard.title
        holder.binding.description.text=itemCard.desc
        holder.binding.root.setOnClickListener {
            val fragment = PopularFragment()
            val bundle = Bundle().apply {
                putString("url", itemCard.videoUrl)
            }
            fragment.arguments = bundle
            val activity = it.context as AppCompatActivity

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()

        }
    }

    override fun getItemCount():Int = popularPractices.size
}