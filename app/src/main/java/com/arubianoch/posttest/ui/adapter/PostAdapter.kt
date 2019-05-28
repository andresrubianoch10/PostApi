package com.arubianoch.posttest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arubianoch.posttest.R
import com.arubianoch.posttest.data.network.response.Post
import kotlinx.android.synthetic.main.view_post.view.*

/**
 * @author Andres Rubiano Del Chiaro
 */
class PostAdapter(
    private val context: Context,
    private val onItemClick: OnItemClickListener? = null
) : RecyclerView.Adapter<PostAdapter.Holder>() {

    var data = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_post, parent, false)
        return Holder(view, onItemClick, data)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(data[position])
    }

    fun cleanData() {
        if (data.isNotEmpty()) {
            data.clear()
        }
        notifyDataSetChanged()
    }

    fun loadPosts(posts: List<Post>) {
        cleanData()
        data.addAll(posts)
        notifyDataSetChanged()
    }

    fun getPostAt(position: Int) = data[position]

    class Holder(
        itemView: View?,
        private val onItemClick: OnItemClickListener? = null,
        data: ArrayList<Post>
    ) :
        RecyclerView.ViewHolder(itemView!!) {

        init {
            itemView!!.setOnClickListener {
                onItemClick?.onItemClicked(data[adapterPosition])
            }
        }

        fun bindView(itemPost: Post?) {
            itemPost?.let { post ->
                itemView.textView_description.text = post.title
                itemView.textView_description.visibility = if (post.title.isNotEmpty()) View.VISIBLE else View.GONE
                if (adapterPosition < 20) {
                    if (!post.isRead) {
                        itemView.imageview_status.visibility = View.VISIBLE
                    } else {
                        itemView.imageview_status.visibility = View.GONE
                    }
                } else {
                    itemView.imageview_status.visibility = View.GONE
                }

                if (!post.isFavorite) {
                    itemView.postIsFavorite.visibility = View.GONE
                } else {
                    itemView.postIsFavorite.visibility = View.VISIBLE
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(itemView: Post)
    }
}