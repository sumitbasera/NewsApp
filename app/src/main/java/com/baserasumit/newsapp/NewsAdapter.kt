package com.baserasumit.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.title_view.view.*
import java.util.zip.Inflater

class NewsAdapter( private val listener : onClicked) : RecyclerView.Adapter<NewsViewHolder>() {
    private val item : ArrayList<news> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.title_view,parent,false)
        val holder = NewsViewHolder(view)
        view.setOnClickListener{
          listener.clickHandel(item[holder.adapterPosition])
        }
       return holder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       val currentItem = item[position]
        holder.titleView.text = currentItem.title
        holder.author.text = currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.imageurl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return item.size
    }
    fun updateNews (updatedNews:ArrayList<news>){
        item.clear()
        item.addAll(updatedNews)
        notifyDataSetChanged()
    }
}



class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.titleOfNews)
    val imageView: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.authorOfNews)
}
interface onClicked {
    fun clickHandel(item: news)
}