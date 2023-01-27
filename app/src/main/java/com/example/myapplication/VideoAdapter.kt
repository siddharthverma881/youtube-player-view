package com.example.myapplication

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Utils.YOUTUBE_API_KEY
import com.example.myapplication.databinding.ItemVideoBinding
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView

class VideoAdapter(private val videoList : ArrayList<Data>, private val listener : YoutubeVideo, private val context : Context) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder =
        VideoViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context),parent, false))

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videoList[position], listener, position, context)
//        holder.clVideo.setOnClickListener { listener.onVideoSelected(position) }
//        holder.clVideoParent.setOnClickListener { listener.onVideoSelected(position) }
//        holder.ytPlayer.setOnClickListener { listener.onVideoSelected(position) }
    }

    override fun getItemCount() = videoList.size

    class VideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

//        val clVideo = binding.clVideo
//        val ytPlayer = binding.ytPlayer
//        val clVideoParent = binding.clVideoParent

        fun bind(data : Data, listener: YoutubeVideo, position: Int, context: Context){
            binding.tvVideoTitle.text = data.title
            Glide.with(context).load(data.thumbnail).into(binding.ivThumbnail)
            binding.clVideo.visibility = View.INVISIBLE
            binding.ivThumbnail.visibility = View.VISIBLE
            binding.clVideoParent.setOnClickListener { listener.onVideoSelected(position) }
            binding.clVideo.setOnClickListener { listener.onVideoSelected(position) }
            binding.ytPlayer.setOnClickListener { listener.onVideoSelected(position) }

        }
    }

    interface YoutubeVideo {
        fun onVideoSelected(position: Int)
    }

}