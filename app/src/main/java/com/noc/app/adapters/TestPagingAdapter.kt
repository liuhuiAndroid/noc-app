package com.noc.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.noc.app.data.TestStudent
import com.noc.app.databinding.TestPagingCellBinding

class TestPagingAdapter() :
    PagedListAdapter<TestStudent, TestPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    class TestPagingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ViewHolder(private val binding: TestPagingCellBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: TestStudent) {
            binding.apply {
                this.listener = listener
                this.student = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TestPagingCellBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = getItem(position)
        holder.apply {
            bind(onCreateListener(student!!.id), student)
            itemView.tag = student
        }
    }

    /**
     * Holder的点击事件
     */
    private fun onCreateListener(id: Int): View.OnClickListener {
        return View.OnClickListener {
            // TODO
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TestStudent>() {

            override fun areItemsTheSame(oldItem: TestStudent, newItem: TestStudent) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TestStudent, newItem: TestStudent) =
                oldItem.name == newItem.name

        }
    }


}