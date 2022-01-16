package com.educational.assemblyapp.presentation.mainMenu;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.educational.assemblyapp.databinding.ItemProjectBinding
import com.educational.assemblyapp.domain.entity.Video
import com.educational.assemblyapp.presentation.common.setImageUrl

class MainMenuAdapter(
    private val onVideoClicked: (Video) -> Unit
) : ListAdapter<Video, MainMenuAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
                ItemProjectBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val item = getItem(position)
            itemProjectName.text = item.title
            itemProjectPoster.setImageUrl(item.previewUrl)
            downloadButton.setOnClickListener {
                onVideoClicked(item)
            }
        }
    }

    class ViewHolder(val binding: ItemProjectBinding) : RecyclerView.ViewHolder(binding.root)
}
