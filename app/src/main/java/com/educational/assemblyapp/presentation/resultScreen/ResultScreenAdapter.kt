package com.educational.assemblyapp.presentation.resultScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.educational.assemblyapp.presentation.resultScreen.ResultScreenAdapter.ViewHolder
import com.educational.assemblyapp.databinding.ItemResultBinding
import com.educational.assemblyapp.domain.entity.VideoSearch
import com.educational.assemblyapp.presentation.common.setImageUrl

class ResultScreenAdapter(
    private val onButtonClicked: (VideoSearch) -> Unit
) : ListAdapter<VideoSearch, ViewHolder>(
    object : DiffUtil.ItemCallback<VideoSearch>() {
        override fun areItemsTheSame(oldItem: VideoSearch, newItem: VideoSearch): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: VideoSearch, newItem: VideoSearch): Boolean =
            oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val item = getItem(position)
            itemResultPoster.setImageUrl(item.thumbnailUrl)
            itemResultTitle.text = item.title
            root.setOnClickListener { onButtonClicked(item) }
        }
    }

    class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root)
}

