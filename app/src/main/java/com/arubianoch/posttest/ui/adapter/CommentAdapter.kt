package com.arubianoch.posttest.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arubianoch.posttest.R
import com.arubianoch.posttest.data.network.response.Comment
import kotlinx.android.synthetic.main.view_item_comment.view.*

/**
 * @author Andres Rubiano Del Chiaro
 */
class CommentAdapter(
    private val context: Context
) : RecyclerView.Adapter<CommentAdapter.Holder>() {

    private var data = ArrayList<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item_comment, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindView(data[position])
    }

    fun loadItems(posts: List<Comment>) {
        cleanData()
        data.addAll(posts)
        notifyDataSetChanged()
    }

    fun cleanData() {
        if (data.isNotEmpty()) {
            data.clear()
        }
        notifyDataSetChanged()
    }

    class Holder(
        itemView: View?) :
        RecyclerView.ViewHolder(itemView!!) {

        fun bindView(item: Comment?) {
            item?.let { comment ->
                itemView.bodyTextView.text = comment.body
            }
        }
    }
}